package nl.ncaj.cf.extension

import kotlinx.cinterop.convert
import kotlinx.cinterop.reinterpret
import platform.CoreFoundation.*

@Suppress("FunctionName")
fun CFMutableBitVector(capacity: Int = 0) = CFBitVectorCreateMutable(kCFAllocatorDefault, capacity.convert())
    ?: error("Could not create CFMutableBag")

fun cfMutableBitVectorOf(vararg items: UByte) = CFMutableBitVector()
    .apply { items.forEachIndexed(::set) }

operator fun CFMutableBitVectorRef.set(index: Int, value: UByte) {
    check(value < 2u) { "Value can only be either 0x0 or 0x1" }
    check(index >= 0) { "Index can't be negative" }
    CFBitVectorSetBitAtIndex(this@set, index.convert(), value.convert())
}

fun CFMutableBitVectorRef.setAll(value: UByte) {
    check(value < 2u) { "Value can only be either 0x0 or 0x1" }
    CFBitVectorSetAllBits(this@setAll, value.convert())
}

fun CFMutableBitVectorRef.setAll(value: UByte, range: IntRange) {
    check(value < 2u) { "Value can only be either 0x0 or 0x1" }
    CFBitVectorSetBits(this@setAll, range.toCFRange(), value.convert())
}

fun CFMutableBitVectorRef.flip(index: Int) {
    check(index in 0 until size) { "Index out of bounds" }
    CFBitVectorFlipBitAtIndex(this@flip, index.convert())
}

fun CFMutableBitVectorRef.flip(range: IntRange) {
    check(range.first >= 0 && range.last < size) { "Range out of bounds" }
    CFBitVectorFlipBits(this@flip, range.toCFRange())
}

fun CFMutableBitVectorRef.resize(count: Int) {
    check(count >= 0) { "Count can't be negative" }
    CFBitVectorSetCount(this, count.convert())
}

fun CFTypeRef.asCFMutableBitVector(): CFMutableBitVectorRef {
    check(CFGetTypeID(this) == CFBitVectorGetTypeID()) {
        "value is not of type CFBitVector"
    }
    return this.reinterpret()
}
