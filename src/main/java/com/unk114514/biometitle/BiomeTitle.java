package com.unk114514.biometitle;

import com.terraformersmc.modmenu.api.ModMenuApi;
import com.unk114514.biometitle.config.BiomeTitleConfig;
import com.unk114514.biometitle.title.TitleManager;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class BiomeTitle implements ClientModInitializer, ModMenuApi {
	public static TitleManager manager;

	public static void getTitleManager() {
		if (manager == null) {
			manager = new TitleManager();
		}
	}

	@Override
	public void onInitializeClient() {
		AutoConfig.register(BiomeTitleConfig.class, JanksonConfigSerializer::new);
		ClientTickEvents.END_CLIENT_TICK.register(client -> manager.tick(client));
		getTitleManager();
	}

}