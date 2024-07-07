import kotlinx.cinterop.COpaquePointer
import kotlinx.cinterop.CPointed
import kotlinx.cinterop.CPointer
import platform.CoreFoundation.CFArrayRef
import platform.Foundation.CFBridgingRetain

fun CFArrayRef.asCFArrayList() = CFArrayList(this)

fun List<COpaquePointer?>.toCFArrayList() = CFBridgingRetain(this)
    ?.asCFArray()
    ?.let(::CFArrayList)
    ?: error("Could not create CFArray")

fun cfArrayListOf(vararg element: COpaquePointer?) = CFArrayList(cfArrayOf(*element))

open class CFArrayList internal constructor(private val cfArray: CFArrayRef) : List<COpaquePointer?> {
    override val size: Int
        get() = cfArray.size

    override fun get(index: Int) = cfArray[index]

    override fun isEmpty() = cfArray.size == 0

    override fun iterator(): Iterator<COpaquePointer?> = listIterator()

    override fun listIterator(): ListIterator<COpaquePointer?> = CFArrayListIterator(cfArray)

    override fun listIterator(index: Int): ListIterator<COpaquePointer?> = CFArrayListIterator(cfArray, index)

    override fun subList(fromIndex: Int, toIndex: Int): List<CPointer<out CPointed>?> =
        CFArraySubList(cfArray, fromIndex, toIndex)

    override fun lastIndexOf(element: COpaquePointer?) = cfArray.lastIndexOf(element)

    override fun indexOf(element: COpaquePointer?) = cfArray.indexOf(element)

    override fun containsAll(elements: Collection<COpaquePointer?>) =
        listIterator().asSequence().count { elements.contains(it) } == elements.size

    override fun contains(element: COpaquePointer?) = cfArray.indexOf(element) != -1

    fun release() = cfArray.release()

    protected open class CFArrayListIterator(
        protected val cfArray: CFArrayRef,
        startIndex: Int = 0
    ) : ListIterator<COpaquePointer?> {
        protected var previousIndex = startIndex - 1
        protected var nextIndex = startIndex
        protected var lastIndex = startIndex
        override fun next(): COpaquePointer? {
            val value = cfArray[nextIndex]
            lastIndex = nextIndex
            nextIndex++
            previousIndex++
            return value
        }

        override fun nextIndex() = nextIndex
        override fun previous(): COpaquePointer? {
            val value = cfArray[previousIndex]
            lastIndex = previousIndex
            nextIndex--
            previousIndex--
            return value
        }

        override fun previousIndex() = previousIndex
        override fun hasNext() = nextIndex < cfArray.size
        override fun hasPrevious() = previousIndex >= 0
    }

    protected open class CFArraySubList(
        protected val cfArray: CFArrayRef,
        protected val fromView: Int = 0,
        protected val toView: Int = cfArray.size
    ) : List<COpaquePointer?> {
        override val size: Int
            get() = (toView - fromView) + 1

        override fun get(index: Int) = cfArray[index + fromView]

        override fun isEmpty() = size == 0

        override fun iterator(): Iterator<COpaquePointer?> =
            CFArraySubListIterator(cfArray, fromView, toView)

        override fun listIterator(): ListIterator<COpaquePointer?> =
            CFArraySubListIterator(cfArray, fromView, toView)

        override fun listIterator(index: Int): ListIterator<COpaquePointer?> =
            CFArraySubListIterator(cfArray, fromView, toView, index)

        override fun subList(fromIndex: Int, toIndex: Int): List<CPointer<out CPointed>?> =
            CFArraySubList(cfArray, fromIndex, toIndex)

        override fun lastIndexOf(element: COpaquePointer?): Int {
            val lastIndex = cfArray.lastIndexOf(element)
            if (lastIndex == -1) return lastIndex
            return lastIndex - fromView
        }

        override fun indexOf(element: COpaquePointer?): Int {
            val index = cfArray.indexOf(element)
            if (index == -1) return index
            return index - fromView
        }

        override fun containsAll(elements: Collection<COpaquePointer?>) =
            listIterator().asSequence().count { elements.contains(it) } == elements.size

        override fun contains(element: COpaquePointer?) = indexOf(element) != -1

        protected open class CFArraySubListIterator(
            protected val cfArray: CFArrayRef,
            private val fromView: Int = 0,
            private val toView: Int = cfArray.size,
            startIndex: Int = 0,
        ) : ListIterator<COpaquePointer?> {
            protected var previousIndex = startIndex - 1
            protected var nextIndex = startIndex
            protected var lastIndex = startIndex
            override fun next(): COpaquePointer? {
                val value = cfArray[nextIndex + fromView]
                lastIndex = nextIndex
                nextIndex++
                previousIndex++
                return value
            }

            override fun nextIndex() = nextIndex
            override fun previous(): COpaquePointer? {
                val value = cfArray[previousIndex + fromView]
                lastIndex = previousIndex
                nextIndex--
                previousIndex--
                return value
            }

            override fun previousIndex() = previousIndex
            override fun hasNext() = nextIndex < ((toView - fromView) + 1)
            override fun hasPrevious() = previousIndex >= 0
        }
    }
}