import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CFArrayTest {

    @Test
    fun cfArrayCanCreateAndGetValues() {
        val dictValue = cfDictionaryOf()
        val arrayValue = cfArrayOf()
        val setValue = cfSetOf()
        val bagValue = cfBagOf()
        val byteValue = 1.toByte().toCFNumber()
        val shortValue = 1.toShort().toCFNumber()
        val intValue = 1.toCFNumber()
        val longValue = 1L.toCFNumber()
        val floatValue = 1f.toCFNumber()
        val doubleValue = 1.0.toCFNumber()
        val booleanValue = true.toCFBoolean()
        val stringValue = "string".toCFString()
        val cfArray = cfArrayOf(
            dictValue,
            arrayValue,
            setValue,
            bagValue,
            byteValue,
            shortValue,
            intValue,
            longValue,
            floatValue,
            doubleValue,
            booleanValue,
            stringValue,
            stringValue,
        )
        assertEquals(13, cfArray.size)
        assertEquals(13, cfArray.count())
        assertEquals(dictValue, cfArray.getCFDictionary(0))
        assertEquals(arrayValue, cfArray.getCFArray(1))
        assertEquals(setValue, cfArray.getCFSet(2))
        assertEquals(bagValue, cfArray.getCFBag(3))
        assertEquals(intValue, cfArray.getCFNumber(6))
        assertEquals(booleanValue, cfArray.getCFBoolean(10))
        assertEquals(stringValue, cfArray.getCFString(11))

        assertEquals("string", cfArray.getString(11))
        assertEquals(1.toByte(), cfArray.getByte(4))
        assertEquals(1.toShort(), cfArray.getShort(5))
        assertEquals(1, cfArray.getInt(6))
        assertEquals(1L, cfArray.getLong(7))
        assertEquals(1f, cfArray.getFloat(8))
        assertEquals(1.0, cfArray.getDouble(9))
        assertEquals(true, cfArray.getBoolean(10))

        assertEquals(11, cfArray.indexOf(stringValue))
        assertEquals(12, cfArray.lastIndexOf(stringValue))
        assertEquals(2, cfArray.countOf(stringValue))
        cfArray.release()
    }

    @Test
    fun canConvertBetweenListAndCFArray() {
        val string = "string".toCFString()
        val arrayToCfArray = listOf(string, string).toCFArray()
        assertEquals(2, arrayToCfArray.size)
        val cfArrayToCFArray = arrayToCfArray.toList()
        assertEquals(2, cfArrayToCFArray.size)
        assertEquals("string", cfArrayToCFArray[0]?.asCFString()?.stringValue)
        assertEquals("string", cfArrayToCFArray[1]?.asCFString()?.stringValue)
        cfArrayToCFArray.listIterator()
        cfArrayToCFArray.iterator()
    }

    @Test
    fun canMutateCFMutableArray() {
        val cfArray = cfMutableArrayOf(
            "string0".toCFString(),
            "string1".toCFString(),
            "string2".toCFString(),
            "string3".toCFString(),
        )

        assertEquals(4, cfArray.size)
        cfArray.removeAt(0)
        assertEquals(3, cfArray.size)
        assertEquals("string1", cfArray[0]?.asCFString()?.stringValue)

        assertEquals(3, cfArray.size)
        cfArray.add(1, "stringx".toCFString())
        assertEquals(4, cfArray.size)
        assertEquals("stringx", cfArray[1]?.asCFString()?.stringValue)

        assertEquals(4, cfArray.size)
        cfArray.addAll(cfArrayOf("new1String0".toCFString(), "new1String1".toCFString()))
        assertEquals(6, cfArray.size)

        val new2String0 = "new2String0".toCFString()
        val cfArray2 = cfArray + cfArrayOf(new2String0, "new2String1".toCFString())
        cfArray2.clear()
        assertEquals(0, cfArray2.size)

        cfArray.release()
        cfArray2.release()
    }
}