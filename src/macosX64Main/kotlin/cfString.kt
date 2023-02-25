import kotlinx.cinterop.*
import platform.CoreFoundation.*
import platform.Foundation.CFBridgingRetain

fun CFTypeRef.asCFString(): CFStringRef {
    check(CFGetTypeID(this) == CFStringGetTypeID()) {
        "value is not of type CFString"
    }
    return this.reinterpret()
}

val CFStringRef.stringValue: String
    get() = memScoped {
        val length = CFStringGetLength(this@stringValue)
        val maxLength = CFStringGetMaximumSizeForEncoding(length, kCFStringEncodingUTF8) + 1
        val buf = allocArray<ByteVar>(maxLength)
        val hasValue = CFStringGetCString(this@stringValue, buf, maxLength, kCFStringEncodingUTF8)
        if (!hasValue) error("Could not read string")
        buf.toKStringFromUtf8()
    }


fun String.toCFString() = CFBridgingRetain(this)?.asCFString()
    ?: error("Could not create CFString")