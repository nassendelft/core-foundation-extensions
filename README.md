# core-foundation-extensions

This is a Kotlin MultiPlatform library that provides extensions for core foundation framework on MacOS.

# Setup

```kotlin
repositories {
    mavenCentral()
}

kotlin {
    sourceSets {
        val appleMain by getting {
            dependencies {
                implementation("nl.ncaj:core-foundation-extensions:0.3.1")
            }
        }
    }
}

```

# Usage

Convert kotlin types to cf types. Each of these calls transfers ownership of the values to the caller.
```kotlin
val cfByte = 1.toByte().toCFNumber()
val cfShort = 2.toShort().toCFNumber()
val cfInt = 3.toCFNumber()
val cfLong = 4L.toCFNumber()
val cfFloat = 5f.toCFNumber()
val cfDouble = 6.7.toCFNumber()
val cfString = "string".toCFString()
val cfBoolean  = true.toCFBoolean()
val cfDictionary = mapOf<COpaquePointer?, COpaquePointer?>().toCFDictionary()
val cfMutableDictionary = mutableMapOf<COpaquePointer?, COpaquePointer?>().toCFMutableDictionary()
val cfArray = listOf<COpaquePointer?>().toCFArray()
val cfMutableArray = mutableListOf<COpaquePointer?>().toCFMutableArray()
val cfSet = setOf<COpaquePointer?>().toCFSet()
val cfMutableSet = mutableSetOf<COpaquePointer?>().toCFMutableSet()
val cfData = listOf(1.toUbyte()).toCFData()
val cfMutableData = mutableListOf(1.toUbyte()).toCFMutableData()
val cfRange = (0 .. 10).toCFRange()
```

Create CF data structures.
```kotlin
val cfDictionary = cfDictionaryOf()
val cfMutableDictionary = cfMutableDictionaryOf() // or CFMutableDictionary()
val cfArray = cfArrayOf()
val cfMutableArray = cfMutableArrayOf() // or CFMutableArray()
val cfSet = cfSetOf()
val cfMutableSet = cfMutableSetOf() // or CFMutableSet()
val cfBag = cfBagOf()
val cfMutableBag = cfMutableBagOf() // or CFMutableBag()
val cfData = cfDataOf()
val cfMutableData = cfMutableDataOf() // CFMutableData()
val cfBinaryHeap = cfBinaryHeapOf(kCFStringBinaryHeapCallBacks)
val cfBitVector = cfBitVectorOf()
val cfMutableBitVector = cfMutableBitVectorOf() // or CFMutableBitVector()
val cfDate = CFDate()
```

When reading data from the above data structures there are helpers to work with the correct type.
```kotlin
val key = "string2".toCFString()
val cfDictionary = cfDictionaryOf(key to "string2".toCFString())

// by default, it's just a non-specific pointer type
val pointer: COpaquePointer? = cfDictionary[key]

// this reinterprets the pointer as a CFStringRef and checks if it's the actual correct type
val string2: CFStringRef = pointer.asCFString()
```

A helper lambda can be used to automatically release the allocated values so you don't need to CFRelease().
```kotlin
import nl.ncaj.cf.extension.CFMemScope.Companion.cfMemScoped

fun func() = cfMemScoped {
    val arr = cfMutableArray()
    
    // ...
    
    // no need to call CFRelease() here, it's automatically released
}
````