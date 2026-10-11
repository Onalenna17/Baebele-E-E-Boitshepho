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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.BibleVerse
import com.example.ui.theme.DarkBg
import com.example.ui.theme.LightBg
import com.example.ui.theme.ParchmentBackground
import com.example.ui.theme.TagBlue
import com.example.ui.theme.TagGreen
import com.example.ui.theme.TagPurple
import com.example.ui.theme.TagRose
import com.example.ui.theme.TagYellow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VersePreviewDialog(
    verse: BibleVerse,
    onPlayVoice: (isSetswana: Boolean) -> Unit,
    onHighlight: ((String) -> Unit)? = null,
    onShare: () -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var previewFontSize by remember { mutableFloatStateOf(18f) }
    var previewCardTheme by remember { mutableStateOf(0) } // 0: Parchment, 1: Clean White, 2: Dark Night

    BasicAlertDialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 8.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Header
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.FormatQuote,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(26.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Ponelopele / Verse Preview",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Tswala")
                    }
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp))

                // Reference Banner
                Text(
                    text = "${verse.bookNameSetswana} ${verse.chapter}:${verse.verse} • ${verse.bookNameEnglish} ${verse.chapter}:${verse.verse}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(12.dp))

                // The Scripture Preview Container (Dynamic appearance)
                val cardBgColor = when (previewCardTheme) {
                    1 -> LightBg
                    2 -> DarkBg
                    else -> ParchmentBackground
                }
                val textColor = if (previewCardTheme == 2) Color.White else Color(0xFF1E1E1E)
                val subtitleColor = if (previewCardTheme == 2) Color.LightGray else Color(0xFF4A4A4A)

                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = cardBgColor,
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)),
                    shadowElevation = 3.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        // Setswana text (Baebele e e Boitshepo)
                        Text(
                            text = "Setswana (Baebele e e Boitshepo):",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = verse.textSetswana,
                            fontSize = previewFontSize.sp,
                            fontWeight = FontWeight.SemiBold,
                            lineHeight = (previewFontSize * 1.38f).sp,
                            color = textColor
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        // English text (Holy Bible - directly beneath)
                        Text(
                            text = "English (Holy Bible):",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.secondary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = verse.textEnglish,
                            fontSize = (previewFontSize * 0.92f).sp,
                            fontWeight = FontWeight.Normal,
                            lineHeight = (previewFontSize * 1.30f).sp,
                            color = subtitleColor
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Interactive Font Size Preview Controls
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Bogolo / Size: ${previewFontSize.toInt()} sp",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(
                            onClick = { previewFontSize = (previewFontSize - 2f).coerceAtLeast(14f) },
                            enabled = previewFontSize > 14f,
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(Icons.Default.Remove, contentDescription = "Fokotsa", modifier = Modifier.size(16.dp))
                        }
                        Slider(
                            value = previewFontSize,
                            onValueChange = { previewFontSize = it },
                            valueRange = 14f..32f,
                            steps = 8,
                            modifier = Modifier.width(130.dp)
                        )
                        IconButton(
                            onClick = { previewFontSize = (previewFontSize + 2f).coerceAtMost(32f) },
                            enabled = previewFontSize < 32f,
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Oketsa", modifier = Modifier.size(16.dp))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Theme switcher pills for preview
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Letlalo / Theme:",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    listOf("Sepia" to 0, "Light" to 1, "Dark" to 2).forEach { (label, idx) ->
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (previewCardTheme == idx) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant,
                            modifier = Modifier.clickable { previewCardTheme = idx }
                        ) {
                            Text(
                                text = label,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (previewCardTheme == idx) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(10.dp))

                // Voice Audio Playback Buttons
                Text(
                    text = "Utlwa ka Lentswe / Listen with Voice:",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = { onPlayVoice(true) },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.AutoMirrored.Filled.VolumeUp, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Setswana Voice", fontSize = 12.sp)
                    }

                    OutlinedButton(
                        onClick = { onPlayVoice(false) },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.AutoMirrored.Filled.VolumeUp, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("English Voice", fontSize = 12.sp)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Bottom Action buttons: Copy & Share
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedButton(
                        onClick = {
                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            val clip = ClipData.newPlainText(
                                "Bible Verse",
                                "${verse.bookNameSetswana} ${verse.chapter}:${verse.verse}\n${verse.textSetswana}\n\n${verse.bookNameEnglish} ${verse.chapter}:${verse.verse}\n${verse.textEnglish}"
                            )
                            clipboard.setPrimaryClip(clip)
                            Toast.makeText(context, "E kopitswe / Copied to clipboard", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Kopitsa / Copy", fontSize = 12.sp)
                    }

                    Button(
                        onClick = onShare,
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Abelana / Share", fontSize = 12.sp)
                    }
                }
            }
        }
    }
}
