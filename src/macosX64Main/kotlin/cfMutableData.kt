import kotlinx.cinterop.*
import platform.CoreFoundation.*

@Suppress("FunctionName")
fun CFMutableData(size: Int) = CFDataCreateMutable(kCFAllocatorDefault, size.convert())
    ?: error("Could not create CFMutableData")

fun cfMutableDataOf(vararg items: UByte) = cfMutableDataOf(items.toList())

fun cfMutableDataOf(items: List<UByte>) = CFMutableData(items.size).apply { append(items) }

fun CFMutableDataRef.copy(size: Int = this.size) = CFDataCreateMutableCopy(kCFAllocatorDefault, size.convert(), this)

fun CFMutableDataRef.append(data: UByte) = append(listOf(data))

fun CFMutableDataRef.append(data: List<UByte>) = memScoped {
    CFDataAppendBytes(this@append, allocArray(data.size) { this.value = data[it] }, data.size.convert())
}
fun CFMutableDataRef.delete(range: IntRange) {
    CFDataDeleteBytes(this@delete, range.toCFRange())
}

fun CFMutableDataRef.toMutableList(): MutableList<UByte> {
    val ptr = CFDataGetMutableBytePtr(this) ?: error("Could not read CFMutableData")
    return MutableList(size) { ptr[it] }
}

fun CFTypeRef.asCFMutableData(): CFMutableDataRef {
    check(CFGetTypeID(this) == CFDataGetTypeID()) {
        "value is not of type CFMutableData"
    }
    return this.reinterpret()
}

fun MutableList<UByte>.toCFData() = cfMutableDataOf(this)
