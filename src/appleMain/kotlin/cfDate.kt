import kotlinx.cinterop.reinterpret
import platform.CoreFoundation.*

@Suppress("FunctionName")
fun CFDate(absoluteTime: Double = 0.0) = CFDateCreate(kCFAllocatorDefault, absoluteTime)

fun CFDateRef.getAbsoluteTime() = CFDateGetAbsoluteTime(this)

fun CFDateRef.intervalSince(date: CFDateRef) = CFDateGetTimeIntervalSinceDate(this, date)

fun CFDateRef.compareTo(date: CFDateRef) = CFDateCompare(this, date, null)

fun CFTypeRef.asCFDate(): CFDateRef {
    check(CFGetTypeID(this) == CFDateGetTypeID()) {
        "value is not of type CFDate"
    }
    return this.reinterpret()
}
