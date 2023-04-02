import platform.CoreFoundation.kCFStringBinaryHeapCallBacks
import kotlin.test.Test
import kotlin.test.assertEquals

class CFArrayTest {

    @Test
    fun cfArrayCanCreateAndGetValues() {
        val dictValue = cfDictionaryOf()
        val arrayValue = cfArrayOf()
        val setValue = cfSetOf()
        val bagValue = cfBagOf()
        val dataValue = cfDataOf()
        val binaryHeapValue = cfBinaryHeapOf(kCFStringBinaryHeapCallBacks)
        val bitVectorValue = cfBitVectorOf()
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
            dataValue,
            binaryHeapValue,
            bitVectorValue,
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
        assertEquals(16, cfArray.size)
        assertEquals(16, cfArray.count())
        assertEquals(dictValue, cfArray.getCFDictionary(0))
        assertEquals(arrayValue, cfArray.getCFArray(1))
        assertEquals(setValue, cfArray.getCFSet(2))
        assertEquals(bagValue, cfArray.getCFBag(3))
        assertEquals(dataValue, cfArray.getCFData(4))
        assertEquals(binaryHeapValue, cfArray.getCFBinaryHeap(5))
        assertEquals(bitVectorValue, cfArray.getCFBitVector(6))
        assertEquals(intValue, cfArray.getCFNumber(9))
        assertEquals(booleanValue, cfArray.getCFBoolean(13))
        assertEquals(stringValue, cfArray.getCFString(14))

        assertEquals("string", cfArray.getString(14))
        assertEquals(1.toByte(), cfArray.getByte(7))
        assertEquals(1.toShort(), cfArray.getShort(8))
        assertEquals(1, cfArray.getInt(9))
        assertEquals(1L, cfArray.getLong(10))
        assertEquals(1f, cfArray.getFloat(11))
        assertEquals(1.0, cfArray.getDouble(12))
        assertEquals(true, cfArray.getBoolean(13))

        assertEquals(2, cfArray.countOf(stringValue))
        assertEquals(14, cfArray.indexOf(stringValue))
        assertEquals(15, cfArray.lastIndexOf(stringValue))
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
        cfArray.insert(1, "stringx".toCFString())
        assertEquals(4, cfArray.size)
        assertEquals("stringx", cfArray[1]?.asCFString()?.stringValue)

        assertEquals(4, cfArray.size)
        cfArray.append(cfArrayOf("new1String0".toCFString(), "new1String1".toCFString()))
        assertEquals(6, cfArray.size)

        val new2String0 = "new2String0".toCFString()
        cfArray.append(cfArrayOf(new2String0, "new2String1".toCFString()))
        assertEquals(8, cfArray.size)
        cfArray.removeAll()
        assertEquals(0, cfArray.size)

        cfArray.release()
    }
}