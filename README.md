# Cobblemon Tim Core — Unofficial 1.20.1 Fabric Backport

This project backports the MIT-licensed Tim Core codebase to Minecraft 1.20.1, Fabric, and Cobblemon 1.5.2. It is an unofficial compatibility port and is not an official TimInc release.

The upstream project targets newer Minecraft and Cobblemon versions. This backport keeps the systems that can be implemented against the 1.20.1 APIs, with the compatibility differences below.

## Target and build

- Minecraft 1.20.1
- Fabric Loader 0.16.14 or newer
- Fabric API 0.92.12+1.20.1
- Cobblemon 1.5.2+1.20.1
- Java 17 or newer

Build the Fabric jar with `./gradlew :fabric:build` (Windows: `gradlew.bat :fabric:build`).

## Compatibility differences

Cobblemon 1.5.2 does not provide several APIs used by newer Tim Core releases. This backport therefore does not include the newer matcher pieces for native Pokémon marks and active marks, riding stamina and stats, fullness, or cosmetic items. `CustomMarkBooleanProperty` stores its compatibility flag in Pokémon persistent data; it does not create a native Cobblemon mark.

Cobblemon 1.5.2 also lacks the newer fishing, Poké Snack, and habitat spawner-influence registration hooks and the fishing-team pre-event used by upstream. General spawner influences in this backport attach to player spawners only, and the fishing-without-a-team canceller is unavailable. EV gain blocking is implemented through a compatibility mixin because the newer EV event is absent.

ExpAll uses local `expAllAwardOnBattleLoss` and `expAllIncludeFainted` settings because the target Cobblemon version does not expose the corresponding global options. Both default to `false`. Cobblemon 1.5.2 does not expose the faint-order timestamp used by newer upstream code, so the optional fainted-recipient timing rule cannot be reproduced exactly.

## Validation status

The Fabric build succeeds. An isolated development client with Fabric Loader 0.16.14 and Cobblemon 1.5.2 completed client startup, initialized Cobblemon registries, and loaded its Pokémon assets without a Tim Core mixin failure. A world session and gameplay behavior have not yet been verified.

## Upstream project

Upstream version used as the porting source: `v1.8.2-1.34`.

[Modrinth](https://modrinth.com/mod/cobblemon-tim-core)

[CurseForge](https://www.curseforge.com/minecraft/mc-mods/cobblemon-tim-core)

[GitHub](https://github.com/timinc-cobble/cobblemon-tim-core)

## What if…

…my Cobblemon mods worked without a bunch of copy/pasting?

## Features

[AbstractMod](https://www.notion.so/AbstractMod-34557e0d4afd816d8e01ee21e5085916?pvs=21)

[AbstractConfig](https://www.notion.so/AbstractConfig-34557e0d4afd810a9eaef342baa4ab2b?pvs=21)

[ConfigBuilder](https://www.notion.so/ConfigBuilder-34557e0d4afd81238b26cede7008e1fc?pvs=21)

[Overridable Options](https://www.notion.so/Overridable-Options-34557e0d4afd81e089d6fa06bc860ee5?pvs=21)

[AbstractHandler](https://www.notion.so/AbstractHandler-34557e0d4afd81f2b818f918be923af1?pvs=21)

[Debugger](https://www.notion.so/Debugger-34557e0d4afd8135ac46e9f36ed11592?pvs=21)

[PokemonExtensions](https://www.notion.so/PokemonExtensions-34557e0d4afd81d281fce19bb434224d?pvs=21)

[Events](https://www.notion.so/Events-34557e0d4afd81b48320c3f5e6e7acb9?pvs=21)

[Custom Pokémon Properties](https://www.notion.so/Custom-Pok-mon-Properties-34557e0d4afd818d906bd38dcc380447?pvs=21)

[Holdings](https://www.notion.so/Holdings-34557e0d4afd81569ff1ee48fbfc392a?pvs=21)

[ExpAll Logic](https://www.notion.so/ExpAll-Logic-34557e0d4afd814197c6fc9eaa0d879a?pvs=21)

[Spawning Buckets Attachment](https://www.notion.so/Spawning-Buckets-Attachment-34557e0d4afd81028b19e20ee31a93d7?pvs=21)

[Spawn Cause Attachment](https://www.notion.so/Spawn-Cause-Attachment-34557e0d4afd815db306e2e2407c73b1?pvs=21)

[`PokemonMatcher`](https://www.notion.so/PokemonMatcher-34557e0d4afd81f8a1a5f52108ca6132?pvs=21)

[`ResourceReloadListeners`](https://www.notion.so/ResourceReloadListeners-34557e0d4afd819da8bfd1d7b1209823?pvs=21)

[Block & Item Registration](https://www.notion.so/Block-Item-Registration-34557e0d4afd817cab91d25fc4a92ed9?pvs=21)

[Commands](https://www.notion.so/Commands-34557e0d4afd81d9961cf9991915b7f5?pvs=21)

[Limited List](https://www.notion.so/Limited-List-34557e0d4afd81deb26fc6fbdb1565a3?pvs=21)

[Wild Pokémon Reservation System](https://www.notion.so/Wild-Pok-mon-Reservation-System-34557e0d4afd81df8af6e07c9d8b1ff1?pvs=21)

[PokemonRepresentation](https://www.notion.so/PokemonRepresentation-34557e0d4afd81ef9dc5e4425fe558f5?pvs=21)

[Base Cobblemon Fixes](https://www.notion.so/Base-Cobblemon-Fixes-34557e0d4afd81a9ab8fd4131a5ec455?pvs=21)

[Prevent Spawns](https://www.notion.so/Prevent-Spawns-34557e0d4afd816cb6a7df8f588ddcfb?pvs=21)

[Spawning Influences](https://www.notion.so/Spawning-Influences-34557e0d4afd8153b677e774d701714f?pvs=21)

[Custom Property Extractor](https://www.notion.so/Custom-Property-Extractor-34557e0d4afd817a8f54cbff2cb092e0?pvs=21)

[Codecs](https://www.notion.so/Codecs-34557e0d4afd8170bd99c3c49d0b2227?pvs=21)

## Player Help

[Config Options](https://www.notion.so/Config-Options-34557e0d4afd81048b19d3177d6fd2db?pvs=21)

## Dependencies

- [Cobblemon](https://www.notion.so/Cobblemon-22157e0d4afd80a49896c70a775a3c7f?pvs=21)

## Known Issues

- v1.33.0 had a breaking issue with its new menu and block type registry handler stuff. v1.33.1 fixed that.
- v1.33.1 didn't load the fix for type gems properly. v1.33.2 fixed that.

## Roadmap

If you’d like to keep up with the work being done on the mod, please join [the Discord](https://discord.com/invite/WKAR27SdSv) and subscribe to notifications on the channel for this content. You can also keep track of the to do list available on [the mod’s main page](https://www.notion.so/Tim-Core-22057e0d4afd809b9c02e78f26805376?pvs=21).

## Feedback

If you have any questions or requests concerning the mod, or just want to drop by and say hi, visit us over at [the Discord](https://discord.com/invite/WKAR27SdSv)!

## Support

If I've made something you enjoyed or helped you make something, please consider [dropping a tip in the cup](https://ko-fi.com/timsminecraftmods) and mention how I helped if you'd like!
