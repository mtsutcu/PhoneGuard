package com.mtsapps.phoneguardian.presentation.categories

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mtsapps.phoneguardian.R
import com.mtsapps.phoneguardian.presentation.components.CategoriesCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(
    categoriesViewModel: CategoriesViewModel = hiltViewModel(),
    ) {

    val uiState by categoriesViewModel.uiState.collectAsState()
    val categoryList = uiState.categoryWithContactsList
    Scaffold(topBar = {
        TopAppBar(title = { Text(text = stringResource(id = R.string.categoriesText)) })
    }) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = innerPadding.calculateTopPadding()), verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = dimensionResource(id = R.dimen.padding8), vertical = dimensionResource(id = R.dimen.padding8))
        ) {
            categoryList?.let {
                itemsIndexed(items = it) { _, item ->
                    CategoriesCard(
                        categoryWithContacts = item,
                        setCategoryTimer = { category, isAlarm ->
                            categoriesViewModel.updateCategory(category = category.copy(isAlarm = isAlarm))
                        },
                        setCategoryStartTime = { category, s ->
                            categoriesViewModel.updateCategory(
                                category = category.copy(
                                    blockedStartTime = s
                                )
                            )
                        },
                        setCategoryEndTime = { category, s ->
                            categoriesViewModel.updateCategory(
                                category = category.copy(
                                    blockedEndTime = s
                                )
                            )
                        },
                        setCategoryBlocked = { category, isBlocked ->
                            categoriesViewModel.updateCategory(category.copy(isActive = isBlocked))
                        }) {
                        categoriesViewModel.deleteContact(it)
                    }

                }
            }
        }

    }
}