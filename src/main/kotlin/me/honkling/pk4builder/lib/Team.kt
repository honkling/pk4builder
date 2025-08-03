package me.honkling.pk4builder.lib

import org.bukkit.Bukkit
import org.bukkit.scoreboard.Team

private val scoreboard = Bukkit.getScoreboardManager().mainScoreboard
val noCollisionTeam = scoreboard.getTeam("no-collision")
    ?: scoreboard.registerNewTeam("no-collision").also {
        it.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.NEVER)
    }