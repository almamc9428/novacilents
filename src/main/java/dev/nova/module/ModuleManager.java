package dev.nova.module;

import dev.nova.module.impl.*;
import net.minecraft.client.MinecraftClient;
import dev.nova.Keys;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
    private final MinecraftClient mc = MinecraftClient.getInstance();
    private final List<Module> modules = new ArrayList<>();

    public ModuleManager() {
        modules.add(new KillAura());
        modules.add(new Flight());
        modules.add(new Speed());
        modules.add(new Sprint());
        modules.add(new Fullbright());
        modules.add(new PlayerESP());
    }

    public List<Module> all() { return modules; }

    public List<Module> in(Category c) {
        List<Module> out = new ArrayList<>();
        for (Module m : modules) if (m.category == c) out.add(m);
        return out;
    }

    public void tick() {
        for (Module m : modules) {
            // keybind toggling (only while no screen is open)
            if (m.key != GLFW.GLFW_KEY_UNKNOWN && mc.currentScreen == null) {
                boolean down = Keys.isDown(m.key);
                if (down && !m.keyWasDown) m.toggle();
                m.keyWasDown = down;
            } else {
                m.keyWasDown = false;
            }
            if (m.isEnabled()) m.onTick();
        }
    }
}
