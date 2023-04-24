package com.example.metgalleryaf.data

import android.content.Context
import com.example.metgalleryaf.data.database.GalleryRoomDB
import com.example.metgalleryaf.data.database.getDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): GalleryRoomDB{
        return getDatabase(context)
    }
}