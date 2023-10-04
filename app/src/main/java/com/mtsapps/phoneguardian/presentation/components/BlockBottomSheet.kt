package com.mtsapps.phoneguardian.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.mtsapps.phoneguardian.R
import com.mtsapps.phoneguardian.data.entities.Category
import com.mtsapps.phoneguardian.domain.models.CallContact
import com.mtsapps.phoneguardian.domain.utils.nameFromUnknownCaller
import com.mtsapps.phoneguardian.domain.utils.toNumberFormat

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun BlockBottomSheet(
    bottomSheetState: SheetState,
    openBottomSheetState: MutableState<Boolean>,
    categoryList: List<Category>,
    bottomSheetCallContactState: MutableState<CallContact>,
    isBlocked: MutableState<Boolean>,
    onButtonClickListener: (Category, CallContact) -> Unit,
) {
    var isExpandedDropDown by remember {
        mutableStateOf(false)
    }
    var openBottomSheet by remember {
        openBottomSheetState
    }
    var expandedMenuValue by remember {
        mutableStateOf(categoryList[0])
    }
    var expandedMenuValueColor by remember {
        mutableStateOf(categoryList[0].colorCode)
    }
    ModalBottomSheet(
        sheetState = bottomSheetState,
        windowInsets = BottomSheetDefaults.windowInsets,
        onDismissRequest = {
            openBottomSheet = false
        },
        dragHandle = { BottomSheetDefaults.DragHandle() }) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(fraction = 0.5f)
                .padding(vertical = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                if (bottomSheetCallContactState.value.photoUri != null) {
                    Image(
                        painter = rememberAsyncImagePainter(
                            ImageRequest.Builder(LocalContext.current)
                                .data(data = bottomSheetCallContactState.value.photoUri).build()
                        ),
                        contentDescription = "",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(
                                RoundedCornerShape(percent = 100)
                            ), contentScale = ContentScale.FillBounds
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.image_person),
                        contentDescription = "",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(
                                RoundedCornerShape(percent = 100)
                            ).background(color = Color.LightGray).padding(4.dp), contentScale = ContentScale.FillBounds
                    )
                }
                Column {
                    Column {
                        Text(
                            text = bottomSheetCallContactState.value.name.nameFromUnknownCaller(),
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        bottomSheetCallContactState.value.number?.let {
                            Text(
                                text = it.toNumberFormat(),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                    ExposedDropdownMenuBox(
                        expanded = isExpandedDropDown,
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .padding(vertical = 16.dp),
                        onExpandedChange = { isExpandedDropDown = !isExpandedDropDown },
                    ) {
                        TextField(
                            // The `menuAnchor` modifier must be passed to the text field for correctness.
                            modifier = Modifier.menuAnchor(),
                            readOnly = true,
                            value = expandedMenuValue.name,
                            onValueChange = {},
                            label = { Text("Category") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpandedDropDown) },
                            colors = ExposedDropdownMenuDefaults.textFieldColors(
                                focusedTextColor = Color(
                                    expandedMenuValueColor.toColorInt()
                                ), unfocusedTextColor = Color(expandedMenuValueColor.toColorInt())
                            ),
                        )
                        ExposedDropdownMenu(
                            expanded = isExpandedDropDown,
                            onDismissRequest = { isExpandedDropDown = false },
                        ) {
                            categoryList.forEach { selectionOption ->
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            selectionOption.name,
                                            color = Color(selectionOption.colorCode.toColorInt())
                                        )
                                    },
                                    onClick = {
                                        expandedMenuValue = selectionOption
                                        expandedMenuValueColor = selectionOption.colorCode
                                        isExpandedDropDown = false
                                    },
                                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                                )
                            }
                        }
                    }
                }
            }
            Button(
                onClick = {
                    onButtonClickListener.invoke(
                        expandedMenuValue,
                        bottomSheetCallContactState.value
                    )

                },
                shape = RoundedCornerShape(25),
                modifier = Modifier
                    .padding(horizontal = 32.dp, vertical = 16.dp)
                    .fillMaxWidth()
            ) {
                Text(text = if (isBlocked.value) "Unblock" else "Block")

            }
        }
    }
}