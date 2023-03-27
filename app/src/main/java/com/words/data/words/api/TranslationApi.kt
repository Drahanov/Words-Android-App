package com.words.data.words.api

import com.words.domain.words.model.ResponseWordModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TranslationApi {
    @GET(value = "get?")
    fun getTranslation(@Query("q") q: String, @Query("langpair") langpair: String): Call<ResponseWordModel>
}