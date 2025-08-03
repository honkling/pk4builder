package me.honkling.pk4builder

import me.honkling.commando.spigot.SpigotCommando
import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Level

val instance = JavaPlugin.getPlugin(PKBuilder::class.java)
lateinit var world: World; private set

class PKBuilder : JavaPlugin() {
    override fun onEnable() {
        world = Bukkit.getWorlds()[0]

        val commando = SpigotCommando(this)
        commando.logger.level = Level.ALL
        commando.register("me.honkling.pk4builder", "command", "event")
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}
