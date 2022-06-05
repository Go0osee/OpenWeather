package com.go0ose.openweather.data.storage.entity

import androidx.annotation.DrawableRes
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "city_weather")
data class CityWeatherEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    @ColumnInfo(name = "city_name")
    var cityName: String,
    @ColumnInfo(name = "full_city_name")
    var fullCityName: String,
    @ColumnInfo(name = "lat")
    var lat: String,
    @ColumnInfo(name = "lon")
    var lon: String,
    @ColumnInfo(name = "temp")
    var temp: String,
    @ColumnInfo(name = "background")
    @DrawableRes
    var background: Int,
    @ColumnInfo(name = "mainIcon")
    @DrawableRes
    var mainIcon: Int,
    @ColumnInfo(name = "description")
    var description: String,
    @ColumnInfo(name = "fellsLike")
    var fellsLike: String,
    @ColumnInfo(name = "favorite")
    var favorite: Int,
)