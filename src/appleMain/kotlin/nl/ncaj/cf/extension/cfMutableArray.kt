package nl.ncaj.cf.extension

import kotlinx.cinterop.COpaquePointer
import kotlinx.cinterop.CValuesRef
import kotlinx.cinterop.reinterpret
import platform.CoreFoundation.*
import platform.Foundation.CFBridgingRelease
import platform.Foundation.CFBridgingRetain

@Suppress("FunctionName")
fun CFMutableArray(
    size: Int = 0,
    cfArrayCallBacks: CValuesRef<CFArrayCallBacks>? = null
) = CFArrayCreateMutable(null, size.toLong(), cfArrayCallBacks)
    ?: error("Could not create CFMutableArray")

fun cfMutableArrayOf(vararg items: COpaquePointer?) = cfMutableArrayOf(items.toList(), cfArrayCallBacks = null)

fun cfMutableArrayOf(
    items: List<COpaquePointer?>,
    cfArrayCallBacks: CValuesRef<CFArrayCallBacks>? = null
) = CFMutableArray(cfArrayCallBacks = cfArrayCallBacks).apply { items.forEach { append(it) } }

operator fun CFMutableArrayRef.set(index: Int, item: COpaquePointer?) =
    CFArraySetValueAtIndex(this, index.toLong(), item)

fun CFMutableArrayRef.append(array: CFArrayRef, inRange: IntRange = 0 .. array.size) =
    CFArrayAppendArray(this, array, inRange.toCFRange())

fun CFMutableArrayRef.append(item: COpaquePointer?) =
    CFArrayAppendValue(this, item)

fun CFMutableArrayRef.insert(index: Int, item: COpaquePointer?) {
    check(index in 0 until size) { "Index ($index) is out of bounds ($size)" }
    CFArrayInsertValueAtIndex(this, index.toLong(), item)
}

fun CFMutableArrayRef.removeAt(index: Int): COpaquePointer? {
    check(index in 0 until size) { "Index ($index) is out of bounds ($size)" }
    val value = this[index]
    CFArrayRemoveValueAtIndex(this, index.toLong())
    return value
}

fun CFMutableArrayRef.removeAll() = CFArrayRemoveAllValues(this)

fun CFMutableArrayRef.copy(capacity: Int = 0) =
    CFArrayCreateMutableCopy(null, capacity.toLong(), this)
        ?: error("Could not create CFMutableArray")

@Suppress("UNCHECKED_CAST")
fun CFMutableArrayRef.asMutableList() = CFBridgingRelease(this) as MutableList<COpaquePointer?>

fun CFTypeRef.asCFMutableArray(): CFMutableArrayRef {
    check(CFGetTypeID(this) == CFArrayGetTypeID()) {
        "value is not of type CFArray"
    }
    return this.reinterpret()
}

fun MutableList<COpaquePointer?>.toCFMutableArray() = CFBridgingRetain(this)?.asCFMutableArray()
    ?: error("Could not create CFMutableSet")