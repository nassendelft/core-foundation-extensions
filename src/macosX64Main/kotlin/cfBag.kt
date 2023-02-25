import kotlinx.cinterop.*
import platform.CoreFoundation.*

fun CFTypeRef.asCFBag(): CFBagRef {
    check(CFGetTypeID(this) == CFBagGetTypeID()) {
        "value is not of type CFSet"
    }
    return this.reinterpret()
}

fun cfBagOf(vararg items: COpaquePointer?) = cfBagOf(items.toList(), cfBagCallback = null)

fun cfBagOf(items: List<COpaquePointer?>, cfBagCallback: CValuesRef<CFBagCallBacks>? = null) = memScoped {
    val array = allocArray<COpaquePointerVar>(items.size)
    items.forEachIndexed { index, value -> array[index] = value }
    CFBagCreate(null, array, items.size.toLong(), cfBagCallback)
        ?: error("Could not create CFBagSet")
}

val CFBagRef.size get() = CFBagGetCount(this).toInt()

fun CFBagRef.contains(item: COpaquePointer?) = CFBagContainsValue(this, item)

fun CFBagRef.copy() = CFBagCreateCopy(null, this)
