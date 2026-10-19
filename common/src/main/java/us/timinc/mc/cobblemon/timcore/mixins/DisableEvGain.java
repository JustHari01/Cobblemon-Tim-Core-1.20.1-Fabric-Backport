package us.timinc.mc.cobblemon.timcore.mixins;

import com.cobblemon.mod.common.api.pokemon.stats.Stat;
import com.cobblemon.mod.common.pokemon.EVs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import us.timinc.mc.cobblemon.timcore.TimCore;

@Mixin(EVs.class)
public abstract class DisableEvGain {
    @Inject(method = "add", at = @At("HEAD"), cancellable = true, remap = false)
    private void timcore$blockPositiveEvGain(Stat stat, int amount, CallbackInfoReturnable<Integer> cir) {
        if (amount > 0 && TimCore.INSTANCE.getConfig().getDisableEvGain()) {
            cir.setReturnValue(0);
        }
    }
}
