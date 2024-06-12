import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.ByteArrayInputStream
import org.example.InputStream as InputStream
import java.io.InputStream as InputStream1

class InputStreamTest{

    @Test
    fun testInputFile() {
        val myString: String = "abc   \nd\nef"
        val stream: InputStream = InputStream(myString.byteInputStream())

        assertTrue(stream.location.line_num == 1)
        assertTrue(stream.location.col_num == 1)

        assertTrue(stream.read_char() == 'a')
        assertTrue(stream.location.line_num == 1)
        assertTrue(stream.location.col_num == 2)

        stream.unread_char('A')
        assertTrue(stream.location.line_num == 1)
        assertTrue(stream.location.col_num == 1)

        assertTrue(stream.read_char() == 'A')
        assertTrue(stream.location.line_num == 1)
        assertTrue(stream.location.col_num == 2)

        assertTrue(stream.read_char() == 'b')
        assertTrue(stream.location.line_num == 1)
        assertTrue(stream.location.col_num == 3)

        assertTrue(stream.read_char() == 'c')
        assertTrue(stream.location.line_num == 1)
        assertTrue(stream.location.col_num == 4)

        stream.skip_whitespaces_and_comments()

        assertTrue(stream.read_char() == 'd')
        assertTrue(stream.location.line_num == 2)
        assertTrue(stream.location.col_num == 2)

        assertTrue(stream.read_char() == '\n')
        assertTrue(stream.location.line_num == 3)
        assertTrue(stream.location.col_num == 1)

        assertTrue(stream.read_char() == 'e')
        assertTrue(stream.location.line_num == 3)
        assertTrue(stream.location.col_num == 2)

        assertTrue(stream.read_char() == 'f')
        assertTrue(stream.location.line_num == 3)
        assertTrue(stream.location.col_num == 3)

        assertTrue(stream.read_char() == '\u0000')
    }
}