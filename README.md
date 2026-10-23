# Nova Client (Fabric, Minecraft 1.21.11)

Standalone client-side Fabric mod: module system, ClickGUI (Right Shift), HUD arraylist.

Modules: KillAura (R), Flight (G), Speed (V), Sprint (Z), Fullbright (C), ESP (X).
Right-click a module in the ClickGUI to rebind it.

## Build the jar (easiest: GitHub)
1. Create a new GitHub repo, upload everything in this folder, commit.
2. Open the "Actions" tab -> "Build Nova Client" -> download the `nova-client-jar` artifact.
3. Put `nova-client-1.0.0.jar` (NOT the -sources jar) + Fabric API in your `mods` folder (Fabric Loader 0.18.1+, MC 1.21.11).

## Build locally
Install JDK 21 + Gradle 9.2, then: `gradle build` -> `build/libs/`.
Or open the folder in IntelliJ IDEA and import as a Gradle project, then run `genSources` / `runClient`.

## Other Minecraft versions
Edit `gradle.properties` (Yarn is only available up to 1.21.11; 26.1+ needs Mojang mappings and a different build script).
Values: https://fabricmc.net/develop

## Important
- No client "works on all servers". Modules like Flight/Speed/KillAura are flagged by anticheats
  (Grim, Vulcan, Watchdog, etc.) and can get you banned. Use singleplayer, LAN, or your own test server for videos.
- Don't use on servers whose rules forbid it.
