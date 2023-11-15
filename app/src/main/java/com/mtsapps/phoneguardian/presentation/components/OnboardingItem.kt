package com.mtsapps.phoneguardian.presentation.components

import android.content.Context
import com.mtsapps.phoneguardian.R

class OnboardingItem(
    val title: String,
    val image: Int,
    val description : String
){

    companion object{
        fun getData(context: Context): List<OnboardingItem>{
            return listOf(
                OnboardingItem(context.getString(R.string.onboardingFirstTitle), R.drawable.calling_amico,context.getString(R.string.onboardingFirstDescription)),
                OnboardingItem(context.getString(R.string.onboardingSecondTitle), R.drawable.calling_cuate,context.getString(R.string.onboardingSecondDescription)),
                OnboardingItem(context.getString(R.string.onboardingThirdTitle), R.drawable.calling_rafiki,context.getString(R.string.onboardingThirdDescription)),
            )
        }
    }
}
