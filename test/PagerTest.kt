import org.assertj.core.api.Assertions
import org.junit.Assert
import org.junit.Test

class PagerTest {

    @Test
    fun should_create_pager() {
        val pager = Pager(20, 10, 1)

        Assert.assertNotNull(pager)
        Assert.assertEquals(20, pager.numberOfItems)
        Assert.assertEquals(10, pager.pageSize)
        Assert.assertEquals(1, pager.pageNumber)
    }

    @Test
    fun should_return_correct_number_of_pages() {
        assertNumberOfPages(10, 10, 1)
        assertNumberOfPages(11, 10, 2)
        assertNumberOfPages(9, 10, 1)
        assertNumberOfPages(39, 10, 4)
        assertNumberOfPages(16, 3, 6)
        assertNumberOfPages(17, 16, 2)
        assertNumberOfPages(99, 1, 99)
    }

    private fun assertNumberOfPages(numberOfItems: Int, pageSize: Int, numberOfPages: Int) {
        val pager = Pager(numberOfItems, pageSize, 1)
        Assert.assertEquals(numberOfPages, pager.numberOfPages)
    }

    @Test
    fun should_throw_exception_when_number_of_items_is_zero() {
        Assertions
                .assertThatThrownBy { Pager(0, 10, 1) }
                .isInstanceOf(IllegalArgumentException::class.java)
                .hasMessage("Zero not allowed as number of items.")
    }

    @Test
    fun should_throw_exception_when_page_size_is_zero() {
        Assertions
                .assertThatThrownBy { Pager(10, 0, 1) }
                .isInstanceOf(IllegalArgumentException::class.java)
                .hasMessage("Zero not allowed as page size.")
    }

    @Test
    fun should_throw_exception_when_page_size_is_negative() {
        Assertions
                .assertThatThrownBy { Pager(10, -5, 1) }
                .isInstanceOf(IllegalArgumentException::class.java)
                .hasMessage("Negative number not allowed as page size.")
    }

    @Test
    fun prev_button_should_not_be_visible_on_first_page() {
        val pager = Pager(20, 10, 1)
        Assertions.assertThat(pager.prevButtonVisible).isFalse()
    }

    @Test
    fun prev_button_should_be_visible_on_second_page() {
        val pager = Pager(20, 10, 2)
        Assertions.assertThat(pager.prevButtonVisible).isTrue()
    }

    @Test
    fun next_button_should_not_be_visible_on_last_page() {
        val pager = Pager(20, 10, 2)
        Assertions.assertThat(pager.nextButtonVisible).isFalse()
    }

    @Test
    fun next_button_should_be_visible_on_page_before_last() {
        val pager = Pager(20, 10, 1)
        Assertions.assertThat(pager.nextButtonVisible).isTrue()
    }

    @Test
    fun should_return_page_labels_to_print() {
        assertPageLabelsToPrint(30, 10, 2, arrayOf("<", "1", "2", "3", ">"))
        assertPageLabelsToPrint(30, 10, 1, arrayOf("1", "2", "3", ">"))
        assertPageLabelsToPrint(30, 10, 3, arrayOf("<", "1", "2", "3"))
        assertPageLabelsToPrint(10, 10, 1, arrayOf("1"))

        assertPageLabelsToPrint(100, 10, 1, arrayOf("1", "2", "...", "10", ">"))
        assertPageLabelsToPrint(100, 10, 2, arrayOf("<", "1", "2", "3", "...", "10", ">"))
        assertPageLabelsToPrint(100, 10, 3, arrayOf("<", "1", "2", "3", "4", "...", "10", ">"))
        assertPageLabelsToPrint(100, 10, 4, arrayOf("<", "1", "...", "3", "4", "5", "...", "10", ">"))
        assertPageLabelsToPrint(100, 10, 5, arrayOf("<", "1", "...", "4", "5", "6", "...", "10", ">"))
        assertPageLabelsToPrint(100, 10, 6, arrayOf("<", "1", "...", "5", "6", "7", "...", "10", ">"))
        assertPageLabelsToPrint(100, 10, 7, arrayOf("<", "1", "...", "6", "7", "8", "...", "10", ">"))
        assertPageLabelsToPrint(100, 10, 8, arrayOf("<", "1", "...", "7", "8", "9", "10", ">"))
        assertPageLabelsToPrint(100, 10, 9, arrayOf("<", "1", "...", "8", "9", "10", ">"))
        assertPageLabelsToPrint(100, 10, 10, arrayOf("<", "1", "...", "9", "10"))
    }

    private fun assertPageLabelsToPrint(numberOfItems: Int, pageSize: Int, pageNumber: Int, pagesToPrint: Array<String>) {
        val pager = Pager(numberOfItems, pageSize, pageNumber)
        Assertions.assertThat(pager.pagesToPrint.map { it.label }.toTypedArray()).isEqualTo(pagesToPrint)
    }

    @Test
    fun should_return_page_urls_to_print() {
        assertPageUrlsToPrint(30, 10, 2, arrayOf(1, 1, null, 3, 3))
        assertPageUrlsToPrint(30, 10, 1, arrayOf(null, 2, 3, 2))
        assertPageUrlsToPrint(30, 10, 3, arrayOf(2, 1, 2, null))
        assertPageUrlsToPrint(10, 10, 1, arrayOf(null))

        assertPageUrlsToPrint(100, 10, 1, arrayOf(null, 2, null, 10, 2))
        assertPageUrlsToPrint(100, 10, 2, arrayOf(1, 1, null, 3, null, 10, 3))
        assertPageUrlsToPrint(100, 10, 3, arrayOf(2, 1, 2, null, 4, null, 10, 4))
        assertPageUrlsToPrint(100, 10, 4, arrayOf(3, 1, null, 3, null, 5, null, 10, 5))
        assertPageUrlsToPrint(100, 10, 5, arrayOf(4, 1, null, 4, null, 6, null, 10, 6))
        assertPageUrlsToPrint(100, 10, 6, arrayOf(5, 1, null, 5, null, 7, null, 10, 7))
        assertPageUrlsToPrint(100, 10, 7, arrayOf(6, 1, null, 6, null, 8, null, 10, 8))
        assertPageUrlsToPrint(100, 10, 8, arrayOf(7, 1, null, 7, null, 9, 10, 9))
        assertPageUrlsToPrint(100, 10, 9, arrayOf(8, 1, null, 8, null, 10, 10))
        assertPageUrlsToPrint(100, 10, 10, arrayOf(9, 1, null, 9, null))
    }

    private fun assertPageUrlsToPrint(numberOfItems: Int, pageSize: Int, pageNumber: Int, urlsToPrint: Array<Int?>) {
        val pager = Pager(numberOfItems, pageSize, pageNumber)
        Assertions.assertThat(pager.pagesToPrint.map { it.url }.toTypedArray()).isEqualTo(urlsToPrint)
    }
}
