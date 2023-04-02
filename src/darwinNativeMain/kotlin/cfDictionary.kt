import kotlinx.cinterop.*
import platform.CoreFoundation.*
import platform.Foundation.CFBridgingRelease
import platform.Foundation.CFBridgingRetain

fun cfDictionaryOf(vararg items: Pair<COpaquePointer, COpaquePointer?>) = cfDictionaryOf(
    items.toList(),
    cfDictionaryKeyCallBacks = null,
    cfDictionaryValueCallBacks = null
)

fun cfDictionaryOf(
    items: List<Pair<COpaquePointer, COpaquePointer?>>,
    cfDictionaryKeyCallBacks: CValuesRef<CFDictionaryKeyCallBacks>? = null,
    cfDictionaryValueCallBacks: CValuesRef<CFDictionaryValueCallBacks>? = null
) = memScoped {
    CFDictionaryCreate(
        kCFAllocatorDefault,
        allocArrayOf(items.map { it.first }),
        allocArrayOf(items.map { it.second }),
        items.size.toLong(),
        cfDictionaryKeyCallBacks,
        cfDictionaryValueCallBacks
    )
} ?: error("Could not create CFDictionary")

operator fun CFDictionaryRef.get(key: COpaquePointer?): COpaquePointer? = memScoped {
    val value: COpaquePointerVar = alloc()
    val hasValue: Boolean = CFDictionaryGetValueIfPresent(this@get, key, value.ptr)
    if (!hasValue) return null
    return value.value ?: error("value for key '$key' not found")
}

val CFDictionaryRef.size get() = CFDictionaryGetCount(this).toInt()

fun CFDictionaryRef.count() = size

fun CFDictionaryRef.contains(key: COpaquePointer?) = containsKey(key)

fun CFDictionaryRef.containsKey(key: COpaquePointer?) = CFDictionaryContainsKey(this, key)

fun CFDictionaryRef.containsValue(value: COpaquePointer?) = CFDictionaryContainsValue(this, value)

@Suppress("UNCHECKED_CAST")
fun CFDictionaryRef.toMap() = CFBridgingRelease(this) as Map<COpaquePointer?, COpaquePointer?>

fun CFDictionaryRef.getCFDictionary(key: COpaquePointer?) = checkNotNull(getCFDictionaryOrNull(key))

fun CFDictionaryRef.getCFDictionaryOrNull(key: COpaquePointer?) = get(key)?.asCFDictionary()

fun CFDictionaryRef.getCFMutableDictionary(key: COpaquePointer?) = checkNotNull(getCFMutableDictionaryOrNull(key))

fun CFDictionaryRef.getCFMutableDictionaryOrNull(key: COpaquePointer?) = get(key)?.asCFMutableDictionary()

fun CFDictionaryRef.getCFArray(key: COpaquePointer?) = checkNotNull(getCFArrayOrNull(key))

fun CFDictionaryRef.getCFArrayOrNull(key: COpaquePointer?) = get(key)?.asCFArray()

fun CFDictionaryRef.getCFMutableArray(key: COpaquePointer?) = checkNotNull(getCFMutableArrayOrNull(key))

fun CFDictionaryRef.getCFMutableArrayOrNull(key: COpaquePointer?) = get(key)?.asCFMutableArray()

fun CFDictionaryRef.getCFSet(key: COpaquePointer?) = checkNotNull(getCFSetOrNull(key))

fun CFDictionaryRef.getCFSetOrNull(key: COpaquePointer?) = get(key)?.asCFSet()

fun CFDictionaryRef.getCFMutableSet(key: COpaquePointer?) = checkNotNull(getCFMutableSetOrNull(key))

fun CFDictionaryRef.getCFMutableSetOrNull(key: COpaquePointer?) = get(key)?.asCFMutableSet()

fun CFDictionaryRef.getCFBag(key: COpaquePointer?) = checkNotNull(getCFBagOrNull(key))

fun CFDictionaryRef.getCFBagOrNull(key: COpaquePointer?) = get(key)?.asCFBag()

fun CFDictionaryRef.getCFMutableBag(key: COpaquePointer?) = checkNotNull(getCFMutableBagOrNull(key))

