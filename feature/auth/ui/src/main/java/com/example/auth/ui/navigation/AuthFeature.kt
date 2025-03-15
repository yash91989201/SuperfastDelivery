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
import com.example.common.navigation.NavigationSubGraph
import com.example.common.navigation.NavigationSubGraphDest

interface AuthFeature : Feature

class AuthFeatureImpl : AuthFeature {
    override fun registerGraph(
        navHostController: NavHostController,
        navGraphBuilder: NavGraphBuilder,
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
                val emailSignInViewModel = hiltViewModel<EmailSignInViewModel>()
                EmailSignInScreen(
                    viewModel = emailSignInViewModel,
                )
            }

            composable<NavigationSubGraphDest.AuthVerifyEmail> {
                val route = it.toRoute<NavigationSubGraphDest.AuthVerifyEmail>()
                val verifyEmailViewModel = hiltViewModel<VerifyEmailViewModel>()
                VerifyEmailScreen(
                    email = route.email,
                    viewModel = verifyEmailViewModel,
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