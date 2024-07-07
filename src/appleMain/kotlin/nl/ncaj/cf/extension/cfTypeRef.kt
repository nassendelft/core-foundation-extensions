package nl.ncaj.cf.extension

import platform.CoreFoundation.CFRelease
import platform.CoreFoundation.CFTypeRef

fun CFTypeRef.release() = CFRelease(this)
