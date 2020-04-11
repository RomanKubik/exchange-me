package com.roman.kubik.exchangerates.ui

import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.roman.kubik.core.util.CurrencyUtils
import com.roman.kubik.exchangerates.R
import com.roman.kubik.exchangerates.domain.model.CurrencyRate
import kotlinx.android.synthetic.main.item_currency.view.*
import kotlin.collections.ArrayList

class ExchangeRatesAdapter(private val callback: ExchangeItemCallback) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val list = ArrayList<CurrencyRate>()

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) 0 else 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType != 0) {
            ExchangeHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_currency, parent, false)
            )
        } else {
            WorkAroundViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_workaround, parent, false)
            )
        }

    }

    override fun getItemCount(): Int = if (list.size == 0) 0 else list.size + 1

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position != 0)
            (holder as ExchangeHolder).bind(list[position - 1], callback)
    }

    fun submitList(list: List<CurrencyRate>) {
        val diffResult = DiffUtil.calculateDiff(RatesDiffCallback(this.list, list))
        this.list.clear()
        this.list.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class ExchangeHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(currencyRate: CurrencyRate, callback: ExchangeItemCallback) {
            itemView.currencyTitle.text = currencyRate.currency
            itemView.currencyName.setText(CurrencyUtils.getCurrencyName(currencyRate.currency))
            itemView.currencyIcon.setImageResource(CurrencyUtils.getCurrencyFlag(currencyRate.currency))

            if (currencyRate.baseCurrency != currencyRate.currency || itemView.amount.text.isEmpty()) {
                itemView.amount.setText(CurrencyUtils.formatDecimal(currencyRate.exchangeRate))
            }

            if (currencyRate.baseCurrency == currencyRate.currency && !itemView.amount.isFocused) {
                itemView.amount.requestFocus()
            }

            itemView.setOnClickListener {
                itemView.requestFocus()
                callback.onItemSelected(currencyRate)
            }

            itemView.amount.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    if (itemView.amount.isFocused) {
                        callback.onAmountEdited(currencyRate, s.toString().toDouble())
                    }
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

            })
            itemView.amount.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    callback.onAmountEdited(currencyRate, currencyRate.exchangeRate)
                }
            }
        }

    }

    class WorkAroundViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class RatesDiffCallback(
        private val oldList: List<CurrencyRate>,
        private val newList: List<CurrencyRate>
    ) :
        DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            if (oldItemPosition == 0 || newItemPosition == 0) return true
            return oldList[oldItemPosition - 1].currency == newList[newItemPosition - 1].currency
        }

        override fun getOldListSize(): Int {
            return oldList.size + 1
        }

        override fun getNewListSize(): Int {
            return newList.size + 1
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            if (oldItemPosition == 0 || newItemPosition == 0) return true
            return oldList[oldItemPosition - 1].hashCode() == newList[newItemPosition - 1].hashCode()
        }

    }
}