package com.mtsapps.phoneguardian.presentation.onboarding

import android.Manifest
import android.app.Activity
import android.app.role.RoleManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.mtsapps.phoneguardian.R
import com.mtsapps.phoneguardian.domain.utils.findActivity
import com.mtsapps.phoneguardian.domain.utils.gotoApplicationSettings
import com.mtsapps.phoneguardian.domain.utils.showSnackBar
import com.mtsapps.phoneguardian.navigation.Screens
import com.mtsapps.phoneguardian.presentation.components.OnboardingItem
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalPagerApi::class, ExperimentalMaterial3Api::class,
    ExperimentalPermissionsApi::class
)
@Composable
fun OnboardingScreen(modifier: Modifier = Modifier,navController: NavController) {
    val viewModel = hiltViewModel<OnboardingViewModel>()
    val context = LocalContext.current
    val items = OnboardingItem.getData(context)
    val scope = rememberCoroutineScope()
    val pageState = rememberPagerState()
    val snackBarState = remember { SnackbarHostState() }
    val roleManager =
        context.findActivity().getSystemService(ComponentActivity.ROLE_SERVICE) as RoleManager
    val intent = roleManager.createRequestRoleIntent("android.app.role.CALL_SCREENING")
    val backIconVisibility = remember {
        mutableStateOf(false)
    }
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                scope.launch {
                    pageState.animateScrollToPage(pageState.currentPage + 1)
                }
                backIconVisibility.value = true
            } else {
                snackBarState.showSnackBar(
                    message = context.getString(R.string.permissionSnackBarText),
                    action = context.getString(R.string.settingsText),
                    coroutineScope = scope,
                    onSnackBarAction = { context.gotoApplicationSettings() },
                    onSnackBarDismiss = {},
                    duration = SnackbarDuration.Short
                )


            }
        }
    val readContactsPermission =
        rememberMultiplePermissionsState(
            permissions = listOf(
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.READ_PHONE_STATE,
            )
        ) { result ->
            if (result.values.contains(false) ) {
                snackBarState.showSnackBar(
                    message = context.getString(R.string.permissionSnackBarText),
                    action = context.getString(R.string.settingsText),
                    coroutineScope = scope,
                    onSnackBarAction = { context.gotoApplicationSettings() },
                    onSnackBarDismiss = {},
                    duration = SnackbarDuration.Short
                )
            } else if(result.values.isNotEmpty()) {
                scope.launch {
                    pageState.animateScrollToPage(pageState.currentPage + 1)
                }

            }

        }

    val readCallLogPermissions =
        rememberMultiplePermissionsState(
            permissions = listOf(
                Manifest.permission.READ_CALL_LOG,
            )
        ) { result ->
            if (result.values.contains(false)) {
                snackBarState.showSnackBar(
                    message = context.getString(R.string.permissionSnackBarText),
                    action = context.getString(R.string.settingsText),
                    coroutineScope = scope,
                    onSnackBarAction = { context.gotoApplicationSettings() },
                    onSnackBarDismiss = {},
                    duration = SnackbarDuration.Short
                )
            } else if(result.values.isNotEmpty()) {
                navController.navigate(Screens.MainScreen.route){
                    popUpTo(route = "root")
                }
                viewModel.updateIsFirstOpen()
            }

        }

    Scaffold(topBar = {
        TopAppBar(title = { /*TODO*/ }, navigationIcon = {
          AnimatedVisibility(visible = backIconVisibility.value) {
              IconButton(
                  onClick = {
                      if (pageState.currentPage + 1 > 1) scope.launch {
                          pageState.animateScrollToPage(pageState.currentPage - 1)
                      }
                      if (pageState.currentPage -1 <=0){
                          backIconVisibility.value = false
                      }
                  }
              ) {
                  Icon(
                      imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowLeft,
                      contentDescription = null
                  )
              }

          }
        })

    }, snackbarHost = {
        SnackbarHost(hostState = snackBarState)
    }, floatingActionButton = {
        FloatingActionButton(
            onClick = {
                if (snackBarState.currentSnackbarData == null){
                    when (pageState.currentPage) {
                        0 -> launcher.launch(intent)
                        1 -> readContactsPermission.launchMultiplePermissionRequest()
                        2 -> readCallLogPermissions.launchMultiplePermissionRequest()
                    }
                }


            },
            containerColor = MaterialTheme.colorScheme.primary,

            ) {
            Icon(
                Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                tint = MaterialTheme.colorScheme.onPrimary,
                contentDescription = ""
            )
        }

    }) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
        ) {


            HorizontalPager(
                userScrollEnabled = false,
                count = items.size,
                state = pageState,
                modifier = modifier
                    .fillMaxHeight(0.9f)
                    .fillMaxWidth()
            ) { page ->
                OnBoardingItem(items = items[page])
            }
            Indicators(size = items.size, index = pageState.currentPage)
        }
    }

}


@Composable
fun Indicators(size: Int, index: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.size8)),
    ) {
        repeat(size) {
            Indicator(isSelected = it == index)
        }
    }
}

@Composable
fun Indicator(isSelected: Boolean) {
    val width = animateDpAsState(
        targetValue = if (isSelected) dimensionResource(id = R.dimen.onboardingIndicatorSelectedSize) else dimensionResource(
            id = R.dimen.onboardingIndicatorUnselectedSize
        ),
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy), label = ""
    )

    Box(
        modifier = Modifier
            .height(dimensionResource(id = R.dimen.size5))
            .width(width.value)
            .clip(CircleShape)
            .background(
                color = if (isSelected) MaterialTheme.colorScheme.primary else Color(0XFFF8E2E7)
            )
    ) {

    }
}

@Composable
fun OnBoardingItem(modifier: Modifier = Modifier,items: OnboardingItem) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = items.image),
            contentDescription = "",
            modifier = modifier.padding(start = dimensionResource(id = R.dimen.padding25), end =dimensionResource(id = R.dimen.padding25))
        )
        Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.size12)))

        Text(
            text = items.title,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            letterSpacing = 1.sp,
        )
        Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.size4)))

        Text(
            text = items.description,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Light,
            textAlign = TextAlign.Center,
            modifier = modifier.padding(dimensionResource(id = R.dimen.padding5)),
            letterSpacing = 1.sp,
        )
    }

}

