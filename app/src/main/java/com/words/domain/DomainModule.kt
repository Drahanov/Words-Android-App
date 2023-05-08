package com.words.domain

import com.words.domain.category.repository.CategoriesRepository
import com.words.domain.category.usecase.AddCategoryUseCase
import com.words.domain.category.usecase.DeleteCategoryUseCase
import com.words.domain.category.usecase.ReadAllCategoriesUseCase
import com.words.domain.category.usecase.UpdateCategoryUseCase
import com.words.domain.languages.repository.LanguagesRepository
import com.words.domain.languages.usecase.GetNativeLanguageUseCase
import com.words.domain.languages.usecase.ReadAllLanguagesUseCase
import com.words.domain.languages.usecase.ReadTargetsUseCase
import com.words.domain.languages.usecase.UpdateLanguageUseCase
import com.words.domain.words.repository.TranslationRepository
import com.words.domain.words.repository.WordsRepository
import com.words.domain.words.usecase.LearnWordUseCase
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

    @Singleton
    @Provides
    fun provideAddCategoryUseCase(repository: CategoriesRepository): AddCategoryUseCase =
        AddCategoryUseCase(repository)

    @Singleton
    @Provides
    fun provideUpdateCategoryUseCase(repository: CategoriesRepository): UpdateCategoryUseCase =
        UpdateCategoryUseCase(repository)

    @Singleton
    @Provides
    fun provideDeleteCategoryUseCase(repository: CategoriesRepository): DeleteCategoryUseCase =
        DeleteCategoryUseCase(repository)

    @Singleton
    @Provides
    fun provideReadAllCategoriesUseCase(repository: CategoriesRepository): ReadAllCategoriesUseCase =
        ReadAllCategoriesUseCase(repository)

    @Singleton
    @Provides
    fun provideReadAllLanguagesUseCase(repository: LanguagesRepository): ReadAllLanguagesUseCase =
        ReadAllLanguagesUseCase(repository)

    @Singleton
    @Provides
    fun provideUpdateLanguageUseCase(repository: LanguagesRepository): UpdateLanguageUseCase =
        UpdateLanguageUseCase(repository)

    @Singleton
    @Provides
    fun provideGetNativeLanguageUseCase(repository: LanguagesRepository): GetNativeLanguageUseCase =
        GetNativeLanguageUseCase(repository)

    @Singleton
    @Provides
    fun provideReadStudiedLanguageUseCase(repository: LanguagesRepository): ReadTargetsUseCase =
        ReadTargetsUseCase(repository)

    @Singleton
    @Provides
    fun provideLearnWordUse(repository: WordsRepository): LearnWordUseCase =
        LearnWordUseCase(repository)
}