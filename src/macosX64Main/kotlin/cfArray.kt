import kotlinx.cinterop.*
import platform.CoreFoundation.*
import platform.Foundation.CFBridgingRelease
import platform.Foundation.CFBridgingRetain

fun cfArrayOf(
    items: List<COpaquePointer?>,
    cfArrayCallBacks: CValuesRef<CFArrayCallBacks>? = null
) = memScoped { CFArrayCreate(null, allocArrayOf(items), items.size.toLong(), cfArrayCallBacks) }
    ?: error("Could not create CFArray")

fun cfArrayOf(vararg items: COpaquePointer?) = cfArrayOf(items.toList(), cfArrayCallBacks = null)

operator fun CFArrayRef.get(index: Int): COpaquePointer? {
    check(index in 0 until size) { "Index ($index) is out of bounds ($size)" }
    return CFArrayGetValueAtIndex(this@get, index.toLong())
}

fun CFArrayRef.indexOf(value: COpaquePointer?, inRange: IntRange = 0..size) =
    CFArrayGetFirstIndexOfValue(this, cfRangeOf(inRange), value).toInt()

fun CFArrayRef.lastIndexOf(value: COpaquePointer?, inRange: IntRange = 0..size) =
    CFArrayGetLastIndexOfValue(this, cfRangeOf(inRange), value).toInt()

fun CFArrayRef.count() = size

fun CFArrayRef.countOf(value: COpaquePointer?, inRange: IntRange = 0..size) =
    CFArrayGetCountOfValue(this, cfRangeOf(inRange), value)

val CFArrayRef.size get() = CFArrayGetCount(this).toInt()

@Suppress("UNCHECKED_CAST")
fun CFArrayRef.toList() = CFBridgingRelease(this) as List<COpaquePointer?>

fun CFArrayRef.getCFDictionary(index: Int) = checkNotNull(getCFDictionaryOrNull(index))

fun CFArrayRef.getCFDictionaryOrNull(index: Int) = get(index)?.asCFDictionary()

fun CFArrayRef.getCFArray(index: Int) = checkNotNull(getCFArrayOrNull(index))

fun CFArrayRef.getCFArrayOrNull(index: Int) = get(index)?.asCFArray()

fun CFArrayRef.getCFSet(index: Int) = checkNotNull(getCFSetOrNull(index))

fun CFArrayRef.getCFSetOrNull(index: Int) = get(index)?.asCFSet()

fun CFArrayRef.getCFBag(index: Int) = checkNotNull(getCFBagOrNull(index))

fun CFArrayRef.getCFBagOrNull(index: Int) = get(index)?.asCFBag()

fun CFArrayRef.getCFString(index: Int) = checkNotNull(getCFStringOrNull(index))

fun CFArrayRef.getCFStringOrNull(index: Int) = get(index)?.asCFString()

fun CFArrayRef.getCFNumber(index: Int) = checkNotNull(getCFNumberOrNull(index))

fun CFArrayRef.getCFNumberOrNull(index: Int) = get(index)?.asCFNumber()

fun CFArrayRef.getCFBoolean(index: Int) = checkNotNull(getCFBooleanOrNull(index))

fun CFArrayRef.getCFBooleanOrNull(index: Int) = get(index)?.asCFBoolean()

fun CFArrayRef.getString(index: Int) = checkNotNull(getStringOrNull(index))

fun CFArrayRef.getStringOrNull(index: Int) = get(index)?.asCFString()?.stringValue

fun CFArrayRef.getBoolean(index: Int) = checkNotNull(getBooleanOrNull(index))

fun CFArrayRef.getBooleanOrNull(index: Int) = get(index)?.asCFBoolean()?.booleanValue

fun CFArrayRef.getLong(index: Int) = checkNotNull(getLongOrNull(index))

fun CFArrayRef.getLongOrNull(index: Int) = get(index)?.asCFNumber()?.longValue

fun CFArrayRef.getInt(index: Int) = checkNotNull(getIntOrNull(index))

fun CFArrayRef.getIntOrNull(index: Int) = getCFNumberOrNull(index)?.intValue

fun CFArrayRef.getShort(index: Int) = checkNotNull(getShortOrNull(index))

fun CFArrayRef.getShortOrNull(index: Int) = getCFNumberOrNull(index)?.shortValue

fun CFArrayRef.getByte(index: Int) = checkNotNull(getByteOrNull(index))

fun CFArrayRef.getByteOrNull(index: Int) = getCFNumberOrNull(index)?.byteValue

fun CFArrayRef.getFloat(index: Int) = checkNotNull(getFloatOrNull(index))

fun CFArrayRef.getFloatOrNull(index: Int) = getCFNumberOrNull(index)?.floatValue

fun CFArrayRef.getDouble(index: Int) = checkNotNull(getDoubleOrNull(index))

fun CFArrayRef.getDoubleOrNull(index: Int) = getCFNumberOrNull(index)?.doubleValue

fun CFTypeRef.asCFArray(): CFArrayRef {
    check(CFGetTypeID(this) == CFArrayGetTypeID()) {
        "value is not of type CFArray"
    }
    return this.reinterpret()
}

fun List<COpaquePointer?>.toCFArray() = CFBridgingRetain(this)?.asCFArray()
    ?: error("Could not create CFArray")
