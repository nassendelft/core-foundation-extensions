import kotlinx.cinterop.*
import platform.CoreFoundation.*

fun CFTypeRef.asCFMutableBag(): CFMutableBagRef {
    check(CFGetTypeID(this) == CFBagGetTypeID()) {
        "value is not of type CFSet"
    }
    return this.reinterpret()
}

@Suppress("FunctionName")
fun CFMutableBag(capacity: Int = 0, cfBagCallback: CValuesRef<CFBagCallBacks>? = null) =
    CFBagCreateMutable(null, capacity.toLong(), cfBagCallback)
        ?: error("Could not create CFMutableBag")

fun cfMutableBagOf(vararg items: COpaquePointer?) = CFMutableBag()
    .apply { items.forEach(::set) }

fun CFMutableBagRef.set(value: COpaquePointer?) = CFBagSetValue(this, value)

fun CFMutableBagRef.remove(value: COpaquePointer?) = CFBagRemoveValue(this, value)
