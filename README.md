# Deglance
[![](https://jitpack.io/v/cvb941/Deglance.svg)](https://jitpack.io/#cvb941/Deglance)

Deglance allows you to use regular Composables inside Glance's special Compose code.

It works by rendering the Composable content to a bitmap and displaying it in Glance as an image.

## Setup
```gradle
implementation("com.github.cvb941:deglance:1.0")
```
Make sure to add the JitPack maven repository to you gradle project.
```gradle
repositories {
    maven("https://jitpack.io")
}
```
## Example
Here is a sample widget constructed using Deglance.

Note that any interactivity in the regular Composables is removed since the view is just an image.

![screenshot.png](screenshot.png)

```kotlin
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
                        ) {
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
                    androidx.glance.Button("Glance button", {}, modifier = GlanceModifier.padding(top = 4.dp))
                }
            }
        }
    }
}
```