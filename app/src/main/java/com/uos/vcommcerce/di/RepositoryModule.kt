package com.uos.vcommcerce.di

import com.uos.vcommcerce.data.repository.MemberRepository
import com.uos.vcommcerce.data.repository.MemberRepositoryImpl
import com.uos.vcommcerce.network.AuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMemberRepository(authService: AuthService): MemberRepository {
        return MemberRepositoryImpl(authService)
    }
}