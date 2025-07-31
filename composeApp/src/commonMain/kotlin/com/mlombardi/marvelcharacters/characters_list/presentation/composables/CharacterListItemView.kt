package com.mlombardi.marvelcharacters.characters_list.presentation.composables

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.mlombardi.marvelcharacters.characters_list.domain.models.MarvelCharacter
import com.mlombardi.marvelcharacters.core.presentation.Black
import com.mlombardi.marvelcharacters.core.presentation.Red500
import com.mlombardi.marvelcharacters.core.presentation.composables.PulseAnimation
import marvelcharacters.composeapp.generated.resources.Res
import marvelcharacters.composeapp.generated.resources.not_available
import org.jetbrains.compose.resources.painterResource

@Composable
fun CharacterListItemView(
    character: MarvelCharacter,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.clickable(
            onClick = onClick
        ),
        color = Red500.copy(alpha = 0.2f)
    ) {
        Row(
            modifier = Modifier.padding(16.dp)
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        )
        {
            Column (
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .height(100.dp),
                    contentAlignment = Alignment.Center
                ) {
                    var imageLoadResult by remember {
                        mutableStateOf<Result<Painter>?>(null)
                    }

                    val painter = rememberAsyncImagePainter(
                        model = character.thumbnail,
                        onSuccess = {
                            imageLoadResult =
                                if (it.painter.intrinsicSize.width > 1 && it.painter.intrinsicSize.height > 1) {
                                    Result.success(it.painter)
                                } else {
                                    Result.failure(Exception("Invalid image size"))
                                }
                        },
                        onError = {
                            it.result.throwable.printStackTrace()
                            imageLoadResult = Result.failure(it.result.throwable)
                        }
                    )

                    val painterState by painter.state.collectAsStateWithLifecycle()
                    val transition by animateFloatAsState(
                        targetValue = if (painterState is AsyncImagePainter.State.Success) {
                            1f
                        } else {
                            0f
                        },
                        animationSpec = tween(durationMillis = 800)
                    )

                    when (val result = imageLoadResult) {
                        null -> PulseAnimation(
                            modifier = Modifier.size(60.dp)
                        )

                        else -> {
                            Image(
                                painter = if (result.isSuccess) painter else {
                                    painterResource(Res.drawable.not_available)
                                },
                                contentDescription = character.name,
                                contentScale = if (result.isSuccess) {
                                    ContentScale.Crop
                                } else {
                                    ContentScale.Fit
                                },
                                modifier = Modifier
                                    .aspectRatio(
                                        ratio = 0.65f,
                                        matchHeightConstraintsFirst = true
                                    )
                                    .graphicsLayer {
                                        rotationX = (1f - transition) * 30f
                                        val scale = 0.8f + (0.2f * transition)
                                        scaleX = scale
                                        scaleY = scale
                                    }
                            )
                        }
                    }
                }
                Text(
                    text = character.name ?: "",
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = Black
                )
            }
        }
    }
}