package com.flipper.controller;

import com.flipper.model.Flip;
import com.flipper.model.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.nio.file.Path;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TradePersisterControllerTest {
    private TradePersisterController tradePersisterController;
    private List<Transaction> buys;
    private List<Transaction> sells;
    private List<Flip> flips;
    private String testFilePath;
    private Transaction buy;
    private Transaction sell;
    private Flip flip;

    @Before
    public void setUp() throws IOException {
        buys = new ArrayList<>();
        sells = new ArrayList<>();
        flips = new ArrayList<>();
        Path currentRelativePath = Paths.get("");
        testFilePath = currentRelativePath.toAbsolutePath().toString() + "\\src\\test\\java\\com\\flipper\\test-result-files";
        tradePersisterController = new TradePersisterController(testFilePath);

        buy = new Transaction(
            1,
            1,
            1,
            Instant.now(),
            true
        );
        sell = new Transaction(
            2,
            2,
            2,
            Instant.now(),
            false
        );
        flip = new Flip(buy, sell);

        buys.add(buy);
        sells.add(sell);
        flips.add(flip);
    }

    @After
    public void tearDown() throws IOException {
        // Delete any generated test-result-files
    }

    @Test
    public void testSaveTransactionsSuccessfullySavesTransactions() {
        boolean isSaved = tradePersisterController.saveTransactions(buys, sells, flips);
        assertTrue(isSaved);
        // Ensure the three files have been created: flipper-buys.json, flipper-sells.json, flipper-flips.json
    }

    @Test
    public void testLoadFlipsReturnsListOfFlips() throws IOException {
        // Create test file to load
        tradePersisterController.saveJson(flips, TradePersisterController.FLIPS_JSON_FILE);
        List<Flip> returnedFlips = tradePersisterController.loadFlips();
        assertEquals(returnedFlips.get(0).getBuy().getItemId(), flip.getBuy().getItemId());
    }

    @Test
    public void testLoadBuysReturnsListOfBuys() throws IOException {
        // Create test file to load
        tradePersisterController.saveJson(buys, TradePersisterController.BUYS_JSON_FILE);
        List<Transaction> returnedBuys = tradePersisterController.loadBuys();
        assertEquals(returnedBuys.get(0).getItemId(), buy.getItemId());
    }

    @Test
    public void testLoadSellsReturnsListOfSells() throws IOException {
        // Create test file to load
        tradePersisterController.saveJson(sells, TradePersisterController.SELLS_JSON_FILE);
        List<Transaction> returnedSells = tradePersisterController.loadSells();
        assertEquals(returnedSells.get(0).getItemId(), sell.getItemId());
    }
}
