package nl.ncaj.cf.extension

import ncaj.CoreFoundation.extensions.*
import platform.CoreFoundation.kCFStringBinaryHeapCallBacks
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CFDictionaryTest {

    @Test
    fun dictionaryOperations() {
        val dictKey = "dict".toCFString()
        val dictValue = cfDictionaryOf()
        val arrayKey = "array".toCFString()
        val arrayValue = cfArrayOf()
        val setKey = "set".toCFString()
        val setValue = cfSetOf()
        val bagKey = "bag".toCFString()
        val bagValue = cfBagOf()
        val dataKey = "data".toCFString()
        val dataValue = cfDataOf()
        val binaryHeapKey = "binaryHeap".toCFString()
        val binaryHeapValue = cfBinaryHeapOf(kCFStringBinaryHeapCallBacks)
        val bitVectorKey = "binaryHeap".toCFString()
        val bitVectorValue = cfBitVectorOf()
        val dateKey = "date".toCFString()
        val dateValue = CFDate()
        val numberKey = "number".toCFString()
        val numberValue = 1.toCFNumber()
        val booleanKey = "boolean".toCFString()
        val booleanValue = true.toCFBoolean()
        val cfDict = cfDictionaryOf(
            dictKey to dictValue,
            arrayKey to arrayValue,
            setKey to setValue,
            bagKey to bagValue,
            dataKey to dataValue,
            binaryHeapKey to binaryHeapValue,
            bitVectorKey to bitVectorValue,
            dateKey to dateValue,
            numberKey to numberValue,
            booleanKey to booleanValue,
        )
        assertEquals(10, cfDict.size)
        assertTrue(cfDict.contains(dictKey))
        assertTrue(cfDict.containsValue(dictValue))
        assertEquals(dictValue, cfDict.getCFDictionary(dictKey))
        assertEquals(arrayValue, cfDict.getCFArray(arrayKey))
        assertEquals(setValue, cfDict.getCFSet(setKey))
        assertEquals(bagValue, cfDict.getCFBag(bagKey))
        assertEquals(dataValue, cfDict.getCFData(dataKey))
        assertEquals(binaryHeapValue, cfDict.getCFBinaryHeap(binaryHeapKey))
        assertEquals(bitVectorValue, cfDict.getCFBitVector(bitVectorKey))
        assertEquals(dateValue, cfDict.getCFDate(dateKey))
        assertEquals(numberValue, cfDict.getCFNumber(numberKey))
        assertEquals(booleanValue, cfDict.getCFBoolean(booleanKey))
        cfDict.release()
    }
}
