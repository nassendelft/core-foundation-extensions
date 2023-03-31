import kotlinx.cinterop.*
import platform.CoreFoundation.*

fun CFTypeRef.asCFData(): CFDataRef {
    check(CFGetTypeID(this) == CFDataGetTypeID()) {
        "value is not of type CFData"
    }
    return this.reinterpret()
}

fun cfDataOf(vararg items: UByte) = cfDataOf(items.toList())

fun cfDataOf(items: List<UByte>) = memScoped {
    CFDataCreate(kCFAllocatorDefault, allocArray(items.size) { this.value = items[it] }, items.size.convert())
} ?: error("Could not create CFData")

fun CFDataRef.length() = size

val CFDataRef.size get() = CFDataGetLength(this).toInt()

fun CFDataRef.copy() = CFDataCreateCopy(kCFAllocatorDefault, this)

fun CFDataRef.toList(): List<UByte> {
    val ptr = CFDataGetBytePtr(this) ?: error("Could not read CFData")
    return List(size) { ptr[it] }
}

fun List<UByte>.toCFData() = cfDataOf(this)
