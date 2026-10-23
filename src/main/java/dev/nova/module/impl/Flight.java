package dev.nova.module.impl;

import dev.nova.module.Category;
import dev.nova.module.Module;
import org.lwjgl.glfw.GLFW;

public class Flight extends Module {
    public Flight() {
        super("Flight", "Creative-style flight.", Category.MOVEMENT, GLFW.GLFW_KEY_G);
    }

    @Override
    public void onTick() {
        var a = mc.player.getAbilities();
        a.allowFlying = true;
        a.flying = true;
        a.setFlySpeed(0.08f);
    }

    @Override
    public void onDisable() {
        var a = mc.player.getAbilities();
        a.setFlySpeed(0.05f);
        if (!mc.player.isCreative() && !mc.player.isSpectator()) {
            a.allowFlying = false;
            a.flying = false;
        }
    }
}
