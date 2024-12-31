package com.tayler.valushopping.repository.di

import com.tayler.valushopping.repository.network.abstracts.IDataNetwork
import com.tayler.valushopping.repository.network.abstracts.IUserNetwork
import com.tayler.valushopping.repository.network.api.DataNetwork
import com.tayler.valushopping.repository.network.api.UserNetwork
import com.tayler.valushopping.repository.preferences.IAppPreferences
import com.tayler.valushopping.repository.preferences.api.AppPreferences
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ConfigMyModule{

    @Singleton
    @Binds
    abstract fun provideIAppPreferences(
        appPreferences: AppPreferences
    ): IAppPreferences


    @Singleton
    @Binds
    abstract fun provideIDataNetwork(
        dataNetwork: DataNetwork
    ): IDataNetwork

    @Singleton
    @Binds
    abstract fun provideIUserNetwork(
        dataNetwork: UserNetwork
    ): IUserNetwork
}