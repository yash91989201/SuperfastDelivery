package com.example.account.ui.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.account.ui.screens.about_app.AboutAppScreen
import com.example.account.ui.screens.about_app.AboutAppViewModel
import com.example.account.ui.screens.account_settings.AccountSettingsScreen
import com.example.account.ui.screens.account_settings.AccountSettingsViewModel
import com.example.account.ui.screens.addresses.AddressesScreen
import com.example.account.ui.screens.addresses.AddressesViewModel
import com.example.account.ui.screens.create_profile.CreateProfileScreen
import com.example.account.ui.screens.create_profile.CreateProfileViewModel
import com.example.account.ui.screens.help_center.HelpCenterScreen
import com.example.account.ui.screens.help_center.HelpCenterViewModel
import com.example.account.ui.screens.home.HomeScreen
import com.example.account.ui.screens.home.HomeViewModel
import com.example.account.ui.screens.new_address.NewAddressScreen
import com.example.account.ui.screens.new_address.NewAddressViewModel
import com.example.account.ui.screens.payment_methods.PaymentMethodsScreen
import com.example.account.ui.screens.payment_methods.PaymentMethodsViewModel
import com.example.account.ui.screens.privacy_policy.PrivacyPolicyScreen
import com.example.account.ui.screens.privacy_policy.PrivacyPolicyViewModel
import com.example.account.ui.screens.profile.ProfileScreen
import com.example.account.ui.screens.profile.ProfileViewModel
import com.example.account.ui.screens.promotions.PromotionsScreen
import com.example.account.ui.screens.promotions.PromotionsViewModel
import com.example.account.ui.screens.search_address.SearchAddressScreen
import com.example.account.ui.screens.search_address.SearchAddressViewModel
import com.example.account.ui.screens.terms_of_service.TermsOfServiceScreen
import com.example.account.ui.screens.terms_of_service.TermsOfServiceViewModel
import com.example.core.navigation.Feature
import com.example.core.navigation.NavigationSubGraph
import com.example.core.navigation.NavigationSubGraphDest

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
                val placeId = it.arguments?.getString("placeId")
                val newAddressViewModel = hiltViewModel<NewAddressViewModel>()
                NewAddressScreen(
                    placeId = placeId,
                    viewModel = newAddressViewModel
                )
            }

            composable<NavigationSubGraphDest.AccountSearchAddress> {
                val searchAddressViewModel = hiltViewModel<SearchAddressViewModel>()
                SearchAddressScreen(
                    viewModel = searchAddressViewModel
                )
            }

            composable<NavigationSubGraphDest.AccountAddresses> {
                val addressesViewModel = hiltViewModel<AddressesViewModel>()
                AddressesScreen(
                    viewModel = addressesViewModel
                )
            }

            composable<NavigationSubGraphDest.AccountPromotions> {
                val promotionsViewModel = hiltViewModel<PromotionsViewModel>()
                PromotionsScreen(
                    viewModel = promotionsViewModel
                )
            }

            composable<NavigationSubGraphDest.AccountPaymentMethods> {
                val paymentMethodsViewModel = hiltViewModel<PaymentMethodsViewModel>()
                PaymentMethodsScreen(
                    viewModel = paymentMethodsViewModel
                )
            }

            composable<NavigationSubGraphDest.AccountHelpCenter> {
                val helpCenterViewModel = hiltViewModel<HelpCenterViewModel>()
                HelpCenterScreen(
                    viewModel = helpCenterViewModel
                )
            }

            composable<NavigationSubGraphDest.AccountTermsOfService> {
                val termsOfServiceViewModel = hiltViewModel<TermsOfServiceViewModel>()
                TermsOfServiceScreen(
                    viewModel = termsOfServiceViewModel
                )
            }

            composable<NavigationSubGraphDest.AccountPrivacyPolicy> {
                val privacyPolicyViewModel = hiltViewModel<PrivacyPolicyViewModel>()
                PrivacyPolicyScreen(
                    viewModel = privacyPolicyViewModel
                )
            }

            composable<NavigationSubGraphDest.AccountSettings> {
                val settingsViewModel = hiltViewModel<AccountSettingsViewModel>()
                AccountSettingsScreen(
                    viewModel = settingsViewModel
                )
            }

            composable<NavigationSubGraphDest.AccountAboutApp> {
                val aboutAppViewModel = hiltViewModel<AboutAppViewModel>()
                AboutAppScreen(
                    viewModel = aboutAppViewModel
                )
            }
        }
    }
}