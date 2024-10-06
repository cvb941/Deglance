package net.kusik.deglance

import android.content.Context
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceId
import androidx.glance.LocalSize
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.provideContent
import androidx.glance.layout.Column

class DeglanceWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = DeglanceWidget()
}

class DeglanceWidget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {

            val size = LocalSize.current

            Deglance(size) {
                Column {
                    Text(text = "Hello from Compose!")

                    Canvas(Modifier.size(64.dp)) {
                        drawCircle(color = androidx.compose.ui.graphics.Color.Red)
                    }
                }
            }

        }
    }
}