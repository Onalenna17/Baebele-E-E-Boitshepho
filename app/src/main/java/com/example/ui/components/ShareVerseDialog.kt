package com.example.ui.components

import android.content.Intent
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.BibleVerse
import com.example.ui.theme.BibleGoldAccent
import com.example.ui.theme.BibleNavyPrimary
import com.example.ui.theme.BibleNavySurface

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShareVerseDialog(
    verse: BibleVerse,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current

    BasicAlertDialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 6.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Abelana Temana / Share Verse",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Tswala")
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Beautiful Scripture Quote Card Preview
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(BibleNavyPrimary, BibleNavySurface)
                            ),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(20.dp)
                ) {
                    Column {
                        Icon(
                            imageVector = Icons.Default.FormatQuote,
                            contentDescription = null,
                            tint = BibleGoldAccent,
                            modifier = Modifier.size(32.dp)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Setswana Scripture
                        Text(
                            text = "\"${verse.textSetswana}\"",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontStyle = FontStyle.Italic,
                            lineHeight = 24.sp
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        // English Scripture
                        Text(
                            text = "\"${verse.textEnglish}\"",
                            color = Color(0xFFD1D5DB),
                            fontSize = 14.sp,
                            lineHeight = 20.sp
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "— ${verse.bookNameSetswana} ${verse.chapter}:${verse.verse} (${verse.bookNameEnglish})",
                                color = BibleGoldAccent,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Baebele e e Boitshepo",
                                color = Color(0xFFAAAAAA),
                                fontSize = 11.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        val shareIntent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(
                                Intent.EXTRA_TEXT,
                                """
                                📖 ${verse.bookNameSetswana} ${verse.chapter}:${verse.verse} (${verse.bookNameEnglish})
                                
                                "${verse.textSetswana}"
                                
                                "${verse.textEnglish}"
                                
                                — Baebele e e Boitshepo (Setswana & English Dual Bible)
                                """.trimIndent()
                            )
                        }
                        context.startActivity(Intent.createChooser(shareIntent, "Abelana Temana ya Baebele"))
                        onDismiss()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Abelana / Share Now")
                }
            }
        }
    }
}
