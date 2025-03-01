DropRate Mod - README
=====================

Overview:
---------
The DropRate Mod allows Minecraft modders to customize item drop rates for different entities using a configurable system. This mod enables specific mobs to drop defined items at specified probabilities and quantities, offering better loot customization.

Installation:
-------------
1. Install Minecraft Forge.
2. Download the DropRate Mod JAR file and place it into your `mods` folder.
3. Launch the game to generate the default configuration file.

Configuration Handling (JSON Files):
------------------------------------
The DropRate Mod uses JSON files for configuration. The configuration file is located at:
`config/droprate.config.json`

Structure of JSON Config:
-------------------------
Each entry in the configuration file follows this structure:
```json
{
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

Explanation of JSON Fields:
---------------------------
- `mob`: The entity type (e.g., `minecraft:zombie`, `minecraft:skeleton`).
- `rate`: Drop rate percentage (0-100).
- `item`: List of item IDs that the mob can drop.
- `item_amount`:
  - `min_amount`: Minimum number of items dropped.
  - `max_amount`: Maximum number of items dropped.

Handling JSON Files:
--------------------
1. Open `droprate.config.json` with a text editor (Notepad++, VS Code, etc.).
2. Modify or add new entries using the structure above.
3. Ensure that commas, brackets, and quotes are correctly placed to prevent syntax errors.
4. Save the file and restart Minecraft to apply changes.

Example Usage:
--------------
To add a new drop rule where a `minecraft:creeper` drops `minecraft:gunpowder` with a 70% chance (1-2 items):
```json
{
  "mob": "minecraft:creeper",
  "rate": 70,
  "item": ["minecraft:gunpowder"],
  "item_amount": {
    "min_amount": 1,
    "max_amount": 2
  }
}
```

Support:
--------
If you experience issues:
- Ensure the JSON format is correct (use a JSON validator online).
- Check Minecraft logs for errors related to DropRate Mod.
- Contact the mod developer or community forums for support.

Enjoy customizing your mob drops with the DropRate Mod!
