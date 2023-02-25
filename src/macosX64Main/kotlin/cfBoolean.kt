import kotlinx.cinterop.reinterpret
import platform.CoreFoundation.*

fun CFTypeRef.asCFBoolean(): CFBooleanRef {
    check(CFGetTypeID(this) == CFBooleanGetTypeID()) {
        "value is not of type CFBoolean"
    }
    return this.reinterpret()
}

val CFBooleanRef.booleanValue: Boolean get() = CFBooleanGetValue(this)

fun Boolean.toCFBoolean() = (if(this) kCFBooleanTrue else kCFBooleanFalse)
    ?: error("Could not create CFBoolean")