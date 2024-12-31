package com.tayler.valushopping.repository.di

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.tayler.valushopping.BuildConfig
import com.tayler.valushopping.R
import com.tayler.valushopping.application.ApplicationVale
import com.tayler.valushopping.repository.AUTHORIZATION
import com.tayler.valushopping.repository.MY_CONTENT_TYPE
import com.tayler.valushopping.repository.MY_TIME_ON
import com.tayler.valushopping.repository.PLATFORM
import com.tayler.valushopping.repository.PREFERENCE_TOKEN
import com.tayler.valushopping.repository.network.ServiceApi
import com.tayler.valushopping.repository.preferences.manager.PreferencesManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SharedPreferencesModule {

    @Singleton
    @Provides
    fun providerSharedPreference(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(context.resources.getString(R.string.encryption_key), AppCompatActivity.MODE_PRIVATE)

}
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Provides
    fun provideBaseUrl() = BuildConfig.BASE_URL


    @Singleton
    @Provides
    fun provideContext(application: ApplicationVale): Context {
        return application.applicationContext
    }

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(
            if (BuildConfig.DEBUG)
                HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        )
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        apiInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(MY_TIME_ON, TimeUnit.SECONDS)
            .writeTimeout(MY_TIME_ON, TimeUnit.SECONDS)
            .readTimeout(MY_TIME_ON, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(apiInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideACMService(retrofit: Retrofit) : ServiceApi = retrofit.create(ServiceApi::class.java)


    @Singleton
    @Provides
    fun providerHeaderInterceptor(preferencesManager : PreferencesManager): Interceptor {
        return ApiInterceptor(preferencesManager)
    }
}

class ApiInterceptor @Inject constructor(private val preferencesManager: PreferencesManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val builder = request.newBuilder()
            .addHeader("Content-Type", MY_CONTENT_TYPE)
            .header("x-os", PLATFORM)
        if (preferencesManager.getString(PREFERENCE_TOKEN).isNotEmpty()) {
            builder.addHeader(AUTHORIZATION, preferencesManager.getString(PREFERENCE_TOKEN))
        }
        request = builder.build()
        return chain.proceed(request)
    }

}
