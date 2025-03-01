package th.tamkungz.droprateapi.api;

import net.minecraft.item.Item;
import net.minecraft.entity.EntityType;
import java.util.*;

public class DropRateAPI {
    // Change Map to store a List of DropData
    private static final Map<EntityType<?>, List<DropData>> dropRules = new HashMap<>();

    // Add DropData to the list instead of replacing
    public static void registerDrop(EntityType<?> mob, int rate, List<Item> items, int minAmount, int maxAmount) {
        DropData newDrop = new DropData(rate, items, minAmount, maxAmount);
        dropRules.computeIfAbsent(mob, k -> new ArrayList<>()).add(newDrop);
        
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

    // Remove DropData from the list
    public static void removeDrop(EntityType<?> mob, Item item) {
        List<DropData> dropList = dropRules.get(mob);
        if (dropList != null) {
            dropList.removeIf(dropData -> dropData.items.contains(item));
            System.out.println("Removed Drop: " + item + " from " + mob);
        }
    }    
}