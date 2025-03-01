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
