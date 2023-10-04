package com.mtsapps.phoneguardian.presentation.contacts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mtsapps.phoneguardian.data.entities.Contact
import com.mtsapps.phoneguardian.domain.models.CallContact
import com.mtsapps.phoneguardian.domain.utils.nameFromUnknownCaller
import com.mtsapps.phoneguardian.presentation.components.BlockBottomSheet
import com.mtsapps.phoneguardian.presentation.components.bottom_navigation.CallLogCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutgoingScreen(
    modifier: Modifier,
    callLogList: List<CallContact>,
    contactsViewModel: ContactsViewModel,
) {
    val uiState by contactsViewModel.uiState.collectAsState()
    val phones = uiState.phoneNumbersList
    val categoryList = uiState.categoryList
    Scaffold { innerPaddig ->
        val isExpanded = remember { mutableIntStateOf(-1) }
        val bottomSheetStateState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        val openBottomSheetState = rememberSaveable { mutableStateOf(false) }
        val bottomSheetContactState = remember {
            mutableStateOf(CallContact(null, null, null))
        }
        if (openBottomSheetState.value) {
            val isBlockedState = remember { mutableStateOf(false) }
            isBlockedState.value =
                bottomSheetContactState.value.number?.let { phones?.contains(it) } == true
            categoryList?.let {
                BlockBottomSheet(
                    bottomSheetState = bottomSheetStateState,
                    openBottomSheetState = openBottomSheetState,
                    categoryList = it,
                    bottomSheetContactState,
                    isBlocked = isBlockedState
                ) { category, callContact ->
                    bottomSheetContactState.value.number?.let {
                        if (isBlockedState.value) {
                            uiState.contactList?.let { contactList ->
                                contactsViewModel.deleteContact(
                                    contactList.first { contact -> contact.number == callContact.number })
                            }
                        } else {
                            contactsViewModel.insertContact(
                                Contact(
                                    categoryID = category.categoryID,
                                    name = callContact.name,
                                    number = callContact.number,
                                    photoUri = callContact.photoUri
                                )
                            )
                        }
                    }
                }
            }
        }
        LazyColumn(
            modifier = modifier
                .padding(innerPaddig)
                .background(color = MaterialTheme.colorScheme.surface)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            itemsIndexed(items = callLogList) { index, item ->
                val containName = item.number?.let { phones?.contains(it) } == true
                CallLogCard(name = item.name.nameFromUnknownCaller(),
                    number = if (!item.number.isNullOrEmpty()) item.number else "Unknown",
                    photo = item.photoUri,
                    isExpanded = isExpanded,
                    index = index,
                    buttonText = if (item.number.let { phones?.contains(it) } == true) "Unblock" else "Block",
                    itemOnClick = {
                        if (isExpanded.value == index) isExpanded.value = -1 else isExpanded.value =
                            index

                    },
                    buttonOnClick = {
                        if (containName) {
                            val containContact =
                                uiState.contactList?.firstOrNull { contactNumber -> contactNumber.number == item.number }
                            containContact?.let { it1 -> contactsViewModel.deleteContact(it1) }

                        } else {
                            bottomSheetContactState.value = CallContact(
                                name = item.name.nameFromUnknownCaller(),
                                number = item.number,
                                item.photoUri
                            )
                            openBottomSheetState.value = true
                        }
                    })
                if (index != callLogList.lastIndex) {
                    Divider(thickness = 1.dp, modifier = modifier.padding(start = 60.dp))
                }
            }


        }

    }
}