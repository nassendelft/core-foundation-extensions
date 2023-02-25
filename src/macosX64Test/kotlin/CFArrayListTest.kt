import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CFArrayListTest {

    @Test
    fun canListIterate() {
        val cfArray = cfArrayListOf(
            "string0".toCFString(),
            "string1".toCFString(),
            "string2".toCFString(),
            "string3".toCFString(),
        )
        val iterator = cfArray.listIterator()

        // go forwards
        assertFalse(iterator.hasPrevious())
        assertTrue(iterator.hasNext())
        assertEquals(-1, iterator.previousIndex())
        assertEquals(0, iterator.nextIndex())
        assertEquals("string0", iterator.next()?.asCFString()?.stringValue)
        assertTrue(iterator.hasPrevious())
        assertTrue(iterator.hasNext())
        assertEquals(0, iterator.previousIndex())
        assertEquals(1, iterator.nextIndex())
        assertEquals("string1", iterator.next()?.asCFString()?.stringValue)
        assertTrue(iterator.hasPrevious())
        assertTrue(iterator.hasNext())
        assertEquals(1, iterator.previousIndex())
        assertEquals(2, iterator.nextIndex())
        assertEquals("string2", iterator.next()?.asCFString()?.stringValue)
        assertTrue(iterator.hasPrevious())
        assertTrue(iterator.hasNext())
        assertEquals(2, iterator.previousIndex())
        assertEquals(3, iterator.nextIndex())
        assertEquals("string3", iterator.next()?.asCFString()?.stringValue)
        assertTrue(iterator.hasPrevious())
        assertFalse(iterator.hasNext())

        // go backwards
        assertEquals(3, iterator.previousIndex())
        assertEquals(4, iterator.nextIndex())
        assertEquals("string3", iterator.previous()?.asCFString()?.stringValue)
        assertTrue(iterator.hasPrevious())
        assertTrue(iterator.hasNext())
        assertEquals(2, iterator.previousIndex())
        assertEquals(3, iterator.nextIndex())
        assertEquals("string2", iterator.previous()?.asCFString()?.stringValue)
        assertTrue(iterator.hasPrevious())
        assertTrue(iterator.hasNext())
        assertEquals(1, iterator.previousIndex())
        assertEquals(2, iterator.nextIndex())
        assertEquals("string1", iterator.previous()?.asCFString()?.stringValue)
        assertTrue(iterator.hasPrevious())
        assertTrue(iterator.hasNext())
        assertEquals(0, iterator.previousIndex())
        assertEquals(1, iterator.nextIndex())
        assertEquals("string0", iterator.previous()?.asCFString()?.stringValue)
        assertFalse(iterator.hasPrevious())
        assertTrue(iterator.hasNext())

        cfArray.release()
    }

    @Test
    fun canSubListIterate() {
        val cfArray = cfArrayListOf(
            "string0".toCFString(),
            "string1".toCFString(),
            "string2".toCFString(),
            "string3".toCFString(),
        )
        val iterator = cfArray.subList(1, 2).listIterator()

        // go forwards
        assertFalse(iterator.hasPrevious())
        assertTrue(iterator.hasNext())
        assertEquals(-1, iterator.previousIndex())
        assertEquals(0, iterator.nextIndex())
        assertEquals("string1", iterator.next()?.asCFString()?.stringValue)
        assertTrue(iterator.hasPrevious())
        assertTrue(iterator.hasNext())
        assertEquals(0, iterator.previousIndex())
        assertEquals(1, iterator.nextIndex())
        assertEquals("string2", iterator.next()?.asCFString()?.stringValue)
        assertTrue(iterator.hasPrevious())
        assertFalse(iterator.hasNext())

        // go backwards
        assertEquals(1, iterator.previousIndex())
        assertEquals(2, iterator.nextIndex())
        assertEquals("string2", iterator.previous()?.asCFString()?.stringValue)
        assertTrue(iterator.hasPrevious())
        assertTrue(iterator.hasNext())
        assertEquals(0, iterator.previousIndex())
        assertEquals(1, iterator.nextIndex())
        assertEquals("string1", iterator.previous()?.asCFString()?.stringValue)
        assertFalse(iterator.hasPrevious())
        assertTrue(iterator.hasNext())

        cfArray.release()
    }

}