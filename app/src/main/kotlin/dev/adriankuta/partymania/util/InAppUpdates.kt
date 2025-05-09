package dev.adriankuta.partymania.util

import androidx.activity.compose.LocalActivity
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.InstallException
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.coroutines.suspendCoroutine

@Composable
fun InAppUpdates(
    modifier: Modifier = Modifier,
    @AppUpdateType updateType: Int = AppUpdateType.FLEXIBLE,
) {
    val activity = LocalActivity.current ?: return
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val appUpdateManager = remember { AppUpdateManagerFactory.create(requireNotNull(activity)) }
    val flexibleUpdateListener = remember {
        InstallStateUpdatedListener { state ->
            if (state.installStatus() == InstallStatus.DOWNLOADED) {
                // After the update is downloaded, show a notification
                // and request user confirmation to restart the app.
                scope.launch {
                    val result = snackbarHostState.showSnackbar(
                        message = "An update has just been downloaded",
                        actionLabel = "Reload",
                    )
                    when (result) {
                        SnackbarResult.Dismissed -> Unit
                        SnackbarResult.ActionPerformed -> {
                            appUpdateManager.completeUpdate()
                        }
                    }
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        try {
            val appUpdateInfo = appUpdateManager.checkUpdateInfo()
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                && appUpdateInfo.isUpdateTypeAllowed(updateType)
            ) {
                appUpdateManager.startUpdateFlow(
                    appUpdateInfo,
                    activity,
                    AppUpdateOptions.newBuilder(updateType).build(),
                )
            }
        } catch (e: InstallException) {
            Timber.w(e)
        }
    }

    DisposableEffect(appUpdateManager) {
        appUpdateManager.registerListener(flexibleUpdateListener)
        onDispose {
            appUpdateManager.unregisterListener(flexibleUpdateListener)
        }
    }

    SnackbarHost(
        hostState = snackbarHostState,
        modifier = modifier,
    )
}

private suspend fun AppUpdateManager.checkUpdateInfo() =
    suspendCoroutine<AppUpdateInfo> { continuation ->
        appUpdateInfo.addOnSuccessListener {
            continuation.resumeWith(Result.success(it))
        }.addOnFailureListener {
            continuation.resumeWith(Result.failure(it))
        }
    }
