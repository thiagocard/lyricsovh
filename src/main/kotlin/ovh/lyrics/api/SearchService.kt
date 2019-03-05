package ovh.lyrics.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface SearchService {

    /**
     * Search for song content.
     *
     * @param artist Name of the artist Example: `Coldplay`.
     * @param title Title of the song Example: `Adventure of a Lifetime`.
     */
    @GET("{artist}/{title}")
    fun search(
        @Path("artist") artist: String,
        @Path("title") title: String
    ): Call<Lyric>

}