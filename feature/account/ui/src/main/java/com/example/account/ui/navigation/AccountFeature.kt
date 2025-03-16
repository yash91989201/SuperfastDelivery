package com.example.account.ui.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.account.ui.screens.addresses.AddressesScreen
import com.example.account.ui.screens.addresses.AddressesViewModel
import com.example.account.ui.screens.create_profile.CreateProfileScreen
import com.example.account.ui.screens.create_profile.CreateProfileViewModel
import com.example.account.ui.screens.home.HomeScreen
import com.example.account.ui.screens.home.HomeViewModel
import com.example.account.ui.screens.new_address.NewAddressScreen
import com.example.account.ui.screens.new_address.NewAddressViewModel
import com.example.account.ui.screens.profile.ProfileScreen
import com.example.account.ui.screens.profile.ProfileViewModel
import com.example.common.navigation.Feature
import com.example.common.navigation.NavigationSubGraph
import com.example.common.navigation.NavigationSubGraphDest

interface AccountFeature : Feature

class AccountFeatureImpl : AccountFeature {
    override fun registerGraph(
        navHostController: NavHostController,
        navGraphBuilder: NavGraphBuilder,
    ) {
        navGraphBuilder.navigation<NavigationSubGraph.Account>(startDestination = NavigationSubGraphDest.AccountHome) {
            composable<NavigationSubGraphDest.AccountHome> {
                val homeViewModel = hiltViewModel<HomeViewModel>()
                HomeScreen(
                    viewModel = homeViewModel
                )
            }

            composable<NavigationSubGraphDest.AccountProfile> {
                val profileViewModel = hiltViewModel<ProfileViewModel>()
                ProfileScreen(
                    viewModel = profileViewModel
                )
            }

            composable<NavigationSubGraphDest.AccountCreateProfile> {
                val createProfileViewModel = hiltViewModel<CreateProfileViewModel>()
                CreateProfileScreen(
                    viewModel = createProfileViewModel
                )
            }

            composable<NavigationSubGraphDest.AccountNewAddress> {
                val newAddressViewModel = hiltViewModel<NewAddressViewModel>()
                NewAddressScreen(
                    viewModel = newAddressViewModel
                )
            }

            composable<NavigationSubGraphDest.AccountAddresses> {
                val addressesViewModel = hiltViewModel<AddressesViewModel>()
                AddressesScreen(
                    viewModel = addressesViewModel
                )
            }
        }
    }
}