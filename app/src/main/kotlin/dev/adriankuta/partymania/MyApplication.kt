package dev.adriankuta.partymania

import com.google.android.gms.ads.MobileAds
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltAndroidApp
class MyApplication : BaseApplication() {

    private val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()

        applicationScope.launch {
            // This runs in background thread
            performBackgroundInitialization()
        }
    }

    private fun performBackgroundInitialization() {
        MobileAds.initialize(this) { status ->
            Timber.i(
                "Ad initialization status: " +
                    "${
                        status.adapterStatusMap.map { (adapterClass, status) -> "$adapterClass: ${status.description}" }
                    }",
            )
        }
    }
}
