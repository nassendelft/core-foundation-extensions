package nl.ncaj.cf.extension

import ncaj.CoreFoundation.extensions.*
import kotlin.test.Test
import kotlin.test.assertEquals

class CFTypeRefTest {


    @Test
    fun canCreateAndConvertCFBasicTypes() {
        val cfByte = 1.toByte().toCFNumber()
        val cfShort = 2.toShort().toCFNumber()
        val cfInt = 3.toCFNumber()
        val cfLong = 4L.toCFNumber()
        val cfFloat = 5f.toCFNumber()
        val cfDouble = 6.7.toCFNumber()
        val cfString = "string".toCFString()
        val cfBoolean  = true.toCFBoolean()

        assertEquals(1.toByte(), cfByte.byteValue)
        assertEquals(2.toShort(), cfShort.shortValue)
        assertEquals(3, cfInt.intValue)
        assertEquals(4L, cfLong.longValue)
        assertEquals(5f, cfFloat.floatValue)
        assertEquals(6.7, cfDouble.doubleValue)
        assertEquals("string", cfString.stringValue)
        assertEquals(true, cfBoolean.booleanValue)
    }
}