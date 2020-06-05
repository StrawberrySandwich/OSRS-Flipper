package com.flipper.controller;

import com.flipper.model.Flip;
import com.flipper.model.Transaction;
import net.runelite.client.RuneLite;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import com.google.gson.reflect.TypeToken;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.RuneLite;

/**
 * Read/Writes information to json file for storage
 */
@Slf4j
public class TradePersisterController {
    public static final File PARENT_DIRECTORY = new File(RuneLite.RUNELITE_DIR, "flipper");
    public File directory;
    public static final String SELLS_JSON_FILE = "flipper-sells.json";
    public static final String BUYS_JSON_FILE = "flipper-buys.json";
    public static final String FLIPS_JSON_FILE = "flipper-flips.json";

    public TradePersisterController(String directoryPath) throws IOException {
        this.directory = new File(directoryPath);
        createDirectory(this.directory);
    }

    public TradePersisterController() throws IOException {
        this.directory = PARENT_DIRECTORY;
        createDirectory(PARENT_DIRECTORY);
    }

    private void generateFileIfDoesNotExist(File file) throws IOException {
        if(!file.exists()) {
            if (!file.createNewFile()) {
                log.info("Failed to generate file {} ", file.getPath());
            }
        }
    }

    private void createDirectory(File directory) throws IOException {
        if (!directory.exists()) {
            log.info("Creating flipper directory");
            if (!directory.mkdir()) {
                throw new IOException("unable to create parent directory!");
            }
        }
    }

    public void saveJson(List<?> list, String filename) throws IOException {
        final Gson gson = new Gson();
        File file = new File(this.directory, filename);
        generateFileIfDoesNotExist(file);
        final String json = gson.toJson(list);
        Files.write(file.toPath(), json.getBytes());
    }

    private String getFileContent(String filename) throws IOException {
        Path filePath = Paths.get(this.directory + "\\" + filename);
        byte[] fileBytes = Files.readAllBytes(filePath);
        return new String(fileBytes);
    }

    /**
     * Saves transaction to flipper's json
     * @param buys list of buys
     * @param sells list of sells
     * @param flips list of flips
     * @return whether transactions has been successfully saved
     */
    public boolean saveTransactions(List<Transaction> buys, List<Transaction> sells, List<Flip> flips) {
        try {
            // Buys
            saveJson(buys, BUYS_JSON_FILE);
            // Sells
            saveJson(sells, SELLS_JSON_FILE);
            // Flips
            saveJson(flips, FLIPS_JSON_FILE);
        } catch (Exception error) {
            log.info("Failed to save some transactions {}", error.toString());
            return false;
        }

        return true;
    }

    public List<Flip> loadFlips() throws IOException {
        Gson gson = new Gson();
        String jsonString = getFileContent(FLIPS_JSON_FILE);
        Type type = new TypeToken<List<Flip>>(){}.getType();
        return gson.fromJson(jsonString, type);
    }

    public List<Transaction> loadBuys() throws IOException {
        Gson gson = new Gson();
        String jsonString = getFileContent(BUYS_JSON_FILE);
        Type type = new TypeToken<List<Transaction>>(){}.getType();
        return gson.fromJson(jsonString, type);
    }

    public List<Transaction> loadSells() throws IOException {
        Gson gson = new Gson();
        String jsonString = getFileContent(SELLS_JSON_FILE);
        Type type = new TypeToken<List<Transaction>>(){}.getType();
        return gson.fromJson(jsonString, type);
    }
}
