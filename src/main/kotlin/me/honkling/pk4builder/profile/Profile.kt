package me.honkling.pk4builder.profile

import me.honkling.pk4builder.world
import org.bukkit.Location
import org.bukkit.entity.Player

var Player.levelCounter by createKey<Int>(0)
var Player.currentLevel by createKey<Location>(Location(world, 0.5, 41.0, 0.5, 0f, 0f))
var Player.previousLevels by createKey<MutableList<Location>>(mutableListOf())