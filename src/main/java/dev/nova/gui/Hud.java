package dev.nova.gui;

import dev.nova.NovaClient;
import dev.nova.module.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Hud {
    public static void render(DrawContext ctx) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null || mc.options.hudHidden || NovaClient.modules == null) return;

        ctx.drawTextWithShadow(mc.textRenderer, NovaClient.NAME + " 1.0", 4, 4, 0xFF55FFFF);

        List<Module> on = new ArrayList<>();
        for (Module m : NovaClient.modules.all()) if (m.isEnabled()) on.add(m);
        on.sort(Comparator.comparingInt((Module m) -> mc.textRenderer.getWidth(m.name)).reversed());

        int sw = mc.getWindow().getScaledWidth();
        int y = 4;
        for (Module m : on) {
            int w = mc.textRenderer.getWidth(m.name);
            ctx.drawTextWithShadow(mc.textRenderer, m.name, sw - w - 4, y, 0xFF55FF55);
            y += 10;
        }
    }
}
