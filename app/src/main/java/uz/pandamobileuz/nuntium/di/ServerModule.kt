package uz.pandamobileuz.nuntium.di

import android.content.Context
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import uz.pandamobileuz.nuntium.data.api.ApiService
import uz.pandamobileuz.nuntium.utils.Constants.API_KEY
import uz.pandamobileuz.nuntium.utils.Constants.BASE_URL
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ServerModule {

    @Provides
    @Singleton
    fun getRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()

    @Provides
    @Singleton
    fun getApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)


    @Provides
    @Singleton
    fun getClient(@ApplicationContext context: Context): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .addInterceptor(ChuckInterceptor(context))
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .addInterceptor(Interceptor { chain ->
            val builder = chain.request().newBuilder()
            builder.header("Content-Type", "application/x-www-form-urlencoded")
            builder.header("Accept", "application/json")
            builder.header("Authorization", API_KEY)
            chain.proceed(builder.build())
        })
        .build()

}