package com.flipper.views.flips;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.math.BigDecimal;
import java.awt.Font;

import com.flipper.helpers.UiUtilities;

import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.FontManager;

public class FlipPage extends JPanel {
    private JPanel container;
    private JLabel totalProfitValueLabel;

    public FlipPage() {
        SwingUtilities.invokeLater(() -> {
            this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
            this.setBackground(ColorScheme.DARK_GRAY_COLOR);
        });
    }

    public void addFlipPanel(FlipPanel flipPanel) {
        container.add(flipPanel);
    }

    private void constructTotalProfit(double totalProfit) {
        JPanel container = new JPanel();
        container.setLayout(new BorderLayout());
        JPanel totalProfitContainer = new JPanel(new GridLayout(2, 1));

        JLabel totalProfitLabel = new JLabel("Total Profit");
        totalProfitLabel.setFont(new Font(FontManager.getRunescapeBoldFont().getName(),
                FontManager.getRunescapeBoldFont().getStyle(), 16));
        totalProfitLabel.setHorizontalAlignment(JLabel.CENTER);
        totalProfitLabel.setForeground(Color.WHITE);

        totalProfitValueLabel = new JLabel("0");
        totalProfitValueLabel.setFont(
                new Font(FontManager.getRunescapeFont().getName(), FontManager.getRunescapeFont().getStyle(), 24));
        totalProfitValueLabel.setHorizontalAlignment(JLabel.CENTER);
        Color totalProfitColor = ColorScheme.GRAND_EXCHANGE_ALCH;
        totalProfitValueLabel.setForeground(totalProfitColor);

        totalProfitContainer.add(totalProfitLabel);
        totalProfitContainer.add(totalProfitValueLabel);
        totalProfitContainer.setBorder(UiUtilities.ITEM_INFO_BORDER);

        container.add(totalProfitContainer);
        container.setBorder(new EmptyBorder(0, 0, 3, 0));
        this.add(container, BorderLayout.WEST);
    }

    public void setTotalProfit(double totalProfit) {
        BigDecimal totalProfitBG = new BigDecimal(totalProfit);
        totalProfitValueLabel.setText(String.valueOf(totalProfitBG.longValue()));
    }

    public void build() {
        constructTotalProfit(0);
        container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));
        container.setBackground(ColorScheme.DARK_GRAY_COLOR);

        JScrollPane scrollPane = new JScrollPane(container);
        scrollPane.setBackground(ColorScheme.LIGHT_GRAY_COLOR);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        this.add(container, BorderLayout.WEST);
    }
}