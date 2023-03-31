import kotlinx.cinterop.*
import platform.CoreFoundation.*

fun CFTypeRef.asCFBag(): CFBagRef {
    check(CFGetTypeID(this) == CFBagGetTypeID()) {
        "value is not of type CFBag"
    }
    return this.reinterpret()
}

fun cfBagOf(vararg items: COpaquePointer?) = cfBagOf(items.toList(), cfBagCallback = null)

fun cfBagOf(items: List<COpaquePointer?>, cfBagCallback: CValuesRef<CFBagCallBacks>? = null) = memScoped {
    CFBagCreate(kCFAllocatorDefault, allocArray(items.size) { items[it] }, items.size.convert(), cfBagCallback)
} ?: error("Could not create CFBagSet")

fun CFBagRef.count() = size

val CFBagRef.size get() = CFBagGetCount(this).toInt()

fun CFBagRef.contains(item: COpaquePointer?) = CFBagContainsValue(this, item)

fun CFBagRef.copy() = CFBagCreateCopy(null, this)
