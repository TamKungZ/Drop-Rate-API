/*
 * Drop Rate API Mod - Custom Mob Drops & API
 * Copyright (C) 2024 TamKungZ_
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
*/

package th.tamkungz.droprateapi;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import th.tamkungz.droprateapi.config.DropRateConfigLoader;
import th.tamkungz.droprateapi.handlers.MobDeathHandler;

@Mod("droprateapi")
public class DropRateMain {
    public static final String MOD_ID = "droprateapi";
    private static final Logger LOGGER = LogManager.getLogger();

    public DropRateMain() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        // Register the event handler
        MinecraftForge.EVENT_BUS.register(new MobDeathHandler());
    }

    private void setup(final FMLCommonSetupEvent event) {
        LOGGER.info("Setup complete for DropRate Mod!");
        DropRateConfigLoader.loadConfig();
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        LOGGER.info("Client setup complete!");
    }
}