package com.example.businesscard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.businesscard.ui.theme.BusinessCardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            BusinessCardTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    // Passed innerPadding here to correctly handle system bars
                    BusinessCard(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun BusinessCard(modifier: Modifier = Modifier) {
    // Outer column sets the soft green background tint for the business card
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFD2E8D4))
            .padding(bottom = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Invisible anchor spacer to help push the profile card down into vertical center equilibrium
        Spacer(modifier = Modifier.height(1.dp))

        // 1. Centered Profile Header Component
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Dark background container for the logo asset
            Box(
                modifier = Modifier
                    .size(110.dp)
                    .background(Color(0xFF073042))
                    .padding(12.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.android_logo),
                    contentDescription = "Android Logo",
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Jayasree",
                fontSize = 32.sp,
                fontWeight = FontWeight.Light,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "B.Tech CSE - AI & ML Student",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF006D38) // Theme green color tint
            )
        }

        // 2. Bottom Contact Details Stacked Component
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 50.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Email Row
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "✉", // Standard visual unicode fallback token representation
                    fontSize = 18.sp,
                    color = Color(0xFF006D38),
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "jayasree@example.com",
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }

            // Phone Row
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "📞",
                    fontSize = 18.sp,
                    color = Color(0xFF006D38),
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "+91 XXXXX XXXXX",
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BusinessCardPreview() {
    BusinessCardTheme {
        BusinessCard()
    }
}