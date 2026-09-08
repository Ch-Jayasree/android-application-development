package com.example.bookshelf

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bookshelf.ui.theme.BookShelfTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BookShelfTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    BookshelfApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

data class Book(
    val id: String,
    val title: String,
    val authors: String,
    val description: String,
    val pages: String,
    val publisher: String,
    val coverGradient: List<Color>,
    val coverIcon: ImageVector
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookshelfApp(modifier: Modifier = Modifier) {
    val booksList = remember {
        listOf(
            Book(
                id = "1",
                title = "Jetpack Compose Internals",
                authors = "Jayasree Dev",
                description = "An extensive deep-dive into compiler plugins, runtime optimizations, and positional memoization mechanics driving clean declarative modern layouts.",
                pages = "412 Pages",
                publisher = "Android Masterclass Tech Press",
                coverGradient = listOf(Color(0xFF0F2027), Color(0xFF203A43), Color(0xFF2C5364)),
                coverIcon = Icons.Default.Code
            ),
            Book(
                id = "2",
                title = "Kotlin Clean Architecture",
                authors = "Alex Studio Artisans",
                description = "Master data layer caching, multi-module setups, dependencies separation, and reactive state preservation principles inside enterprise environments.",
                pages = "320 Pages",
                publisher = "JetBrains Advanced Publications",
                coverGradient = listOf(Color(0xFF8A2387), Color(0xFFE94057), Color(0xFFF27121)),
                coverIcon = Icons.Default.MenuBook
            ),
            Book(
                id = "3",
                title = "Advanced Data Systems",
                authors = "Computer Science Press",
                description = "A thorough structural evaluation tracking indexing algorithms, distributed memory databases, and memory caching strategies.",
                pages = "580 Pages",
                publisher = "Silicon Valley Academic Library",
                coverGradient = listOf(Color(0xFF11998e), Color(0xFF38ef7d)),
                coverIcon = Icons.Default.AutoStories
            ),
            Book(
                id = "4",
                title = "AI & ML on Mobile Devices",
                authors = "Neural Networks Lab",
                description = "Train and deploy lightweight neural networks directly inside Android hardware contexts using on-device TensorFlow Lite optimizations.",
                pages = "295 Pages",
                publisher = "Generative Engineering Group",
                coverGradient = listOf(Color(0xFF141E30), Color(0xFF243B55)),
                coverIcon = Icons.Default.Psychology
            )
        )
    }

    var selectedBook by remember { mutableStateOf<Book?>(null) }

    BackHandler(enabled = selectedBook != null) {
        selectedBook = null
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = selectedBook?.title ?: "My Premium Bookshelf",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    if (selectedBook != null) {
                        IconButton(onClick = { selectedBook = null }) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back to Grid")
                        }
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { innerPadding ->
        Box(modifier = modifier.padding(innerPadding)) {
            if (selectedBook == null) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize().background(Color(0xFF0F172A)).padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    items(booksList) { book ->
                        BookGridCard(book = book, onBookClick = { selectedBook = book })
                    }
                }
            } else {
                BookDetailScreen(book = selectedBook!!)
            }
        }
    }
}
@Composable
fun BookGridCard(book: Book, onBookClick: () -> Unit, modifier: Modifier = Modifier) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(290.dp)
            .clickable { onBookClick() }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .background(Brush.verticalGradient(book.coverGradient))
                    .padding(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(4.dp)
                        .background(Color.Black.copy(alpha = 0.15f))
                        .align(Alignment.CenterStart)
                )

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = book.coverIcon,
                        contentDescription = null,
                        modifier = Modifier.size(32.dp),
                        tint = Color.White.copy(alpha = 0.8f)
                    )
                    Text(
                        text = book.title,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        lineHeight = 16.sp,
                        //modifier = Modifier.padding(horizontal = 4.dp, bottom = 8.dp)
                    )
                }
            }

            Column(modifier = Modifier.fillMaxWidth().padding(12.dp)) {
                Text(
                    text = book.title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = book.authors,
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color(0xFF94A3B8)
                )
            }
        }
    }
}

@Composable
fun BookDetailScreen(book: Book) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F172A))
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .width(160.dp)
                .height(240.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Brush.verticalGradient(book.coverGradient))
                    .padding(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(6.dp)
                        .background(Color.Black.copy(alpha = 0.15f))
                        .align(Alignment.CenterStart)
                )
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = book.coverIcon,
                        contentDescription = null,
                        modifier = Modifier.size(44.dp),
                        tint = Color.White.copy(alpha = 0.8f)
                    )
                    Text(
                        text = book.title,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        maxLines = 4,
                        lineHeight = 18.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = book.title, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White, textAlign = TextAlign.Center)
        Text(text = "By ${book.authors}", fontSize = 16.sp, color = Color(0xFF94A3B8), modifier = Modifier.padding(top = 4.dp))

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "LENGTH", fontSize = 11.sp, color = Color(0xFF64748B), fontWeight = FontWeight.Bold)
                    Text(text = book.pages, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White, modifier = Modifier.padding(top = 4.dp))
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "PUBLISHER", fontSize = 11.sp, color = Color(0xFF64748B), fontWeight = FontWeight.Bold)
                    Text(text = book.publisher, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White, modifier = Modifier.padding(top = 4.dp), maxLines = 1)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
            Text(text = "Synopsis", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = book.description, fontSize = 15.sp, lineHeight = 24.sp, color = Color(0xFF94A3B8))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BookshelfPreview() {
    BookShelfTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            BookshelfApp()
        }
    }
}