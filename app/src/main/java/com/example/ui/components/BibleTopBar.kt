package com.example.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.BibleBook
import com.google.firebase.auth.FirebaseUser

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BibleTopBar(
    currentBook: BibleBook,
    currentChapter: Int,
    currentUser: FirebaseUser?,
    isTtsPlaying: Boolean,
    onOpenBookPicker: () -> Unit,
    onOpenAccessibilityDialog: () -> Unit,
    onPlayAudio: () -> Unit,
    onOpenSearch: () -> Unit,
    onOpenAccountSync: () -> Unit
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.onSurface,
            actionIconContentColor = MaterialTheme.colorScheme.onSurface
        ),
        title = {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                modifier = Modifier
                    .testTag("book_chapter_picker_button")
                    .clickable { onOpenBookPicker() }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "${currentBook.nameSetswana} $currentChapter",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowDown,
                                contentDescription = "Tlhopha kgaolo / Select Chapter",
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Text(
                            text = "${currentBook.nameEnglish} $currentChapter • Setswana-English Verse by Verse",
                            style = MaterialTheme.typography.bodySmall,
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        },
        actions = {
            // Audio / Voice button
            IconButton(
                onClick = onPlayAudio,
                modifier = Modifier.testTag("audio_voice_button")
            ) {
                Icon(
                    imageVector = if (isTtsPlaying) Icons.AutoMirrored.Filled.VolumeUp else Icons.Default.Headphones,
                    contentDescription = "Utlwa ka lentswe / Audio Voice",
                    tint = if (isTtsPlaying) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                )
            }

            // Font size & Accessibility button
            IconButton(
                onClick = onOpenAccessibilityDialog,
                modifier = Modifier.testTag("font_accessibility_button")
            ) {
                Icon(
                    imageVector = Icons.Default.FormatSize,
                    contentDescription = "Bogolo jwa ditlhaka / Font size and accessibility"
                )
            }

            // Search button
            IconButton(
                onClick = onOpenSearch,
                modifier = Modifier.testTag("search_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Batla temana / Search verses"
                )
            }

            // Cloud sync & account
            IconButton(
                onClick = onOpenAccountSync,
                modifier = Modifier.testTag("account_sync_button")
            ) {
                if (currentUser != null) {
                    Icon(
                        imageVector = Icons.Default.CloudDone,
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = "Cloud Synced (${currentUser.displayName ?: "User"})"
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "Kgolagano le Akhaonto / Cloud Sync & Account"
                    )
                }
            }
        }
    )
}
