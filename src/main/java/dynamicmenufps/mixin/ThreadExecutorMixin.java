package dynamicmenufps.mixin;

import net.minecraft.util.thread.ThreadExecutor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.concurrent.locks.LockSupport;

@Mixin(ThreadExecutor.class)
public final class ThreadExecutorMixin {
    @Overwrite
    public void method_20813() {
        LockSupport.parkNanos("waiting for tasks", 500000L);
    }
}
