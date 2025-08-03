package me.honkling.pk4builder.lib

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.Tag
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import net.kyori.adventure.text.minimessage.tag.standard.StandardTags

val miniMessage = MiniMessage.builder()
    .tags(TagResolver.resolver(
        StandardTags.defaults(),
        Placeholder.parsed("system", "<s>pk4builder <gray>»</s> "),
        TagResolver.resolver("p", Tag.styling(NamedTextColor.WHITE)),
        TagResolver.resolver("s", Tag.styling(TextColor.color(27, 217, 106)))
    )).build()

val String.mm: Component
    get() = miniMessage.deserialize(this)