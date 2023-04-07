import kotlinx.cinterop.*
import platform.CoreFoundation.*

@Suppress("FunctionName")
fun CFData(items: List<UByte>) = memScoped {
    CFDataCreate(kCFAllocatorDefault, allocArray(items.size) { this.value = items[it] }, items.size.convert())
} ?: error("Could not create CFData")

@OptIn(ExperimentalUnsignedTypes::class)
fun cfDataOf(vararg items: UByte) = items.asByteArray().toCFData()

fun CFDataRef.length() = size

val CFDataRef.size get() = CFDataGetLength(this).toInt()

fun CFDataRef.copy() = CFDataCreateCopy(kCFAllocatorDefault, this)

fun CFDataRef.toList(): List<UByte> {
    val ptr = CFDataGetBytePtr(this) ?: error("Could not read CFData")
    return List(size) { ptr[it] }
}

fun CFTypeRef.asCFData(): CFDataRef {
    check(CFGetTypeID(this) == CFDataGetTypeID()) {
        "value is not of type CFData"
    }
    return this.reinterpret()
}

fun List<UByte>.toCFData() = CFData(this)

@OptIn(ExperimentalUnsignedTypes::class)
fun ByteArray.toCFData() =
    CFDataCreate(kCFAllocatorDefault, asUByteArray().refTo(0), size.convert())
