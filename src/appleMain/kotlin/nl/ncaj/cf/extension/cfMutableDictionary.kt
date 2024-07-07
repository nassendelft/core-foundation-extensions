package nl.ncaj.cf.extension

import kotlinx.cinterop.COpaquePointer
import kotlinx.cinterop.CValuesRef
import kotlinx.cinterop.convert
import kotlinx.cinterop.reinterpret
import platform.CoreFoundation.*
import platform.Foundation.CFBridgingRelease
import platform.Foundation.CFBridgingRetain

@Suppress("FunctionName")
fun CFMutableDictionary(
    capacity: Int = 0,
    cfDictionaryKeyCallBacks: CValuesRef<CFDictionaryKeyCallBacks>? = null,
    cfDictionaryValueCallBacks: CValuesRef<CFDictionaryValueCallBacks>? = null
) = CFDictionaryCreateMutable(
    null,
    capacity.convert(),
    cfDictionaryKeyCallBacks,
    cfDictionaryValueCallBacks
) ?: error("Could not create CFMutableDictionary")

fun cfMutableDictionaryOf(vararg items: Pair<COpaquePointer?, COpaquePointer?>) = CFMutableDictionary()
    .apply { items.forEach { (key, value) -> set(key, value) } }

operator fun CFMutableDictionaryRef.set(key: COpaquePointer?, value: COpaquePointer?) =
    CFDictionarySetValue(this, key, value)

fun CFMutableDictionaryRef.add(key: COpaquePointer?, value: COpaquePointer?) = CFDictionaryAddValue(this, key, value)

fun CFMutableDictionaryRef.remove(key: COpaquePointer?) = CFDictionaryRemoveValue(this, key)

fun CFMutableDictionaryRef.removeAll() = CFDictionaryRemoveAllValues(this)

@Suppress("UNCHECKED_CAST")
fun CFMutableDictionaryRef.toMutableMap() = CFBridgingRelease(this) as MutableMap<COpaquePointer?, COpaquePointer?>


fun CFTypeRef.asCFMutableDictionary(): CFMutableDictionaryRef {
    check(CFGetTypeID(this) == CFDictionaryGetTypeID()) {
        "value is not of type CFDictionary"
    }
    return this.reinterpret()
}

fun MutableMap<COpaquePointer?, COpaquePointer?>.toCFMutableDictionary() = CFBridgingRetain(this)?.asCFMutableDictionary()
    ?: error("Could not create CFMutableDictionary")
