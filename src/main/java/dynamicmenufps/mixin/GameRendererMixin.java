package dynamicmenufps.mixin;

import dynamicmenufps.DynamicMenuFPSMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Shadow @Final private MinecraftClient client;

    @Inject(
            at = @At("HEAD"),
            method = "render",
            cancellable = true
    )
    private void onRender(float tickDelta, long startTime, boolean tick, CallbackInfo callbackInfo) {
        if (this.client.currentScreen instanceof GameMenuScreen && !DynamicMenuFPSMod.checkForRender()) {
            callbackInfo.cancel();
        }

    }
}
