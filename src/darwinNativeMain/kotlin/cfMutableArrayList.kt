import kotlinx.cinterop.COpaquePointer
import platform.CoreFoundation.CFArrayRef
import platform.CoreFoundation.CFMutableArrayRef
import platform.Foundation.CFBridgingRetain

fun CFMutableArrayRef.asCFMutableArrayList() = CFArrayList(this)

fun MutableList<COpaquePointer?>.toCFMutableArrayList() = CFBridgingRetain(this)
    ?.asCFMutableArray()
    ?.let(::CFArrayList)
    ?: error("Could not create CFMutableArray")

fun cfMutableArrayListOf(vararg element: COpaquePointer?) = CFMutableArrayList(cfArrayOf(*element))

open class CFMutableArrayList internal constructor(private val cfArray: CFMutableArrayRef) :
    MutableList<COpaquePointer?>, CFArrayList(cfArray) {
    override fun add(element: COpaquePointer?): Boolean {
        cfArray.append(element)
        return true
    }

    override fun add(index: Int, element: COpaquePointer?) {
        cfArray.insert(index, element)
    }

    override fun addAll(index: Int, elements: Collection<COpaquePointer?>): Boolean {
        val array = elements.toList().toCFArray()
        cfArray.append(array, index .. elements.size + index)
        return true
    }

    override fun addAll(elements: Collection<COpaquePointer?>): Boolean {
        val array = elements.toList().toCFArray()
        cfArray.append(array)
        return true
    }

    override fun clear() = cfArray.removeAll()

    override fun iterator(): MutableIterator<COpaquePointer?> =
        CFMutableArrayListIterator(cfArray)

    override fun listIterator(): MutableListIterator<COpaquePointer?> =
        CFMutableArrayListIterator(cfArray)

    override fun listIterator(index: Int): MutableListIterator<COpaquePointer?> =
        CFMutableArrayListIterator(cfArray, index)

    override fun removeAt(index: Int) = cfArray.removeAt(index)

    override fun subList(fromIndex: Int, toIndex: Int): MutableList<COpaquePointer?> {
        TODO("Not yet implemented")
    }

    override fun set(index: Int, element: COpaquePointer?): COpaquePointer? {
        val value = cfArray[index]
        cfArray[index] = element
        return value
    }

    override fun retainAll(elements: Collection<COpaquePointer?>): Boolean {
        var didRemove = false
        val iterator = listIterator()
        while(iterator.hasNext()) {
            val element = iterator.next()
            if (!elements.contains(element)) {
                didRemove = true
                iterator.remove()
            }
        }
        return didRemove
    }

    override fun removeAll(elements: Collection<COpaquePointer?>): Boolean {
        var didRemove = false
        val iterator = listIterator()
        while(iterator.hasNext()) {
            val element = iterator.next()
            if (elements.contains(element)) {
                didRemove = true
                iterator.remove()
            }
        }
        return didRemove
    }

    override fun remove(element: COpaquePointer?): Boolean {
        val index = cfArray.indexOf(element)
        if (index == -1) return false
        cfArray.removeAt(index)
        return true
    }

    protected open class CFMutableArrayListIterator(
        cfArray: CFArrayRef,
        startIndex: Int = 0
    ) : MutableListIterator<COpaquePointer?>, CFArrayListIterator(cfArray, startIndex) {
        override fun add(element: COpaquePointer?) = cfArray.insert(nextIndex, element)

        override fun remove() {
            cfArray.removeAt(previousIndex)
        }


        override fun set(element: COpaquePointer?) {
            cfArray[previousIndex] = element
        }
    }

    private class CFMutableArraySubList(
        cfArray: CFArrayRef,
        fromView: Int = 0,
        toView: Int = cfArray.size
    ): CFArraySubList(cfArray, fromView, toView), MutableList<COpaquePointer?> {
        override fun add(element: COpaquePointer?): Boolean {
            cfArray.insert(toView, element)
            return true
        }

        override fun add(index: Int, element: COpaquePointer?) {
            cfArray.insert(index + fromView, element)
        }

        override fun addAll(index: Int, elements: Collection<COpaquePointer?>): Boolean {
            elements.forEachIndexed { i, element -> cfArray.insert(index + fromView + i, element) }
            return true
        }

        override fun addAll(elements: Collection<COpaquePointer?>): Boolean {
            elements.forEachIndexed { index, element -> cfArray.insert(index + fromView, element) }
            return true
        }

        override fun clear() {
            (fromView .. toView).reversed().forEach { cfArray.removeAt(it) }
        }

        override fun iterator(): MutableIterator<COpaquePointer?> =
            CFMutableArrayListIterator(cfArray)

        override fun listIterator(): MutableListIterator<COpaquePointer?> =
            CFMutableArrayListIterator(cfArray)

        override fun listIterator(index: Int): MutableListIterator<COpaquePointer?> =
            CFMutableArrayListIterator(cfArray, index)

        override fun removeAt(index: Int) = cfArray.removeAt(index)

        override fun subList(fromIndex: Int, toIndex: Int) =
            CFMutableArraySubList(cfArray, fromIndex + fromView, toIndex + fromView)

        override fun set(index: Int, element: COpaquePointer?): COpaquePointer? {
            val value = cfArray[index + fromView]
            cfArray[index + fromView] = element
            return value
        }

        override fun retainAll(elements: Collection<COpaquePointer?>): Boolean {
            var didRemove = false
            val iterator = listIterator()
            while(iterator.hasNext()) {
                val element = iterator.next()
                if (!elements.contains(element)) {
                    didRemove = true
                    iterator.remove()
                }
            }
            return didRemove
        }

        override fun removeAll(elements: Collection<COpaquePointer?>): Boolean {
            var didRemove = false
            val iterator = listIterator()
            while(iterator.hasNext()) {
                val element = iterator.next()
                if (elements.contains(element)) {
                    didRemove = true
                    iterator.remove()
                }
            }
            return didRemove
        }

        override fun remove(element: COpaquePointer?): Boolean {
            val index = cfArray.indexOf(element)
            if (index == -1 || index !in fromView .. toView) return false
            cfArray.removeAt(index)
            return true
        }


        private class CFMutableArraySubListIterator(
            cfArray: CFArrayRef,
            fromView: Int = 0,
            toView: Int = cfArray.size,
            startIndex: Int = 0,
        ) : MutableListIterator<COpaquePointer?>, CFArraySubListIterator(cfArray, fromView, toView, startIndex) {
            override fun add(element: COpaquePointer?) = cfArray.insert(nextIndex, element)

            override fun remove() {
                cfArray.removeAt(previousIndex)
            }


            override fun set(element: COpaquePointer?) {
                cfArray[previousIndex] = element
            }
        }
    }
}
