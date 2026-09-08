package com.example.thirtydaysapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thirtydaysapp.ui.theme._30DaysAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _30DaysAppTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { ThirtyDaysTopAppBar() }
                ) { innerPadding ->
                    ThirtyDaysListScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThirtyDaysTopAppBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "30 Days of Healthy Habits",
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    )
}

@Composable
fun ThirtyDaysListScreen(modifier: Modifier = Modifier) {
    val tipsList = remember {
        val titles = listOf(
            "Morning Stretch", "Hydration Goal", "Desktop Walks", "Plank Core", "Green Veggies",
            "Screen Break", "Deep Breathing", "Posture Check", "Morning Sun", "Jumping Jacks",
            "Arm Circles", "Sleep Quality", "Fruit Snack", "Gratitude Log", "Air Squats",
            "Take Stairs", "Home Cooking", "Desk Clean", "Read Pages", "Less Salt",
            "Toe Reaches", "Herbal Tea", "Push-Ups", "Call a Friend", "No Fast Food",
            "Calf Raises", "Budget Check", "Spine Twists", "No Soda", "Progress Review"
        )
        val descriptions = listOf(
            "Spend 5 minutes doing dynamic stretching to start your blood circulation.",
            "Drink 3 liters of pure water today to keep muscle groups hydrated.",
            "For every hour at your desk, stand up and walk around for 2 minutes.",
            "Hold a steady forearm plank for 60 seconds to build core stability.",
            "Add a dedicated serving of green vegetables to your dinner plate.",
            "Power down all phone and monitor screens 30 minutes before bed.",
            "Sit silently and take 10 deep, controlled diaphragmatic breaths.",
            "Roll your shoulders back and sit straight at your workstation.",
            "Step outdoors into natural morning sunlight for 10 minutes.",
            "Complete 20 high-energy jumping jacks to elevate your heart rate.",
            "Perform 15 continuous slow arm circles to unlock shoulder joints.",
            "Ensure your bedroom environment is completely dark and cool.",
            "Swap out midday processed sugary snacks for a fresh piece of fruit.",
            "Write down exactly three micro-things you are thankful for today.",
            "Execute 15 bodyweight air squats maintaining proper knee tracking.",
            "Skip mechanical elevator rides today and take the stairs instead.",
            "Prepare your lunch entirely from scratch using fresh ingredients.",
            "Declutter and wipe down your keyboard, mousepad, and desk area.",
            "Read 5 pages of a physical book to build cognitive mental focus.",
            "Avoid adding supplemental table salt seasoning to your meals today.",
            "Reach cleanly towards your toes from a standing position for 30s.",
            "Enjoy a warm cup of unsweetened organic green tea this afternoon.",
            "Complete 10 controlled, full-range push-ups off the floor or a desk.",
            "Call a close family member or friend for a meaningful catchup.",
            "Commit to zero commercial processed fast food intake today.",
            "Perform 25 steady bodyweight calf raises standing on a step.",
            "Review your personal financial tracking app transactions today.",
            "Sit tall and rotate your upper torso to the left and right sides.",
            "Drink strictly plain beverages today—completely skipping sodas.",
            "Reflect intentionally on how your health habits shifted over 30 days."
        )
        val icons = listOf(
            Icons.Default.Accessibility, Icons.Default.WaterDrop, Icons.Default.Accessibility,
            Icons.Default.FitnessCenter, Icons.Default.Restaurant, Icons.Default.PhonelinkOff,
            Icons.Default.SelfImprovement, Icons.Default.Accessibility, Icons.Default.WbSunny,
            Icons.Default.Favorite, Icons.Default.Refresh, Icons.Default.Bedtime,
            Icons.Default.ShoppingBag, Icons.Default.EditNote, Icons.Default.Accessibility,
            Icons.Default.TrendingUp, Icons.Default.SoupKitchen, Icons.Default.CleanHands,
            Icons.Default.Book, Icons.Default.Scale, Icons.Default.Accessibility,
            Icons.Default.Coffee, Icons.Default.Accessibility, Icons.Default.Call,
            Icons.Default.NoMeals, Icons.Default.Height, Icons.Default.Payments,
            Icons.Default.Rowing, Icons.Default.Block, Icons.Default.EmojiEvents
        )

        List(30) { index ->
            DayTip(
                dayNumber = index + 1,
                title = titles[index],
                description = descriptions[index],
                imageResId = icons[index].hashCode() // Clean mapping to match your DayTip file fields exactly
            )
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF7F9FA))
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        items(tipsList) { item ->
            DayItemCard(dayTip = item)
        }
    }
}

@Composable
fun DayItemCard(dayTip: DayTip, modifier: Modifier = Modifier) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = modifier
            .fillMaxWidth()
            .clickable { isExpanded = !isExpanded }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioLowBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Day ${dayTip.dayNumber}",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
                Icon(
                    imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = dayTip.title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)),
                contentAlignment = Alignment.Center
            ) {
                // Displays the vector fallback
                Icon(
                    imageVector = Icons.Default.FitnessCenter,
                    contentDescription = dayTip.title,
                    modifier = Modifier.size(72.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            if (isExpanded) {
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = dayTip.description,
                    style = MaterialTheme.typography.bodyLarge,
                    lineHeight = 24.sp,
                    color = Color.DarkGray
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ThirtyDaysPreview() {
    _30DaysAppTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            ThirtyDaysListScreen()
        }
    }
}