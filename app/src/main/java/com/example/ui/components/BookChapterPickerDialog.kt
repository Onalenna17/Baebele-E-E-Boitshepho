package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.BibleDataProvider
import com.example.model.BibleBook
import com.example.model.Testament

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookChapterPickerDialog(
    currentBook: BibleBook,
    currentChapter: Int,
    onDismiss: () -> Unit,
    onSelectBookAndChapter: (BibleBook, Int) -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(if (currentBook.testament == Testament.OLD) 0 else 1) }
    var searchQuery by remember { mutableStateOf("") }
    var step by remember { mutableIntStateOf(1) } // 1: Book picker, 2: Chapter picker
    var pickedBook by remember { mutableStateOf(currentBook) }

    BasicAlertDialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 6.dp,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.85f)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                // Header
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (step == 2) {
                        IconButton(onClick = { step = 1 }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Boela / Back")
                        }
                    }
                    Text(
                        text = if (step == 1) "Tlhopha Buka / Select Book" else "${pickedBook.nameSetswana} (${pickedBook.nameEnglish})",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Tswala / Close")
                    }
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                if (step == 1) {
                    // Search field
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("Batla buka / Search book...") },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )

                    // Tabs: Old Testament vs New Testament
                    TabRow(selectedTabIndex = selectedTab) {
                        Tab(
                            selected = selectedTab == 0,
                            onClick = { selectedTab = 0 },
                            text = { Text("Kgologolo (Old)", fontWeight = FontWeight.SemiBold) }
                        )
                        Tab(
                            selected = selectedTab == 1,
                            onClick = { selectedTab = 1 },
                            text = { Text("E Ntšhwa (New)", fontWeight = FontWeight.SemiBold) }
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    val filteredBooks = BibleDataProvider.books.filter { b ->
                        val matchesTab = if (selectedTab == 0) b.testament == Testament.OLD else b.testament == Testament.NEW
                        val matchesQuery = searchQuery.isBlank() ||
                                b.nameSetswana.contains(searchQuery, ignoreCase = true) ||
                                b.nameEnglish.contains(searchQuery, ignoreCase = true)
                        matchesTab && matchesQuery
                    }

                    LazyColumn(modifier = Modifier.weight(1f)) {
                        items(filteredBooks) { book ->
                            val isCurrent = book.id == currentBook.id
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = if (isCurrent) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 3.dp)
                                    .clickable {
                                        pickedBook = book
                                        step = 2
                                    }
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                                ) {
                                    Column {
                                        Text(
                                            text = book.nameSetswana,
                                            style = MaterialTheme.typography.bodyLarge,
                                            fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Medium,
                                            color = if (isCurrent) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface
                                        )
                                        Text(
                                            text = book.nameEnglish,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = MaterialTheme.colorScheme.surfaceVariant
                                    ) {
                                        Text(
                                            text = "${book.chaptersCount} dikg.",
                                            style = MaterialTheme.typography.labelSmall,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                } else {
                    // Chapter Grid Picker
                    Text(
                        text = "Tlhopha Kgaolo / Select Chapter (1 - ${pickedBook.chaptersCount})",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(minSize = 54.dp),
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items((1..pickedBook.chaptersCount).toList()) { ch ->
                            val isCurrent = pickedBook.id == currentBook.id && ch == currentChapter
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .size(54.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(
                                        if (isCurrent) MaterialTheme.colorScheme.primary
                                        else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f)
                                    )
                                    .clickable {
                                        onSelectBookAndChapter(pickedBook, ch)
                                        onDismiss()
                                    }
                            ) {
                                Text(
                                    text = "$ch",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = if (isCurrent) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
