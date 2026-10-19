package us.timinc.mc.cobblemon.timcore

import com.cobblemon.mod.common.api.properties.CustomPokemonPropertyType
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity
import com.cobblemon.mod.common.pokemon.Pokemon
import com.cobblemon.mod.common.pokemon.properties.BooleanProperty
import net.minecraft.resources.ResourceLocation

@Suppress("Unused", "MemberVisibilityCanBePrivate")
class CustomMarkBooleanProperty(override val keys: Iterable<String>, val markId: ResourceLocation) :
    CustomPokemonPropertyType<BooleanProperty> {
    constructor(key: String, markId: ResourceLocation) : this(setOf(key), markId)

    override val needsKey: Boolean = true

    override fun examples(): Collection<String> = setOf("yes", "no")

    override fun fromString(value: String?) = BooleanProperty(
        keys.first(), value == "yes", ::pokemonApplicator, ::entityApplicator, ::pokemonMatcher, ::entityMatcher
    )

    fun pokemonApplicator(pokemon: Pokemon, value: Boolean) {
        val key = markDataKey()
        if (value) {
            pokemon.persistentData.putBoolean(key, true)
        } else {
            pokemon.persistentData.remove(key)
        }
    }

    fun entityApplicator(entity: PokemonEntity, value: Boolean) {
        pokemonApplicator(entity.pokemon, value)
    }

    fun pokemonMatcher(pokemon: Pokemon, value: Boolean): Boolean =
        (pokemon.persistentData.contains(markDataKey()) && pokemon.persistentData.getBoolean(markDataKey())) == value

    fun entityMatcher(entity: PokemonEntity, value: Boolean): Boolean = pokemonMatcher(entity.pokemon, value)

    private fun markDataKey() = "tim_core:mark:${markId}"
}
