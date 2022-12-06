package hw.project.cryptoapp

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.PopupMenu
import androidx.annotation.MainThread
import androidx.appcompat.app.AlertDialog
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
import hw.project.cryptoapp.databinding.DialogNewCryptoBinding
import hw.project.cryptoapp.databinding.DialogPortfolioBinding
import kotlinx.coroutines.Dispatchers
import kotlin.concurrent.thread

class PortfolioFragment : DialogFragment() {

    private lateinit var binding: DialogPortfolioBinding

    private lateinit var portfolio_database: PortfolioDatabase
    private lateinit var crypto_database: CryptoCoinDatabase

    private lateinit var entries: ArrayList<PieEntry>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        binding = DialogPortfolioBinding.inflate(LayoutInflater.from(context))

    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        crypto_database = CryptoCoinDatabase.getDatabase(this.requireContext())
        portfolio_database = PortfolioDatabase.getDatabase(this.requireContext())

        loadPortfolio()

        this.dialog?.setCanceledOnTouchOutside(true)

        return AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .setNegativeButton(R.string.cancel, null)
            .create()
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
        binding.chartCrypto.invalidate()
    }


}