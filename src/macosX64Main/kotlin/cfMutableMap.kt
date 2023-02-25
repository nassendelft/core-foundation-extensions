import kotlinx.cinterop.*
import platform.CoreFoundation.CFDictionaryGetKeysAndValues
import platform.CoreFoundation.CFDictionaryRef
import platform.CoreFoundation.CFMutableDictionaryRef
import platform.Foundation.CFBridgingRetain

fun CFMutableDictionaryRef.asCFMutableMap() = CFMutableMap(this)

fun MutableMap<COpaquePointer?, COpaquePointer?>.toCFMutableMap() = CFBridgingRetain(this)
    ?.asCFMutableDictionary()
    ?.let(::CFMutableMap)
    ?: error("Could not create CFMutableDictionary")

class CFMutableMap internal constructor(
    cfDictionary: CFMutableDictionaryRef
) : MutableMap<COpaquePointer?, COpaquePointer?>, CFMap(cfDictionary) {

    override val entries: MutableSet<MutableMap.MutableEntry<COpaquePointer?, COpaquePointer?>>
        get() = super.entries.map { CFMutableMapEntry(cfDictionary, it.key) }.toMutableSet()

    override val keys: MutableSet<COpaquePointer?>
        get() = super.keys.toMutableSet()

    override val values: MutableCollection<COpaquePointer?>
        get() = super.values.toMutableList()

    override fun clear() = cfDictionary.clear()

    override fun remove(key: COpaquePointer?): COpaquePointer? {
        val element = cfDictionary[key]
        cfDictionary.remove(key)
        return element
    }

    override fun putAll(from: Map<out COpaquePointer?, COpaquePointer?>) {
        from.forEach { (key, value) -> cfDictionary.add(key, value) }
    }

    override fun put(key: COpaquePointer?, value: COpaquePointer?): COpaquePointer? {
        val element = cfDictionary[key]
        cfDictionary[key] = value
        return element
    }

    private class CFMutableMapEntry(
        private val cfDictionary: CFMutableDictionaryRef,
        override val key: COpaquePointer?,
    ) : MutableMap.MutableEntry<COpaquePointer?, COpaquePointer?> {
        override val value: COpaquePointer?
            get() = cfDictionary[key]

        override fun setValue(newValue: COpaquePointer?): COpaquePointer? {
            val element = cfDictionary[key]
            cfDictionary[key] = newValue
            return element
        }
    }
}
