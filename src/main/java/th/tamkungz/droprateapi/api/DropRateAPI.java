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

package th.tamkungz.droprateapi.api;

import net.minecraft.item.Item;
import net.minecraft.entity.EntityType;
import java.util.*;

public class DropRateAPI {
    // Change Map to store a List of DropData
    private static final Map<EntityType<?>, List<DropData>> dropRules = new HashMap<>();

    // Keep track of mobs registered from the config file
    private static final Set<EntityType<?>> configRegisteredMobs = new HashSet<>();

    // Add DropData to the list instead of replacing
    public static void registerDrop(EntityType<?> mob, int rate, List<Item> items, int minAmount, int maxAmount) {
        DropData newDrop = new DropData(rate, items, minAmount, maxAmount);
        dropRules.computeIfAbsent(mob, k -> new ArrayList<>()).add(newDrop);

        // Track mobs added via the config file (only if it's a config-based registration)
        configRegisteredMobs.add(mob);
        
        System.out.println("Registered Drop: Mob=" + mob + ", Rate=" + rate + 
            ", Items=" + items + ", Amount=" + minAmount + "-" + maxAmount);
    }

    // Return a list of DropData instead of a single entry
    public static List<DropData> getDropData(EntityType<?> mob) {
        return dropRules.getOrDefault(mob, Collections.emptyList());
    }

    // Inner class to store drop data
    public static class DropData {
        public final int rate;
        public final List<Item> items;
        public final int minAmount;
        public final int maxAmount;

        public DropData(int rate, List<Item> items, int minAmount, int maxAmount) {
            this.rate = rate;
            this.items = items;
            this.minAmount = minAmount;
            this.maxAmount = maxAmount;
        }
    }

    public static void clearConfigBasedDrops() {
        for (EntityType<?> mob : configRegisteredMobs) {
            dropRules.remove(mob);
            System.out.println("[DropRate] Removed config-based drops for: " + mob);
        }
        configRegisteredMobs.clear(); // Reset tracking for the next config load
    }    

    // Remove DropData from the list
    public static void removeDrop(EntityType<?> mob, Item item) {
        List<DropData> dropList = dropRules.get(mob);
        if (dropList != null) {
            dropList.removeIf(dropData -> dropData.items.contains(item));
            System.out.println("Removed Drop: " + item + " from " + mob);
        }
    }    
}