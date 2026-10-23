package dev.nova.module.impl;

import dev.nova.module.Category;
import dev.nova.module.Module;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import org.lwjgl.glfw.GLFW;

public class KillAura extends Module {
    public double range = 3.0;
    public boolean players = true;
    public boolean mobs = true;

    public KillAura() {
        super("KillAura", "Auto-attacks nearby targets when the cooldown is ready.", Category.COMBAT, GLFW.GLFW_KEY_R);
    }

    @Override
    public void onTick() {
        var p = mc.player;
        if (p == null || mc.interactionManager == null || !p.isAlive()) return;
        if (p.getAttackCooldownProgress(0.5f) < 1.0f) return;

        Entity best = null;
        double bestDist = range;
        for (Entity e : mc.world.getEntities()) {
            if (!(e instanceof LivingEntity le) || e == p || !le.isAlive()) continue;
            boolean ok = (players && e instanceof PlayerEntity) || (mobs && e instanceof HostileEntity);
            if (!ok) continue;
            double d = p.distanceTo(e);
            if (d <= bestDist) { best = e; bestDist = d; }
        }
        if (best == null) return;

        mc.interactionManager.attackEntity(p, best);
        p.swingHand(Hand.MAIN_HAND);
    }
}
