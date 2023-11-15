package com.mtsapps.phoneguardian.presentation.contacts

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.mtsapps.phoneguardian.R
import com.mtsapps.phoneguardian.data.entities.CallContact
import com.mtsapps.phoneguardian.data.entities.Contact
import com.mtsapps.phoneguardian.domain.utils.nameFromUnknownCaller
import com.mtsapps.phoneguardian.presentation.components.BlockBottomSheet
import com.mtsapps.phoneguardian.presentation.components.bottom_navigation.CallLogCard
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class)
@SuppressLint("SuspiciousIndentation")
@Composable
fun ContactsScreen(
    contactsViewModel: ContactsViewModel,
) {
    val uiState by contactsViewModel.uiState.collectAsState()
    val contactsList = uiState.localContactList
    val context = LocalContext.current
    val isGranted = remember {
        mutableStateOf(false)
    }
    val snackBarState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val readContactsPermission = rememberPermissionState(permission = "android.permission.READ_CONTACTS"){granted->
        if (granted){
            isGranted.value = true
        }else{
            scope.launch {
                snackBarState.showSnackbar(message = context.getString(R.string.permissionSnackBarText),actionLabel = context.getString(R.string.settingsText), duration = SnackbarDuration.Long)
            }
        }

    }
LaunchedEffect(key1 = Unit){
    readContactsPermission.launchPermissionRequest()
}
if (isGranted.value){
    if (contactsList != null) {
        ContactsScreenContent( contactsViewModel =contactsViewModel , contactsList =contactsList )
    }
}



}
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactsScreenContent(modifier: Modifier = Modifier,contactsViewModel: ContactsViewModel,contactsList : List<CallContact>){
    val uiState by contactsViewModel.uiState.collectAsState()
    val phones = uiState.phoneNumbersList
    val categoryList = uiState.categoryList
    Scaffold {_ ->
        val isExpanded = remember { mutableIntStateOf(-1) }
        val bottomSheetStateState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        val openBottomSheetState = rememberSaveable { mutableStateOf(false) }
        val bottomSheetContactState = remember {
            mutableStateOf(CallContact(null, null, null,null))
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
                    bottomSheetCallContactState= bottomSheetContactState,
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
                .background(color = MaterialTheme.colorScheme.surface)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            itemsIndexed(items = contactsList) { index, item ->
                val containName = item.number?.let { phones?.contains(it) } == true
                CallLogCard(
                    name = if (!item.name.isNullOrEmpty()) item.name else stringResource(id = R.string.unknownText),
                    number = item.number!!,
                    photo = item.photoUri,
                    isExpanded = isExpanded,
                    index = index,
                    buttonText = if (item.number.let { phones?.contains(it) } == true) stringResource(
                        id = R.string.unblockText
                    ) else stringResource(id = R.string.blockText),
                    itemOnClick = {
                        if (isExpanded.intValue == index) isExpanded.intValue =
                            -1 else isExpanded.intValue =
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
                                photoUri = item.photoUri
                            )
                            openBottomSheetState.value = true
                        }
                    })
                if (index != contactsList.lastIndex) {
                    HorizontalDivider(modifier = modifier.padding(start = dimensionResource(id = R.dimen.padding30)))
                }

            }
        }

    }
}


