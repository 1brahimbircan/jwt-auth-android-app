package com.example.jwtauth.di
import android.content.Context
import com.example.jwtauth.data.repositories.AuthRepository
import com.example.jwtauth.retrofit.ApiUtils
import com.example.jwtauth.retrofit.AuthDao
import com.example.jwtauth.utils.TokenManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideAuthRepository(authDao: AuthDao,tokenManager: TokenManager):AuthRepository{
        return AuthRepository(authDao,tokenManager)
    }

    @Provides
    @Singleton
    fun provideAuthDao(): AuthDao {
        return ApiUtils.getAuthDao()
    }

    @Provides
    @Singleton
    fun provideTokenManager(@ApplicationContext context: Context): TokenManager {
        return TokenManager(context)
    }
}