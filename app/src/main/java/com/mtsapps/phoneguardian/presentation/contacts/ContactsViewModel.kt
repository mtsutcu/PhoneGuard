package com.mtsapps.phoneguardian.presentation.contacts

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mtsapps.phoneguardian.data.entities.Contact
import com.mtsapps.phoneguardian.data.repository.ContactRepositoryImpl
import com.mtsapps.phoneguardian.domain.use_case.DeleteContactUseCase
import com.mtsapps.phoneguardian.domain.use_case.GetAllPhoneNumbersUseCase
import com.mtsapps.phoneguardian.domain.use_case.GetCategoriesWithContactsUseCase
import com.mtsapps.phoneguardian.domain.use_case.GetContactsUseCase
import com.mtsapps.phoneguardian.domain.use_case.InsertContactUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val insertContactUseCase: InsertContactUseCase,
    private val getCategoriesWithContactsUseCase: GetCategoriesWithContactsUseCase,
    private val getPersonsUseCase: GetContactsUseCase,
    private val getAllPhoneNumbersUseCase: GetAllPhoneNumbersUseCase,
    private val deleteContactUseCase: DeleteContactUseCase,
    private val categoriesRepositoryImpl: ContactRepositoryImpl
) : ViewModel() {
    private val _uiState = MutableStateFlow(PersonsUIState())
    val uiState: StateFlow<PersonsUIState> = _uiState.asStateFlow()

    init {
        getCategoryWithPerson()
        getPersons()
        getAllPhoneNumbers()
        getCategories()
    }

    fun insertContact(contact: Contact){
        viewModelScope.launch {
                insertContactUseCase(contact = contact)
        }
    }
    fun deleteContact(contact: Contact){
        viewModelScope.launch {
            deleteContactUseCase(contact)
        }
    }

    private fun getAllPhoneNumbers(){
        viewModelScope.launch {
            getAllPhoneNumbersUseCase().collect{numbers->
                _uiState.update { uiState->
                    uiState.copy(phoneNumbersList =numbers )
                }
                Log.e("numberrs",numbers.toString())
            }
        }
    }

    private fun getCategoryWithPerson() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                getCategoriesWithContactsUseCase().collect {
                    _uiState.update { uiState ->
                        uiState.copy(
                            categoryListWithPersons = it
                        )
                    }
                }
            }
        }
    }

    private fun getPersons() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                getPersonsUseCase().collect {
                    _uiState.update { uiState ->
                        uiState.copy(
                            contactList = it
                        )
                    }
                }
            }
        }
    }
    private fun getCategories(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                categoriesRepositoryImpl.getCategories().collect{
                    _uiState.update { uiState->
                        uiState.copy(categoryList = it)
                    }
                }
            }
        }
    }
}