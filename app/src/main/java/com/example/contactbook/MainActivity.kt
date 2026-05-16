package com.example.contactbook

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
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
import androidx.compose.ui.res.stringResource
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
                        CallButton(stringResource(R.string.phoneNumber))
                        Spacer(modifier = Modifier.height(20.dp))

                        EmailButton(
                            address = stringResource(R.string.EmailAddress),
                            subject = stringResource(R.string.EmailSubject),
                            body = stringResource(R.string.EmailBody)
                        )
                        Spacer(modifier = Modifier.height(20.dp))

                        MapButton(
                            latitude = 60.0237,
                            longitude = 30.2289,
                            label = stringResource(R.string.MapLabel)
                        )
                        Spacer(modifier = Modifier.height(20.dp))

                        ShareButton(
                            text = stringResource(R.string.ShareButtonText)
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
        startIntent(context, intent)
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
            stringResource(R.string.CallBtnText),
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
            startIntent(context, intent)
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
            stringResource(R.string.CreateEmail),
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
            startIntent(context, intent)
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
            stringResource(R.string.ShowOnMap),
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
            val chooser = Intent.createChooser(sendIntent, context.getString(R.string.ShareVia))
            startIntent(context, chooser)
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
            stringResource(R.string.ShareContact),
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}

private fun startIntent(context: Context, intent: Intent) {

    if (intent.resolveActivity(context.packageManager) != null)
        context.startActivity(intent)
    else
    {
        Toast.makeText(context, context.getString(R.string.ExeptionMessage), Toast.LENGTH_SHORT).show()
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
            CallButton(stringResource(R.string.phoneNumber))
            Spacer(modifier = Modifier.height(20.dp))

            EmailButton(
                address = stringResource(R.string.EmailAddress),
                subject = stringResource(R.string.EmailSubject),
                body = stringResource(R.string.EmailBody)
            )
            Spacer(modifier = Modifier.height(20.dp))

            MapButton(
                latitude = 60.0237,
                longitude = 30.2289,
                label = stringResource(R.string.MapLabel)
            )
            Spacer(modifier = Modifier.height(20.dp))

            ShareButton(
                text = stringResource(R.string.ShareButtonText)
            )
        }
    }
}
