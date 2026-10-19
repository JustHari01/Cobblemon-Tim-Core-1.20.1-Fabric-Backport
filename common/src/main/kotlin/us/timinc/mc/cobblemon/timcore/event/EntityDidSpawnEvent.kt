package us.timinc.mc.cobblemon.timcore.event

import com.cobblemon.mod.common.api.spawning.context.SpawningContext
import com.cobblemon.mod.common.api.spawning.spawner.Spawner
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.Entity

data class EntityDidSpawnEvent<T : Entity>(
    val entity: T,
    val context: SpawningContext,
    val bucket: String,
) {
    val cause: Entity?
        get() = context.cause.entity
    val playerCause: ServerPlayer?
        get() = context.cause.entity as? ServerPlayer
    val spawner: Spawner
        get() = context.spawner
}
