package com.reportvox.reportvoxcontributor.presentation.auth

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.reportvox.reportvoxcontributor.AppViewModel
import com.reportvox.reportvoxcontributor.LocalAppViewModel
import com.reportvox.reportvoxcontributor.LocalSnackbarHostState
import com.reportvox.reportvoxcontributor.di.getViewModel
import com.reportvox.reportvoxcontributor.presentation.components.ButtonContent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.experimental.ExperimentalObjCRefinement
import kotlin.native.HiddenFromObjC

class LoginViewModel : StateScreenModel<LoginViewModel.State>(State()) {
    data class State(
        val isLoading: Boolean = false,
        val email: String = "",
        val password: String = "",
    )

    sealed interface Event {
        data class OnEmailChanged(val email: String) : Event
        data class OnPasswordChanged(val password: String) : Event
        data class OnLoginClicked(val onCompleted: (Boolean) -> Unit) : Event
    }

    fun onEvent(e: Event) {
        when (e) {
            is Event.OnEmailChanged -> {
                mutableState.update { it.copy(email = e.email) }
            }

            is Event.OnPasswordChanged -> {
                mutableState.update { it.copy(password = e.password) }
            }

            is Event.OnLoginClicked -> {
                mutableState.update { it.copy(isLoading = true) }
                coroutineScope.launch {
                    delay(1000)
                    mutableState.update { it.copy(isLoading = false) }
                    e.onCompleted(true)
                }
            }
        }
    }
}


@OptIn(ExperimentalObjCRefinement::class)
@HiddenFromObjC
class LoginScreen : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val appViewModel = LocalAppViewModel.current

        val viewModel = getViewModel<LoginViewModel>()
        val state by viewModel.state.collectAsState()

        val snackbarHostState = LocalSnackbarHostState.current
        val scope = rememberCoroutineScope()

        Scaffold(
            topBar = {
                TopAppBar(
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                navigator.pop()
                            }
                        ) {
                            Icon(Icons.Outlined.ArrowBack, contentDescription = null)
                        }
                    },
                    title = { Text(text = "Login") },
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding).padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Column(Modifier.padding(bottom = 16.dp)) {
                    AnimatedVisibility(visible = state.isLoading) {
                        LinearProgressIndicator(Modifier.fillMaxWidth())
                    }
                }

                OutlinedTextField(
                    value = state.email,
                    onValueChange = { viewModel.onEvent(LoginViewModel.Event.OnEmailChanged(it)) },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                )

                OutlinedTextField(
                    value = state.password,
                    onValueChange = { viewModel.onEvent(LoginViewModel.Event.OnPasswordChanged(it)) },
                    label = { Text("Password") },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                    visualTransformation = PasswordVisualTransformation()
                )

                Spacer(Modifier.height(16.dp))

                Button(
                    onClick = {
                        viewModel.onEvent(LoginViewModel.Event.OnLoginClicked {
                            appViewModel.onEvent(AppViewModel.Event.OnAuthorized(it))
                            scope.launch {
                                snackbarHostState.showSnackbar(if (it) "Login success" else "Login failed")
                            }
                        })
                    },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                ) {
                    ButtonContent {
                        Text(text = "Continue")
                    }
                }
            }
        }
    }
}