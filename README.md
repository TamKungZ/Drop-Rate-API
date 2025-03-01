# DropRate - Custom Mob Drops & API

DropRate is a powerful Minecraft Forge mod that enables custom mob drop configurations. This guide provides in-depth instructions for both players and developers, explaining how to use JSON configurations and the DropRate API to modify loot tables effectively.

## Overview
DropRate allows users to:
- **Customize drops** for any vanilla or modded mobs via JSON.
- **Register new drops programmatically** using the API.
- **Override default loot tables** without modifying base game files.
- **Ensure cross-mod compatibility** by allowing other mods to interact with DropRate's drop system.

---

# 🔧 JSON Configuration Guide
### Basic Structure
DropRate uses a JSON file (`config/droprate.config.json`) to define drop rules. Below is a breakdown of the file structure:

```json
{
  "config": [
    {
      "mob": "minecraft:zombie",
      "rate": 80,
      "item": ["minecraft:apple"],
      "item_amount": 3
    }
  ]
}
```

### Explanation of Fields
| Field         | Type           | Description |
|--------------|---------------|-------------|
| `mob`        | String         | The entity ID (e.g., `minecraft:zombie`) |
| `rate`       | Integer (0-100) | Percentage chance of dropping items |
| `item`       | List of Strings | List of item IDs that may drop |
| `item_amount` | Integer/Object  | Fixed number or `{ "min_amount": X, "max_amount": Y }` |

### Advanced Configurations
#### Example 1: Multiple Items for a Single Mob
```json
{
  "config": [
    {
      "mob": "minecraft:skeleton",
      "rate": 75,
      "item": ["minecraft:arrow", "minecraft:bone"],
      "item_amount": 2
    }
  ]
}
```

#### Example 2: Variable Drop Amounts
```json
{
  "config": [
    {
      "mob": "minecraft:ender_dragon",
      "rate": 100,
      "item": ["minecraft:nether_star"],
      "item_amount": { "min_amount": 1, "max_amount": 3 }
    }
  ]
}
```

#### Example 3: Adding Drops for Modded Mobs
```json
{
  "config": [
    {
      "mob": "moddedmobs:fire_zombie",
      "rate": 50,
      "item": ["minecraft:blaze_powder"],
      "item_amount": 5
    }
  ]
}
```

---

# 📜 Developer API Guide
DropRate provides an API to register drops dynamically at runtime. This is useful for other mods that need to modify drop behavior programmatically.

## Importing the API
```java
import th.tamkungz.droprateapi.api.DropRateAPI;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraftforge.registries.ForgeRegistries;
import java.util.Arrays;
```

## Registering Drops in Code
```java
// Example: Add a 50% chance for zombies to drop 1-3 diamonds/gold ingots
EntityType<?> zombie = ForgeRegistries.ENTITIES.getValue(new ResourceLocation("minecraft:zombie"));
DropRateAPI.registerDrop(zombie, 50, Arrays.asList(Items.DIAMOND, Items.GOLD_INGOT), 1, 3);
```

### Overriding Vanilla Drops
```java
// Force Creepers to drop TNT instead of gunpowder
DropRateAPI.registerDrop(
    EntityType.CREEPER, 
    100, 
    Arrays.asList(Items.TNT), 
    3, 
    3
);
```

### Retrieving Drop Data
```java
// Fetch drop rules for a skeleton
EntityType<?> skeleton = ForgeRegistries.ENTITIES.getValue(new ResourceLocation("minecraft:skeleton"));
List<DropRateAPI.DropData> skeletonDrops = DropRateAPI.getDropData(skeleton);

for (DropRateAPI.DropData drop : skeletonDrops) {
    System.out.println("Drop Rate: " + drop.rate + "%");
    System.out.println("Items: " + drop.items);
    System.out.println("Amount: " + drop.minAmount + "-" + drop.maxAmount);
}
```

### Registering Drops for Modded Mobs
```java
// Register custom drops for a modded entity
EntityType<?> cyberZombie = ForgeRegistries.ENTITIES.getValue(new ResourceLocation("othermod:cyber_zombie"));
DropRateAPI.registerDrop(cyberZombie, 75, Arrays.asList(Items.NETHERITE_SCRAP, Items.REDSTONE), 2, 5);
```

---

# 🔗 Cross-Mod Compatibility
DropRate is designed to work alongside other loot-enhancing mods such as:
- **Lootr** (for loot table modifications)
- **Apotheosis** (for improved loot handling)

Other mods can also use DropRate’s API to modify drop rules dynamically.

---

# 🛠️ Installation & Setup
### 🔹 For Players
1. Download the latest `droprate-X.X.X.jar` from the [Releases](https://github.com/YOUR_GITHUB/DropRate/releases).
2. Place the file in your Minecraft `mods` folder.
3. Configure `config/droprate.config.json` as needed.

### 🔹 For Mod Developers
1. Add DropRate as a dependency in your Forge mod.
2. Use the API methods to define or override loot tables.
3. Ensure compatibility with other mods by using `getDropData()` before modifying existing drop rules.

---

# ❓ FAQ
### Does this mod conflict with other loot-modifying mods?
No, DropRate uses standard Forge events and should work alongside most mods. If conflicts arise, use the API to override rules.

### Can I reload the config without restarting Minecraft?
Currently, configurations are loaded once at startup. Restart the game to apply changes.

### How do I add drops for modded mobs?
Use their entity ID (e.g., `mymod:fire_zombie`) in the JSON file or API.

---

# 🤝 Contribution
Want to contribute? Submit pull requests or report issues in the [GitHub Issues](https://github.com/YOUR_GITHUB/DropRate/issues) section.

---

# 📜 License
DropRate is licensed under the MIT License. See the `LICENSE` file for details.

---

🚀 **Enjoy customizing mob drops with DropRate!**

