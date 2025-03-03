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

package th.tamkungz.droprateapi.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.EntityType;
import net.minecraftforge.registries.ForgeRegistries;
import th.tamkungz.droprateapi.api.DropRateAPI;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DropRateConfigLoader {
    private static final Path CONFIG_PATH = Paths.get("config/droprate.config.json");

    public static void loadConfig() {
        if (!Files.exists(CONFIG_PATH)) {
            createExampleConfig();
            return;
        }
    
        try (Reader reader = Files.newBufferedReader(CONFIG_PATH)) {
            // Remove ONLY the drops that were registered from the config file, not other mods' drops
            DropRateAPI.clearConfigBasedDrops();

            JsonObject json = new JsonParser().parse(reader).getAsJsonObject();
            JsonArray configArray = json.getAsJsonArray("config");
    
            if (configArray == null || configArray.size() == 0) {
                System.err.println("[DropRate] WARNING: Config file is empty or missing 'config' section.");
                return;
            }
    
            for (JsonElement element : configArray) {
                JsonObject obj = element.getAsJsonObject();
                String mobId = obj.get("mob").getAsString();
                int rate = obj.get("rate").getAsInt();
                JsonArray itemArray = obj.getAsJsonArray("item");
    
                // Parse item amount (default to 1 if missing)
                int minAmount = 1;
                int maxAmount = 1;
                if (obj.has("item_amount")) {
                    JsonElement amountElement = obj.get("item_amount");
                    if (amountElement.isJsonObject()) {
                        JsonObject amountObj = amountElement.getAsJsonObject();
                        minAmount = amountObj.get("min_amount").getAsInt();
                        maxAmount = amountObj.get("max_amount").getAsInt();
                    } else {
                        minAmount = amountElement.getAsInt();
                        maxAmount = minAmount;
                    }
                }
    
                // Ensure valid amounts
                if (minAmount > maxAmount) {
                    int temp = minAmount;
                    minAmount = maxAmount;
                    maxAmount = temp;
                }
                minAmount = Math.max(1, Math.min(minAmount, 64));
                maxAmount = Math.max(1, Math.min(maxAmount, 64));
    
                // Validate EntityType
                EntityType<?> mob = ForgeRegistries.ENTITIES.getValue(new ResourceLocation(mobId));
                if (mob == null) {
                    System.err.println("[DropRate] ERROR: Invalid mob ID '" + mobId + "'. Skipping entry.");
                    continue;
                }
    
                // Validate Items
                List<Item> items = new ArrayList<>();
                for (JsonElement itemElement : itemArray) {
                    String itemId = itemElement.getAsString();
                    Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemId));
                    if (item == null) {
                        System.err.println("[DropRate] ERROR: Invalid item ID '" + itemId + "' for mob '" + mobId + "'. Skipping item.");
                    } else {
                        items.add(item);
                    }
                }
    
                if (!items.isEmpty()) {
                    DropRateAPI.registerDrop(mob, rate, items, minAmount, maxAmount);
                    System.out.println("[DropRate] Loaded config: Mob=" + mobId + ", Rate=" + rate + ", Items=" + items + ", Amount=" + minAmount + "-" + maxAmount);
                } else {
                    System.err.println("[DropRate] WARNING: No valid items found for mob '" + mobId + "'. Skipping drop registration.");
                }
            }
    
        } catch (IOException e) {
            System.err.println("[DropRate] ERROR: Failed to read the config file!");
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            System.err.println("[DropRate] ERROR: Invalid JSON syntax in the config file!");
            e.printStackTrace();
        }
    }


    private static void createExampleConfig() {
        JsonObject exampleConfig = new JsonObject();
        JsonArray configArray = new JsonArray();
    
        // Example 1: Zombie drops apples (80% chance, 1 apple)
        JsonObject exampleEntry1 = new JsonObject();
        exampleEntry1.addProperty("mob", "minecraft:zombie");
        exampleEntry1.addProperty("rate", 80);
        JsonArray items1 = new JsonArray();
        items1.add("minecraft:apple");
        exampleEntry1.add("item", items1);
    
        // Example 2: Zombie drops nuggets (100% chance, 1 nugget)
        JsonObject exampleEntry2 = new JsonObject();
        exampleEntry2.addProperty("mob", "minecraft:zombie");
        exampleEntry2.addProperty("rate", 100);
        JsonArray items2 = new JsonArray();
        items2.add("minecraft:gold_nugget");
        items2.add("minecraft:iron_nugget");
        exampleEntry2.add("item", items2);
        exampleEntry2.addProperty("item_amount", 1);
    
        // Example 3: Chicken drops stone (50% chance, 4 stones)
        JsonObject exampleEntry3 = new JsonObject();
        exampleEntry3.addProperty("mob", "minecraft:chicken");
        exampleEntry3.addProperty("rate", 50);
        JsonArray items3 = new JsonArray();
        items3.add("minecraft:stone");
        exampleEntry3.add("item", items3);
        exampleEntry3.addProperty("item_amount", 4);
    
        // Example 4: Chicken drops arrows (90% chance, 1-7 arrows)
        JsonObject exampleEntry4 = new JsonObject();
        exampleEntry4.addProperty("mob", "minecraft:chicken");
        exampleEntry4.addProperty("rate", 90);
        JsonArray items4 = new JsonArray();
        items4.add("minecraft:arrow");
        exampleEntry4.add("item", items4);
        JsonObject amountObj = new JsonObject();
        amountObj.addProperty("min_amount", 1);
        amountObj.addProperty("max_amount", 7);
        exampleEntry4.add("item_amount", amountObj);
    
        // Add all examples to the config array
        configArray.add(exampleEntry1);
        configArray.add(exampleEntry2);
        configArray.add(exampleEntry3);
        configArray.add(exampleEntry4);
        exampleConfig.add("config", configArray);
    
        // Convert JSON to a string
        String jsonString = exampleConfig.toString();
    
        // Manually format the JSON string with comments
        StringBuilder commentedConfig = new StringBuilder();
        commentedConfig.append("{\n")
            .append("    // Example configuration for DropRate mod\n")
            .append("    \"config\": [\n")
            .append("        // Uncomment the entries below to enable them\n")
            .append("        // \n")
            .append("        // Example 1: Zombie drops apples (80% chance, 1 apple)\n")
            .append("        // {\n")
            .append("        //     \"mob\": \"minecraft:zombie\",\n")
            .append("        //     \"rate\": 80,\n")
            .append("        //     \"item\": [\"minecraft:apple\"]\n")
            .append("        // },\n")
            .append("        // \n")
            .append("        // Example 2: Zombie drops nuggets (100% chance, 1 nugget)\n")
            .append("        // {\n")
            .append("        //     \"mob\": \"minecraft:zombie\",\n")
            .append("        //     \"rate\": 100,\n")
            .append("        //     \"item\": [\"minecraft:gold_nugget\", \"minecraft:iron_nugget\"],\n")
            .append("        //     \"item_amount\": 1\n")
            .append("        // },\n")
            .append("        // \n")
            .append("        // Example 3: Chicken drops stone (50% chance, 4 stones)\n")
            .append("        // {\n")
            .append("        //     \"mob\": \"minecraft:chicken\",\n")
            .append("        //     \"rate\": 50,\n")
            .append("        //     \"item\": [\"minecraft:stone\"],\n")
            .append("        //     \"item_amount\": 4\n")
            .append("        // },\n")
            .append("        // \n")
            .append("        // Example 4: Chicken drops arrows (90% chance, 1-7 arrows)\n")
            .append("        // {\n")
            .append("        //     \"mob\": \"minecraft:chicken\",\n")
            .append("        //     \"rate\": 90,\n")
            .append("        //     \"item\": [\"minecraft:arrow\"],\n")
            .append("        //     \"item_amount\": {\n")
            .append("        //         \"min_amount\": 1,\n")
            .append("        //         \"max_amount\": 7\n")
            .append("        //     }\n")
            .append("        // }\n")
            .append("    ]\n")
            .append("}");
    
        try {
            Files.write(CONFIG_PATH, commentedConfig.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}