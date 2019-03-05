package ovh.lyrics.api

import com.google.gson.annotations.SerializedName

data class LyricsResult(
    val lyrics: Lyric? = null,
    val error: ApiError? = null
)

data class ApiError(val message: String?)

inline class Lyric(@SerializedName("content") val content: String)
