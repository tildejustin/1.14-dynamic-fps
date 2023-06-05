package dynamicmenufps;


import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;
import net.minecraft.util.Util;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

import java.util.concurrent.locks.LockSupport;

public class DynamicMenuFPSMod implements ModInitializer {
    private static long lastRender;

    public void onInitialize() {
    }

    public static boolean checkForRender() {
        long currentTime = Util.getMeasuringTimeMs();
        long timeSinceLastRender = currentTime - lastRender;
        if (!checkForRender(timeSinceLastRender)) {
            return false;
        } else {
            lastRender = currentTime;
            return true;
        }
    }

    private static boolean checkForRender(long timeSinceLastRender) {
        Integer fpsOverride = fpsOverride();
        if (fpsOverride == null) {
            return true;
        } else if (fpsOverride == 0) {
            idle(1000L);
            return false;
        } else {
            long frameTime = (long)(1000 / fpsOverride);
            boolean shouldSkipRender = timeSinceLastRender < frameTime;
            if (!shouldSkipRender) {
                return true;
            } else {
                idle(frameTime);
                return false;
            }
        }
    }

    private static void idle(long waitMillis) {
        waitMillis = Math.min(waitMillis, 30L);
        LockSupport.parkNanos("waiting to render", waitMillis * 1000000L);
    }

    @Nullable
    private static Integer fpsOverride() {
        MinecraftClient client = MinecraftClient.getInstance();
        Window window = ((DynamicMenuFPSMod.WindowHolder)client).getWindow();
        boolean isHovered = GLFW.glfwGetWindowAttrib(window.getHandle(), 131083) != 0;
        if (isHovered) {
            return null;
        } else {
            return !client.isWindowFocused() ? 1 : null;
        }
    }

    public interface WindowHolder {
        Window getWindow();
    }
}
