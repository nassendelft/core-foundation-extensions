# core-foundation-extensions

This is a Kotlin MultiPlatform library that provides extensions for core foundation framework on MacOS.

# Setup

```kotlin
repositories {
    mavenCentral()
}

kotlin {
    sourceSets {
        val macosX64Main by getting {
            dependencies {
                implementation("nl.ncaj:core-foundation-extensions:0.1.0")
            }
        }
    }
}

```

# Usage

Convert kotlin types to cf types: 
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
```

Create CF data structures
```kotlin
val cfDictionary = cfDictionaryOf()
val cfMutableDictionary = cfMutableDictionaryOf()
val cfArray = cfArrayOf()
val cfMutableArray = cfMutableArrayOf()
val cfSet = cfSetOf()
val cfMutableSet = cfMutableSetOf()
val cfBag = cfBagOf()
val cfMutableBag = cfMutableBagOf()
val cfData = cfDataOf()
val cfMutableData = cfMutableDataOf()
```

When reading data from the above data structures there are helpers to work with the correct type:

```kotlin
val key = "string2".toCFString()
val cfDictionary = cfDictionaryOf(key to "string2".toCFString())

// by default, it's just a non-specific pointer type
val pointer: COpaquePointer? = cfDictionary[key]

// this reinterprets the pointer as a CFStringRef and checks if it's the actual correct type
val string2: CFStringRef = pointer.asCFString()
```