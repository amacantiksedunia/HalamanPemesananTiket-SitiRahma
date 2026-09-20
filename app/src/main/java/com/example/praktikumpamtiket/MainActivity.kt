package com.example.praktikumpamtiket

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.NumberFormat
import java.util.Locale

private val BiruUtama = Color(0xFF2979FF)
private val HijauTotal = Color(0xFF1B7F3B)
private val MerahReset = Color(0xFFE53935)
private val AbuKotak = Color(0xFFF1F3F7)
private val LatarLayar = Color(0xFFF5F7FA)

private const val HARGA_TIKET = 25000

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TicketScreen()
        }
    }
}

private fun formatRupiah(nominal: Int): String {
    val angka = NumberFormat.getNumberInstance(Locale.forLanguageTag("id-ID")).format(nominal)
    return "Rp$angka"
}

@Composable
fun TicketScreen() {
    // State: jumlah tiket. Total dihitung dari state ini.
    var jumlah by remember { mutableStateOf(1) }
    val total = HARGA_TIKET * jumlah

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LatarLayar)
            .navigationBarsPadding()
    ) {
        // Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
                .background(BiruUtama)
                .statusBarsPadding()
                .padding(vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Pemesanan Tiket",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Pesan tiket dengan mudah!",
                color = Color.White.copy(alpha = 0.9f),
                fontSize = 14.sp
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Harga tiket
            InfoCard(judul = "Harga Tiket") {
                Text(
                    text = formatRupiah(HARGA_TIKET),
                    color = BiruUtama,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(text = "per tiket", color = Color.Gray, fontSize = 14.sp)
            }

            // Jumlah tiket
            InfoCard(judul = "Jumlah Tiket") {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = { if (jumlah > 1) jumlah-- },
                        enabled = jumlah > 1,
                        shape = CircleShape,
                        contentPadding = PaddingValues(0.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = BiruUtama),
                        modifier = Modifier.size(52.dp)
                    ) {
                        Text("-", fontSize = 26.sp, fontWeight = FontWeight.Bold)
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(AbuKotak),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "$jumlah",
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Button(
                        onClick = { jumlah++ },
                        shape = CircleShape,
                        contentPadding = PaddingValues(0.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = BiruUtama),
                        modifier = Modifier.size(52.dp)
                    ) {
                        Text("+", fontSize = 26.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            // Total bayar
            InfoCard(judul = "Total") {
                Text(
                    text = formatRupiah(total),
                    color = HijauTotal,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Tombol reset
            Button(
                onClick = { jumlah = 1 },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MerahReset),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Text("RESET", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun InfoCard(judul: String, isi: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = judul, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(8.dp))
            isi()
        }
    }
}