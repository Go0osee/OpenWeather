package com.go0ose.openweather.utils.di

import android.content.Context
import com.go0ose.openweather.utils.prefs.SharedPreferenceImpl
import com.go0ose.openweather.utils.prefs.SharedPreferenceManager
import com.go0ose.openweather.utils.resource.ResourceProvider
import com.go0ose.openweather.utils.resource.ResourceProviderImpl
import dagger.Module
import dagger.Provides

@Module
class UtilsModule {

    @Provides
    fun provideResourceProvider(context: Context): ResourceProvider {
        return ResourceProviderImpl(context)
    }

    @Provides
    fun provideSharedPreference(context: Context): SharedPreferenceManager {
        return SharedPreferenceImpl(context)
    }
}