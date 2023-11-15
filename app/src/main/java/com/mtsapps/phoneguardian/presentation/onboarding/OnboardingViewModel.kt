package com.mtsapps.phoneguardian.presentation.onboarding

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mtsapps.phoneguardian.domain.use_case.pref_data_store_use_case.UpdateBooleanPrefUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(private val updateBooleanPrefUseCase: UpdateBooleanPrefUseCase,) : ViewModel() {

    fun updateIsFirstOpen(){
        viewModelScope.launch{
            updateBooleanPrefUseCase(booleanPreferencesKey("isFirstOpen"),false)
        }
    }
}