package nl.ncaj.cf.extension

import kotlinx.cinterop.*
import platform.CoreFoundation.*
import platform.Foundation.CFBridgingRetain
import platform.Foundation.NSNumber

val CFNumberRef.longValue: Long
    get() = memScoped {
        val value: LongVar = alloc()
        val hasValue = CFNumberGetValue(this@longValue, kCFNumberSInt64Type, value.ptr)
        if (!hasValue) error("Could not get value")
        value.value
    }

val CFNumberRef.intValue: Int
    get() = memScoped {
        val value: IntVar = alloc()
        val hasValue = CFNumberGetValue(this@intValue, kCFNumberSInt32Type, value.ptr)
        if (!hasValue) error("Could not get value")
        value.value
    }

val CFNumberRef.shortValue: Short
    get() = memScoped {
        val value: ShortVar = alloc()
        val hasValue = CFNumberGetValue(this@shortValue, kCFNumberSInt16Type, value.ptr)
        if (!hasValue) error("Could not get value")
        value.value
    }

val CFNumberRef.byteValue: Byte
    get() = memScoped {
        val value: ByteVar = alloc()
        val hasValue = CFNumberGetValue(this@byteValue, kCFNumberSInt8Type, value.ptr)
        if (!hasValue) error("Could not get value")
        value.value
    }

val CFNumberRef.floatValue: Float
    get() = memScoped {
        val value: FloatVar = alloc()
        val hasValue = CFNumberGetValue(this@floatValue, kCFNumberFloat32Type, value.ptr)
        if (!hasValue) error("Could not get value")
        value.value
    }

val CFNumberRef.doubleValue: Double
    get() = memScoped {
        val value: DoubleVar = alloc()
        val hasValue = CFNumberGetValue(this@doubleValue, kCFNumberFloat64Type, value.ptr)
        if (!hasValue) error("Could not get value")
        value.value
    }

fun CFTypeRef.asCFNumber(): CFNumberRef {
    check(CFGetTypeID(this) == CFNumberGetTypeID()) {
        "value is not of type CFNumber"
    }
    return this.reinterpret()
}


fun Byte.toCFNumber() = CFBridgingRetain(NSNumber(char = this))?.asCFNumber()
    ?: error("Could not create CFNumber")

fun Short.toCFNumber() = CFBridgingRetain(NSNumber(short = this))?.asCFNumber()
    ?: error("Could not create CFNumber")

fun Int.toCFNumber() = CFBridgingRetain(NSNumber(int = this))?.asCFNumber()
    ?: error("Could not create CFNumber")

fun Long.toCFNumber() = CFBridgingRetain(NSNumber(long = this))?.asCFNumber()
    ?: error("Could not create CFNumber")

fun Float.toCFNumber() = CFBridgingRetain(NSNumber(float = this))?.asCFNumber()
    ?: error("Could not create CFNumber")

fun Double.toCFNumber() = CFBridgingRetain(NSNumber(double = this))?.asCFNumber()
    ?: error("Could not create CFNumber")
