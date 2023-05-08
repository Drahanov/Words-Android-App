package com.words.data

import android.content.Context
import androidx.room.Room
import com.words.data.categories.dao.CategoriesDao
import com.words.data.categories.repository.CategoriesRepositoryImpl
import com.words.data.database.WordsDatabase
import com.words.data.languages.dao.LanguagesDao
import com.words.data.languages.repository.LanguagesRepositoryImpl
import com.words.data.quiz.dao.QuizDao
import com.words.data.quiz.repository.QuizRepositoryImpl
import com.words.data.words.api.TranslationApi
import com.words.data.words.dao.WordsDao
import com.words.data.words.repository.TranslationRepositoryImpl
import com.words.data.words.repository.WordsRepositoryImpl
import com.words.domain.category.repository.CategoriesRepository
import com.words.domain.languages.repository.LanguagesRepository
import com.words.domain.quiz.repository.QuizRepository
import com.words.domain.words.repository.TranslationRepository
import com.words.domain.words.repository.WordsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Singleton
    @Provides
    fun provideWordsRepository(dao: WordsDao): WordsRepository =
        WordsRepositoryImpl(dao)

    @Singleton
    @Provides
    fun provideCategoriesRepository(dao: CategoriesDao): CategoriesRepository =
        CategoriesRepositoryImpl(dao)

    @Singleton
    @Provides
    fun provideLanguagesRepository(dao: LanguagesDao): LanguagesRepository =
        LanguagesRepositoryImpl(dao)


    @Singleton
    @Provides
    fun provideQuizRepository(dao: QuizDao): QuizRepository =
        QuizRepositoryImpl(dao)

    @Provides
    fun provideWordsDao(wordsDatabase: WordsDatabase): WordsDao {
        return wordsDatabase.wordsDao()
    }

    @Provides
    fun provideQuizDao(wordsDatabase: WordsDatabase): QuizDao {
        return wordsDatabase.quizDao()
    }


    @Provides
    fun provideCategoriesDao(wordsDatabase: WordsDatabase): CategoriesDao {
        return wordsDatabase.categoriesDao()
    }

    @Provides
    fun provideLanguagesDao(wordsDatabase: WordsDatabase): LanguagesDao {
        return wordsDatabase.languagesDao()
    }

    @Provides
    @Singleton
    fun provideWordsDatabase(@ApplicationContext appContext: Context): WordsDatabase {
        return Room.databaseBuilder(
            appContext,
            WordsDatabase::class.java,
            "words"
        ).createFromAsset("database/words.db").build()
    }

}