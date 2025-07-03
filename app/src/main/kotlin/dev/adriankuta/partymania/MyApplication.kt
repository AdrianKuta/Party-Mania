package dev.adriankuta.partymania

import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@HiltAndroidApp
class MyApplication : BaseApplication() {

    private val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()

        applicationScope.launch {
            registerTestDevice()
            performBackgroundInitialization()
        }
    }

    private suspend fun performBackgroundInitialization() = suspendCoroutine { continuation ->
        MobileAds.initialize(this) { status ->
            Timber.i(
                "Ad initialization status: " +
                    "${
                        status.adapterStatusMap.map { (adapterClass, status) -> "$adapterClass: ${status.description}" }
                    }",
            )
            continuation.resume(Unit)
        }
    }

    private fun registerTestDevice() {
        val testDeviceIds = listOf("14DDD5B975FA298EEDF9D4517A04F246")
        val configuration = RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build()
        MobileAds.setRequestConfiguration(configuration)
    }
}
