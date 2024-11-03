package com.example.collegedating.di

import com.example.collegedating.networkrepository.MainRepository
import com.example.collegedating.networkrepository.MainRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideMyRepository(): MainRepository {
        return MainRepositoryImpl ()
    }
}