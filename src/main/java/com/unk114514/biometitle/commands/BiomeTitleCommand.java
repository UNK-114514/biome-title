package com.unk114514.biometitle.commands;

import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;

import static com.unk114514.biometitle.BiomeTitle.LOGGER;
import static com.unk114514.biometitle.BiomeTitle.titleManager;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class BiomeTitleCommand {
    public static void register() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher.register(literal("biome-title")
                .then(literal("reload")
                        .executes(commandContext -> {
                            titleManager.refresh();
                            LOGGER.info("Title Manager Refreshed!");
                            return 1;
                        })
                )
        ));
    }
}
