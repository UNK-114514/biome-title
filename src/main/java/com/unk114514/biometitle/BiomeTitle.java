package com.unk114514.biometitle;

import com.terraformersmc.modmenu.api.ModMenuApi;
import com.unk114514.biometitle.commands.BiomeTitleCommand;
import com.unk114514.biometitle.config.BiomeTitleConfig;
import com.unk114514.biometitle.title.TitleManager;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class BiomeTitle implements ClientModInitializer, ModMenuApi {
	public static final String MOD_ID = "biome_title";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static TitleManager titleManager;

	public static void getTitleManager() {
		if (titleManager == null) {
			titleManager = new TitleManager(new Random());
		}
	}

	@Override
	public void onInitializeClient() {
		AutoConfig.register(BiomeTitleConfig.class, JanksonConfigSerializer::new);
		ClientTickEvents.END_CLIENT_TICK.register(client -> titleManager.tick(client));
		BiomeTitleCommand.register();
		getTitleManager();
	}
}