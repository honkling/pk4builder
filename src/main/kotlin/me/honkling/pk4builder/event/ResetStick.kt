@file:Listener

package me.honkling.pk4builder.event

import me.honkling.commando.spigot.event.Listener
import me.honkling.pk4builder.lib.mm
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.inventory.ItemStack

private val itemStack = ItemStack(Material.PINK_CANDLE).also {
    val meta = it.itemMeta
    meta.displayName("<s>Checkpoint".mm)
    meta.lore(listOf(Component.text("Takes you to your last checkpoint.")))
    it.itemMeta = meta
}

private fun onPlayerJoin(event: PlayerJoinEvent) {
    val player = event.player
    player.inventory.clear()
    player.inventory.addItem(itemStack)
}

private fun onRightClick(event: PlayerInteractEvent) {
    val player = event.player

    if (event.item != itemStack)
        return

    player.health = 0.0
}

private fun onDrop(event: PlayerDropItemEvent) {
    if (event.itemDrop.itemStack == itemStack)
        event.isCancelled = true
}