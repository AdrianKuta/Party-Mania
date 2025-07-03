package dev.adriankuta.partymania.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import dev.adriankuta.partymania.PartyManiaNavGraph
import dev.adriankuta.partymania.core.designsystem.theme.Elevation
import dev.adriankuta.partymania.ui.adMob.BannerAd

@Composable
fun PartyManiaApp(
    modifier: Modifier = Modifier,
) {
    Surface(
        tonalElevation = Elevation.Surface,
        modifier = modifier,
    ) {
        Scaffold(
            snackbarHost = { InAppUpdates() },
        ) { paddingValues ->
            Column(
                modifier = Modifier.padding(paddingValues),
            ) {
                PartyManiaNavGraph(
                    modifier = Modifier
                        .weight(1f),
                )
                AdMobBanner(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                )
            }
        }
    }
}

@Composable
private fun AdMobBanner(
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val adView = remember { AdView(context) }

    adView.adUnitId = "ca-app-pub-3805287728591173/5068000941"

    val adSize = AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, 360)
    adView.setAdSize(adSize)

    DisposableEffect(Unit) {
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

        onDispose {
            adView.destroy()
        }
    }

    BannerAd(
        adView = adView,
        modifier = modifier,
    )
}
