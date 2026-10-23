package dev.nova.module.impl;

import dev.nova.module.Category;
import dev.nova.module.Module;
import org.lwjgl.glfw.GLFW;

public class Sprint extends Module {
    public Sprint() {
        super("Sprint", "Always sprint while moving forward.", Category.MOVEMENT, GLFW.GLFW_KEY_Z);
    }

    @Override
    public void onTick() {
        var p = mc.player;
        if (p == null) return;
        if (mc.options.forwardKey.isPressed() && !p.isSneaking()
                && !p.horizontalCollision && p.getHungerManager().getFoodLevel() > 6) {
            p.setSprinting(true);
        }
    }
}
