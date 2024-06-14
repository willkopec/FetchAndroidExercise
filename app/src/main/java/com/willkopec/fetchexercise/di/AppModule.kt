package com.willkopec.fetchexercise.di

import com.willkopec.fetchexercise.repository.FetchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideNewsRepository(): FetchRepository {
        return FetchRepository()
    }

}