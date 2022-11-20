package hw.project.cryptoapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.MainThread
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import hw.project.cryptoapp.data.Asset
import hw.project.cryptoapp.data.CryptoCoinDatabase
import hw.project.cryptoapp.data.PortfolioDatabase
import hw.project.cryptoapp.databinding.DialogPortfolioBinding
import kotlinx.coroutines.Dispatchers
import kotlin.concurrent.thread

class PortfolioFragment : DialogFragment() {

    private lateinit var binding: DialogPortfolioBinding

    private lateinit var portfolio_database: PortfolioDatabase
    private lateinit var crypto_database: CryptoCoinDatabase

    private lateinit var entries: ArrayList<PieEntry>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogPortfolioBinding.inflate(layoutInflater, container, false)

        crypto_database = CryptoCoinDatabase.getDatabase(this.requireContext())
        portfolio_database = PortfolioDatabase.getDatabase(this.requireContext())

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadPortfolio()
    }

    private fun loadPortfolio(){
        entries = ArrayList<PieEntry>()
        thread {
            for(asset in portfolio_database.portfolioDao().getAll()){
                //Crypto
                if(asset.cryptoTag != "USD") {
                    val value: Float = (asset.amount * crypto_database.cryptoCoinDao()
                        .getPriceWithTag(asset.cryptoTag)).toFloat()
                    entries.add(PieEntry(value,asset.cryptoTag))
                }
                //FIAT
                else{
                    entries.add(PieEntry(asset.amount.toFloat(),"USD"))
                }
            }
        }

        val dataSet = PieDataSet(entries, "")
        dataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()

        val data = PieData(dataSet)
        binding.chartCrypto.data = data
        binding.chartCrypto.setEntryLabelTextSize(13f)
        binding
        binding.chartCrypto.invalidate()
    }

}