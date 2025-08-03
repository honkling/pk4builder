@file:Listener

package me.honkling.pk4builder.event

import me.honkling.commando.spigot.event.Listener
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.FoodLevelChangeEvent

private fun onHungerChange(event: FoodLevelChangeEvent) {
    event.entity.foodLevel = 20
    event.isCancelled = true
}

private fun onDamage(event: EntityDamageEvent) {
    val victim = event.entity as? Player ?: return

    if (event is EntityDamageByEntityEvent && event.damager is Player) {
        event.isCancelled = true
        return
    }

    if (event.finalDamage < victim.health)
        event.damage = 0.0
}