package zed.rainxch.apps.presentation.components

import android.content.pm.PackageManager.NameNotFoundException
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.core.graphics.drawable.toBitmap
import org.jetbrains.compose.resources.painterResource
import zed.rainxch.kiristore.core.presentation.res.Res
import zed.rainxch.kiristore.core.presentation.res.app_icon

@Composable
actual fun InstalledAppIcon(
    packageName: String,
    appName: String,
    modifier: Modifier,
) {
    val packageManager = LocalContext.current.packageManager
    val iconBitmap =
        remember(packageName, packageManager) {
            try {
                packageManager
                    .getApplicationIcon(packageName)
                    .toBitmap()
                    .asImageBitmap()
            } catch (_: NameNotFoundException) {
                null
            }
        }

    if (iconBitmap != null) {
        Image(
            bitmap = iconBitmap,
            contentDescription = appName,
            modifier = modifier,
        )
    } else {
        Image(
            painter = painterResource(Res.drawable.app_icon),
            contentDescription = appName,
            modifier = modifier,
        )
    }
}

