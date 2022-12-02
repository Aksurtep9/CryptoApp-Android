package hw.project.cryptoapp

import android.annotation.SuppressLint
import android.app.Dialog
import android.app.PendingIntent
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.google.android.material.slider.Slider
import hw.project.cryptoapp.data.Asset
import hw.project.cryptoapp.data.CryptoCoinDatabase
import hw.project.cryptoapp.data.PortfolioDatabase
import hw.project.cryptoapp.databinding.ActivityTransactionBinding
import kotlin.concurrent.thread


class TransactionActivity :  AppCompatActivity() {
    private lateinit var binding: ActivityTransactionBinding

    private var tagForTransaction: String? = null
    private var percentageFinal: Double? = null

    private lateinit var database: CryptoCoinDatabase
    private lateinit var portfolio_database: PortfolioDatabase

    companion object{
        private const val TAG = "TransactionActivity"
        const val cryptoTag = "extra.CryptoTag"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = CryptoCoinDatabase.getDatabase(applicationContext)
        portfolio_database = PortfolioDatabase.getDatabase(applicationContext)

        tagForTransaction = intent.getStringExtra(cryptoTag)

        binding.tagOfCryptoID.text = tagForTransaction
        InitSlide()
        InitBuy()
        InitCancel()
        InitSell()
    }

    private fun InitSlide() {
        binding.slider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            @SuppressLint("RestrictedApi")
            override fun onStartTrackingTouch(slider: Slider) {
                binding.Percentage.text = slider.value.toString() + " %"
                percentageFinal = slider.value.toDouble()
            }


            @SuppressLint("RestrictedApi")
            override fun onStopTrackingTouch(slider: Slider) {
                binding.Percentage.text = slider.value.toString() + " %"
                percentageFinal = slider.value.toDouble()
            }
        })
    }

    private fun InitSell(){
        binding.sellButton.setOnClickListener {
            onFinished(percentageFinal!!, tagForTransaction!!, false)
            finish()
        }
    }

    private  fun InitBuy(){
        binding.buyButton.setOnClickListener {
            onFinished(percentageFinal!!, tagForTransaction!!, true)
            finish()
        }
    }

    private fun InitCancel(){
        binding.cancelButton.setOnClickListener {
            finish()
        }
    }

    private fun onFinished(percentage: Double, tagCrypto: String, buy: Boolean) {
        thread {
            if (buy) {
                val price = database.cryptoCoinDao().getPriceWithTag(tagCrypto)
                val USDamount =
                    portfolio_database.portfolioDao().getAmount("USD") * percentage / 100
                portfolio_database.portfolioDao().updateAmount("USD", -USDamount)
                if (portfolio_database.portfolioDao().isNotInPortfolio(tagCrypto)) {
                    val asset = Asset(null, tagCrypto, USDamount / price)
                    val id = portfolio_database.portfolioDao().insert(asset)
                    asset.id = id
                } else {
                    portfolio_database.portfolioDao().updateAmount(tagCrypto, USDamount / price)
                }

            } else {
                if (portfolio_database.portfolioDao().isNotInPortfolio(tagCrypto)) {
                    Toast.makeText(this, "No such crypto with that Symbol", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    val cryptoPrice = database.cryptoCoinDao().getPriceWithTag(tagCrypto)
                    val cryptoAmount =
                        portfolio_database.portfolioDao().getAmount(tagCrypto) * percentage / 100

                    val USDamount = cryptoAmount * cryptoPrice

                    portfolio_database.portfolioDao().updateAmount(tagCrypto, -cryptoAmount)
                    portfolio_database.portfolioDao().updateAmount("USD", USDamount)

                }
            }
        }
    }
}