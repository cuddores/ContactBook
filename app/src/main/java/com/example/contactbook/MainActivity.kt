package com.example.contactbook

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.contactbook.ui.theme.ContactBookTheme
import androidx.core.net.toUri
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ContactBookTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = Color.White
                ) { innerPadding ->

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CallButton("+79001234567")
                        Spacer(modifier = Modifier.height(20.dp))

                        EmailButton(
                            address = "support@example.com",
                            subject = "Обращение",
                            body = "Добрый день,"
                        )
                        Spacer(modifier = Modifier.height(20.dp))

                        MapButton(
                            latitude = 60.0237,
                            longitude = 30.2289,
                            label = "Наш офис"
                        )
                        Spacer(modifier = Modifier.height(20.dp))

                        ShareButton(
                            text = "Контакт: +7 (495) 123-45-67, contact@example.com"
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CallButton(phoneNumber: String) {
    val context = LocalContext.current
    Button(onClick = {
        val intent = Intent(Intent.ACTION_DIAL, "tel:$phoneNumber".toUri())
        context.startActivity(intent)
    },
        modifier = Modifier
            .width(250.dp)
            .height(55.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF6A5ACD),
            contentColor = Color.White
        )
    ) {
        Text(
            "Позвонить",
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium
        )
    }

}

@Composable
fun EmailButton(address: String, subject: String, body: String) {
    val context = LocalContext.current

    Button(
        onClick = {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = "mailto:".toUri()
                putExtra(Intent.EXTRA_EMAIL, arrayOf(address))
                putExtra(Intent.EXTRA_SUBJECT, subject)
                putExtra(Intent.EXTRA_TEXT, body)
            }
            context.startActivity(intent)
        },
        modifier = Modifier
            .width(250.dp)
            .height(55.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF6A5ACD),
            contentColor = Color.White
        )
    ) {
        Text("Написать email",
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium
        )
    }
}


@Composable
fun MapButton(latitude: Double, longitude: Double, label: String) {
    val context = LocalContext.current

    Button(
        onClick = {
            val geoUri = "geo:0,0?q=$latitude,$longitude($label)".toUri()
            val intent = Intent(Intent.ACTION_VIEW, geoUri)
            context.startActivity(intent)
        },
        modifier = Modifier
            .width(250.dp)
            .height(55.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF6A5ACD), // Можно заменить на нужный
            contentColor = Color.White
        )
    ) {
        Text(
            "Показать на карте",
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun ShareButton(text: String) {
    val context = LocalContext.current

    Button(
        onClick = {
            val sendIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, text)
            }
            val chooser = Intent.createChooser(sendIntent, "Поделиться через...")
            context.startActivity(chooser)
        },
        modifier = Modifier
            .width(250.dp)
            .height(55.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF6A5ACD), // Можно заменить на нужный
            contentColor = Color.White
        )
    ) {
        Text(
            "Поделиться контактом",
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewScreen() {
    ContactBookTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CallButton("+79001234567")
            Spacer(modifier = Modifier.height(20.dp))

            EmailButton(
                address = "support@example.com",
                subject = "Обращение",
                body = "Добрый день,"
            )
            Spacer(modifier = Modifier.height(20.dp))

            MapButton(
                latitude = 60.0237,
                longitude = 30.2289,
                label = "Наш офис"
            )
            Spacer(modifier = Modifier.height(20.dp))

            ShareButton(
                text = "Контакт: +7 (495) 123-45-67, contact@example.com"
            )
        }
    }
}
