package us.timinc.mc.cobblemon.timcore

import com.cobblemon.mod.common.api.properties.CustomPokemonPropertyType
import com.cobblemon.mod.common.api.properties.CustomPokemonProperty
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity
import com.cobblemon.mod.common.pokemon.Pokemon
import org.apache.commons.lang3.math.NumberUtils.toFloat

class TimCoreFloatPokemonProperty(
    private val key: String,
    private val value: Float,
    private val pokemonApplicator: (Pokemon, Float) -> Unit,
    private val entityApplicator: (PokemonEntity, Float) -> Unit,
    private val pokemonMatcher: (Pokemon, Float) -> Boolean,
    private val entityMatcher: (PokemonEntity, Float) -> Boolean,
) : CustomPokemonProperty {
    override fun asString() = "$key=$value"
    override fun apply(pokemon: Pokemon) = pokemonApplicator(pokemon, value)
    override fun apply(pokemonEntity: PokemonEntity) = entityApplicator(pokemonEntity, value)
    override fun matches(pokemon: Pokemon) = pokemonMatcher(pokemon, value)
    override fun matches(pokemonEntity: PokemonEntity) = entityMatcher(pokemonEntity, value)
}

@Suppress("Unused", "MemberVisibilityCanBePrivate")
open class CustomFloatProperty(override val keys: Iterable<String>) : CustomPokemonPropertyType<TimCoreFloatPokemonProperty> {
    constructor(key: String) : this(setOf(key))

    override val needsKey: Boolean = true

    override fun examples(): Collection<String> = (0..100).map { (it / 100.0).toString() }

    override fun fromString(value: String?) = TimCoreFloatPokemonProperty(
        keys.first(),
        toFloat(value),
        ::pokemonApplicator,
        ::entityApplicator,
        ::pokemonMatcher,
        ::entityMatcher
    )

    fun pokemonApplicator(pokemon: Pokemon, value: Float) {
        pokemon.persistentData.putFloat(keys.first(), value)
    }

    fun entityApplicator(entity: PokemonEntity, value: Float) {
        pokemonApplicator(entity.pokemon, value)
    }

    fun getValue(pokemon: Pokemon): Float? = pokemon.persistentData.getFloatOrNull(keys.first())

    fun pokemonMatcher(pokemon: Pokemon, value: Float): Boolean = getValue(pokemon) == value

    fun entityMatcher(entity: PokemonEntity, value: Float): Boolean = pokemonMatcher(entity.pokemon, value)
}
