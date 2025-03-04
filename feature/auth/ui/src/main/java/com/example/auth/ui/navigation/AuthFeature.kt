package com.example.auth.ui.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.example.auth.ui.screens.email_sign_in.EmailSignInScreen
import com.example.auth.ui.screens.email_sign_in.EmailSignInViewModel
import com.example.auth.ui.screens.sign_in.SignInScreen
import com.example.auth.ui.screens.verify_email.VerifyEmailScreen
import com.example.auth.ui.screens.verify_email.VerifyEmailViewModel
import com.example.auth.ui.screens.verify_phone.VerifyPhoneScreen
import com.example.common.application_state_store.ApplicationStateStore
import com.example.common.navigation.Feature
import com.example.common.navigation.NavigationSubGraphDest
import com.example.common.navigation.NavigationSubGraph

interface AuthFeature : Feature

class AuthFeatureImpl : AuthFeature {
    override fun registerGraph(
        navHostController: NavHostController,
        navGraphBuilder: NavGraphBuilder,
        applicationStateStore: ApplicationStateStore,
    ) {
        navGraphBuilder.navigation<NavigationSubGraph.Auth>(startDestination = NavigationSubGraphDest.AuthSignIn) {
            composable<NavigationSubGraphDest.AuthSignIn> {
                SignInScreen(
                    onPhoneSignIn = {
                        navHostController.navigate(NavigationSubGraphDest.AuthVerifyPhone)
                    },
                    onEmailSignIn = {
                        navHostController.navigate(NavigationSubGraphDest.AuthEmailSignIn)
                    }
                )
            }

            composable<NavigationSubGraphDest.AuthEmailSignIn> {
                val viewModel = hiltViewModel<EmailSignInViewModel>()
                EmailSignInScreen(
                    viewModel = viewModel,
                    onGoBack = {
                        navHostController.navigate(NavigationSubGraphDest.AuthSignIn)
                    },
                    onContinue = { email ->
                        navHostController.navigate(NavigationSubGraphDest.AuthVerifyEmail(email = email))
                    }
                )
            }

            composable<NavigationSubGraphDest.AuthVerifyEmail> {
                val route = it.toRoute<NavigationSubGraphDest.AuthVerifyEmail>()
                val viewModel = hiltViewModel<VerifyEmailViewModel>()
                VerifyEmailScreen(
                    email = route.email,
                    viewModel = viewModel,
                    onGoBack = {
                        navHostController.navigate(NavigationSubGraphDest.AuthEmailSignIn)
                    },
                    goToSearchHomeScreen = {
                        navHostController.navigate(NavigationSubGraphDest.SearchHome)
                    },
                    goToAccountCreateProfileScreen = {
                        navHostController.navigate(NavigationSubGraphDest.AccountCreateProfile)
                    }
                )
            }

            composable<NavigationSubGraphDest.AuthVerifyPhone> {
                VerifyPhoneScreen(
                    onGoBack = {
                        navHostController.navigate(NavigationSubGraphDest.AuthSignIn)
                    }
                )
            }
        }
    }
}