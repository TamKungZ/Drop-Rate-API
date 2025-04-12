<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>DropRate - Custom Mob Drops & API (GNU Licensed)</title>
    <style>
        body {
            font-family: 'DejaVu Sans', Arial, sans-serif;
            line-height: 1.6;
            max-width: 900px;
            margin: 0 auto;
            padding: 20px;
            color: #333;
        }
        header {
            border-bottom: 2px solid #4a6da7;
            padding-bottom: 10px;
            margin-bottom: 30px;
        }
        h1, h2, h3 {
            color: #2e5496;
        }
        h2 {
            border-bottom: 1px solid #ddd;
            padding-bottom: 5px;
        }
        pre {
            background: #f5f5f5;
            border-left: 4px solid #4a6da7;
            padding: 15px;
            overflow-x: auto;
        }
        code {
            background: #f0f0f0;
            padding: 2px 5px;
            border-radius: 3px;
            font-family: 'DejaVu Sans Mono', monospace;
        }
        table {
            border-collapse: collapse;
            width: 100%;
            margin: 20px 0;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 8px 12px;
            text-align: left;
        }
        th {
            background-color: #f2f6fc;
        }
        .license {
            background: #f8f9fa;
            padding: 15px;
            border-left: 4px solid #d63384;
            font-size: 0.9em;
        }
        .warning {
            background: #fff3cd;
            border-left: 4px solid #ffc107;
            padding: 15px;
        }
    </style>
</head>
<body>
    <header>
        <h1>DropRate - Custom Mob Drops & API</h1>
        <p>Version 1.0.2 | GNU General Public License v3.0</p>
    </header>

    <section id="overview">
        <h2>Overview</h2>
        <p>DropRate is a Minecraft Forge mod that provides:</p>
        <ul>
            <li>JSON-based mob drop configuration</li>
            <li>Programmatic API for mod developers</li>
            <li>Cross-mod compatibility layer</li>
            <li>Non-destructive loot table overrides</li>
        </ul>
    </section>

    <section id="installation">
        <h2>Installation</h2>
        <h3>For Players</h3>
        <pre><code>1. Download droprate-1.0.2.jar
2. Place in /mods folder
3. Configure /config/droprate.config.json</code></pre>

        <h3>For Developers</h3>
        <p>Add to build.gradle:</p>
        <pre><code>repositories {
    maven {
        url "https://cursemaven.com"
        content { includeGroup "curse.maven" }
    }
}

dependencies {
    implementation fg.deobf("curse.maven:drop-rate-api-1208468:6249868")
}</code></pre>
    </section>

    <section id="configuration">
        <h2>JSON Configuration</h2>
        <p>File: <code>/config/droprate.config.json</code></p>
        
        <h3>Basic Example</h3>
        <pre><code class="language-json">{
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
}</code></pre>

        <h3>Field Reference</h3>
        <table>
            <tr><th>Field</th><th>Type</th><th>Description</th></tr>
            <tr><td><code>mob</code></td><td>String</td><td>Entity ID (format: modid:entity)</td></tr>
            <tr><td><code>rate</code></td><td>Integer</td><td>Drop chance percentage (0-100)</td></tr>
            <tr><td><code>item</code></td><td>String[]</td><td>List of item IDs to drop</td></tr>
            <tr><td><code>item_amount</code></td><td>Integer/Object</td><td>Fixed amount or {min_amount, max_amount} range</td></tr>
        </table>
    </section>

    <section id="api">
        <h2>Developer API</h2>
        <h3>Core Methods</h3>
        <pre><code class="language-java">// Register drops
DropRateAPI.registerDrop(
    EntityType.ZOMBIE,
    75,
    Arrays.asList(Items.DIAMOND, Items.EMERALD),
    1,
    3
);

// Query existing drops
List&lt;DropData&gt; drops = DropRateAPI.getDropData(EntityType.SKELETON);

// Remove specific drops
DropRateAPI.removeDrop(EntityType.CREEPER, Items.GUNPOWDER);</code></pre>

        <h3>Event Integration</h3>
        <pre><code class="language-java">@SubscribeEvent
public void onMobDeath(LivingDeathEvent event) {
    // Access DropRate data
    List&lt;DropData&gt; drops = DropRateAPI.getDropData(event.getEntity().getType());
    
    // Custom drop logic here
}</code></pre>
    </section>

    <section id="compatibility">
        <h2>Compatibility</h2>
        <div class="warning">
            <p><strong>Note:</strong> When using with other loot-modifying mods, load order matters. DropRate should load after core content mods but before other loot modifiers.</p>
        </div>
        <table>
            <tr><th>Mod</th><th>Compatibility</th><th>Notes</th></tr>
            <tr><td>Apotheosis</td><td>Full</td><td>Use Apotheosis for drop scaling</td></tr>
            <tr><td>Lootr</td><td>Partial</td><td>Test chest interactions</td></tr>
            <tr><td>TConstruct</td><td>Limited</td><td>No tool modification support</td></tr>
        </table>
    </section>

    <section id="license">
        <h2>License</h2>
        <div class="license">
            <pre>DropRate - Custom Mob Drops & API
Copyright (C) 2023 TamKungZ_

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program. If not, see &lt;https://www.gnu.org/licenses/&gt;.</pre>
        </div>
    </section>

    <footer>
        <p>Documentation generated: August 2023 | DropRate Project</p>
    </footer>
</body>
</html>
