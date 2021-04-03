package com.example.ditest.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ditest.R
import com.example.ditest.domain.entity.Currency
import com.example.ditest.inflate
import kotlinx.android.synthetic.main.currency_item.view.*

class CurrenciesAdapter(private val items: List<Currency>) : RecyclerView.Adapter<CurrencyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder =
        CurrencyViewHolder(parent)

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bind(items[position])
    }
}

class CurrencyViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.currency_item)) {

    fun bind(item: Currency) {
        with(itemView) {
            code.text = item.charCode
            name.text = item.name
            previousValue.text = item.previous.toString()
            currentValue.text = item.value.toString()
        }
    }
}