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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.mtsapps.phoneguardian.R

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
                modifier = modifier.padding(dimensionResource(id = R.dimen.padding6))
            ) {
                if (photo == null) {
                    Image(
                        painter = painterResource(id = R.drawable.image_person) ,
                        contentDescription = "",
                        modifier = modifier
                            .size(dimensionResource(id = R.dimen.size22))
                            .clip(
                                RoundedCornerShape(percent = integerResource(id = R.integer.callLogCardRoundedImagePercent))
                            )
                            .background(color = Color.LightGray)
                            .padding(dimensionResource(id = R.dimen.padding2)), contentScale = ContentScale.FillBounds
                    )
                } else {
                    Image(
                        painter = rememberAsyncImagePainter(ImageRequest.Builder(LocalContext.current)
                            .data(data = photo)
                            .build()),
                        contentDescription = "",
                        modifier = modifier
                            .size(dimensionResource(id = R.dimen.size22))
                            .clip(
                                RoundedCornerShape(percent = integerResource(id = R.integer.callLogCardRoundedImagePercent))
                            ), contentScale = ContentScale.FillBounds
                    )
                }

                Column(
                    modifier = modifier
                        .fillMaxHeight()
                        .padding(horizontal = dimensionResource(id = R.dimen.padding8))
                ) {
                    Text(
                        text = name.toString(),
                        style = MaterialTheme.typography.titleSmall,
                    )
                    Text(
                        text = number,
                        style = MaterialTheme.typography.bodySmall,
                    )
                    AnimatedVisibility(visible = isExpanded.intValue == index) {

                        Button(
                            onClick = {
                                buttonOnClick.invoke(number)
                            },
                            shape = RoundedCornerShape(integerResource(id = R.integer.callLogCardRoundedButtonPercent)),
                            modifier = modifier
                                .padding(
                                    top = dimensionResource(id = R.dimen.padding4),
                                    end = dimensionResource(
                                        id = R.dimen.padding8
                                    )
                                )
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