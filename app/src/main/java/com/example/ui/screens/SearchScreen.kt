package com.example.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.BibleDataProvider
import com.example.model.BibleBook
import com.example.model.BibleVerse
import com.example.model.Testament

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun SearchScreen(
    searchQuery: String,
    searchResults: List<BibleVerse>,
    onQueryChange: (String) -> Unit,
    onSearch: (String, Testament?) -> Unit,
    onSelectVerse: (BibleBook, Int) -> Unit,
    onPreviewVerse: (BibleVerse) -> Unit = {},
    onPlayVoice: (verse: BibleVerse, isSetswana: Boolean) -> Unit = { _, _ -> },
    onBack: () -> Unit
) {
    var selectedFilter by remember { mutableStateOf<Testament?>(null) }
    var textInput by remember { mutableStateOf(searchQuery) }

    val popularKeywords = listOf("Modimo", "Lorato", "Kagiso", "Tumelo", "Jesu", "Lesedi", "Love", "Peace", "Faith")

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("Batla mo Baebeleng / Search", fontWeight = FontWeight.Bold) },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Boela / Back")
                }
            }
        )

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            // Search Input
            OutlinedTextField(
                value = textInput,
                onValueChange = {
                    textInput = it
                    onQueryChange(it)
                    onSearch(it, selectedFilter)
                },
                placeholder = { Text("Kwala lefoko (sekao: Modimo, Lorato)...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                trailingIcon = {
                    if (textInput.isNotEmpty()) {
                        IconButton(onClick = {
                            textInput = ""
                            onQueryChange("")
                            onSearch("", selectedFilter)
                        }) {
                            Icon(Icons.Default.Clear, contentDescription = "Sutlha / Clear")
                        }
                    }
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("search_text_field")
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Filter Chips
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                FilterChip(
                    selected = selectedFilter == null,
                    onClick = {
                        selectedFilter = null
                        onSearch(textInput, null)
                    },
                    label = { Text("Tsotlhe / All") }
                )
                FilterChip(
                    selected = selectedFilter == Testament.OLD,
                    onClick = {
                        selectedFilter = Testament.OLD
                        onSearch(textInput, Testament.OLD)
                    },
                    label = { Text("Kgologolo (Old)") }
                )
                FilterChip(
                    selected = selectedFilter == Testament.NEW,
                    onClick = {
                        selectedFilter = Testament.NEW
                        onSearch(textInput, Testament.NEW)
                    },
                    label = { Text("E Ntšhwa (New)") }
                )
            }

            // Quick suggestion chips when query is empty
            if (textInput.isBlank()) {
                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    text = "Mafoko a a Tlwaelegileng / Popular Keywords:",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(6.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    popularKeywords.forEach { kw ->
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                            modifier = Modifier.clickable {
                                textInput = kw
                                onQueryChange(kw)
                                onSearch(kw, selectedFilter)
                            }
                        ) {
                            Text(
                                text = kw,
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            )
                        }
                    }
                }
            }
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

        // Search Results List
        if (textInput.isNotBlank()) {
            Text(
                text = "Dipholo / Results: ${searchResults.size} verses found",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            )

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(searchResults) { verse ->
                    val book = BibleDataProvider.books.find { it.id == verse.bookId } ?: BibleDataProvider.books.first()
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 5.dp)
                            .clickable { onSelectVerse(book, verse.chapter) }
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "${verse.bookNameSetswana} ${verse.chapter}:${verse.verse}",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    Text(
                                        text = "${verse.bookNameEnglish} ${verse.chapter}:${verse.verse}",
                                        fontSize = 12.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    IconButton(
                                        onClick = { onPlayVoice(verse, true) },
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
                                        onClick = { onPreviewVerse(verse) },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Visibility,
                                            contentDescription = "Leba Pele / Preview",
                                            tint = MaterialTheme.colorScheme.secondary,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = verse.textSetswana,
                                style = MaterialTheme.typography.bodyMedium,
                                maxLines = 2,
                                fontWeight = FontWeight.Medium
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = verse.textEnglish,
                                style = MaterialTheme.typography.bodySmall,
                                maxLines = 2,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}
