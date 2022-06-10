package com.go0ose.openweather

import android.content.Context
import com.go0ose.openweather.data.di.DataModule
import com.go0ose.openweather.data.di.RoomModule
import com.go0ose.openweather.domain.di.DomainModule
import com.go0ose.openweather.presentation.mainActivity.MainActivity
import com.go0ose.openweather.presentation.di.ViewModelModule
import com.go0ose.openweather.presentation.fragments.citybase.CityBaseFragment
import com.go0ose.openweather.presentation.fragments.search.SearchFragment
import com.go0ose.openweather.presentation.fragments.weather.WeatherFragment
import com.go0ose.openweather.presentation.widget.WeatherWidget
import com.go0ose.openweather.utils.di.UtilsModule
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        DomainModule::class,
        DataModule::class,
        RoomModule::class,
        ViewModelModule::class,
        UtilsModule::class
    ]
)
interface AppComponent {
    fun inject(target: MainActivity)
    fun inject(target: WeatherFragment)
    fun inject(target: CityBaseFragment)
    fun inject(target: SearchFragment)
    fun inject(target: WeatherWidget)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun buildContext(context: Context): Builder

        fun build(): AppComponent
    }
}