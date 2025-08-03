package me.honkling.pk4builder.profile

import me.honkling.pk4builder.instance
import org.bukkit.Location
import org.bukkit.NamespacedKey
import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.persistence.PersistentDataHolder
import org.bukkit.persistence.PersistentDataType
import java.lang.reflect.ParameterizedType
import kotlin.reflect.KClass
import kotlin.reflect.KProperty
import kotlin.reflect.KType
import kotlin.reflect.full.createType
import kotlin.reflect.typeOf

inline fun <reified T : Any> createKey(fallbackValue: T): Key<T> {
    val persistentType = getPersistentDataType<T>()
    return Key(persistentType, fallbackValue)
}

inline fun <reified T> createKey(): Key<T?> {
    val persistentType = getPersistentDataType<T?>()
    return Key(persistentType, null)
}

class Key<T>(val type: PersistentDataType<*, T>, val fallbackValue: T?) {
    lateinit var key: NamespacedKey; private set

    @Suppress("UNCHECKED_CAST")
    operator fun getValue(thisRef: PersistentDataContainer, property: KProperty<*>): T {
        if (!::key.isInitialized)
            key = NamespacedKey(instance, property.name)

        val value = thisRef.get(key, type as PersistentDataType<*, *>) as T?
            ?: fallbackValue
            ?: null as T

        if (value is List<*>)
            return value.toMutableList() as T

        return value
    }

    operator fun setValue(thisRef: PersistentDataContainer, property: KProperty<*>, value: T) {
        if (!::key.isInitialized)
            key = NamespacedKey(instance, property.name)

        if (value == null)
            return thisRef.remove(key)

        thisRef.set(key, type, value)
    }

    @Suppress("UNCHECKED_CAST")
    operator fun getValue(thisRef: PersistentDataHolder, property: KProperty<*>): T {
        return getValue(thisRef.persistentDataContainer, property)
    }

    operator fun setValue(thisRef: PersistentDataHolder, property: KProperty<*>, value: T) {
        return setValue(thisRef.persistentDataContainer, property, value)
    }
}

inline fun <reified T> getPersistentDataType(): PersistentDataType<*, T> {
    val clazz = T::class.java

    @Suppress("UNCHECKED_CAST")
    return when (clazz) {
        List::class.java -> {
            val tree = mutableListOf<KClass<*>>()
            var type = typeOf<T>()

            while (type.arguments.isNotEmpty()) {
                val component = type.arguments[0].type!!
                val classifier = component.classifier as KClass<*>
                tree += classifier
                type = component
            }

            tree.reverse()

            var listType: PersistentDataType<*, *>? = null

            for (clazz in tree) {
                val dataType = getSimplePersistentDataType(clazz.java)

                listType = if (listType == null)
                    PersistentDataType.LIST.listTypeFrom(dataType)
                else PersistentDataType.LIST.listTypeFrom(listType)
            }

            listType
        }
        else -> getSimplePersistentDataType(clazz)
    } as PersistentDataType<*, T>
}

fun <T> getSimplePersistentDataType(clazz: Class<T>): PersistentDataType<*, T> {
    @Suppress("UNCHECKED_CAST")
    return when (clazz) {
        Byte::class.javaObjectType, Byte::class.javaPrimitiveType -> PersistentDataType.BYTE
        Short::class.javaObjectType, Short::class.javaPrimitiveType -> PersistentDataType.SHORT
        Int::class.javaObjectType, Int::class.javaPrimitiveType -> PersistentDataType.INTEGER
        Long::class.javaObjectType, Long::class.javaPrimitiveType -> PersistentDataType.LONG
        Float::class.javaObjectType, Float::class.javaPrimitiveType -> PersistentDataType.FLOAT
        Double::class.javaObjectType, Double::class.javaPrimitiveType -> PersistentDataType.DOUBLE
        String::class.java -> PersistentDataType.STRING
        Location::class.java -> LocationDataType
        else -> throw IllegalArgumentException("Unknown persistent data type ${clazz.name}")
    } as PersistentDataType<*, T>
}