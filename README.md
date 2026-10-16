# Backpack Mod

A small Fabric mod for Minecraft 26.3 that adds a craftable, wearable backpack.

## Features
- Craftable backpack with 36 slots
- Contents are stored inside the item, so they survive dropping or moving it
- Wear it in the chestplate slot and it shows on your back
- Carry several backpacks and open any of them from your hand

## Recipe

| | | |
|---|---|---|
| Leather | Iron Ingot | Leather |
| Leather | String | Leather |
| Leather | Leather | Leather |

## Controls

| Action | Input |
|--------|-------|
| Wear the backpack | Right-click while holding it |
| Open worn backpack | B (rebindable in Controls) |
| Open held backpack | Shift + right-click |

## Requirements
- Minecraft 26.3
- [Fabric Loader](https://fabricmc.net/use/installer/)
- [Fabric API](https://modrinth.com/mod/fabric-api)
- Java 25 (the launcher installs it automatically)

## Installation
1. Install Fabric Loader for Minecraft 26.3.
2. Download Fabric API for 26.3.
3. Download the latest `backpackmod` jar from the [Releases](https://github.com/petarjef/backpackmod/releases) page.
4. Put both jars in your `mods` folder (on Windows: press Win + R and type `%appdata%\.minecraft\mods`).
5. Start Minecraft with the Fabric profile.

## Known issues
- A backpack can currently be moved into itself while it is open.
- The worn backpack is a flat texture; a 3D model is planned.

## License
CC0-1.0