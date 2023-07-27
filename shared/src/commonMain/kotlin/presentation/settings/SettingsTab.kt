package com.reportvox.reportvoxcontributor.presentation.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.reportvox.reportvoxcontributor.LocalAppViewModel
import com.reportvox.reportvoxcontributor.di.getViewModel
import com.reportvox.reportvoxcontributor.presentation.home.ForYouViewModel
import kotlin.experimental.ExperimentalObjCRefinement
import kotlin.native.HiddenFromObjC

class SettingsViewModel : ScreenModel {

}

@OptIn(ExperimentalObjCRefinement::class)
@HiddenFromObjC
class SettingsTab : Tab {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
//        val viewModel = getViewModel<SettingsViewModel>()
//        val state by viewModel.state.collectAsState()

        val appViewModel = LocalAppViewModel.current
        val appState by appViewModel.state.collectAsState()

        val viewModel = getViewModel<ForYouViewModel>()

        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text(text = "Settings") },
                )
            }
        ) {
            Column(Modifier.padding(it)) {
                Text(appState.isAuthorized.toString())
            }
        }
    }

    override val options: TabOptions
        @Composable
        get() {
            val title = "Settings"
            val icon = rememberVectorPainter(Icons.Rounded.Settings)

            return TabOptions(
                index = 1u,
                title = title,
                icon = icon
            )
        }
}