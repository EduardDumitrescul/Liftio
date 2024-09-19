package com.thinkerbyte.fitnesstracker.hilt

import androidx.lifecycle.SavedStateHandle
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object NavigationModule {

    @Provides
    @ViewModelScoped
    fun provideId(
        savedStateHandle: SavedStateHandle,
    ): Int {
        val id: String = checkNotNull(savedStateHandle["id"])
        return id.toInt()
    }
}