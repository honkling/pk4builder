@file:Command("reset")

package me.honkling.pk4builder.command

import io.papermc.paper.command.brigadier.argument.ArgumentTypes.player
import me.honkling.commando.spigot.command.Command
import me.honkling.pk4builder.lib.mm
import me.honkling.pk4builder.profile.currentLevel
import me.honkling.pk4builder.profile.levelCounter
import me.honkling.pk4builder.profile.previousLevels
import me.honkling.pk4builder.world
import org.bukkit.Location
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

private fun reset(player: Player, confirm: Boolean = false) {
    if (!confirm) {
        player.sendMessage("<system>This will reset your progress. Are you sure you want to do this?".mm)
        player.sendMessage("<system><s><u><click:run_command:/reset true>Click here</s> to confirm.".mm)
        return
    }

    player.levelCounter = 0
    player.currentLevel = Location(world, 0.5, 41.0, 0.5, 0f, 0f)
    player.previousLevels = player.previousLevels.also(MutableList<Location>::clear)
    player.health = 0.0
    player.sendMessage("<system>Your progress has been reset.".mm)
}

private fun reset(sender: CommandSender, target: Player) {
    if (!sender.hasPermission("admin.reset"))
        return sender.sendMessage("<system>You don't have permission to do that.".mm)

    target.levelCounter = 0
    target.currentLevel = Location(world, 0.5, 41.0, 0.5, 0f, 0f)
    target.previousLevels = target.previousLevels.also(MutableList<Location>::clear)
    target.health = 0.0
    target.sendMessage("<system>Your progress has been reset by an admin.".mm)
    sender.sendMessage("<system><s>${target.name}</s>'s progress has been reset.".mm)
}