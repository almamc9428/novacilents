package dev.nova;

import dev.nova.gui.ClickGui;
import dev.nova.gui.Hud;
import dev.nova.module.ModuleManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

public class NovaClient implements ClientModInitializer {
    public static final String NAME = "Nova";
    public static final int GUI_KEY = GLFW.GLFW_KEY_RIGHT_SHIFT;
    public static ModuleManager modules;
    private boolean guiKeyWasDown = false;

    @Override
    public void onInitializeClient() {
        modules = new ModuleManager();

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null || client.world == null) return;

            boolean down = Keys.isDown(GUI_KEY);
            if (down && !guiKeyWasDown && client.currentScreen == null) {
                client.setScreen(new ClickGui());
            }
            guiKeyWasDown = down;

            modules.tick();
        });

        HudElementRegistry.attachElementBefore(
                VanillaHudElements.CHAT,
                Identifier.of("nova", "hud"),
                (ctx, tickCounter) -> Hud.render(ctx));
    }
}
