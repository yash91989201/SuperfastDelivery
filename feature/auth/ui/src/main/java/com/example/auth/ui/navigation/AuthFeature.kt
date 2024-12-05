package com.example.auth.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.auth.ui.screens.email_sign_in.EmailSignInScreen
import com.example.auth.ui.screens.sign_in.SignInScreen
import com.example.auth.ui.screens.verify_email.VerifyEmailScreen
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
                EmailSignInScreen(
                    onGoBack = {
                        navHostController.navigate(NavigationSubGraphDest.SignIn)
                    },
                    onContinue = {
                        navHostController.navigate(NavigationSubGraphDest.VerifyEmail)
                    }
                )
            }

            composable<NavigationSubGraphDest.VerifyEmail> {
                VerifyEmailScreen(
                    onGoBack = {
                        navHostController.navigate(NavigationSubGraphDest.EmailSignIn)
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