fun CFDictionaryRef.getCFMutableBagOrNull(key: COpaquePointer?) = get(key)?.asCFMutableBag()

fun CFDictionaryRef.getCFData(key: COpaquePointer?) = checkNotNull(getCFDataOrNull(key))

fun CFDictionaryRef.getCFDataOrNull(key: COpaquePointer?) = get(key)?.asCFData()

fun CFDictionaryRef.getCFMutableData(key: COpaquePointer?) = checkNotNull(getCFMutableDataOrNull(key))

fun CFDictionaryRef.getCFMutableDataOrNull(key: COpaquePointer?) = get(key)?.asCFMutableData()

fun CFDictionaryRef.getCFBinaryHeap(key: COpaquePointer?) = checkNotNull(getCFBinaryHeapOrNull(key))

fun CFDictionaryRef.getCFBinaryHeapOrNull(key: COpaquePointer?) = get(key)?.asCFBinaryHeap()

fun CFDictionaryRef.getCFString(key: COpaquePointer?) = checkNotNull(getCFStringOrNull(key))

fun CFDictionaryRef.getCFStringOrNull(key: COpaquePointer?) = get(key)?.asCFString()

fun CFDictionaryRef.getCFNumber(key: COpaquePointer?) = checkNotNull(getCFNumberOrNull(key))

fun CFDictionaryRef.getCFNumberOrNull(key: COpaquePointer?) = get(key)?.asCFNumber()

fun CFDictionaryRef.getCFBoolean(key: COpaquePointer?) = checkNotNull(getCFBooleanOrNull(key))

fun CFDictionaryRef.getCFBooleanOrNull(key: COpaquePointer?) = get(key)?.asCFBoolean()

fun CFDictionaryRef.getString(key: COpaquePointer?) = checkNotNull(getStringOrNull(key))

fun CFDictionaryRef.getStringOrNull(key: COpaquePointer?) = get(key)?.asCFString()?.stringValue

fun CFDictionaryRef.getBoolean(key: COpaquePointer?) = checkNotNull(getBooleanOrNull(key))

fun CFDictionaryRef.getBooleanOrNull(key: COpaquePointer?) = get(key)?.asCFBoolean()?.booleanValue

fun CFDictionaryRef.getLong(key: COpaquePointer?) = checkNotNull(getLongOrNull(key))

fun CFDictionaryRef.getLongOrNull(key: COpaquePointer?) = get(key)?.asCFNumber()?.longValue

fun CFDictionaryRef.getInt(key: COpaquePointer?) = checkNotNull(getIntOrNull(key))

fun CFDictionaryRef.getIntOrNull(key: COpaquePointer?) = get(key)?.asCFNumber()?.intValue

fun CFDictionaryRef.getShort(key: COpaquePointer?) = checkNotNull(getShortOrNull(key))

fun CFDictionaryRef.getShortOrNull(key: COpaquePointer?) = get(key)?.asCFNumber()?.shortValue

fun CFDictionaryRef.getByte(key: COpaquePointer?) = checkNotNull(getByteOrNull(key))

fun CFDictionaryRef.getByteOrNull(key: COpaquePointer?) = get(key)?.asCFNumber()?.byteValue

fun CFDictionaryRef.getFloat(key: COpaquePointer?) = getFloatOrNull(key)

fun CFDictionaryRef.getFloatOrNull(key: COpaquePointer?) = get(key)?.asCFNumber()?.floatValue

fun CFDictionaryRef.getDouble(key: COpaquePointer?) = checkNotNull(getDoubleOrNull(key))

fun CFDictionaryRef.getDoubleOrNull(key: COpaquePointer?) = get(key)?.asCFNumber()?.doubleValue


fun CFTypeRef.asCFDictionary(): CFDictionaryRef {
    check(CFGetTypeID(this) == CFDictionaryGetTypeID()) {
        "value is not of type CFDictionary"
    }
    return this.reinterpret()
}

fun Map<COpaquePointer?, COpaquePointer?>.toCFDictionary() = CFBridgingRetain(this)?.asCFDictionary()
    ?: error("Could not create CFDictionary")