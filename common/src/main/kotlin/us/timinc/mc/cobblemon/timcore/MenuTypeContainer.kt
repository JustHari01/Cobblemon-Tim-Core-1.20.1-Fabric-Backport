package us.timinc.mc.cobblemon.timcore

import net.minecraft.world.flag.FeatureFlagSet
import net.minecraft.world.flag.FeatureFlags
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.MenuType
import net.minecraft.world.entity.player.Inventory

fun interface MenuBuilder<T : AbstractContainerMenu> {
    fun create(syncId: Int, inventory: Inventory): T
}

open class MenuTypeContainer<T : AbstractContainerMenu>(
    val menuBuilder: MenuBuilder<T>,
    val requiredFeatures: FeatureFlagSet = FeatureFlags.DEFAULT_FLAGS,
) {
    lateinit var type: MenuType<T>
}
