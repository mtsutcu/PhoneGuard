package com.mtsapps.phoneguardian.presentation.contacts

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.mtsapps.phoneguardian.R
import com.mtsapps.phoneguardian.domain.models.TabRowItem
import com.mtsapps.phoneguardian.domain.utils.GetCallLogs
import com.mtsapps.phoneguardian.ui.theme.PhoneGuardianTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ContactsMainScreen(
    contactsViewModel: ContactsViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val tabList2 = listOf(
        TabRowItem(
            title = "Contacts",
            selectedIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.image_person),
                    contentDescription = ""
                )
            },
            unselectedIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.image_person_outline),
                    contentDescription = ""
                )
            },
            screen = {
                ContactsScreen(
                    modifier = Modifier,
                    contactsViewModel
                )
            },
        ),
        TabRowItem(
            title = "Incoming",
            selectedIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.call_recieved_icon),
                    contentDescription = ""
                )
            },
            unselectedIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.call_recieved_icon),
                    contentDescription = ""
                )
            },
            screen = {
                IncomingScreen(
                    modifier = Modifier,
                    callLogList = GetCallLogs.readIncomingCalls(contentResolver = context.contentResolver),
                    contactsViewModel = contactsViewModel
                )
            },
        ),
        TabRowItem(
            title = "Outgoing",
            selectedIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.call_outgoing_icon),
                    contentDescription = ""
                )
            },
            unselectedIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.call_outgoing_icon),
                    contentDescription = ""
                )
            },
            screen = {
                OutgoingScreen(
                    modifier = Modifier,
                    callLogList = GetCallLogs.readOutgoingCalls(contentResolver = context.contentResolver),
                    contactsViewModel = contactsViewModel
                )
            }),
        TabRowItem(
            title = "Missed",
            selectedIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.call_missed_icon),
                    contentDescription = ""
                )
            },
            unselectedIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.call_missed_icon),
                    contentDescription = ""
                )
            },
            screen = {
                MissedScreen(
                    modifier = Modifier,
                    callLogList = GetCallLogs.readMissedCalls(contentResolver = context.contentResolver),
                    contactsViewModel = contactsViewModel
                )
            })
    )
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        tabList2.size
    }
    Column {
        Column(modifier = Modifier.fillMaxWidth()) {
            TopAppBar(title = { Text(text = "Contacts", style = MaterialTheme.typography.titleLarge) })
            PhonesTab(
                pagerState = pagerState,
                scope = scope,
                tabsList = tabList2
            )
        }
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
            HorizontalPager(state = pagerState) { page ->
                tabList2[page].screen.invoke()

            }

        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PhonesTab(
    pagerState: PagerState,
    scope: CoroutineScope,
    tabsList: List<TabRowItem>,
) {
    TabRow(
        selectedTabIndex = pagerState.currentPage,

        ) {
        tabsList.forEachIndexed { index, tab ->
            Tab(selected = index == pagerState.currentPage, onClick = {
                scope.launch {
                    pagerState.animateScrollToPage(index)
                }
            }, text = {
                Text(
                    text = tab.title, style = MaterialTheme.typography.titleSmall
                )
            }, icon = {
                if (index == pagerState.currentPage) {
                    tab.selectedIcon.invoke()
                } else tab.unselectedIcon.invoke()

            })
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PhoneGuardianTheme {
    }
}