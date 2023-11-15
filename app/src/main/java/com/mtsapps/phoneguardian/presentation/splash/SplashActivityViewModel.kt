package com.mtsapps.phoneguardian.presentation.splash

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mtsapps.phoneguardian.domain.use_case.pref_data_store_use_case.GetBooleanPrefUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashActivityViewModel @Inject constructor(private val getBooleanPrefUseCase: GetBooleanPrefUseCase) : ViewModel() {

    private val _isLoading = MutableLiveData(true)
    val isLoading = _isLoading
    private val _isFirstOpen = MutableLiveData<Boolean>()
    val isFirstOpen = _isFirstOpen

    init {
        checkIsFirstOpen()
    }
    private fun checkIsFirstOpen(){
        viewModelScope.launch {
            getBooleanPrefUseCase(booleanPreferencesKey("isFirstOpen")).collect{
                _isFirstOpen.postValue(it)
                _isLoading.value = false
            }
        }
    }
}