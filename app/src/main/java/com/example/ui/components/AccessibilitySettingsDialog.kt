package com.example.ui.components

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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ReadingMode
import com.example.model.ReadingTheme
import com.example.ui.theme.DarkBg
import com.example.ui.theme.HighContrastBg
import com.example.ui.theme.LightBg
import com.example.ui.theme.ParchmentBackground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccessibilitySettingsDialog(
    currentFontSizeSp: Float,
    currentTheme: ReadingTheme,
    currentReadingMode: ReadingMode,
    onFontSizeChange: (Float) -> Unit,
    onThemeChange: (ReadingTheme) -> Unit,
    onReadingModeChange: (ReadingMode) -> Unit,
    onDismiss: () -> Unit
) {
    BasicAlertDialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 6.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                // Header
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Visibility,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Dithulaganyo tsa Padi / Reading & Accessibility",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Tswala")
                    }
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

                // Font Size Section
                Text(
                    text = "Bogolo jwa Ditlhaka / Font Size: ${currentFontSizeSp.toInt()} sp",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(
                        onClick = { onFontSizeChange((currentFontSizeSp - 2).coerceAtLeast(14f)) },
                        enabled = currentFontSizeSp > 14f
                    ) {
                        Icon(Icons.Default.Remove, contentDescription = "Fokotsa / Smaller")
                    }

                    Slider(
                        value = currentFontSizeSp,
                        onValueChange = onFontSizeChange,
                        valueRange = 14f..32f,
                        steps = 8,
                        modifier = Modifier.weight(1f)
                    )

                    IconButton(
                        onClick = { onFontSizeChange((currentFontSizeSp + 2).coerceAtMost(32f)) },
                        enabled = currentFontSizeSp < 32f
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Oketsa / Larger")
                    }
                }

                // Live Preview Card
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = "JEHOFA ke Modisa wa me; ga nkitla ke tlhoka sepe.",
                            fontSize = currentFontSizeSp.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "The LORD is my shepherd; I shall not want.",
                            fontSize = (currentFontSizeSp * 0.9f).sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Theme Selection
                Text(
                    text = "Mmala wa Letlalo / Background Theme",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ThemeChoiceItem(
                        title = "Letlalo",
                        subtitle = "Sepia",
                        bgColor = ParchmentBackground,
                        isSelected = currentTheme == ReadingTheme.PARCHMENT,
                        onClick = { onThemeChange(ReadingTheme.PARCHMENT) }
                    )
                    ThemeChoiceItem(
                        title = "Lesedi",
                        subtitle = "Light",
                        bgColor = LightBg,
                        isSelected = currentTheme == ReadingTheme.LIGHT,
                        onClick = { onThemeChange(ReadingTheme.LIGHT) }
                    )
                    ThemeChoiceItem(
                        title = "Lefifi",
                        subtitle = "Dark",
                        bgColor = DarkBg,
                        isSelected = currentTheme == ReadingTheme.DARK,
                        onClick = { onThemeChange(ReadingTheme.DARK) }
                    )
                    ThemeChoiceItem(
                        title = "Pharologano",
                        subtitle = "Hi-Contrast",
                        bgColor = HighContrastBg,
                        isSelected = currentTheme == ReadingTheme.HIGH_CONTRAST,
                        onClick = { onThemeChange(ReadingTheme.HIGH_CONTRAST) }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Reading Mode
                Text(
                    text = "Mokgwa wa Padi / Layout Mode",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ReadingModeChip(
                        title = "Temana ka Temana",
                        subtitle = "Setswana & English",
                        isSelected = currentReadingMode == ReadingMode.VERSE_BY_VERSE,
                        onClick = { onReadingModeChange(ReadingMode.VERSE_BY_VERSE) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ReadingModeChip(
                        title = "Setswana Fela",
                        subtitle = "Setswana Only",
                        isSelected = currentReadingMode == ReadingMode.SETSWANA_ONLY,
                        onClick = { onReadingModeChange(ReadingMode.SETSWANA_ONLY) },
                        modifier = Modifier.weight(1f)
                    )
                    ReadingModeChip(
                        title = "Sekgowa Fela",
                        subtitle = "English Only",
                        isSelected = currentReadingMode == ReadingMode.ENGLISH_ONLY,
                        onClick = { onReadingModeChange(ReadingMode.ENGLISH_ONLY) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun ThemeChoiceItem(
    title: String,
    subtitle: String,
    bgColor: Color,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { onClick() }
            .padding(4.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(bgColor)
                .border(
                    width = if (isSelected) 3.dp else 1.dp,
                    color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray.copy(alpha = 0.4f),
                    shape = CircleShape
                )
        ) {
            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = if (bgColor == HighContrastBg || bgColor == DarkBg) Color.White else Color.Black,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = title, fontSize = 11.sp, fontWeight = FontWeight.Bold)
        Text(text = subtitle, fontSize = 9.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
private fun ReadingModeChip(
    title: String,
    subtitle: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        border = if (isSelected) androidx.compose.foundation.BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary) else null,
        modifier = modifier.clickable { onClick() }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp)
        ) {
            Text(
                text = title,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = subtitle,
                fontSize = 10.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
