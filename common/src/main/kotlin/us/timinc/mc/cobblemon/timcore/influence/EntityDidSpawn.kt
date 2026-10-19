package us.timinc.mc.cobblemon.timcore.influence

import com.cobblemon.mod.common.api.spawning.detail.SpawnAction
import com.cobblemon.mod.common.api.spawning.influence.SpawningInfluence
import net.minecraft.world.entity.Entity
import us.timinc.mc.cobblemon.timcore.TimCoreEvents
import us.timinc.mc.cobblemon.timcore.event.EntityDidSpawnEvent

class EntityDidSpawn : SpawningInfluence {
    override fun affectAction(action: SpawnAction<*>) {
        action.future.thenAccept { result ->
            val entity = result as? Entity ?: return@thenAccept
            TimCoreEvents.ENTITY_DID_SPAWN.emit(
                EntityDidSpawnEvent(
                    entity,
                    action.ctx,
                    action.detail.bucket.name,
                )
            )
        }
    }
}
