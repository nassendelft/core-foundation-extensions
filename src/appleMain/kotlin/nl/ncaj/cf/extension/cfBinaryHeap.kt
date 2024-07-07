package nl.ncaj.cf.extension

import kotlinx.cinterop.*
import platform.CoreFoundation.*

fun cfBinaryHeapOf(
    callbacks: CFBinaryHeapCallBacks,
    vararg items: COpaquePointer?
) = cfBinaryHeapOf(callbacks, items.toList())

fun cfBinaryHeapOf(
    callbacks: CFBinaryHeapCallBacks,
    items: List<COpaquePointer?>,
) = memScoped {
    CFBinaryHeapCreate(kCFAllocatorDefault, items.size.convert(), callbacks.ptr, null)
        ?: error("Could not create CFBinaryHeap")
}.apply { items.forEach(::add) }

fun CFBinaryHeapRef.add(value: COpaquePointer?) = CFBinaryHeapAddValue(this, value)

fun CFBinaryHeapRef.count(value: COpaquePointer?) = CFBinaryHeapGetCountOfValue(this, value)

fun CFBinaryHeapRef.clear() = CFBinaryHeapRemoveAllValues(this)

fun CFBinaryHeapRef.removeMinimum() = CFBinaryHeapRemoveMinimumValue(this)

val CFBinaryHeapRef.minimum: COpaquePointer?
    get() = memScoped {
        val ptr = alloc<COpaquePointerVar>()
        val contains = CFBinaryHeapGetMinimumIfPresent(this@minimum, ptr.ptr)
        return if (contains) ptr.value else null
    }

val CFBinaryHeapRef.size get() = CFBinaryHeapGetCount(this).toInt()

fun CFBinaryHeapRef.contains(item: COpaquePointer?) = CFBinaryHeapContainsValue(this, item)

fun CFBinaryHeapRef.copy(capacity: Int = this.size) =
    CFBinaryHeapCreateCopy(null, capacity.convert(), this)

fun CFTypeRef.asCFBinaryHeap(): CFBinaryHeapRef {
    check(CFGetTypeID(this) == CFBinaryHeapGetTypeID()) {
        "value is not of type CFBinaryHeap"
    }
    return this.reinterpret()
}
