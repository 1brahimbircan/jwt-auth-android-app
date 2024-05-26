package com.example.jwtauth.di
import android.content.Context
import com.example.jwtauth.api.UserAPI
import com.example.jwtauth.repository.AuthRepository
import com.example.jwtauth.utils.Constants.BASE_URL
import com.example.jwtauth.utils.TokenManager
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
class NetworkModule {
    @Provides
    @Singleton
    fun provideAuthRepository(userAPI: UserAPI,tokenManager: TokenManager): AuthRepository {
        return AuthRepository(userAPI,tokenManager)
    }

    @Provides
    @Singleton
    fun providesTokenManager(@ApplicationContext context: Context): TokenManager {
        return TokenManager(context)
    }
    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }

    @Singleton
    @Provides
    fun providesUserAPI(retrofit: Retrofit): UserAPI {
        return retrofit.create(UserAPI::class.java)
    }





}