@file:OptIn(ExperimentalFoundationApi::class)

package dev.adriankuta.partymania.screens.home

import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.adriankuta.partymania.ui.gametype.GameTypeCard

@Composable
fun MainMenu(
    uiState: HomeUiState,
    modifier: Modifier = Modifier,
    showOnlySelected: Boolean = false,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    BackHandler(uiState.selectedGameType != null) {
        viewModel.clearSelectedType()
    }

    val gameTypeOptions by remember(uiState.gameTypes) {
        derivedStateOf {
            if (showOnlySelected) {
                listOfNotNull(uiState.selectedGameType)
            } else {
                uiState.gameTypes
            }
        }
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(if (showOnlySelected) 1 else 2),
        modifier = modifier
            .height(200.dp),
    ) {
        items(gameTypeOptions, key = { item -> item.title }) {
            GameTypeCard(
                gameCardUiInfo = it,
                modifier = Modifier
                    // NicNote: Needs to be inside a LazyLayout to work
                    .animateItem()
                    .animateContentSize(
                        animationSpec = tween(),
                    )
                    .height(if (showOnlySelected) 200.dp else 100.dp)
                    .padding(if (showOnlySelected) 16.dp else 4.dp),
            ) { typeUIInfo -> viewModel.onGameTypeSelected(typeUIInfo) }
        }
    }
}
