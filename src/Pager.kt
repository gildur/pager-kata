class Pager(val numberOfItems: Int, val pageSize: Int, val pageNumber: Int) {
    init {
        if (numberOfItems == 0) throw IllegalArgumentException("Zero not allowed as number of items.")
        if (pageSize == 0) throw IllegalArgumentException("Zero not allowed as page size.")
        if (pageSize < 0) throw IllegalArgumentException("Negative number not allowed as page size.")
    }

    val numberOfPages: Int
        get() {
            val result = numberOfItems / pageSize
            if (numberOfItems % pageSize == 0) return result
            return result + 1
        }

    val prevButtonVisible: Boolean
        get() {
            return pageNumber > 1
        }

    val nextButtonVisible: Boolean
        get() {
            return pageNumber < numberOfPages
        }

    val pagesToPrint: Array<Link>
        get() {
            return with(mutableListOf<Link>()) {
                if (prevButtonVisible) add(Link("<", pageNumber - 1 ))
                if (pageNumber > 2) add(Link(1, 1))
                if (pageNumber > 3) add(Link("..."))

                addAll(middlePages())

                if (pageNumber < numberOfPages - 2) add(Link("..."))
                if (pageNumber < numberOfPages - 1) add(Link(numberOfPages, numberOfPages))
                if (nextButtonVisible) add(Link(">", pageNumber + 1))

                toTypedArray()
            }
        }

    private fun middlePages(): Array<Link> {
        if (numberOfPages == 1) return arrayOf(Link(1))
        if (pageNumber == 1) return arrayOf(Link(1), Link(2, 2))
        if (pageNumber == numberOfPages) return arrayOf(
                Link(numberOfPages - 1, numberOfPages - 1),
                Link(numberOfPages))

        return arrayOf(
                Link(pageNumber - 1, pageNumber - 1),
                Link(pageNumber),
                Link(pageNumber + 1, pageNumber + 1))
    }
}
