package com.example.ui.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.BibleVerse
import com.example.ui.theme.TagBlue
import com.example.ui.theme.TagGreen
import com.example.ui.theme.TagPurple
import com.example.ui.theme.TagRose
import com.example.ui.theme.TagYellow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerseActionSheet(
    verse: BibleVerse,
    isBookmarked: Boolean,
    existingNote: String? = null,
    onSaveBookmark: (tagColor: String, note: String?) -> Unit,
    onDeleteBookmark: () -> Unit,
    onPlayTts: (isSetswana: Boolean) -> Unit,
    onShareVerse: () -> Unit,
    onPreviewVerse: () -> Unit = {},
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val context = LocalContext.current

    var selectedTag by remember { mutableStateOf("yellow") }
    var noteText by remember { mutableStateOf(existingNote ?: "") }
    var isEditingNote by remember { mutableStateOf(false) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
            // Header reference
            Text(
                text = "${verse.bookNameSetswana} ${verse.chapter}:${verse.verse} • ${verse.bookNameEnglish} ${verse.chapter}:${verse.verse}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Verse preview
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "Setswana:",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = verse.textSetswana,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        lineHeight = 22.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "English:",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        text = verse.textEnglish,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 22.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Bookmark Color Tags
            Text(
                text = "Tshwaya ka Mmala / Highlight & Bookmark",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                val tagOptions = listOf(
                    "yellow" to TagYellow,
                    "green" to TagGreen,
                    "blue" to TagBlue,
                    "purple" to TagPurple,
                    "rose" to TagRose
                )
                tagOptions.forEach { (tagKey, color) ->
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(color)
                            .border(
                                width = if (selectedTag == tagKey) 2.5.dp else 0.dp,
                                color = if (selectedTag == tagKey) MaterialTheme.colorScheme.onSurface else Color.Transparent,
                                shape = CircleShape
                            )
                            .clickable { selectedTag = tagKey }
                    ) {
                        if (selectedTag == tagKey) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = Color.Black,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Note input
            OutlinedTextField(
                value = noteText,
                onValueChange = { noteText = it },
                label = { Text("Tshwaelo ya gago / Personal Note (Optional)") },
                placeholder = { Text("Kwala kakanyo ya gago mo temaneng e...") },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 3
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Action Buttons
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        onSaveBookmark(selectedTag, noteText.ifBlank { null })
                        Toast.makeText(context, "E tshwailwe / Bookmarked!", Toast.LENGTH_SHORT).show()
                        onDismiss()
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.Bookmark, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Boloka / Save")
                }

                if (isBookmarked) {
                    OutlinedButton(
                        onClick = {
                            onDeleteBookmark()
                            Toast.makeText(context, "Tshwayo e phumotswe / Removed", Toast.LENGTH_SHORT).show()
                            onDismiss()
                        }
                    ) {
                        Text("Phumola / Remove")
                    }
                }
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

            // Audio & Sharing Toolbar
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Audio Read Aloud Setswana
                OutlinedButton(onClick = {
                    onPlayTts(true)
                    onDismiss()
                }) {
                    Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Utlwa (Setswana)", fontSize = 12.sp)
                }

                // Audio Read Aloud English
                OutlinedButton(onClick = {
                    onPlayTts(false)
                    onDismiss()
                }) {
                    Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Listen (Eng)", fontSize = 12.sp)
                }

                // Preview Verse
                OutlinedButton(onClick = {
                    onDismiss()
                    onPreviewVerse()
                }) {
                    Icon(Icons.Default.Visibility, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Leba Pele / Preview", fontSize = 12.sp)
                }

                // Copy to clipboard
                OutlinedButton(onClick = {
                    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val clip = ClipData.newPlainText(
                        "Bible Verse",
                        "${verse.bookNameSetswana} ${verse.chapter}:${verse.verse}\n${verse.textSetswana}\n\n${verse.bookNameEnglish} ${verse.chapter}:${verse.verse}\n${verse.textEnglish}"
                    )
                    clipboard.setPrimaryClip(clip)
                    Toast.makeText(context, "E kopitswe / Copied to clipboard", Toast.LENGTH_SHORT).show()
                    onDismiss()
                }) {
                    Icon(Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(16.dp))
                }

                // Share Quote Card
                OutlinedButton(onClick = {
                    onDismiss()
                    onShareVerse()
                }) {
                    Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
