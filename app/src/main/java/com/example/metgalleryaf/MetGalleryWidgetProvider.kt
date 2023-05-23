package com.example.metgalleryaf

import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.provideContent
import com.example.metgalleryaf.ui.MetGalleryWidget

object MetGalleryWidgetProvider : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {

        provideContent { MetGalleryWidget() }

    }
}

class MetGalleryWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget
        get() = MetGalleryWidgetProvider
}
