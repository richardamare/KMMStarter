package com.reportvox.reportvoxcontributor.presentation.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.reportvox.reportvoxcontributor.LocalSnackbarHostState
import kotlin.experimental.ExperimentalObjCRefinement
import kotlin.native.HiddenFromObjC

@OptIn(ExperimentalObjCRefinement::class)
@HiddenFromObjC
class AuthScreen : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        val snackbarHostState = LocalSnackbarHostState.current

        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
        ) {
            Column(
                modifier = Modifier.padding(it).padding(horizontal = 16.dp).padding(bottom = 48.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Spacer(
                    Modifier.weight(0.5f)
                )

                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = "KMMStarter",
                        style = MaterialTheme.typography.displaySmall,
                    )
                }

                Spacer(
                    Modifier.weight(0.5f)
                )

                Row {
                    Button(
                        onClick = {
                            navigator.push(LoginScreen())
                        },
                        modifier = Modifier.fillMaxWidth().weight(1f),
                    ) {
                        Text(text = "Login", modifier = Modifier.padding(vertical = 4.dp))
                    }

                    Spacer(Modifier.width(8.dp))

                    FilledTonalButton(
                        onClick = {
                            navigator.push(RegisterScreen())
                        },
                        modifier = Modifier.fillMaxWidth().weight(1f),
                    ) {
                        Text(text = "Register", modifier = Modifier.padding(vertical = 4.dp))
                    }
                }
            }
        }
    }
}

