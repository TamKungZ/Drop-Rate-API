# DropRate - Custom Mob Drops & API ![GNU GPLv3](https://img.shields.io/badge/license-GPLv3-blue)

[![Minecraft 1.16.5](https://img.shields.io/badge/Minecraft-1.16.5-brightgreen)](https://www.minecraft.net/)
[![Forge](https://img.shields.io/badge/Forge-36.2.39-orange)](https://files.minecraftforge.net/)
[![Docs](https://img.shields.io/badge/Docs-API+Maven-blue)](https://tamkungz.github.io/droprateapi/)

A Minecraft Forge mod for customizing mob drops via JSON config or API.

## 📦 Installation

### For Players
1. Download [DropRate-1.0.2.jar](https://www.curseforge.com/minecraft/mc-mods/drop-rate-api/files/6249868)
2. Place in your `mods` folder
3. Configure `/config/droprate.config.json`

### For Developers
Add to your `build.gradle`:
```gradle
repositories {
    maven {
        url "https://cursemaven.com"
        content { includeGroup "curse.maven" }
    }
}

dependencies {
    implementation fg.deobf("curse.maven:drop-rate-api-1208468:6249868")
}
```

## 🛠️ Configuration
### Basic JSON Example
```{
  "config": [
    {
      "mob": "minecraft:zombie",
      "rate": 80,
      "item": ["minecraft:apple"],
      "item_amount": {
        "min_amount": 1,
        "max_amount": 3
      }
    }
  ]
}
```

### Field Reference

| Field        | Type             | Description                                 |
|--------------|------------------|---------------------------------------------|
| `mob`        | `String`         | Entity ID (e.g., `modid:entity`)            |
| `rate`       | `Integer`        | Drop chance (0–100)                         |
| `item`       | `String[]`       | Item IDs to drop                            |
| `item_amount`| `Integer/Object` | Fixed amount or `{ "min_amount", "max_amount" }` |

## 💻 Developer API
### Register Drops
```
DropRateAPI.registerDrop(
    EntityType.ZOMBIE, 
    75, 
    Arrays.asList(Items.DIAMOND), 
    1, 
    3
);
```
### Query Drops
```
List<DropData> drops = DropRateAPI.getDropData(EntityType.SKELETON);
```

### Remove Drops
```
DropRateAPI.removeDrop(EntityType.ZOMBIE, Items.DIAMOND);
```

## 🔄 Commands
- `/droprate_reload` - Reload config without restart

## ❓ FAQ
**Q**: How to add drops for modded mobs?

**A**: Use their entity ID:

```
{
  "mob": "iceandfire:dragon",
  "item": ["minecraft:dragon_egg"]
}
```

**Q**: Does this work with other loot mods?

**A**: Yes, but test load order if issues occur.

## 📜 License
```
DropRate - Custom Mob Drops & API
Copyright (C) 2023 TamKungZ_

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Full license at LICENSE.md
```
