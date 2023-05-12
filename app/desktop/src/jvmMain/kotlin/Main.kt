
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetbrains.lifecycle.LifecycleController
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.parcelable.ParcelableContainer
import com.arkivanov.essenty.statekeeper.StateKeeperDispatcher
import com.joshrose.common.PlotsForComposeApp
import com.joshrose.common.components.root.DefaultRootComponent
import com.joshrose.common.theme.PlotsForComposeTheme
import java.awt.Dimension
import java.io.File
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

@OptIn(ExperimentalDecomposeApi::class)
fun main() {
    val lifecycle = LifecycleRegistry()
    val stateKeeper = StateKeeperDispatcher(tryRestoreStateFromFile())

    val root = runOnUiThread {
        DefaultRootComponent(
            componentContext = DefaultComponentContext(
                lifecycle = lifecycle,
                stateKeeper = stateKeeper
            )
        )
    }

    application {
        val windowParams = DpSize(750.dp, 750.dp)
        val windowState = rememberWindowState(size = windowParams)

        LifecycleController(lifecycle, windowState)

        var isCloseRequested by remember { mutableStateOf(false) }

        Window(
            onCloseRequest = { isCloseRequested = true },
            state = windowState,
            resizable = true,
            title = "Plots for Kotlin"
        ) {
            window.minimumSize = Dimension(
                windowParams.width.value.toInt(),
                windowParams.height.value.toInt()
            )

            PlotsForComposeTheme(
                darkTheme = isSystemInDarkTheme(),
                dynamicColor = false
            ) {
                PlotsForComposeApp(root)

                if (isCloseRequested) {
                    SaveStateDialog(
                        onSaveState = { saveStateToFile(stateKeeper.save()) },
                        onExitApplication = ::exitApplication,
                        onDismiss = { isCloseRequested = false },
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SaveStateDialog(
    onSaveState: () -> Unit,
    onExitApplication: () -> Unit,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Plots for Compose Sample", style = typography.headlineMedium, fontWeight = FontWeight.Bold) },
        text = { Text(text = "Do you want to save the application's state?") },
        buttons ={
            Row(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onDismiss) {
                    Text(text = "Cancel", color = colorScheme.primary)
                }

                TextButton(onClick = onExitApplication) {
                    Text(text = "No", color = colorScheme.primary)
                }

                TextButton(
                    onClick = {
                        onSaveState()
                        onExitApplication()
                    }
                ) {
                    Text(text = "Yes", color = colorScheme.primary)
                }
            }
        },
        backgroundColor = colorScheme.primaryContainer,
        contentColor = colorScheme.primary
    )
}

private const val SAVED_STATE_FILE_NAME = "saved_state.dat"

private fun saveStateToFile(state: ParcelableContainer) {
    ObjectOutputStream(File(SAVED_STATE_FILE_NAME).outputStream()).use { output ->
        output.writeObject(state)
    }
}

private fun tryRestoreStateFromFile(): ParcelableContainer? =
    File(SAVED_STATE_FILE_NAME).takeIf(File::exists)?.let { file ->
        try {
            ObjectInputStream(file.inputStream()).use(ObjectInputStream::readObject) as ParcelableContainer
        } catch (e: Exception) {
            null
        } finally {
            file.delete()
        }
    }
