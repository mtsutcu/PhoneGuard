package com.mtsapps.phoneguardian.presentation.components.bottom_navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.mtsapps.phoneguardian.R
import com.mtsapps.phoneguardian.domain.utils.toNumberFormat

@Composable
fun CallLogCard(
    name: String?,
    number: String,
    photo: String?,
    modifier: Modifier = Modifier,
    isExpanded: MutableIntState,
    index: Int,
    buttonText: String,
    itemOnClick: () -> Unit,
    buttonOnClick: (number: String) -> Unit,
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.Transparent)
            .clickable {
                itemOnClick.invoke()
            },
    ) {

        Column(verticalArrangement = Arrangement.Center, modifier = modifier.fillMaxWidth()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier.padding(12.dp)
            ) {
                if (photo == null) {
                    Image(
                        painter = painterResource(id = R.drawable.image_person) ,
                        contentDescription = "",
                        modifier = modifier
                            .size(45.dp)
                            .clip(
                                RoundedCornerShape(percent = 100)
                            ).background(color = Color.LightGray).padding(4.dp), contentScale = ContentScale.FillBounds
                    )
                } else {
                    Image(
                        painter = rememberAsyncImagePainter(ImageRequest.Builder(LocalContext.current)
                            .data(data = photo)
                            .build()),
                        contentDescription = "",
                        modifier = modifier
                            .size(45.dp)
                            .clip(
                                RoundedCornerShape(percent = 100)
                            ), contentScale = ContentScale.FillBounds
                    )
                }

                Column(
                    modifier = modifier
                        .fillMaxHeight()
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = name.toString(),
                        style = MaterialTheme.typography.titleSmall,
                    )
                    Text(
                        text = number.toNumberFormat(),
                        style = MaterialTheme.typography.bodySmall,
                    )
                    AnimatedVisibility(visible = isExpanded.intValue == index) {

                        Button(
                            onClick = {
                                buttonOnClick.invoke(number)
                            },
                            shape = RoundedCornerShape(25),
                            modifier = modifier
                                .padding(top = 8.dp, end = 16.dp)
                                .fillMaxWidth()
                        ) {
                            Text(text = buttonText)

                        }

                    }
                }
            }

        }
    }
}