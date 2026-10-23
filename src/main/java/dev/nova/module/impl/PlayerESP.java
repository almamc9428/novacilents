package dev.nova.module.impl;

import dev.nova.module.Category;
import dev.nova.module.Module;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.lwjgl.glfw.GLFW;

public class PlayerESP extends Module {
    public PlayerESP() {
        super("ESP", "Outline players through walls (client-side glow).", Category.RENDER, GLFW.GLFW_KEY_X);
    }

    @Override
    public void onTick() {
        for (Entity e : mc.world.getEntities()) {
            if (e instanceof PlayerEntity && e != mc.player) e.setGlowing(true);
        }
    }

    @Override
    public void onDisable() {
        if (mc.world == null) return;
        for (Entity e : mc.world.getEntities()) {
            if (e instanceof PlayerEntity && e != mc.player) e.setGlowing(false);
        }
    }
}
