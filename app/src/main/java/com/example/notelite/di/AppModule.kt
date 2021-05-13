package com.example.notelite.di

import android.app.Application
import androidx.room.Room
import com.example.notelite.data.NoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

//Sets up Dagger Hilt
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(
        app: Application,
        callback: NoteDatabase.Callback
    ) =     Room.databaseBuilder(app, NoteDatabase::class.java, "note_database")
            .fallbackToDestructiveMigration()
            .addCallback(callback)
            .build()


    @Provides
    fun provideDao(db: NoteDatabase) = db.dao()

    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())
}