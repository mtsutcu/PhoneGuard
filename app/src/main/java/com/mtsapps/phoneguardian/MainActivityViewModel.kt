package com.mtsapps.phoneguardian

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mtsapps.phoneguardian.data.entities.CallContact
import com.mtsapps.phoneguardian.domain.use_case.call_contacts_use_case.DeleteAllCallContactsUseCase
import com.mtsapps.phoneguardian.domain.use_case.call_contacts_use_case.InsertCallContactsUseCase
import com.mtsapps.phoneguardian.domain.use_case.pref_data_store_use_case.GetBooleanPrefUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val insertCallContactsUseCase: InsertCallContactsUseCase,
    private val deleteCallContactsUseCase: DeleteAllCallContactsUseCase,
) : ViewModel() {



    fun insertCallContacts(callContactList: List<CallContact>) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                insertCallContactsUseCase(callContactList = callContactList)
            }
        }
    }

    fun deleteAllCallContact() {
        viewModelScope.launch {
            deleteCallContactsUseCase()
        }
    }

}