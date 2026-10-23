package dev.nova.module.impl;

import dev.nova.module.Category;
import dev.nova.module.Module;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;

public class Speed extends Module {
    public double speed = 0.30;

    public Speed() {
        super("Speed", "Faster ground movement.", Category.MOVEMENT, GLFW.GLFW_KEY_V);
    }

    @Override
    public void onTick() {
        var p = mc.player;
        if (p == null || p.isTouchingWater() || p.isInLava() || p.isGliding()) return;

        float f = (mc.options.forwardKey.isPressed() ? 1 : 0) - (mc.options.backKey.isPressed() ? 1 : 0);
        float s = (mc.options.leftKey.isPressed() ? 1 : 0) - (mc.options.rightKey.isPressed() ? 1 : 0);
        if (f == 0 && s == 0) return;

        double yaw = Math.toRadians(p.getYaw());
        double sin = Math.sin(yaw), cos = Math.cos(yaw);
        double len = Math.max(1.0, Math.sqrt(f * f + s * s));
        double vx = (-sin * f + cos * s) / len * speed;
        double vz = (cos * f + sin * s) / len * speed;

        Vec3d v = p.getVelocity();
        p.setVelocity(vx, v.y, vz);
    }
}
