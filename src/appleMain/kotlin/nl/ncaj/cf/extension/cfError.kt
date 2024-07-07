package nl.ncaj.cf.extension

import kotlinx.cinterop.reinterpret
import platform.CoreFoundation.*
import platform.Foundation.CFBridgingRelease
import platform.Foundation.NSError

fun CFErrorRef.toNSError() = CFBridgingRelease(this) as NSError

fun CFTypeRef.asCFError(): CFErrorRef {
    check(CFGetTypeID(this) == CFErrorGetTypeID()) {
        "value is not of type CFError"
    }
    return this.reinterpret()
}
