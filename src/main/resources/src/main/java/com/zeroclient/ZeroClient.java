
package com.zeroclient;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZeroClient implements ClientModInitializer {

    public static final String MOD_ID = "zeroclient";
    public static final String MOD_NAME = "ZERO CLIENT";
    public static final String VERSION = "1.0.0";

    public static final Logger LOGGER =
            LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {
        LOGGER.info("================================");
        LOGGER.info("       ZERO CLIENT");
        LOGGER.info("       Version: {}", VERSION);
        LOGGER.info("       Minecraft: 1.21.11");
        LOGGER.info("       Purple PvP Edition");
        LOGGER.info("================================");

        LOGGER.info("ZERO CLIENT initialized successfully!");
    }
}
