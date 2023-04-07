import kotlinx.cinterop.*
import platform.CoreFoundation.*
import platform.darwin.UInt8Var

fun cfBitVectorOf(vararg items: UByte) = memScoped {
    check(items.all { it < 2u }) { "Each value can only be either 0x0 or 0x1" }
    CFBitVectorCreate(kCFAllocatorDefault, allocArrayOf(items.asByteArray()).reinterpret(), items.size.convert())
} ?: error("Could not create CFBitVector")

operator fun CFBitVectorRef.get(index: Int) = CFBitVectorGetBitAtIndex(this, index.convert())

fun CFBitVectorRef.asList(range: IntRange = 0 .. this.size): List<Byte> = memScoped {
    val bytes = allocArray<UInt8Var>(size)
    CFBitVectorGetBits(this@asList, range.toCFRange(), bytes)
    return bytes.readBytes(size).asList()
}

fun CFBitVectorRef.firstIndexOf(value: UByte, range: IntRange = 0 .. this.size) = memScoped {
    CFBitVectorGetFirstIndexOfBit(this@firstIndexOf, range.toCFRange(), value.convert())
}

fun CFBitVectorRef.lastIndexOf(value: UByte, range: IntRange = 0 .. this.size) = memScoped {
    CFBitVectorGetLastIndexOfBit(this@lastIndexOf, range.toCFRange(), value.convert())
}

fun CFBitVectorRef.count(value: UByte, range: IntRange = 0 .. this.size) =
    CFBitVectorGetCountOfBit(this, range.toCFRange(), value.convert())

val CFBitVectorRef.size get() = CFBitVectorGetCount(this).toInt()

fun CFBitVectorRef.contains(value: UByte, range: IntRange = 0 .. this.size) =
    CFBitVectorContainsBit(this, range.toCFRange(), value.convert())

fun CFBitVectorRef.copy() = CFBitVectorCreateCopy(null, this)

fun CFTypeRef.asCFBitVector(): CFBitVectorRef {
    check(CFGetTypeID(this) == CFBitVectorGetTypeID()) {
        "value is not of type CFBitVector"
    }
    return this.reinterpret()
}
