package ovh.lyrics.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

private const val API_URL = "https://api.content.ovh"

private const val SC_NOT_FOUND = 404

class LyricsApi(private val apiUrl: String = API_URL) {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(apiUrl)
            .build()
    }

    private val service by lazy { retrofit.create(SearchService::class.java) }

    fun search(artist: String, title: String): LyricsResult {
        return try {
            val response = service.search(artist, title).execute()
            if (response.code() == SC_NOT_FOUND) {
                LyricsResult(error = ApiError(message = "No Lyrics found"))
            } else LyricsResult(lyrics = response.body())
        } catch (e: IOException) {
            LyricsResult(error = ApiError(message = e.message))
        }
    }

}