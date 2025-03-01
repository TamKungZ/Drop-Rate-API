# DropRate - Custom Mob Drops & API

DropRate is a flexible Minecraft Forge mod that allows players and developers to customize mob drops effortlessly. Whether you want to tweak vanilla mob loot, add drops for custom mobs, or integrate with other mods, DropRate provides both a user-friendly JSON configuration and a developer-friendly API.

## Features

### 🔧 Customizable Mob Drops via JSON
- Configure drop rates, items, and quantities for any mob.
- Define fixed or random amounts (e.g., 1-5 diamonds).
- No coding required!

#### Example JSON Configuration (`config/droprate.config.json`):
```json
{
  "config": [
    {
      "mob": "minecraft:zombie",
      "rate": 80,
      "item": ["minecraft:apple"],
      "item_amount": 3
    },
    {
      "mob": "minecraft:ender_dragon",
      "rate": 100,
      "item": ["minecraft:diamond_block"],
      "item_amount": {
        "min_amount": 1,
        "max_amount": 5
      }
    }
  ]
}
```

### 📜 Modder-Friendly API
- Register custom drops for vanilla or custom mobs.
- Override default drop rules.
- Retrieve drop data for cross-mod compatibility.

#### Example API Usage:
```java
import th.tamkungz.droprateapi.api.DropRateAPI;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import java.util.Arrays;

// Register drops for Zombies (50% chance to drop 2-4 emeralds)
DropRateAPI.registerDrop(EntityType.ZOMBIE, 50, Arrays.asList(Items.EMERALD), 2, 4);
```

### 🔗 Cross-Mod Compatibility
- Works standalone or with modded mobs.
- Other mods can modify drop tables via the API.

## Installation

1. Download the latest `droprate-X.X.X.jar` from the [Releases](https://github.com/YOUR_GITHUB/DropRate/releases) page.
2. Place it in your Minecraft `mods` folder.
3. Configure `config/droprate.config.json` to your liking.

## Compatibility
- **Minecraft Version:** 1.16.5 (Forge)
- **Mod Loaders:** Forge
- **Dependencies:** None

## FAQ

### ❓ Does this mod conflict with other loot-modifying mods?
No, DropRate uses standard Forge events and should work alongside most mods. If conflicts arise, use the API to override rules.

### ❓ Can I reload the config without restarting Minecraft?
Currently, configurations are loaded once at startup. Restart the game to apply changes.

### ❓ How do I add drops for modded mobs?
Use their entity ID (e.g., `mymod:fire_zombie`) in the JSON file or API.

## Contribution

Want to contribute? Feel free to submit pull requests or report issues in the [GitHub Issues](https://github.com/YOUR_GITHUB/DropRate/issues) section.

## License

This mod is licensed under the MIT License. See the `LICENSE` file for details.

---

🚀 **Enjoy customizing mob drops with DropRate!**

