package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.BibleDataProvider
import com.example.data.local.LocalBookmark
import com.example.model.BibleBook
import com.example.ui.theme.TagBlue
import com.example.ui.theme.TagGreen
import com.example.ui.theme.TagPurple
import com.example.ui.theme.TagRose
import com.example.ui.theme.TagYellow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarksScreen(
    bookmarks: List<LocalBookmark>,
    onSelectBookmark: (BibleBook, Int) -> Unit,
    onDeleteBookmark: (String) -> Unit,
    onPreviewVerse: (com.example.model.BibleVerse) -> Unit = {},
    onPlayVoice: (verse: com.example.model.BibleVerse, isSetswana: Boolean) -> Unit = { _, _ -> },
    onBack: () -> Unit
) {
    var selectedColorFilter by remember { mutableStateOf<String?>(null) }

    val filtered = if (selectedColorFilter == null) {
        bookmarks
    } else {
        bookmarks.filter { it.tagColor == selectedColorFilter }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("Ditshwayo tsa me / Bookmarks", fontWeight = FontWeight.Bold) },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Boela / Back")
                }
            }
        )

        // Color tag filter chips
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp)
        ) {
            FilterChip(
                selected = selectedColorFilter == null,
                onClick = { selectedColorFilter = null },
                label = { Text("Tsotlhe (${bookmarks.size})") }
            )
            val colors = listOf("yellow" to TagYellow, "green" to TagGreen, "blue" to TagBlue, "purple" to TagPurple, "rose" to TagRose)
            colors.forEach { (key, color) ->
                val count = bookmarks.count { it.tagColor == key }
                if (count > 0) {
                    FilterChip(
                        selected = selectedColorFilter == key,
                        onClick = { selectedColorFilter = if (selectedColorFilter == key) null else key },
                        leadingIcon = {
                            Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(color))
                        },
                        label = { Text("$count") }
                    )
                }
            }
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        if (filtered.isEmpty()) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.Bookmark,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Ga o ise o tlhophise ditshwayo dipe.",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "Tobetsa temana nngwe le nngwe fa o bala go e tshwaya.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(filtered) { bm ->
                    val book = BibleDataProvider.books.find { it.id == bm.bookId } ?: BibleDataProvider.books.first()
                    val tagBgColor = when (bm.tagColor) {
                        "green" -> TagGreen
                        "blue" -> TagBlue
                        "purple" -> TagPurple
                        "rose" -> TagRose
                        else -> TagYellow
                    }

                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 6.dp)
                            .clickable { onSelectBookmark(book, bm.chapter) }
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(12.dp)
                                            .clip(CircleShape)
                                            .background(tagBgColor)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "${bm.bookNameSetswana} ${bm.chapter}:${bm.verse}",
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "(${bm.bookNameEnglish})",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    val verseObj = com.example.model.BibleVerse(
                                        bookId = bm.bookId,
                                        bookNameSetswana = bm.bookNameSetswana,
                                        bookNameEnglish = bm.bookNameEnglish,
                                        chapter = bm.chapter,
                                        verse = bm.verse,
                                        textSetswana = bm.textSetswana,
                                        textEnglish = bm.textEnglish
                                    )

                                    IconButton(
                                        onClick = { onPlayVoice(verseObj, true) },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                                            contentDescription = "Utlwa / Listen",
                                            tint = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }

                                    IconButton(
                                        onClick = { onPreviewVerse(verseObj) },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Visibility,
                                            contentDescription = "Leba Pele / Preview",
                                            tint = MaterialTheme.colorScheme.secondary,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }

                                    IconButton(
                                        onClick = { onDeleteBookmark(bm.id) },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "Phumola / Delete",
                                            tint = MaterialTheme.colorScheme.error.copy(alpha = 0.7f),
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = bm.textSetswana,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = bm.textEnglish,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            if (!bm.note.isNullOrBlank()) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = "📝 Kakanyo: ${bm.note}",
                                        style = MaterialTheme.typography.bodySmall,
                                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                                        modifier = Modifier.padding(8.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
