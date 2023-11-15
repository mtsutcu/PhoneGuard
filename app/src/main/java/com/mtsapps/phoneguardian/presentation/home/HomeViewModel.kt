package com.mtsapps.phoneguardian.presentation.home

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mtsapps.phoneguardian.data.entities.Contact
import com.mtsapps.phoneguardian.domain.use_case.call_contacts_use_case.InsertCallContactsUseCase
import com.mtsapps.phoneguardian.domain.use_case.contact_use_case.DeleteContactUseCase
import com.mtsapps.phoneguardian.domain.use_case.contact_use_case.GetContactsUseCase
import com.mtsapps.phoneguardian.domain.use_case.pref_data_store_use_case.GetBooleanPrefUseCase
import com.mtsapps.phoneguardian.domain.use_case.pref_data_store_use_case.UpdateBooleanPrefUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getContactsUseCase: GetContactsUseCase,
    private val getBooleanPrefUseCase: GetBooleanPrefUseCase,
    private val updateBooleanPrefUseCase: UpdateBooleanPrefUseCase,
    private val deleteContactUseCase: DeleteContactUseCase,
    private val insertCallContactsUseCase: InsertCallContactsUseCase,
    ) : ViewModel() {

    private val _contacts = MutableLiveData<List<Contact>>()
    val contacts: LiveData<List<Contact>>
        get() = _contacts
    private val _blockStatus= MutableLiveData<Boolean>()
    val blockStatus: LiveData<Boolean>
        get() = _blockStatus
    init {
        getContacts()
        getBlockStatus()
    }

    private fun getContacts() {
        viewModelScope.launch {
            getContactsUseCase().collect {
                _contacts.value = it
            }
        }
    }

    private fun getBlockStatus(){
        viewModelScope.launch {
            getBooleanPrefUseCase(booleanPreferencesKey("isBlock")).collect{
                _blockStatus.value = it
            }
        }
    }
    fun updateBlockStatus(status : Boolean){
        viewModelScope.launch {
            updateBooleanPrefUseCase(key = booleanPreferencesKey("isBlock"),status=status)
        }
    }
    fun deleteContact(contact: Contact){
        viewModelScope.launch {
            deleteContactUseCase(contact = contact)
        }
    }

}