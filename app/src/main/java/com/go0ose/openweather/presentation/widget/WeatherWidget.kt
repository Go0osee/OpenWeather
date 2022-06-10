package com.go0ose.openweather.presentation.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.Toast
import com.go0ose.openweather.R
import com.go0ose.openweather.WeatherApplication
import com.go0ose.openweather.utils.AppConstants.ID_WIDGET
import com.go0ose.openweather.utils.AppConstants.RELOAD_START
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherWidget : AppWidgetProvider() {

    @Inject
    lateinit var widgetViewModel: WidgetViewModel

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        WeatherApplication.appComponent?.inject(this)

        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        WeatherApplication.appComponent?.inject(this)
        if (RELOAD_START == intent.action) {
            val appWidgetManage = AppWidgetManager.getInstance(context)

            val views = RemoteViews(context.packageName, R.layout.weather_widget)
            views.run {
                CoroutineScope(Dispatchers.Main.immediate).launch {
                    val result = withContext(Dispatchers.Main) {
                        widgetViewModel.loadWeather()
                    }
                    setTextViewText(R.id.cityNameWidget, result.cityName)
                    setTextViewText(R.id.tempWidget, result.temp)
                    setTextViewText(
                        R.id.fellsLikeWidget,
                        context.getString(R.string.feels_like) + " " + result.fellsLike
                    )
                    setImageViewResource(R.id.iconWidget, result.mainIcon)

                    Toast.makeText(context, context.getString(R.string.updated), Toast.LENGTH_SHORT)
                        .show()

                    appWidgetManage!!.updateAppWidget(intent.getIntExtra(ID_WIDGET, 0), views)
                }
            }
        }
    }

    private fun updateAppWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {

        val views = RemoteViews(context.packageName, R.layout.weather_widget)

        views.run {
            CoroutineScope(Dispatchers.Main.immediate).launch {
                val result = withContext(Dispatchers.Main) {
                    widgetViewModel.loadWeather()
                }
                setTextViewText(R.id.cityNameWidget, result.cityName)
                setTextViewText(R.id.tempWidget, result.temp)
                setTextViewText(
                    R.id.fellsLikeWidget,
                    context.getString(R.string.feels_like) + " " + result.fellsLike
                )
                setImageViewResource(R.id.iconWidget, result.mainIcon)

                setOnClickPendingIntent(
                    R.id.reload,
                    getPendingSelfIntent(context, RELOAD_START, appWidgetId)
                )
                appWidgetManager.updateAppWidget(appWidgetId, views)
            }
        }
    }


    private fun getPendingSelfIntent(
        context: Context?,
        action: String?,
        appWidgetId: Int
    ): PendingIntent {
        val intent = Intent(context, WeatherWidget::class.java)
        intent.action = action
        intent.putExtra(ID_WIDGET, appWidgetId)
        return PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    override fun onEnabled(context: Context) {
    }

    override fun onDisabled(context: Context) {
    }
}

