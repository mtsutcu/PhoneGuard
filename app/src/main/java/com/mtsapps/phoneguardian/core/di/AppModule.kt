package com.mtsapps.phoneguardian.core.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.mtsapps.phoneguardian.core.room.AppDatabase
import com.mtsapps.phoneguardian.core.room.CategoryCallBack
import com.mtsapps.phoneguardian.core.room.CategoryDao
import com.mtsapps.phoneguardian.data.repository.ContactRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideAppDataBase(@ApplicationContext context: Context,provider: Provider<CategoryDao>): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "AppDatabase").addCallback(CategoryCallBack(
            provider)).build()
    }

    @Singleton
    @Provides
    fun provideDao(database: AppDatabase) = database.categoryDao()

    @Provides
    @Singleton
    fun provideContactRepositoryImpl(appDatabase: AppDatabase): ContactRepositoryImpl {
        return ContactRepositoryImpl(appDatabase)
    }

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context) : DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            produceFile = { context.preferencesDataStoreFile("user_data") }
        )
    }
}