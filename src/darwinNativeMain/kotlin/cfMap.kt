import kotlinx.cinterop.*
import platform.CoreFoundation.CFDictionaryGetKeysAndValues
import platform.CoreFoundation.CFDictionaryRef
import platform.Foundation.CFBridgingRetain

fun CFDictionaryRef.asCFMap() = CFMap(this)

fun Map<COpaquePointer?, COpaquePointer?>.toCFMap() = CFBridgingRetain(this)
    ?.asCFDictionary()
    ?.let(::CFMap)
    ?: error("Could not create CFDictionary")

open class CFMap internal constructor(
    protected val cfDictionary: CFDictionaryRef
) : Map<COpaquePointer?, COpaquePointer?> {

    override val entries: Set<Map.Entry<COpaquePointer?, COpaquePointer?>>
        get() = memScoped {
            val length = size
            val keys = allocArray<COpaquePointerVar>(length)
            val values = allocArray<COpaquePointerVar>(length)
            CFDictionaryGetKeysAndValues(cfDictionary, keys, values)
            (0 until length).map { CFMapEntry(keys[it], values[it]) }.toSet()
        }
    override val keys: Set<COpaquePointer?>
        get() = entries.map { it.key }.toSet()
    override val size: Int
        get() = cfDictionary.size
    override val values: Collection<COpaquePointer?>
        get() = entries.map { it.value }

    override fun isEmpty() = cfDictionary.size == 0

    override fun get(key: COpaquePointer?) = cfDictionary[key]

    override fun containsValue(value: COpaquePointer?) = cfDictionary.containsValue(value)

    override fun containsKey(key: COpaquePointer?) = cfDictionary.containsKey(key)

    private class CFMapEntry(
        override val key: COpaquePointer?,
        override val value: COpaquePointer?
    ) : Map.Entry<COpaquePointer?, COpaquePointer?>
}
