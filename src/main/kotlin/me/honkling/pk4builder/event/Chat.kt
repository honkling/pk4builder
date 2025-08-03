@file:Listener

package me.honkling.pk4builder.event

import io.papermc.paper.event.player.AsyncChatEvent
import me.honkling.commando.spigot.event.Listener
import me.honkling.pk4builder.lib.mm
import me.honkling.pk4builder.lib.secondaryColor
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.luckperms.api.LuckPermsProvider

private fun onChat(event: AsyncChatEvent) {
    val player = event.player
    val message = event.message()

    val user = LuckPermsProvider.get().userManager.getUser(player.uniqueId)
    val prefix = user?.cachedData?.metaData?.prefix

    event.renderer { source, _, message, _ ->
        (prefix?.plus(" ")?.mm ?: Component.empty().color(secondaryColor))
            .append(player.name())
            .append(Component.text(": ")
                .color(NamedTextColor.WHITE)
                .append(message))
    }
}