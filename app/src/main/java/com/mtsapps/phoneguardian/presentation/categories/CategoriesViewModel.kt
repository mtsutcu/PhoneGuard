package com.mtsapps.phoneguardian.presentation.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mtsapps.phoneguardian.data.entities.Category
import com.mtsapps.phoneguardian.data.entities.Contact
import com.mtsapps.phoneguardian.domain.use_case.DeleteContactUseCase
import com.mtsapps.phoneguardian.domain.use_case.GetCategoriesUseCase
import com.mtsapps.phoneguardian.domain.use_case.GetCategoriesWithContactsUseCase
import com.mtsapps.phoneguardian.domain.use_case.UpdateCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getCategoriesWithContactsUseCase: GetCategoriesWithContactsUseCase,
    private val deleteContactUseCase: DeleteContactUseCase,
    private val updateCategoryUseCase: UpdateCategoryUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(CategoriesUIState())
    val uiState: StateFlow<CategoriesUIState> = _uiState.asStateFlow()

    init {
        getCategories()
        getCategoriesWithContacts()
    }

    private fun getCategories() {
        viewModelScope.launch {
            getCategoriesUseCase().collect { categoryList ->
                _uiState.update { uiState ->
                    uiState.copy(categoryList = categoryList)
                }
            }
        }
    }

    private fun getCategoriesWithContacts() {
        viewModelScope.launch {
            getCategoriesWithContactsUseCase().collect{
                _uiState.update { uiState->
                    uiState.copy(
                        categoryWithContactsList = it
                    )
                }
            }
        }
    }

    fun deleteContact(contact: Contact){
        viewModelScope.launch {
            deleteContactUseCase(contact)
        }
    }

    fun updateCategory(category: Category){
        viewModelScope.launch {
            updateCategoryUseCase(category)
        }
    }

}