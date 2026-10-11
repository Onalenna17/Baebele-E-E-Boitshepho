package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.BibleBook
import com.example.model.BibleVerse
import com.example.model.ReadingMode
import com.example.ui.theme.TagBlue
import com.example.ui.theme.TagGreen
import com.example.ui.theme.TagPurple
import com.example.ui.theme.TagRose
import com.example.ui.theme.TagYellow

@Composable
fun VerseByVerseBibleReader(
    book: BibleBook,
    chapter: Int,
    verses: List<BibleVerse>,
    readingMode: ReadingMode = ReadingMode.VERSE_BY_VERSE,
    fontSizeSp: Float,
    highlightsMap: Map<String, String>,
    isTtsPlaying: Boolean,
    isTtsPaused: Boolean,
    currentTtsIndex: Int?,
    speechRate: Float,
    onVerseClick: (BibleVerse) -> Unit,
    onQuickHighlight: (BibleVerse, String) -> Unit,
    onRemoveHighlight: (BibleVerse) -> Unit,
    onPlayVerseTts: (verse: BibleVerse, isSetswana: Boolean) -> Unit,
    onPlayChapterAudio: (isSetswana: Boolean, startIndex: Int) -> Unit,
    onTogglePlayPauseAudio: (isSetswana: Boolean) -> Unit,
    onPlayNextVerse: () -> Unit,
    onPlayPreviousVerse: () -> Unit,
    onSetSpeechRate: (Float) -> Unit,
    onNextChapter: () -> Unit,
    onPreviousChapter: () -> Unit,
    onStopTts: () -> Unit,
    onShareVerse: (BibleVerse) -> Unit,
    onPreviewVerse: (BibleVerse) -> Unit,
    modifier: Modifier = Modifier
) {
    var audioLanguageSetswana by remember { mutableStateOf(true) }

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 14.dp),
            contentPadding = PaddingValues(top = 8.dp, bottom = 140.dp)
        ) {
            // Chapter Header Banner & Audio Voice Controller
            item {
                Surface(
                    shape = RoundedCornerShape(18.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.55f),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "${book.nameSetswana} $chapter",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "${book.nameEnglish} $chapter",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Setswana • English (Temana ka Temana / Verse by Verse)",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.secondary,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.height(14.dp))
                        HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
                        Spacer(modifier = Modifier.height(12.dp))

                        // Audio & Voice Chapter Controls
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Headphones,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Lentswe:",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.width(8.dp))

                                // Language Toggle Pill
                                Surface(
                                    shape = RoundedCornerShape(20.dp),
                                    color = if (audioLanguageSetswana) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface,
                                    modifier = Modifier.clickable { audioLanguageSetswana = true }
                                ) {
                                    Text(
                                        text = "Setswana",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (audioLanguageSetswana) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(4.dp))
                                Surface(
                                    shape = RoundedCornerShape(20.dp),
                                    color = if (!audioLanguageSetswana) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface,
                                    modifier = Modifier.clickable { audioLanguageSetswana = false }
                                ) {
                                    Text(
                                        text = "English",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (!audioLanguageSetswana) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }
                            }

                            // Play / Stop Chapter Audio Button
                            Button(
                                onClick = {
                                    if (isTtsPlaying || isTtsPaused) {
                                        onStopTts()
                                    } else {
                                        onPlayChapterAudio(audioLanguageSetswana, 0)
                                    }
                                },
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.testTag("play_chapter_audio_button")
                            ) {
                                Icon(
                                    imageVector = if (isTtsPlaying || isTtsPaused) Icons.Default.Stop else Icons.Default.PlayArrow,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = if (isTtsPlaying || isTtsPaused) "Emisa" else "Utlwa Kgaolo",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }

            // Setswana & English Verse-by-Verse List
            itemsIndexed(verses) { index, verse ->
                val highlightTag = highlightsMap["${verse.bookId}_${verse.chapter}_${verse.verse}"]
                val highlightColor = when (highlightTag) {
                    "yellow" -> TagYellow
                    "green" -> TagGreen
                    "blue" -> TagBlue
                    "purple" -> TagPurple
                    "rose" -> TagRose
                    else -> null
                }
                val isCurrentTts = (isTtsPlaying || isTtsPaused) && currentTtsIndex == index

                VerseCard(
                    verse = verse,
                    index = index,
                    readingMode = readingMode,
                    fontSizeSp = fontSizeSp,
                    highlightColor = highlightColor,
                    activeHighlightTag = highlightTag,
                    isTtsActive = isCurrentTts,
                    isTtsPlaying = isTtsPlaying,
                    onClick = { onVerseClick(verse) },
                    onQuickHighlight = { color -> onQuickHighlight(verse, color) },
                    onRemoveHighlight = { onRemoveHighlight(verse) },
                    onPlayTts = { isSetswana -> onPlayVerseTts(verse, isSetswana) },
                    onPreview = { onPreviewVerse(verse) },
                    onShare = { onShareVerse(verse) }
                )
            }

            // Bottom Chapter Navigation Bar
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedButton(
                        onClick = onPreviousChapter,
                        enabled = chapter > 1 || book.order > 1
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Kgaolo e e Fetileng")
                    }

                    Text(
                        text = "$chapter / ${book.chaptersCount}",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Button(onClick = onNextChapter) {
                        Text("Kgaolo e e Latelang")
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
                    }
                }
                Spacer(modifier = Modifier.height(30.dp))
            }
        }

        // Floating Audio Voice Player bar during active Voice/TTS reading
        AnimatedVisibility(
            visible = isTtsPlaying || isTtsPaused,
            enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
                .padding(horizontal = 14.dp)
        ) {
            Surface(
                shape = RoundedCornerShape(26.dp),
                color = MaterialTheme.colorScheme.primaryContainer,
                shadowElevation = 12.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                            Icon(
                                imageVector = Icons.Default.GraphicEq,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                val activeVerseNumber = currentTtsIndex?.let { it + 1 } ?: 1
                                Text(
                                    text = if (isTtsPlaying) "Lentswe le a Bala..." else "Le Emeditswe / Paused",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                                Text(
                                    text = "Temana $activeVerseNumber • ${book.nameSetswana} $chapter (${if (audioLanguageSetswana) "Setswana" else "English"})",
                                    style = MaterialTheme.typography.bodySmall,
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f)
                                )
                            }
                        }

                        // Speed selector
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.6f),
                            modifier = Modifier.clickable {
                                val nextRate = when (speechRate) {
                                    0.75f -> 1.0f
                                    1.0f -> 1.25f
                                    1.25f -> 1.5f
                                    else -> 0.75f
                                }
                                onSetSpeechRate(nextRate)
                            }
                        ) {
                            Text(
                                text = "${speechRate}x",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    // Player Control Buttons
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        IconButton(onClick = onPlayPreviousVerse, modifier = Modifier.size(36.dp)) {
                            Icon(
                                imageVector = Icons.Default.SkipPrevious,
                                contentDescription = "Temana e e fetileng",
                                tint = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Surface(
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .size(42.dp)
                                .clickable { onTogglePlayPauseAudio(audioLanguageSetswana) }
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = if (isTtsPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                    contentDescription = if (isTtsPlaying) "Pause" else "Play",
                                    tint = MaterialTheme.colorScheme.onPrimary,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        IconButton(onClick = onPlayNextVerse, modifier = Modifier.size(36.dp)) {
                            Icon(
                                imageVector = Icons.Default.SkipNext,
                                contentDescription = "Temana e e latelang",
                                tint = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        IconButton(onClick = onStopTts, modifier = Modifier.size(36.dp)) {
                            Icon(
                                imageVector = Icons.Default.Stop,
                                contentDescription = "Emisa / Stop",
                                tint = MaterialTheme.colorScheme.error,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun VerseCard(
    verse: BibleVerse,
    index: Int,
    readingMode: ReadingMode,
    fontSizeSp: Float,
    highlightColor: Color?,
    activeHighlightTag: String?,
    isTtsActive: Boolean,
    isTtsPlaying: Boolean,
    onClick: () -> Unit,
    onQuickHighlight: (String) -> Unit,
    onRemoveHighlight: () -> Unit,
    onPlayTts: (Boolean) -> Unit,
    onPreview: () -> Unit,
    onShare: () -> Unit
) {
    // Card background tint when highlighted or when TTS voice is active
    val cardBg = when {
        isTtsActive -> MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.45f)
        highlightColor != null -> highlightColor.copy(alpha = 0.22f)
        else -> MaterialTheme.colorScheme.surface
    }

    val cardBorder = when {
        isTtsActive -> androidx.compose.foundation.BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
        highlightColor != null -> androidx.compose.foundation.BorderStroke(1.5.dp, highlightColor.copy(alpha = 0.85f))
        else -> androidx.compose.foundation.BorderStroke(0.5.dp, MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f))
    }

    Surface(
        shape = RoundedCornerShape(14.dp),
        color = cardBg,
        border = cardBorder,
        shadowElevation = if (isTtsActive) 4.dp else 0.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Verse Header & Action Bar
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Verse Badge
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = if (isTtsActive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer
                    ) {
                        Text(
                            text = "${verse.verse}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isTtsActive) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    if (isTtsActive) {
                        Icon(
                            imageVector = Icons.Default.GraphicEq,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (isTtsPlaying) "E buiwa jaanong" else "E emisitse",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    } else if (highlightColor != null) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = highlightColor.copy(alpha = 0.45f)
                        ) {
                            Text(
                                text = "E Tshwailwe / Highlighted",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                }

                // Quick Action Icons on the Verse Card
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Voice Play Button (Setswana audio)
                    IconButton(
                        onClick = { onPlayTts(true) },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                            contentDescription = "Utlwa ka Setswana / Listen in Setswana",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    // Preview Button (Explicit user requirement)
                    IconButton(
                        onClick = onPreview,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Visibility,
                            contentDescription = "Leba Pele / Preview",
                            tint = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    // Bookmark / Note Details Button
                    IconButton(
                        onClick = onClick,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Bookmark,
                            contentDescription = "Tshwayo / Bookmark & Note",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    // Share Quote Button
                    IconButton(
                        onClick = onShare,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Abelana / Share",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // 1. Setswana Scripture Passage (Primary, bold, prominent)
            if (readingMode != ReadingMode.ENGLISH_ONLY) {
                Text(
                    text = verse.textSetswana,
                    fontSize = fontSizeSp.sp,
                    fontWeight = FontWeight.SemiBold,
                    lineHeight = (fontSizeSp * 1.38f).sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            // 2. English Scripture Passage (Directly beneath, graceful contrast - Verse by Verse)
            if (readingMode != ReadingMode.SETSWANA_ONLY) {
                if (readingMode != ReadingMode.ENGLISH_ONLY) {
                    Spacer(modifier = Modifier.height(8.dp))
                }
                Text(
                    text = verse.textEnglish,
                    fontSize = (fontSizeSp * 0.94f).sp,
                    fontWeight = FontWeight.Normal,
                    lineHeight = (fontSizeSp * 1.32f).sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Quick Highlight Color Picker Bar right on the verse card
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Tshwaya / Highlight:",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Medium
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val colors = listOf(
                        "yellow" to TagYellow,
                        "green" to TagGreen,
                        "blue" to TagBlue,
                        "purple" to TagPurple,
                        "rose" to TagRose
                    )
                    colors.forEach { (tagKey, color) ->
                        val isSelected = activeHighlightTag == tagKey
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(color)
                                .border(
                                    width = if (isSelected) 2.dp else 0.5.dp,
                                    color = if (isSelected) MaterialTheme.colorScheme.onSurface else Color.Gray.copy(alpha = 0.4f),
                                    shape = CircleShape
                                )
                                .clickable { onQuickHighlight(tagKey) }
                        ) {
                            if (isSelected) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = Color.Black,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                        }
                    }

                    // Remove highlight icon if highlighted
                    if (activeHighlightTag != null) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                                .clickable { onRemoveHighlight() }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Phumola Mmala / Remove Highlight",
                                tint = MaterialTheme.colorScheme.error,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
