package hw.project.cryptoapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hw.project.cryptoapp.data.CryptoCoin
import hw.project.cryptoapp.databinding.ItemCryptoListBinding

class CryptoAdapter(private val listener: CryptoItemClickListener) :
    RecyclerView.Adapter<CryptoAdapter.CryptoViewHolder>(){

    private val items = mutableListOf<CryptoCoin>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CryptoViewHolder(
        ItemCryptoListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: CryptoViewHolder, position: Int) {
        val cryptoItem = items[position]
        holder.binding.tvName.text = cryptoItem.name
        holder.binding.tvTag.text = cryptoItem.tag
        holder.binding.tvPrice.text = "${cryptoItem.price}"

        holder.binding.cbIsSelected.setOnCheckedChangeListener { buttonView, isChecked ->
            cryptoItem.isChecked = isChecked
            listener.onItemChanged(cryptoItem)
        }
    }

    fun addItem(item: CryptoCoin) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun update(cryptoItems: List<CryptoCoin>) {
        items.clear()
        items.addAll(cryptoItems)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    interface CryptoItemClickListener {
        fun onItemChanged(item: CryptoCoin)
    }

    inner class CryptoViewHolder(val binding: ItemCryptoListBinding) : RecyclerView.ViewHolder(binding.root)
}