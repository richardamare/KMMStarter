package com.reportvox.reportvoxcontributor

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.reportvox.reportvoxcontributor.presentation.auth.AuthScreen
import com.reportvox.reportvoxcontributor.presentation.home.ForYouTab
import com.reportvox.reportvoxcontributor.presentation.navigation.TabNavigationItem
import com.reportvox.reportvoxcontributor.presentation.settings.SettingsTab
import kotlinx.coroutines.flow.update


class AppViewModel : StateScreenModel<AppViewModel.State>(State()) {

    data class State(
        var isAuthorized: Boolean = false,
    )

    sealed interface Event {
        data class OnAuthorized(val isAuthorized: Boolean) : Event
    }

    fun onEvent(e: Event) {
        when (e) {
            is Event.OnAuthorized -> {
                this.mutableState.update { it.copy(isAuthorized = e.isAuthorized) }
            }
        }
    }
}

val LocalAppViewModel = compositionLocalOf { AppViewModel() }

val LocalSnackbarHostState = compositionLocalOf { SnackbarHostState() }

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = MaterialTheme.colorScheme.copy(
            background = Color.White,
            surface = Color.White,
        )
    ) {
        content()
    }
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun App() {
    val appViewModel = LocalAppViewModel.current
    val appState by appViewModel.state.collectAsState()

    val snackbarHostState = LocalSnackbarHostState.current

    AppTheme {
        CompositionLocalProvider(LocalAppViewModel provides appViewModel) {
            CompositionLocalProvider(LocalSnackbarHostState provides snackbarHostState) {
                if (appState.isAuthorized) {
                    TabNavigator(ForYouTab()) {
                        Scaffold(
                            content = { CurrentTab() },
                            snackbarHost = { SnackbarHost(snackbarHostState) },
                            bottomBar = {
                                NavigationBar {
                                    TabNavigationItem(ForYouTab())
                                    TabNavigationItem(SettingsTab())
                                }
                            },
                        )
                    }
                } else {
                    Navigator(AuthScreen()) { navigator ->
                        SlideTransition(navigator)
                    }
                }
            }
        }
    }
}

expect fun getPlatformName(): String