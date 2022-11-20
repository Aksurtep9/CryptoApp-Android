package hw.project.cryptoapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import hw.project.cryptoapp.TransactionDialogFragment
import hw.project.cryptoapp.data.CryptoCoin
import hw.project.cryptoapp.databinding.ItemCryptoListBinding

class CryptoAdapter(private val listener: CryptoItemClickListener, private val context: Context) :
    RecyclerView.Adapter<CryptoAdapter.CryptoViewHolder>(){

    private val items = mutableListOf<CryptoCoin>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CryptoViewHolder(
        ItemCryptoListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: CryptoViewHolder, position: Int) {
        val cryptoItem = items[position]
        holder.binding.tvName.text = cryptoItem.name
        holder.binding.tvTag.text = cryptoItem.tag
        holder.binding.tvPrice.text = String.format("%.3f $", cryptoItem.price)

        holder.binding.ibRemove.setOnClickListener {
            notifyItemRemoved(position)
            listener.onItemDeleted(items[position])
            items.removeAt(position)
        }

        Glide.with(context).load("https://s2.coinmarketcap.com/static/img/coins/64x64/${cryptoItem.apiID}.png")
            .transition(DrawableTransitionOptions().crossFade())
            .into(holder.binding.ivIcon)

        holder.binding.cbIsSelected.setOnClickListener{
            listener.onTransaction(cryptoItem)
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
        fun onItemDeleted(item: CryptoCoin)
        fun onTransaction(item: CryptoCoin)
    }

    inner class CryptoViewHolder(val binding: ItemCryptoListBinding) : RecyclerView.ViewHolder(binding.root)

}