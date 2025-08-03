@file:Listener

package me.honkling.pk4builder.event

import me.honkling.commando.spigot.event.Listener
import me.honkling.pk4builder.lib.mm
import me.honkling.pk4builder.lib.noCollisionTeam
import me.honkling.pk4builder.profile.currentLevel
import net.kyori.adventure.inventory.Book
import net.kyori.adventure.text.Component
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.event.player.PlayerRespawnEvent

private val welcomeBook = Book.book(Component.empty(), Component.empty(), listOf(
    """
        welcome to <#1bd96a>pk4builder!<reset>
        
           this is a parkour      server. if you finish the course, you will be rewarded with builder.
                    :)
        
        our builders help extend the map. there are some rules, so expect them when you're done.
    """.trimIndent().mm
))

private fun onPlayerJoin(event: PlayerJoinEvent) {
    val player = event.player
    noCollisionTeam.addPlayer(player)

    event.joinMessage("<system>Welcome, <s>${player.name}</s>!".mm)

//    if (!player.hasPlayedBefore())
        player.openBook(welcomeBook)
}

private fun onPlayerQuit(event: PlayerQuitEvent) {
    event.quitMessage(Component.empty())
}

private fun onPlayerRespawn(event: PlayerRespawnEvent) {
    event.respawnLocation = event.player.currentLevel
}