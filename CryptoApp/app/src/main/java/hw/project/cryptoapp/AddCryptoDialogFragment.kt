package hw.project.cryptoapp

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import hw.project.cryptoapp.databinding.DialogNewCryptoBinding

class AddCryptoDialogFragment : AppCompatDialogFragment() {

    private lateinit var binding: DialogNewCryptoBinding
    private lateinit var listener: AddCryptoDialogListener

    interface AddCryptoDialogListener {
        fun onCryptoAdded(TAG: String?)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        binding = DialogNewCryptoBinding.inflate(LayoutInflater.from(context))

        listener = context as? AddCryptoDialogListener
            ?: throw RuntimeException("Activity must implement the AddCityDialogListener interface!")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.new_crypto)
            .setView(binding.root)
            .setPositiveButton(R.string.ok) { _, _ ->
                listener.onCryptoAdded(
                    binding.NewCryptoDialogEditText.text.toString()
                )
            }
            .setNegativeButton(R.string.cancel, null)
            .create()
    }
}