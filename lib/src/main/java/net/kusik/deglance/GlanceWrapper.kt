package net.kusik.deglance

import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.unit.DpSize
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.LocalContext
import androidx.glance.layout.ContentScale
import androidx.glance.layout.height
import androidx.glance.layout.width
import kotlinx.coroutines.launch

/**
 * Enables the use of regular Composables in Glance through a Bitmap.
 */
// TODO Find a way to remove the size parameter and figure it out automatically
@Composable
fun Deglance(size: DpSize, modifier: GlanceModifier = GlanceModifier, content: @Composable () -> Unit) {
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }

    BitmapCapture(size, onBitmap = {
//        Logger.d("GlanceWrapper") { "Captured Glance composable" }
        bitmap = it
    }) {
        content()
    }

    bitmap?.let { bitmap ->
        Image(
            modifier = modifier.height(size.height).width(size.width),
            contentDescription = null,
            provider = ImageProvider(bitmap),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
internal fun BitmapCapture(size: DpSize, onBitmap: (Bitmap) -> Unit, content: @Composable () -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    SideEffect {
        coroutineScope.launch {
            val bitmap = useVirtualDisplay(context) { display ->
                captureComposable(
                    context = context,
                    size = size,
                    display = display,
                ) {

                    LaunchedEffect(Unit) {
                        capture()
                    }

                    content()
                }
            }.asAndroidBitmap()

            onBitmap(bitmap)
        }
    }
}
