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