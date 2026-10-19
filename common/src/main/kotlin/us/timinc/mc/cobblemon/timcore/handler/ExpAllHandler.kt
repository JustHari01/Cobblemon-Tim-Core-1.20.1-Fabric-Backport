package us.timinc.mc.cobblemon.timcore.handler

import com.cobblemon.mod.common.Cobblemon
import com.cobblemon.mod.common.api.events.battles.BattleVictoryEvent
import com.cobblemon.mod.common.api.tags.CobblemonItemTags
import us.timinc.mc.cobblemon.timcore.AbstractHandler
import us.timinc.mc.cobblemon.timcore.TimCore.config
import us.timinc.mc.cobblemon.timcore.TimCore.debugger
import us.timinc.mc.cobblemon.timcore.getIdentifier
import us.timinc.mc.cobblemon.timcore.hasExpAllFor

object ExpAllHandler : AbstractHandler<BattleVictoryEvent>() {
    override fun handle(evt: BattleVictoryEvent) {
        if (!config.enableExpAll) return

        val caseDebugger = debugger.getCaseDebugger()
        caseDebugger.debug("Reviewing post-battle for ExpAll logic.")

        val defeatedPokemon = evt.losers.flatMap { actor ->
            val defeated = actor.pokemonList.filter { it.health <= 0 }
            if (defeated.isEmpty()) return@flatMap emptyList()

            val teamWiped = actor.pokemonList.all { it.health <= 0 }
            if (!teamWiped && !config.expAllAwardOnBattleLoss) {
                caseDebugger.debug("Skipping a non-wiped team because ExpAll on battle loss is disabled.")
                emptyList()
            } else {
                defeated
            }
        }
        if (defeatedPokemon.isEmpty()) return

        for (recipientActor in evt.winners) {
            for (recipient in recipientActor.pokemonList) {
                val recipientIdentifier = recipient.effectedPokemon.getIdentifier()
                caseDebugger.debug("Reviewing recipient $recipientIdentifier...")

                val owner = recipient.originalPokemon.getOwnerPlayer()
                if (owner == null) {
                    caseDebugger.debug("Not player-owned, skipping.")
                    continue
                }
                if (!owner.hasExpAllFor(recipient.effectedPokemon)) {
                    caseDebugger.debug("Player ${owner.name} does not have an ExpAll, skipping.")
                    continue
                }
                if (recipient.effectedPokemon.heldItem().`is`(CobblemonItemTags.EXPERIENCE_SHARE)) {
                    caseDebugger.debug("Holding ExpShare, skipping as exp is already given by Cobblemon.")
                    continue
                }
                if (recipient.health <= 0 && !config.expAllIncludeFainted) {
                    caseDebugger.debug("Fainted and ExpAll for fainted Pokémon is disabled, skipping.")
                    continue
                }

                for (faintedOpponent in defeatedPokemon) {
                    val faintedOpponentIdentifier = faintedOpponent.effectedPokemon.getIdentifier()
                    caseDebugger.debug("Reviewing fainted opponent $faintedOpponentIdentifier...")

                    if (recipient.facedOpponents.contains(faintedOpponent)) {
                        caseDebugger.debug("Faced $faintedOpponentIdentifier, skipping as exp is already given by Cobblemon.")
                        continue
                    }

                    val experience = Cobblemon.experienceCalculator.calculate(
                        recipient,
                        faintedOpponent,
                        config.expAllMultiplier.toDouble()
                    )
                    if (experience <= 0) {
                        caseDebugger.debug("Calculated experience less than or equal to 0, skipping.")
                        continue
                    }

                    caseDebugger.debug("Awarding $experience experience.")
                    recipient.actor.awardExperience(recipient, experience)
                }
            }
        }
    }
}
