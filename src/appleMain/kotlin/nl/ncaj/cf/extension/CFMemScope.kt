package nl.ncaj.cf.extension

import kotlinx.cinterop.COpaquePointer
import platform.CoreFoundation.CFRelease
import platform.CoreFoundation.CFTypeRef

class CFMemScope {

    private val items = mutableListOf<CFTypeRef>()

    fun scoped(type: CFTypeRef) = type.also(items::add)

    fun cfArray(list: List<COpaquePointer?>) = list.toCFArray().also(::scoped)

    fun cfMutableArray(list: MutableList<COpaquePointer?>) = list.toCFMutableArray().also(::scoped)

    fun cfMutableArray(size: Int = 0) = CFMutableArray(size).also(::scoped)

    fun cfDictionary(map: Map<COpaquePointer?, COpaquePointer?>) = map.toCFDictionary().also(::scoped)

    fun cfMutableDictionary(map: MutableMap<COpaquePointer?, COpaquePointer?>) =
        map.toCFMutableDictionary().also(::scoped)

    fun cfMutableDictionary(size: Int = 0) = CFMutableDictionary(size).also(::scoped)

    fun cfSet(set: Set<COpaquePointer?>) = set.toCFSet().also(::scoped)

    fun cfMutableSet(set: MutableSet<COpaquePointer?>) = set.toCFMutableSet().also(::scoped)

    fun cfMutableSet(size: Int = 0) = CFMutableSet(size).also(::scoped)

    fun cfData(list: List<UByte>) = list.toCFData().also(::scoped)

    fun cfMutableData(list: MutableList<UByte>) = list.toCFMutableData().also(::scoped)

    fun cfMutableData(size: Int = 0) = CFMutableData(size).also(::scoped)

    fun cfString(string: String) = string.toCFString().also(::scoped)

    fun cfNumber(value: Byte) = value.toCFNumber().also(::scoped)

    fun cfNumber(value: Short) = value.toCFNumber().also(::scoped)

    fun cfNumber(value: Int) = value.toCFNumber().also(::scoped)

    fun cfNumber(value: Long) = value.toCFNumber().also(::scoped)

    fun cfNumber(value: Float) = value.toCFNumber().also(::scoped)

    fun cfNumber(value: Double) = value.toCFNumber().also(::scoped)

    @PublishedApi
    internal fun clear() = items.forEach(::CFRelease)

    companion object {
        inline fun <R> cfMemScoped(block: CFMemScope.() -> R): R {
            val scope = CFMemScope()
            try {
                return scope.block()
            } finally {
                scope.clear()
            }
        }
    }
}
