package com.flipper.controllers;

import net.runelite.client.util.ImageUtil;
import net.runelite.client.ui.NavigationButton;

import com.flipper.views.TabManager;
import com.flipper.views.buys.BuysPanel;
import com.flipper.views.sells.SellsPanel;

import net.runelite.client.ui.ClientToolbar;

public class TabManagerController {
    private TabManager tabManager;
    private NavigationButton navButton;
    private ClientToolbar clientToolbar;

    public TabManagerController(
        ClientToolbar clientToolbar,
        BuysPanel buysPanel,
        SellsPanel sellsPanel
    ) {
        this.clientToolbar = clientToolbar;    
        tabManager = new TabManager(buysPanel, sellsPanel);
        setUpNavigationButton();
    }

    private void setUpNavigationButton() {
        navButton = NavigationButton
                .builder()
                .tooltip("Flipper")
                .icon(ImageUtil.getResourceStreamFromClass(getClass(), "/flipper_nav_button.png")).priority(1)
                .panel(tabManager)
                .build();
        clientToolbar.addNavigation(navButton);
    }
}