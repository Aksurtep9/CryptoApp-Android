package hw.project.cryptoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import hw.project.cryptoapp.adapter.CryptoAdapter
import hw.project.cryptoapp.data.CryptoCoin
import hw.project.cryptoapp.data.CryptoCoinDatabase
import hw.project.cryptoapp.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity(), CryptoAdapter.CryptoItemClickListener {
    private lateinit var binding: ActivityMainBinding

    private lateinit var database: CryptoCoinDatabase
    private lateinit var adapter: CryptoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        database = CryptoCoinDatabase.getDatabase(applicationContext)

        adapter = CryptoAdapter(this)
        binding.fab.setOnClickListener {
            adapter.addItem(CryptoCoin(1,"Bitcoin", "BTC", 0, 18000, true))
            adapter.addItem(CryptoCoin(1,"Ethereum", "ETH", 0, 1300, true))
        }
        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapter = CryptoAdapter(this)
        //adapter.addItem(CryptoCoin(1,"Bitcoin", "BTC", 0, 18000, true))
        binding.rvMain.layoutManager = LinearLayoutManager(this)
        binding.rvMain.adapter = adapter
        loadItemsInBackground()
    }

    private fun loadItemsInBackground() {
        thread {
            val items = database.cryptoCoinDao().getAll()
            runOnUiThread {
                adapter.update(items)
            }
        }
    }

    override fun onItemChanged(item: CryptoCoin) {
        thread {
            database.cryptoCoinDao().update(item)
            Log.d("MainActivity", "CryptoItem update was successful")
        }
    }
}