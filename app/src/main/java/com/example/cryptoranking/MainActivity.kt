package com.example.cryptoranking

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoranking.ui.theme.CryptoRankingTheme
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Set up Logging Interceptor for debugging API calls
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        // Retrofit instance
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.coinranking.com/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        val cryptoApiService = retrofit.create(CryptoApiService::class.java)

        // API Call
        val call: Call<CoinResponse> = cryptoApiService.getCoinResponse()
        call.enqueue(object : Callback<CoinResponse> {
            override fun onResponse(call: Call<CoinResponse>, response: Response<CoinResponse>) {
                if (!response.isSuccessful) {
                    Log.e("MainActivity", "Error: ${response.code()}")
                    Toast.makeText(this@MainActivity, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                    return
                }

                val coinResponse = response.body()
                val coinList = coinResponse?.data?.coins

                if (coinList.isNullOrEmpty()) {
                    Log.e("MainActivity", "No coins found")
                    Toast.makeText(this@MainActivity, "No coins found", Toast.LENGTH_SHORT).show()
                    return
                }

                Log.d("MainActivity", "Fetched ${coinList.size} coins")

                val coinAdapter = CoinAdapter(this@MainActivity, coinList)
                recyclerView.adapter = coinAdapter
            }

            override fun onFailure(call: Call<CoinResponse>, t: Throwable) {
                Log.e("MainActivity", "Failure: ${t.message}")
                Toast.makeText(this@MainActivity, "Failed: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CryptoRankingTheme {
        Greeting("Android")
    }
}