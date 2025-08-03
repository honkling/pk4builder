@file:Listener

package me.honkling.pk4builder.event

import me.honkling.commando.spigot.event.Listener
import me.honkling.pk4builder.profile.currentLevel
import me.honkling.pk4builder.profile.levelCounter
import me.honkling.pk4builder.profile.previousLevels
import net.kyori.adventure.key.Key
import net.kyori.adventure.sound.Sound
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

private fun onInteract(event: PlayerInteractEvent) {
    val player = event.player
    val block = event.clickedBlock
        ?: return

    if (event.action != Action.PHYSICAL || block.type != Material.LIGHT_WEIGHTED_PRESSURE_PLATE || block.location in player.previousLevels)
        return

    player.levelCounter++
    player.currentLevel = block.location
    player.previousLevels = player.previousLevels.also { it += block.location }
    player.playSound(Sound.sound {
        it.type(Key.key(Key.MINECRAFT_NAMESPACE, "block.note_block.pling"))
        it.pitch(2.0f)
    })
}