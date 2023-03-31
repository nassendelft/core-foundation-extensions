import kotlinx.cinterop.*
import platform.CoreFoundation.*
import platform.Foundation.CFBridgingRelease
import platform.Foundation.CFBridgingRetain

fun cfSetOf(vararg items: COpaquePointer?) = cfSetOf(items.toList(), cfSetCallback = null)

fun cfSetOf(items: List<COpaquePointer?>, cfSetCallback: CValuesRef<CFSetCallBacks>? = null) = memScoped {
    val array = allocArray<COpaquePointerVar>(items.size)
    items.forEachIndexed { index, value -> array[index] = value }
    CFSetCreate(kCFAllocatorDefault, array, items.size.toLong(), cfSetCallback)
        ?: error("Could not create CFSet")
}

val CFSetRef.size get() = CFSetGetCount(this).toInt()

fun CFSetRef.count() = size

@Suppress("UNCHECKED_CAST")
fun CFSetRef.toSet() = CFBridgingRelease(this) as Set<COpaquePointer?>

fun CFSetRef.contains(item: COpaquePointer?) = CFSetContainsValue(this, item)


@Suppress("FunctionName")
fun CFMutableSet(capacity: Int = 0, cfSetCallback: CValuesRef<CFSetCallBacks>? = null) =
    CFSetCreateMutable(kCFAllocatorDefault, capacity.convert(), cfSetCallback)
        ?: error("Could not create CFMutableSet")

fun cfMutableSetOf(vararg items: COpaquePointer?) = CFMutableSet()
    .apply { items.forEach(::set) }

fun CFMutableSetRef.set(value: COpaquePointer?) = CFSetSetValue(this, value)

fun CFMutableSetRef.remove(value: COpaquePointer?) = CFSetRemoveValue(this, value)

@Suppress("UNCHECKED_CAST")
fun CFMutableSetRef.toMutableSet() = CFBridgingRelease(this) as MutableSet<COpaquePointer?>


fun CFTypeRef.asCFSet(): CFSetRef {
    check(CFGetTypeID(this) == CFSetGetTypeID()) {
        "value is not of type CFSet"
    }
    return this.reinterpret()
}

fun CFTypeRef.asCFMutableSet(): CFMutableSetRef {
    check(CFGetTypeID(this) == CFSetGetTypeID()) {
        "value is not of type CFSet"
    }
    return this.reinterpret()
}


fun MutableSet<COpaquePointer?>.toCFMutableSet() = CFBridgingRetain(this)?.asCFMutableSet()
    ?: error("Could not create CFMutableSet")

fun Set<COpaquePointer?>.toCFSet(): CFSetRef = CFBridgingRetain(this)?.asCFSet()
    ?: error("Could not create CFSet")