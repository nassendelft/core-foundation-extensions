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
            numberKey to numberValue,
            booleanKey to booleanValue,
        )
        assertEquals(7, cfDict.size)
        assertTrue(cfDict.contains(dictKey))
        assertTrue(cfDict.containsValue(dictValue))
        assertEquals(dictValue, cfDict.getCFDictionary(dictKey))
        assertEquals(arrayValue, cfDict.getCFArray(arrayKey))
        assertEquals(setValue, cfDict.getCFSet(setKey))
        assertEquals(bagValue, cfDict.getCFBag(bagKey))
        assertEquals(dataValue, cfDict.getCFData(dataKey))
        assertEquals(numberValue, cfDict.getCFNumber(numberKey))
        assertEquals(booleanValue, cfDict.getCFBoolean(booleanKey))
        cfDict.release()
    }
}
