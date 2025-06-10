package dev.adriankuta.partymania.ui.truthordare.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimatedCurvedTopBar(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit = {},
) {
    val isPreview = LocalInspectionMode.current
    var expanded by remember { mutableStateOf(isPreview) }

    // Animate height with spring
    val height by animateDpAsState(
        targetValue = if (expanded) TopAppBarDefaults.LargeAppBarExpandedHeight else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow,
        ),
    )

    // Trigger animation on enter
    LaunchedEffect(Unit) {
        delay(500)
        expanded = true
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .clip(CurvedBottomShape())
            .background(MaterialTheme.colorScheme.primary),
    ) {
        content()
    }
}
