package dev.nova;

import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;

/** Raw GLFW input helpers (stable across Minecraft's input API changes). */
public final class Keys {
    private Keys() {}

    public static long window() {
        return MinecraftClient.getInstance().getWindow().getHandle();
    }

    public static boolean isDown(int key) {
        if (key <= GLFW.GLFW_KEY_UNKNOWN) return false;
        return GLFW.glfwGetKey(window(), key) == GLFW.GLFW_PRESS;
    }

    public static boolean mouseDown(int button) {
        return GLFW.glfwGetMouseButton(window(), button) == GLFW.GLFW_PRESS;
    }

    public static String name(int key) {
        if (key <= GLFW.GLFW_KEY_UNKNOWN) return "";
        switch (key) {
            case GLFW.GLFW_KEY_RIGHT_SHIFT: return "RSHIFT";
            case GLFW.GLFW_KEY_LEFT_SHIFT: return "LSHIFT";
            case GLFW.GLFW_KEY_RIGHT_CONTROL: return "RCTRL";
            case GLFW.GLFW_KEY_LEFT_CONTROL: return "LCTRL";
            case GLFW.GLFW_KEY_RIGHT_ALT: return "RALT";
            case GLFW.GLFW_KEY_LEFT_ALT: return "LALT";
            case GLFW.GLFW_KEY_SPACE: return "SPACE";
            case GLFW.GLFW_KEY_TAB: return "TAB";
            case GLFW.GLFW_KEY_UP: return "UP";
            case GLFW.GLFW_KEY_DOWN: return "DOWN";
            case GLFW.GLFW_KEY_LEFT: return "LEFT";
            case GLFW.GLFW_KEY_RIGHT: return "RIGHT";
            default:
                String n = GLFW.glfwGetKeyName(key, 0);
                return n != null ? n.toUpperCase() : "K" + key;
        }
    }
}
