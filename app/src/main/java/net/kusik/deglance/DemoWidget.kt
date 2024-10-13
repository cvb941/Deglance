package net.kusik.deglance

import android.R.attr.top
import android.content.Context
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.LocalSize
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.components.Scaffold
import androidx.glance.appwidget.provideContent
import androidx.glance.layout.padding

class DeglanceWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = DeglanceWidget()
}

class DeglanceWidget : GlanceAppWidget() {

    // Must be SizeMode.Exact in order to get accurate widget size in LocalSize.current
    override val sizeMode = SizeMode.Exact

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            val size = LocalSize.current

            Scaffold(horizontalPadding = 0.dp) {
                androidx.glance.layout.Column(
                    horizontalAlignment = androidx.glance.layout.Alignment.CenterHorizontally
                ) {
                    Deglance(DpSize(size.width, size.height - 48.dp)) {  // Use classic Compose imports here instead of Glance
                        Column(
                            Modifier
                                .fillMaxSize()
                                .background(color = GlanceTheme.colors.secondaryContainer.getColor(context)),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        )
                        {
                            Text(text = "Hello from Compose!", fontSize = 21.sp)

                            Canvas(Modifier.size(48.dp)) {
                                drawCircle(color = Color.Red)
                            }

                            InputChip(false, {}, {
                                Text("Material 3 Chip")
                            })

                            // Buttons do not work, they are just images
                            // Use androidx.glance.layout.Button instead
                            Button({}) {
                                Text("Compose button (inactive)")
                            }
                        }
                    }
                    androidx.glance.Button("Glance button", {})
                }
            }
        }
    }
}