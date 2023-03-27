package com.words.domain

import com.words.domain.words.repository.TranslationRepository
import com.words.domain.words.repository.WordsRepository
import com.words.domain.words.usecase.ReadAllWordsUseCase
import com.words.domain.words.usecase.TranslateWordUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {
    @Singleton
    @Provides
    fun provideTranslateWordUseCase(repository: TranslationRepository): TranslateWordUseCase =
        TranslateWordUseCase(repository)

    @Singleton
    @Provides
    fun provideReadALlWordsUseCase(repository: WordsRepository): ReadAllWordsUseCase =
        ReadAllWordsUseCase(repository)
}