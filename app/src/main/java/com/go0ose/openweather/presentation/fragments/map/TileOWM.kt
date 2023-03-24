package com.go0ose.openweather.presentation.fragments.map

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import com.go0ose.openweather.data.retrofit.RetrofitClient
import com.google.android.gms.maps.model.Tile
import com.google.android.gms.maps.model.TileProvider
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL

class TileOWM(private val tileType: String) : TileProvider {

    private val opacityPaint = Paint()

    override fun getTile(x: Int, y: Int, zoom: Int): Tile? {
        val tileUrl = getTileUrl(x, y, zoom)
        var tile: Tile? = null
        var stream: ByteArrayOutputStream? = null
        try {
            var image = BitmapFactory.decodeStream(tileUrl.openConnection().getInputStream())
            image = adjustOpacity(image)
            stream = ByteArrayOutputStream()
            image.compress(Bitmap.CompressFormat.PNG, 100, stream)
            val byteArray = stream.toByteArray()
            tile = Tile(256, 256, byteArray)
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (stream != null) {
                try {
                    stream.close()
                } catch (e: IOException) {

                }
            }
        }
        return tile
    }

    private fun getTileUrl(x: Int, y: Int, zoom: Int): URL {
        val tileUrl = String.format(
            OWM_TILE_URL,
            tileType, zoom, x, y
        )
        return try {
            URL(tileUrl)
        } catch (e: MalformedURLException) {
            throw AssertionError(e)
        }
    }

    private fun adjustOpacity(bitmap: Bitmap): Bitmap {
        val adjustedBitmap = Bitmap.createBitmap(256, 256, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(adjustedBitmap)
        canvas.drawBitmap(bitmap, 0f, 0f, opacityPaint)
        return adjustedBitmap
    }

    companion object {

        private const val OWM_TILE_URL =
            "https://tile.openweathermap.org/map/%s/%d/%d/%d.png?appid=${RetrofitClient.OPEN_WEATHER_API_KEY}"
    }
}