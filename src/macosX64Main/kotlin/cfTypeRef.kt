import platform.CoreFoundation.CFRangeMake
import platform.CoreFoundation.CFRelease
import platform.CoreFoundation.CFTypeRef

fun CFTypeRef.release() = CFRelease(this)

fun cfRangeOf(range: IntRange) = CFRangeMake((range.first).toLong(), (range.last - range.first).toLong())
