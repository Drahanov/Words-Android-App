package com.words.data

import com.words.data.words.api.TranslationApi
import com.words.data.words.repository.TranslationRepositoryImpl
import com.words.domain.words.repository.TranslationRepository
import com.words.domain.words.usecase.TranslateWordUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    private const val BASE_URL = "https://api.mymemory.translated.net/"

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): TranslationApi =
        retrofit.create(TranslationApi::class.java)

    @Singleton
    @Provides
    fun provideTranslationRepository(api: TranslationApi): TranslationRepository =
        TranslationRepositoryImpl(api)
}