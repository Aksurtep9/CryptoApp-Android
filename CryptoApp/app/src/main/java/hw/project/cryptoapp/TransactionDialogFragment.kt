package hw.project.cryptoapp

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Binder
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.google.android.material.slider.Slider
import hw.project.cryptoapp.databinding.DialogNewCryptoBinding

import hw.project.cryptoapp.databinding.DialogTransactionBinding

class TransactionDialogFragment : DialogFragment() {
    private lateinit var binding: DialogTransactionBinding
    private lateinit var listener: TransactionDialogListener

    private var tagForTransaction: String? = "Tag"

    companion object{
        private const val ARG_TAG = "argTag"
        fun newInstance(tagForTransactions: String) = TransactionDialogFragment().apply{
            arguments = Bundle().apply {
                putString(ARG_TAG , tagForTransaction)
            }
        }
    }

    interface TransactionDialogListener {
        fun onFinished(percentage: Double, tagCrypto: String, buy: Boolean)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        binding = DialogTransactionBinding.inflate(LayoutInflater.from(context))

        binding.tagOfCrypto.text = tagForTransaction

        listener = context as? TransactionDialogFragment.TransactionDialogListener
            ?: throw RuntimeException("Activity must implement the TransactionDialogListener interface!")

        InitSlide()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            tagForTransaction = it.getString(ARG_TAG)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.sell_crypto)
            .setView(binding.root)
            .create()
    }

    private fun InitSlide() {
        binding.slider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            @SuppressLint("RestrictedApi")
            override fun onStartTrackingTouch(slider: Slider) {
                binding.Percentage.text = slider.value.toString() + " %"
            }


            @SuppressLint("RestrictedApi")
            override fun onStopTrackingTouch(slider: Slider) {
                // Responds to when slider's touch event is being stopped
            }
        })
    }

    private fun InitSell(){

    }
}