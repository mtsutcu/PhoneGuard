package com.mtsapps.phoneguardian.presentation.components

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
import androidx.compose.ui.res.painterResource
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
import com.mtsapps.phoneguardian.domain.utils.toNumberFormat
import com.mtsapps.phoneguardian.domain.utils.toTimeFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesCard(categoryWithContacts: CategoryWithContacts,setCategoryTimer:(Category,Boolean)->Unit,setCategoryStartTime:(Category,String)->Unit,setCategoryEndTime:(Category,String)->Unit,setCategoryBlocked : (Category,Boolean)->Unit,buttonClickListner :(Contact)->Unit) {
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
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
        shape = MaterialTheme.shapes.medium,
        onClick = { /*TODO*/ }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = "#${categoryWithContacts.category.name}", color = Color(categoryWithContacts.category.colorCode.toColorInt()))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Timer")
                Switch(modifier = Modifier.scale(0.5f), checked = isAlarmSwitchChecked, onCheckedChange = {
                    isVisibleTimes = it
                    isAlarmSwitchChecked = it
                    setCategoryTimer.invoke(categoryWithContacts.category,it)
                })
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Block")
                Switch(modifier = Modifier.scale(0.5f), checked = isBlockedSwitchChecked, onCheckedChange = {
                    isBlockedSwitchChecked = it
                    setCategoryBlocked.invoke(categoryWithContacts.category,it)
                })
            }

        }
        AnimatedVisibility(visible = isVisibleTimes) {
            Row(
                modifier = Modifier
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
                    Text(text = "Start Time: $textStateStartTime")
                    if (showTimePickerStartTime) {
                        TimePickerDialog(
                            onCancel = { showTimePickerStartTime = false },
                            onConfirm = {
                                showTimePickerStartTime = false
                                textStateStartTime = toTimeFormat(hour = timePickerStateStartTime.hour, minute = timePickerStateStartTime.minute)
                                setCategoryStartTime.invoke(categoryWithContacts.category,textStateStartTime)

                            }) {
                            TimePicker(state = timePickerStateStartTime)
                        }
                    }
                }
                TextButton(onClick = { showTimePickerEndTime = true }) {
                    var textState by remember {
                        mutableStateOf(categoryWithContacts.category.blockedEndTime)
                    }
                    Text(text = "End Time: $textState")
                    if (showTimePickerEndTime) {
                        TimePickerDialog(
                            onCancel = { showTimePickerEndTime = false },
                            onConfirm = {
                                showTimePickerEndTime = false
                                textState = toTimeFormat(hour = timePickerStateEndTime.hour, minute = timePickerStateEndTime.minute)
                                setCategoryEndTime.invoke(categoryWithContacts.category,textState)
                            }) {
                            TimePicker(state = timePickerStateEndTime)
                        }
                    }
                }

            }
        }
        LazyColumn(
            modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally, contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp)
        ) {
            itemsIndexed(items = categoryWithContacts.contacts) { index, item ->
                Column(modifier = Modifier
                    .fillMaxWidth()
                    ) {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        , horizontalArrangement = Arrangement.SpaceBetween) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            if (item.photoUri == null) {
                                Image(
                                    painter = painterResource(id = R.drawable.image_person) ,
                                    contentDescription = "",
                                    modifier = Modifier
                                        .size(45.dp)
                                        .clip(
                                            RoundedCornerShape(percent = 100)
                                        )
                                        .background(color = Color.LightGray)
                                        .padding(4.dp), contentScale = ContentScale.FillBounds
                                )
                            } else {
                                Image(
                                    painter = rememberAsyncImagePainter(
                                        ImageRequest.Builder(LocalContext.current)
                                            .data(data = item.photoUri)
                                            .build()),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .size(45.dp)
                                        .clip(
                                            RoundedCornerShape(percent = 100)
                                        ), contentScale = ContentScale.FillBounds
                                )
                            }

                            Column(modifier = Modifier.padding(start = 16.dp)) {
                                Text(
                                    text = item.name.toString(),
                                    style = MaterialTheme.typography.titleSmall,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                                Text(
                                    text = item.number.toString().toNumberFormat(),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }
                        }
                        OutlinedButton(onClick = { buttonClickListner.invoke(item) }, shape = RoundedCornerShape(25)) {
                            Text(text = "Unblock")

                        }
                    }
                   }
                }

            }
        }

    }


@Composable
fun TimePickerDialog(
    title: String = "Select Time",
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
            tonalElevation = 6.dp,
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .height(IntrinsicSize.Min)
                .background(
                    shape = MaterialTheme.shapes.extraLarge,
                    color = MaterialTheme.colorScheme.surface
                ),
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    text = title,
                    style = MaterialTheme.typography.labelMedium
                )
                content()
                Row(
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(
                        onClick = onCancel
                    ) { Text("Cancel") }
                    TextButton(
                        onClick = onConfirm
                    ) { Text("OK") }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoriesCardPreview() {

}