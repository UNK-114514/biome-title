package com.unk114514.biometitle.helper;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class TitleHelper {
    public static void showTitle(Text title, Text subtitle, int fadeInTicks, int stayTicks, int fadeOutTicks) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        client.inGameHud.setTitleTicks(fadeInTicks, stayTicks, fadeOutTicks);
        client.inGameHud.setTitle(title);
        if (subtitle != null) {
            client.inGameHud.setSubtitle(subtitle);
        }
    }
}