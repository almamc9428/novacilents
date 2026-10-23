package dev.nova.module.impl;

import dev.nova.module.Category;
import dev.nova.module.Module;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import org.lwjgl.glfw.GLFW;

public class Fullbright extends Module {
    public Fullbright() {
        super("Fullbright", "See in the dark (client-side night vision).", Category.RENDER, GLFW.GLFW_KEY_C);
    }

    @Override
    public void onTick() {
        mc.player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 1000, 0, false, false, false));
    }

    @Override
    public void onDisable() {
        mc.player.removeStatusEffect(StatusEffects.NIGHT_VISION);
    }
}
