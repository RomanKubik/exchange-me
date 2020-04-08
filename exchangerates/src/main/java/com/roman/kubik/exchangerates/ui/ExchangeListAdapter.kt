package com.roman.kubik.exchangerates.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.roman.kubik.core.util.CurrencyUtils
import com.roman.kubik.exchangerates.R
import com.roman.kubik.exchangerates.domain.model.CurrencyRate
import kotlinx.android.synthetic.main.item_currency.view.*

class ExchangeListAdapter :
    ListAdapter<CurrencyRate, ExchangeListAdapter.ExchangeListViewHolder>(
        object : DiffUtil.ItemCallback<CurrencyRate>() {
            override fun areItemsTheSame(oldItem: CurrencyRate, newItem: CurrencyRate): Boolean {
                return oldItem.currencyName == newItem.currencyName
            }

            override fun areContentsTheSame(oldItem: CurrencyRate, newItem: CurrencyRate): Boolean {
                return oldItem.exchangeRate == newItem.exchangeRate
            }

        }
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExchangeListViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_currency, parent, false)
        return ExchangeListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExchangeListViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    class ExchangeListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(currencyRate: CurrencyRate) {
            itemView.currencyTitle.text = currencyRate.currencyName
            itemView.currencyName.setText(CurrencyUtils.getCurrencyName(currencyRate.currencyName))
            itemView.currencyIcon.setImageResource(CurrencyUtils.getCurrencyFlag(currencyRate.currencyName))
            itemView.amount.setText(CurrencyUtils.formatDecimal(currencyRate.exchangeRate))
        }
    }
}