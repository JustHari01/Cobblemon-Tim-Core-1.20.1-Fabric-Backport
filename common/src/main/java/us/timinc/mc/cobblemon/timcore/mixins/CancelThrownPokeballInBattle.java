package us.timinc.mc.cobblemon.timcore.mixins;

import com.cobblemon.mod.common.entity.pokeball.EmptyPokeBallEntity;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import net.minecraft.world.phys.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import us.timinc.mc.cobblemon.timcore.mixin.helper.CancelThrownPokeballInBattleHelper;

@Mixin(EmptyPokeBallEntity.class)
public abstract class CancelThrownPokeballInBattle {
    @Shadow(remap = false)
    protected abstract void drop();

    @Inject(method = "onHitEntity", at = @At(value = "INVOKE", target =
            "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;getCaptureActions()Ljava/util/List;",
            remap = false), cancellable = true)
    private void tim_core$checkBattleHit(EntityHitResult hitResult, CallbackInfo ci) {
        PokemonEntity pokemonEntity = (PokemonEntity) hitResult.getEntity();
        if (CancelThrownPokeballInBattleHelper.INSTANCE.checkForCancel((EmptyPokeBallEntity) (Object) this, pokemonEntity)) {
            drop();
            ci.cancel();
        }
    }
}
