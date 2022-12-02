package hw.project.cryptoapp


import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import hw.project.cryptoapp.databinding.DialogAddAssetBinding

class AddAssetDialogFragment : AppCompatDialogFragment() {

    private lateinit var binding: DialogAddAssetBinding
    private lateinit var listener: AddAssetDialogListener

    interface AddAssetDialogListener {
        fun onAssetAdded(value: Double)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        binding = DialogAddAssetBinding.inflate(LayoutInflater.from(context))

        listener = context as? AddAssetDialogListener
            ?: throw RuntimeException("Activity must implement the AddCityDialogListener interface!")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.adding_capital)
            .setView(binding.root)
            .setPositiveButton(R.string.ok) { _, _ ->
                listener.onAssetAdded(
                    binding.AddAssetDialogEditText.text.toString().toDouble()
                )
            }
            .setNegativeButton(R.string.cancel, null)
            .create()
    }
}