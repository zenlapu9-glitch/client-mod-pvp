
package com.zeroclient.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ZeroClickGui extends Screen {

    private static final int BACKGROUND = 0xE6090710;
    private static final int PANEL = 0xFF12101D;
    private static final int SIDEBAR = 0xFF0D0A16;
    private static final int PURPLE = 0xFF8B5CF6;
    private static final int WHITE = 0xFFFFFFFF;
    private static final int GRAY = 0xFFA1A1AA;

    private final List<String> categories = List.of(
            "Combat", "Movement", "Render", "Player",
            "HUD", "Utility", "Performance", "Settings"
    );

    private final Map<String, List<String>> modules =
            new LinkedHashMap<>();

    private final Map<String, Boolean> enabled =
            new LinkedHashMap<>();

    private String selectedCategory = "Combat";

    private int panelX;
    private int panelY;
    private int panelWidth = 440;
    private int panelHeight = 290;

    public ZeroClickGui() {
        super(Component.literal("ZERO CLIENT"));

        modules.put("Combat", List.of(
                "CPS Counter",
                "Attack Indicator",
                "Custom Crosshair",
                "Hit Color",
                "Damage Indicator",
                "Target HUD",
                "Combo Counter",
                "Armor Status"
        ));

        modules.put("Movement", List.of(
                "Sprint Toggle",
                "Toggle Sneak",
                "Keystrokes",
                "Speed Display",
                "Jump Indicator",
                "Direction HUD"
        ));

        modules.put("Render", List.of(
                "FPS Display",
                "Ping Display",
                "Custom Nametags",
                "Particle Customizer",
                "Fullbright",
                "Zoom"
        ));

        modules.put("Player", List.of(
                "Inventory Status",
                "Durability Display",
                "Potion Effects",
                "Health Display",
                "Session Information"
        ));

        modules.put("HUD", List.of(
                "Coordinates",
                "Armor HUD",
                "Potion HUD",
                "Target Information",
                "Session Timer",
                "Clock"
        ));

        modules.put("Utility", List.of(
                "Notifications",
                "Configuration Manager",
                "Keybind Manager"
        ));

        modules.put("Performance", List.of(
                "Performance Monitor",
                "FPS Information"
        ));

        modules.put("Settings", List.of(
                "GUI Settings",
                "Theme Settings",
                "Reset Configuration"
        ));

        for (List<String> categoryModules : modules.values()) {
            for (String module : categoryModules) {
                enabled.put(module, false);
            }
        }
    }

    @Override
    protected void init() {
        super.init();

        panelX = (this.width - panelWidth) / 2;
        panelY = (this.height - panelHeight) / 2;
    }

    @Override
    public void render(
            GuiGraphics graphics,
            int mouseX,
            int mouseY,
            float delta
    ) {
        graphics.fill(
                0, 0, this.width, this.height,
                BACKGROUND
        );

        graphics.fill(
                panelX, panelY,
                panelX + panelWidth,
                panelY + panelHeight,
                PANEL
        );

        // Sidebar
        graphics.fill(
                panelX, panelY,
                panelX + 112,
                panelY + panelHeight,
                SIDEBAR
        );

        // Top accent
        graphics.fill(
                panelX, panelY,
                panelX + panelWidth,
                panelY + 3,
                PURPLE
        );

        // Branding
        graphics.drawString(
                this.font,
                Component.literal("ZERO CLIENT"),
                panelX + 15,
                panelY + 15,
                PURPLE
        );

        graphics.drawString(
                this.font,
                Component.literal("1.0.0"),
                panelX + 15,
                panelY + 29,
                GRAY
        );

        // Category navigation
        for (int i = 0; i < categories.size(); i++) {
            String category = categories.get(i);

            int x = panelX + 8;
            int y = panelY + 55 + (i * 27);

            boolean selected =
                    category.equals(selectedCategory);

            if (selected) {
                graphics.fill(
                        x, y - 4,
                        x + 96, y + 19,
                        0xFF302044
                );

                graphics.fill(
                        x, y - 4,
                        x + 3, y + 19,
                        PURPLE
                );
            }

            graphics.drawString(
                    this.font,
                    Component.literal(category),
                    x + 10,
                    y + 3,
                    selected ? WHITE : GRAY
            );
        }

        // Main content title
        graphics.drawString(
                this.font,
                Component.literal(selectedCategory),
                panelX + 130,
                panelY + 17,
                WHITE
        );

        graphics.drawString(
                this.font,
                Component.literal("Manage your modules"),
                panelX + 130,
                panelY + 32,
                GRAY
        );

        // Module list
        List<String> categoryModules =
                modules.get(selectedCategory);

        for (int i = 0; i < categoryModules.size(); i++) {
            String module = categoryModules.get(i);

            int x = panelX + 128;
            int y = panelY + 58 + (i * 27);

            boolean active = enabled.get(module);

            graphics.fill(
                    x, y,
                    panelX + panelWidth - 12,
                    y + 22,
                    0xFF201A2D
            );

            graphics.drawString(
                    this.font,
                    Component.literal(module),
                    x + 9,
                    y + 7,
                    WHITE
            );

            // Module toggle
            int toggleColor = active
                    ? PURPLE
                    : 0xFF48404F;

            graphics.fill(
                    panelX + panelWidth - 44,
                    y + 5,
                    panelX + panelWidth - 20,
                    y + 17,
                    toggleColor
            );

            graphics.drawString(
                    this.font,
                    Component.literal(active ? "ON" : "OFF"),
                    panelX + panelWidth - 42,
                    y + 7,
                    WHITE
            );
        }

        // Footer
        graphics.drawString(
                this.font,
                Component.literal("RIGHT SHIFT  |  ZERO CLIENT"),
                panelX + 130,
                panelY + panelHeight - 20,
                GRAY
        );

        super.render(graphics, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(
            double mouseX,
            double mouseY,
            int button
    ) {
        if (button == 0) {

            // Category selection
            for (int i = 0; i < categories.size(); i++) {
                int x = panelX + 8;
                int y = panelY + 55 + (i * 27);

                if (mouseX >= x && mouseX <= x + 96
                        && mouseY >= y - 4
                        && mouseY <= y + 19) {

                    selectedCategory = categories.get(i);
                    return true;
                }
            }

            // Module toggles
            List<String> categoryModules =
                    modules.get(selectedCategory);

            for (int i = 0; i < categoryModules.size(); i++) {
                String module = categoryModules.get(i);

                int x = panelX + 128;
                int y = panelY + 58 + (i * 27);

                if (mouseX >= x
                        && mouseX <= panelX + panelWidth - 12
                        && mouseY >= y
                        && mouseY <= y + 22) {

                    enabled.put(
                            module,
                            !enabled.get(module)
                    );

                    return true;
                }
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
  }
