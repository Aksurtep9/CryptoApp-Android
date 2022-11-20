package hw.project.cryptoapp

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import hw.project.cryptoapp.adapter.CryptoAdapter
import hw.project.cryptoapp.data.*
import hw.project.cryptoapp.databinding.ActivityMainBinding
import hw.project.cryptoapp.network.CryptoData
import hw.project.cryptoapp.network.CryptoDataHolder
import hw.project.cryptoapp.network.Data
import hw.project.cryptoapp.network.NetworkManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity(), CryptoAdapter.CryptoItemClickListener,
    AddCryptoDialogFragment.AddCryptoDialogListener,
    AddAssetDialogFragment.AddAssetDialogListener,
    TransactionDialogFragment.TransactionDialogListener,
    CryptoDataHolder{
    private lateinit var binding: ActivityMainBinding

    private lateinit var database: CryptoCoinDatabase
    private lateinit var portfolio_database: PortfolioDatabase
    private lateinit var adapter: CryptoAdapter
    private var cryptoData: CryptoData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        database = CryptoCoinDatabase.getDatabase(applicationContext)
        portfolio_database = PortfolioDatabase.getDatabase(applicationContext)
        adapter = CryptoAdapter(this, this@MainActivity)



        initFab()
        initProf()
        initAsset()
        loadCryptoData()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapter = CryptoAdapter(this, this@MainActivity)
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

    private fun initFab() {
        binding.fab.setOnClickListener {
            AddCryptoDialogFragment().show(supportFragmentManager, AddCryptoDialogFragment::class.java.simpleName)
        }
    }

    private fun initProf() {
        binding.portfolio.setOnClickListener {
            PortfolioFragment().show(supportFragmentManager,PortfolioFragment::class.java.simpleName)
        }
    }

    private fun initAsset(){
        binding.addAsset.setOnClickListener{
            AddAssetDialogFragment().show(supportFragmentManager, AddAssetDialogFragment::class.java.simpleName)
        }
    }

    override fun onItemChanged(item: CryptoCoin) {
        thread {
            database.cryptoCoinDao().update(item)
            Log.d("MainActivity", "CryptoItem update was successful")
        }
    }

    override fun onItemDeleted(item: CryptoCoin) {
        thread{
            database.cryptoCoinDao().deleteItem(item)
        }
    }

    override fun onTransaction(item: CryptoCoin) {


        System.out.println(TransactionDialogFragment.newInstance(item.tag).tag)
        TransactionDialogFragment.newInstance(item.tag).show(supportFragmentManager, TransactionDialogFragment::class.java.simpleName)
    }

    override fun onCryptoAdded(TAG: String?) {
        loadCryptoData()
        val data = searchBySymbol(TAG)
        if(data == null){
            Toast.makeText(this, "No such crypto with that Symbol", Toast.LENGTH_SHORT).show()
        }
        val coin: CryptoCoin = CryptoCoin(
            id = null,
            name = data!!.name,
            tag = data!!.symbol,
            apiID = data!!.id,
            price = data!!.quote.USD.price,
            isChecked = true
        )
        thread {
            val id = database.cryptoCoinDao().insert(coin)
            coin.id = id
            runOnUiThread {
                adapter.addItem(coin!!)
            }
        }

    }

    override fun getCryptoData(): CryptoData? {
        return cryptoData
    }

    private fun loadCryptoData() {
        NetworkManager.getCryptos()?.enqueue(object : Callback<CryptoData?> {
            override fun onResponse(
                call: Call<CryptoData?>,
                response: Response<CryptoData?>
            ) {
                Log.d(TAG, "onResponse: " + response.code())
                if (response.isSuccessful) {
                    cryptoData = response.body()!!
                    Log.d("WTF", cryptoData!!.data[0].name)
                } else {
                    Toast.makeText(this@MainActivity, "Error: " + response.message(), Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(
                call: Call<CryptoData?>,
                throwable: Throwable
            ) {
                throwable.printStackTrace()
                Toast.makeText(this@MainActivity, "Network request error occured, check LOG", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun refreshData(){
        val daoInstance = database.cryptoCoinDao()
        for(crypto in cryptoData!!.data){
            if(daoInstance.isNotExists(crypto.symbol)) {
                    daoInstance.updatePrice_With_tag_ApiID(
                        crypto.quote.USD.price,
                        crypto.symbol,
                        crypto.id)
            }
        }
        loadItemsInBackground()
    }

    private fun searchBySymbol(symbol: String?): Data?{
        for(data in cryptoData!!.data){
            if(data.symbol == symbol){
                return data
            }
        }
        return null
    }

    override fun onAssetAdded(value: Int) {
        thread{
            portfolio_database.portfolioDao().updateAmount("USD", value.toDouble())
        }
    }

    override fun onFinished(percentage: Double, tagCrypto: String, buy: Boolean) {
        if(buy){

        }
    }
}