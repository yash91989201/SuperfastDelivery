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
import com.example.common.navigation.Feature
import com.example.common.navigation.NavigationSubGraphDest
import com.example.common.navigation.NavigationSubGraph

interface AuthFeature : Feature

class AuthFeatureImpl : AuthFeature {
    override fun registerGraph(
        navHostController: NavHostController,
        navGraphBuilder: NavGraphBuilder
    ) {
        navGraphBuilder.navigation<NavigationSubGraph.Auth>(startDestination = NavigationSubGraphDest.SignIn) {
            composable<NavigationSubGraphDest.SignIn> {
                SignInScreen(
                    onPhoneSignIn = {
                        navHostController.navigate(NavigationSubGraphDest.VerifyPhone)
                    },
                    onEmailSignIn = {
                        navHostController.navigate(NavigationSubGraphDest.EmailSignIn)
                    }
                )
            }

            composable<NavigationSubGraphDest.EmailSignIn> {
                val viewModel = hiltViewModel<EmailSignInViewModel>()
                EmailSignInScreen(
                    viewModel = viewModel,
                    onGoBack = {
                        navHostController.navigate(NavigationSubGraphDest.SignIn)
                    },
                    onContinue = { email ->
                        navHostController.navigate(NavigationSubGraphDest.VerifyEmail(email = email))
                    }
                )
            }

            composable<NavigationSubGraphDest.VerifyEmail> {
                val route = it.toRoute<NavigationSubGraphDest.VerifyEmail>()
                val viewModel = hiltViewModel<VerifyEmailViewModel>()
                VerifyEmailScreen(
                    email = route.email,
                    viewModel = viewModel,
                    onGoBack = {
                        navHostController.navigate(NavigationSubGraphDest.EmailSignIn)
                    },
                    onContinue = {
                        // go to create profile / home screen
                    }
                )
            }

            composable<NavigationSubGraphDest.VerifyPhone> {
                VerifyPhoneScreen(
                    onGoBack = {
                        navHostController.navigate(NavigationSubGraphDest.SignIn)
                    }
                )
            }
        }
    }
}