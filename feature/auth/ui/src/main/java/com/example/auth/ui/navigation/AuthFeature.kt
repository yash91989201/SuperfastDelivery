package com.example.auth.ui.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.example.auth.ui.screens.email_sign_in.EmailSignInScreen
import com.example.auth.ui.screens.email_sign_in.EmailSignInViewModel
import com.example.auth.ui.screens.home.HomeScreen
import com.example.auth.ui.screens.home.HomeViewModel
import com.example.auth.ui.screens.verify_email.VerifyEmailScreen
import com.example.auth.ui.screens.verify_email.VerifyEmailViewModel
import com.example.auth.ui.screens.verify_phone.VerifyPhoneScreen
import com.example.auth.ui.screens.verify_phone.VerifyPhoneViewModel
import com.example.core.navigation.Feature
import com.example.core.navigation.NavigationSubGraph
import com.example.core.navigation.NavigationSubGraphDest

interface AuthFeature : Feature

class AuthFeatureImpl : AuthFeature {
    override fun registerGraph(
        navHostController: NavHostController,
        navGraphBuilder: NavGraphBuilder,
        startDestination: NavigationSubGraphDest?
    ) {
        navGraphBuilder.navigation<NavigationSubGraph.Auth>(
            startDestination = startDestination ?: NavigationSubGraphDest.AuthHome
        ) {
            composable<NavigationSubGraphDest.AuthHome> {
                val homeViewModel = hiltViewModel<HomeViewModel>()
                HomeScreen(
                    viewModel = homeViewModel
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
                val route = it.toRoute<NavigationSubGraphDest.AuthVerifyPhone>()
                val verifyPhoneViewModel = hiltViewModel<VerifyPhoneViewModel>()
                VerifyPhoneScreen(
                    phone = route.phone,
                    viewModel = verifyPhoneViewModel
                )
            }
        }
    }
}