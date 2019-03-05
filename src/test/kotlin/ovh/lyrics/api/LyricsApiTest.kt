package ovh.lyrics.api

import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class LyricsApiTest {

    private val api = LyricsApi("http://localhost:8080")

    private val mockWebServer = MockWebServer()

    @Before
    fun setUp() = mockWebServer.start(8080)

    @After
    fun tearDown() = mockWebServer.shutdown()

    @Test
    fun `Get music lyrics with success`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody("{\n" +
                        "  \"content\": \"Here the content of the song\"\n" +
                        "}")
        )

        val result = api.search("The Strokes", "80's Comedown Machine")

        assertEquals("Here the content of the song", result.lyrics?.content)
    }

    @Test
    fun `Music lyrics not found`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(404)
                .setBody("{\n" +
                        "  \"error\": \"No Lyrics found\"\n" +
                        "}")
        )

        val result = api.search("1234", "Not found")

        assertEquals("No Lyrics found", result.error?.message)
    }

}