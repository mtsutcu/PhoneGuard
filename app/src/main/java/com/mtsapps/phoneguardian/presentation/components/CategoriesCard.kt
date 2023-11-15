package com.mtsapps.phoneguardian.presentation.components

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.graphics.toColorInt
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.mtsapps.phoneguardian.R
import com.mtsapps.phoneguardian.data.entities.Category
import com.mtsapps.phoneguardian.data.entities.CategoryWithContacts
import com.mtsapps.phoneguardian.data.entities.Contact
import com.mtsapps.phoneguardian.domain.utils.toTimeFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesCard(
    modifier: Modifier= Modifier,
    categoryWithContacts: CategoryWithContacts,
    setCategoryTimer: (Category, Boolean) -> Unit,
    setCategoryStartTime: (Category, String) -> Unit,
    setCategoryEndTime: (Category, String) -> Unit,
    setCategoryBlocked: (Category, Boolean) -> Unit,
    buttonClickListner: (Contact) -> Unit,
) {
    Log.e("catt","category : ${categoryWithContacts.category.name} contacts: ${categoryWithContacts.contacts}")
    val contactsList = remember {
      mutableStateOf(categoryWithContacts.contacts)
    }
    Log.e("catt","category  : ${categoryWithContacts.category.name} contacts remember: ${contactsList.value}")

    val timePickerStateStartTime = rememberTimePickerState(is24Hour = true)
    val timePickerStateEndTime = rememberTimePickerState(is24Hour = true)
    var isVisibleTimes by remember { mutableStateOf(categoryWithContacts.category.isAlarm) }
    var isAlarmSwitchChecked by remember {
        mutableStateOf(categoryWithContacts.category.isAlarm)
    }
    var isBlockedSwitchChecked by remember {
        mutableStateOf(categoryWithContacts.category.isActive)
    }
    var showTimePickerStartTime by remember {
        mutableStateOf(false)
    }
    var showTimePickerEndTime by remember {
        mutableStateOf(false)
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.categoriesCardHight)),
        shape = MaterialTheme.shapes.medium,
        onClick = { /*TODO*/ }) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(id = R.dimen.padding8)),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "#${categoryWithContacts.category.name}",
                color = Color(categoryWithContacts.category.colorCode.toColorInt())
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = stringResource(id = R.string.timerText))
                Switch(
                    modifier = modifier.scale(0.5f),
                    checked = isAlarmSwitchChecked,
                    onCheckedChange = {
                        isVisibleTimes = it
                        isAlarmSwitchChecked = it
                        setCategoryTimer.invoke(categoryWithContacts.category, it)
                    })
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = stringResource(id = R.string.blockText))
                Switch(
                    modifier = modifier.scale(0.5f),
                    checked = isBlockedSwitchChecked,
                    onCheckedChange = {
                        isBlockedSwitchChecked = it
                        setCategoryBlocked.invoke(categoryWithContacts.category, it)
                    })
            }

        }
        AnimatedVisibility(visible = isVisibleTimes) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .clickable {
                    },
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = {
                    showTimePickerStartTime = true
                }) {
                    var textStateStartTime by remember {
                        mutableStateOf(categoryWithContacts.category.blockedStartTime)
                    }
                    Text(text = stringResource(id = R.string.startTimeTextWithInput,textStateStartTime))
                    if (showTimePickerStartTime) {
                        TimePickerDialog(
                            onCancel = { showTimePickerStartTime = false },
                            onConfirm = {
                                showTimePickerStartTime = false
                                textStateStartTime = toTimeFormat(
                                    hour = timePickerStateStartTime.hour,
                                    minute = timePickerStateStartTime.minute
                                )
                                setCategoryStartTime.invoke(
                                    categoryWithContacts.category,
                                    textStateStartTime
                                )

                            }) {
                            TimePicker(state = timePickerStateStartTime)
                        }
                    }
                }
                TextButton(onClick = { showTimePickerEndTime = true }) {
                    var textStateEndTime by remember {
                        mutableStateOf(categoryWithContacts.category.blockedEndTime)
                    }
                    Text(text = stringResource(id = R.string.endTimeTextWithInput,textStateEndTime))
                    if (showTimePickerEndTime) {
                        TimePickerDialog(
                            onCancel = { showTimePickerEndTime = false },
                            onConfirm = {
                                showTimePickerEndTime = false
                                textStateEndTime = toTimeFormat(
                                    hour = timePickerStateEndTime.hour,
                                    minute = timePickerStateEndTime.minute
                                )
                                setCategoryEndTime.invoke(categoryWithContacts.category, textStateEndTime)
                            }) {
                            TimePicker(state = timePickerStateEndTime)
                        }
                    }
                }

            }
        }
        LazyColumn(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.size4)),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(vertical = dimensionResource(id = R.dimen.padding4), horizontal = dimensionResource(
                id = R.dimen.padding8
            ))
        ) {
            itemsIndexed(items = categoryWithContacts.contacts,key = { _, item -> item.contactID  }) { _, item ->
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                ) {
                    Row(
                        modifier = modifier
                            .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween,verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            if (item.photoUri == null) {
                                Image(
                                    painter = painterResource(id = R.drawable.image_person),
                                    contentDescription = "",
                                    modifier = modifier
                                        .size(dimensionResource(id = R.dimen.categoriesCardRoundedImageSize))
                                        .clip(
                                            RoundedCornerShape(percent = integerResource(id = R.integer.categoriesCardRoundedImagePercent))
                                        )
                                        .background(color = Color.LightGray)
                                        .padding(4.dp), contentScale = ContentScale.FillBounds
                                )
                            } else {
                                Image(
                                    painter = rememberAsyncImagePainter(
                                        ImageRequest.Builder(LocalContext.current)
                                            .data(data = item.photoUri)
                                            .build()
                                    ),
                                    contentDescription = "",
                                    modifier = modifier
                                        .size(dimensionResource(id = R.dimen.categoriesCardRoundedImageSize))
                                        .clip(
                                            RoundedCornerShape(percent = integerResource(id = R.integer.categoriesCardRoundedImagePercent))
                                        ), contentScale = ContentScale.FillBounds
                                )
                            }

                            Column(modifier = modifier.padding(start = dimensionResource(id = R.dimen.padding8))) {
                                Text(
                                    text = item.name.toString(),
                                    style = MaterialTheme.typography.titleSmall,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                                Text(
                                    text = item.number.toString(),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }
                        }
                        OutlinedButton(
                            onClick = { buttonClickListner.invoke(item) },
                            shape = RoundedCornerShape(integerResource(id = R.integer.categoriesCardRoundedButtonPercent))
                        ) {
                            Text(text = stringResource(id = R.string.unblockText))

                        }
                    }
                }
            }

        }
    }

}


@Composable
fun TimePickerDialog(
    modifier: Modifier = Modifier,
    title: String = stringResource(id = R.string.selectTimeText),
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = onCancel,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = dimensionResource(id = R.dimen.tonalElevation3),
            modifier = modifier
                .width(IntrinsicSize.Min)
                .height(IntrinsicSize.Min)
                .background(
                    shape = MaterialTheme.shapes.extraLarge,
                    color = MaterialTheme.colorScheme.surface
                ),
        ) {
            Column(
                modifier = modifier.padding(dimensionResource(id = R.dimen.padding12)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(bottom = dimensionResource(id = R.dimen.padding10)),
                    text = title,
                    style = MaterialTheme.typography.labelMedium
                )
                content()
                Row(
                    modifier = modifier
                        .height(dimensionResource(id = R.dimen.size20))
                        .fillMaxWidth()
                ) {
                    Spacer(modifier = modifier.weight(1f))
                    TextButton(
                        onClick = onCancel
                    ) { Text(stringResource(id = R.string.cancelText)) }
                    TextButton(
                        onClick = onConfirm
                    ) { Text(stringResource(id = R.string.okText)) }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoriesCardPreview() {

}