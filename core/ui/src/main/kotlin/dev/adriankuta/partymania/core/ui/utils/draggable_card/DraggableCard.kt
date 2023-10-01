package dev.adriankuta.partymania.core.ui.utils.draggable_card

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector2D
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import dev.adriankuta.partymania.core.ui.utils.draggable_card.CardState.DRAGGING
import dev.adriankuta.partymania.core.ui.utils.draggable_card.CardState.INITIAL
import dev.adriankuta.partymania.core.ui.utils.draggable_card.CardState.SWIPED
import kotlin.math.roundToInt
import kotlin.random.Random

fun Modifier.draggableCard(state: DraggableCardState): Modifier =
    composed(inspectorInfo = debugInspectorInfo {
        name = "draggableCard"
    }) {
        var layoutSize by remember {
            mutableStateOf(IntSize.Zero)
        }

        onGloballyPositioned { layoutSize = it.size }
            .offset { state.offset.value }
    }

interface DraggableCardState {

    val currentState: CardState

    val offset: Animatable<IntOffset, AnimationVector2D>

    suspend fun animateToState(cardState: CardState)
}

@Composable
fun rememberDraggableCardState(key: Any = Unit): DraggableCardState {
    return remember(key) {
        DraggableCardStateImpl(512, 512)
    }
}

private class DraggableCardStateImpl(
    private val cardWidth: Int,
    private val cardHeight: Int
) : DraggableCardState {

    override var currentState by mutableStateOf(INITIAL)
        private set
    override var offset = Animatable(targetValueByState(INITIAL), IntOffset.VectorConverter)
        private set

    override suspend fun animateToState(cardState: CardState) {
        if (currentState != cardState) {
            currentState = DRAGGING
            offset.animateTo(
                targetValueByState(cardState),
                animationSpec = tween(500, easing = FastOutSlowInEasing)
            )
            currentState = cardState
        }
    }

    private fun targetValueByState(targetState: CardState): IntOffset {
        return when (targetState) {
            INITIAL -> IntOffset.Zero
            SWIPED -> IntOffset(
                cardWidth,
                (cardHeight * Random.nextDouble(-0.25, 0.25)).roundToInt()
            )

            DRAGGING -> IntOffset.Zero
        }
    }
}
