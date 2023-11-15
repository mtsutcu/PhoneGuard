package com.mtsapps.phoneguardian.presentation.home

import android.annotation.SuppressLint
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.mtsapps.phoneguardian.R
import com.mtsapps.phoneguardian.domain.utils.toNumberFormat
import kotlinx.coroutines.launch
import md_theme_dark_primary

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
    val contacts = homeViewModel.contacts.observeAsState().value
    val blockStatus = homeViewModel.blockStatus.observeAsState().value
    val scope = rememberCoroutineScope()
    Scaffold { _ ->
        contacts?.let {
            Box(
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.padding8))
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.6f),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box {
                        Box(modifier = Modifier.align(Alignment.Center)) {
                            if (blockStatus == true) {
                                PulseAnimation()
                            }
                        }
                        Image(
                            painter = painterResource(id = R.drawable.security_icon),
                            contentDescription = "",
                            colorFilter = ColorFilter.lighting(
                                Color.Transparent,
                                MaterialTheme.colorScheme.onSurface
                            ),
                            modifier = Modifier
                                .padding(dimensionResource(id = R.dimen.padding4))
                                .size(dimensionResource(id = R.dimen.homePageMainImageSize))
                                .clip(shape = RoundedCornerShape(integerResource(id = R.integer.homePageRoundedImagePercent)))
                                .align(Alignment.Center)
                                .background(color = MaterialTheme.colorScheme.surface)
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(id = R.string.protectionText).uppercase(),
                            style = MaterialTheme.typography.titleLarge
                        )
                        Switch(checked = blockStatus ?: false, onCheckedChange = {
                            scope.launch {
                                homeViewModel.updateBlockStatus(status = it)

                            }
                        })

                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxHeight(0.4f)
                        .align(Alignment.BottomCenter)

                ) {
                    if (contacts.isNotEmpty()) {
                        Text(
                            text = stringResource(id = R.string.blockedText),
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(
                                vertical = dimensionResource(
                                    id = R.dimen.padding4
                                )
                            )
                        )
                    }
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.size8)),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        contentPadding = PaddingValues(vertical = dimensionResource(id = R.dimen.padding4))
                    ) {
                        itemsIndexed(items = contacts) { _, item ->
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        if (item.photoUri == null) {
                                            Image(
                                                painter = painterResource(id = R.drawable.image_person),
                                                contentDescription = "",
                                                modifier = Modifier
                                                    .size(dimensionResource(id = R.dimen.homePageColumnItemImageSize))
                                                    .clip(
                                                        RoundedCornerShape(
                                                            percent = integerResource(
                                                                id = R.integer.homePageColumnItemRoundedImagePercent
                                                            )
                                                        )
                                                    )
                                                    .background(color = Color.LightGray)
                                                    .padding(dimensionResource(id = R.dimen.padding2)),
                                                contentScale = ContentScale.FillBounds
                                            )
                                        } else {
                                            Image(
                                                painter = rememberAsyncImagePainter(
                                                    ImageRequest.Builder(LocalContext.current)
                                                        .data(data = item.photoUri)
                                                        .build()
                                                ),
                                                contentDescription = "",
                                                modifier = Modifier
                                                    .size(dimensionResource(id = R.dimen.homePageColumnItemImageSize))
                                                    .clip(
                                                        RoundedCornerShape(
                                                            percent = integerResource(
                                                                id = R.integer.homePageColumnItemRoundedImagePercent
                                                            )
                                                        )
                                                    ), contentScale = ContentScale.FillBounds
                                            )
                                        }

                                        Column(modifier = Modifier.padding(start = dimensionResource(
                                            id = R.dimen.padding8
                                        ))) {
                                            Text(
                                                text = item.name.toString(),
                                                style = MaterialTheme.typography.titleSmall,
                                            )
                                            Text(
                                                text = item.number.toString().toNumberFormat(),
                                                style = MaterialTheme.typography.bodySmall,
                                            )
                                        }
                                    }
                                    OutlinedButton(onClick = {
                                        homeViewModel.deleteContact(item)
                                    }, shape = RoundedCornerShape(percent = integerResource(id = R.integer.homePageColumnItemRoundedButtonPercent))) {
                                        Text(text = stringResource(id = R.string.unblockText))

                                    }
                                }
                            }

                        }

                    }
                }

            }
        }
    }
}


@Composable
fun PulseAnimation() {
    val context = LocalContext.current
    Box(
        contentAlignment = Alignment.Center,
        ) {
        val transition = rememberInfiniteTransition(label = "")
        val duration = 2000
        val count = 1
        val progress = List(1) {
            transition.animateFloat(
                initialValue = 0f,
                targetValue = 1f,
                animationSpec = InfiniteRepeatableSpec(
                    animation = tween(durationMillis = duration, easing = LinearEasing),
                    initialStartOffset = StartOffset(it * duration / count),
                    repeatMode = RepeatMode.Restart,
                ), label = ""
            )
        }
        Canvas(modifier = Modifier) {
            val radius = context.resources.getDimensionPixelSize(R.dimen.homePagePulseAnimationSize) / 2f
            progress.forEach {
                drawCircle(
                    color = md_theme_dark_primary.copy(alpha = 1f - it.value),
                    radius = radius * it.value,
                    center = Offset(size.width, size.height / 2)
                )
            }
        }
    }

}
