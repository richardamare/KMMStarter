package com.reportvox.reportvoxcontributor.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Upcoming
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.reportvox.reportvoxcontributor.AppViewModel
import com.reportvox.reportvoxcontributor.LocalAppViewModel
import com.reportvox.reportvoxcontributor.di.getViewModel
import kotlinx.coroutines.flow.update
import kotlin.experimental.ExperimentalObjCRefinement
import kotlin.native.HiddenFromObjC

class ForYouViewModel : StateScreenModel<ForYouViewModel.State>(State.Loading) {
    sealed class State {
        data object Loading : State()
        data class Error(val message: String) : State()
    }

    fun onEvent(e: ForYouEvent) {
        when (e) {
            ForYouEvent.Click -> {
                this.mutableState.update { State.Error("Error") }
            }
        }
    }
}

sealed interface ForYouEvent {
    object Click : ForYouEvent
}

@OptIn(ExperimentalObjCRefinement::class)
@HiddenFromObjC
class ForYouTab : Tab {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {

        val viewModel = getViewModel<ForYouViewModel>()
        val appViewModel = LocalAppViewModel.current

        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text(text = "For You") },
                )
            }
        ) {
            Column(Modifier.padding(it)) {
                Button(onClick = {
                    appViewModel.onEvent(AppViewModel.Event.OnAuthorized(!appViewModel.state.value.isAuthorized))
                }) {
                    Text(text = "For You")
                }
            }
        }
    }

    override val options: TabOptions
        @Composable
        get() {
            val title = "For You"
            val icon = rememberVectorPainter(Icons.Rounded.Upcoming)

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }
}