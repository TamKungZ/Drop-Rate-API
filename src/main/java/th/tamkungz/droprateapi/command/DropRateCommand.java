package th.tamkungz.droprateapi.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import th.tamkungz.droprateapi.config.DropRateConfigLoader;
import net.minecraft.util.text.StringTextComponent;

@Mod.EventBusSubscriber
public class DropRateCommand {
    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSource> dispatcher = event.getDispatcher();
        dispatcher.register(Commands.literal("droprate_reload")
            .executes(context -> {
                DropRateConfigLoader.loadConfig();
                context.getSource().sendSuccess(new StringTextComponent("DropRate Config Reloaded!"), true);
                return Command.SINGLE_SUCCESS;
            }));
    }
}
