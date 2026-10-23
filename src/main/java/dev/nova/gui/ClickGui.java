package dev.nova.gui;

import dev.nova.Keys;
import dev.nova.NovaClient;
import dev.nova.module.Category;
import dev.nova.module.Module;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

/**
 * Input is polled through GLFW inside render() instead of overriding
 * mouseClicked/keyPressed, because those signatures changed in 1.21.9+.
 */
public class ClickGui extends Screen {
    private static final int PW = 110, HEAD = 16, ROW = 14, GAP = 10, TOP = 30;

    private Module listening = null;
    private boolean leftWas = true, rightWas = true;
    private boolean guiKeyWas = true;
    private final boolean[] prevKeys = new boolean[GLFW.GLFW_KEY_LAST + 1];

    public ClickGui() {
        super(Text.literal("Nova"));
    }

    @Override
    public boolean shouldPause() { return false; }

    private int panelX(int index) {
        int total = Category.values().length * (PW + GAP) - GAP;
        return (width - total) / 2 + index * (PW + GAP);
    }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        ctx.fill(0, 0, width, height, 0x90000000);
        ctx.drawCenteredTextWithShadow(textRenderer,
                "Nova Client  |  left click: toggle  |  right click: bind key (BACKSPACE clears)",
                width / 2, 10, 0xFFFFFFFF);

        boolean left = Keys.mouseDown(GLFW.GLFW_MOUSE_BUTTON_LEFT);
        boolean right = Keys.mouseDown(GLFW.GLFW_MOUSE_BUTTON_RIGHT);
        boolean clickL = left && !leftWas;
        boolean clickR = right && !rightWas;
        leftWas = left;
        rightWas = right;

        int i = 0;
        for (Category c : Category.values()) {
            int x = panelX(i++);
            ctx.fill(x, TOP, x + PW, TOP + HEAD, 0xFF1E6F8C);
            ctx.drawTextWithShadow(textRenderer, c.label, x + 4, TOP + 4, 0xFFFFFFFF);

            int y = TOP + HEAD;
            for (Module m : NovaClient.modules.in(c)) {
                boolean hover = mouseX >= x && mouseX < x + PW && mouseY >= y && mouseY < y + ROW;
                if (hover && clickL) m.toggle();
                if (hover && clickR) listening = m;

                int bg = m.isEnabled() ? (hover ? 0xFF2E9E4F : 0xFF228B3D) : (hover ? 0xFF3A3A3A : 0xFF262626);
                ctx.fill(x, y, x + PW, y + ROW, bg);
                ctx.drawTextWithShadow(textRenderer, m.name, x + 4, y + 3, 0xFFFFFFFF);

                String tag = (listening == m) ? "[...]" : (m.key <= GLFW.GLFW_KEY_UNKNOWN ? "" : "[" + Keys.name(m.key) + "]");
                int tw = textRenderer.getWidth(tag);
                ctx.drawTextWithShadow(textRenderer, tag, x + PW - tw - 4, y + 3, 0xFFAAAAAA);
                y += ROW;
            }
        }

        // key polling: rebinding + close with the GUI key
        boolean guiDown = Keys.isDown(NovaClient.GUI_KEY);
        for (int k = GLFW.GLFW_KEY_SPACE; k <= GLFW.GLFW_KEY_LAST; k++) {
            boolean d = Keys.isDown(k);
            if (d && !prevKeys[k] && listening != null && k != GLFW.GLFW_KEY_ESCAPE) {
                listening.key = (k == GLFW.GLFW_KEY_BACKSPACE || k == GLFW.GLFW_KEY_DELETE)
                        ? GLFW.GLFW_KEY_UNKNOWN : k;
                listening = null;
                guiKeyWas = true; // don't close the GUI if RSHIFT was just bound
            }
            prevKeys[k] = d;
        }
        if (listening == null && guiDown && !guiKeyWas) close();
        guiKeyWas = guiDown;
    }
}
