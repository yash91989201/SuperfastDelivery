package com.example.superfastdelivery.screens.onboarding

import androidx.annotation.DrawableRes
import com.example.superfastdelivery.R

sealed class OnboardingModel (
    @DrawableRes val image : Int,
    val title:String,
    val description:String
){

    data object  First: OnboardingModel(
        image = R.drawable.onboarding_img_1,
        title = "Local shops available online",
        description = "Order products from your nearby stores."
    )

    data object  Second: OnboardingModel(
        image = R.drawable.onboarding_img_2,
        title = "Superfast Delivery",
        description = "Get your order on as fast as possible."
    )

    data object  Third: OnboardingModel(
        image = R.drawable.onboarding_img_3,
        title = "Order tracking",
        description = "Track your order in real time."
    )
}