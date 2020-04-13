package com.roman.kubik.exchangerates.ui

import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.roman.kubik.core.ui.util.BaseTextWatcher
import com.roman.kubik.currency.CurrencyUtils
import com.roman.kubik.exchangerates.R
import com.roman.kubik.exchangerates.domain.model.CurrencyRate
import kotlinx.android.synthetic.main.item_currency.view.*
import kotlinx.android.synthetic.main.item_error.view.*
import java.math.BigDecimal

class ExchangeRatesAdapter(private val callback: ExchangeItemCallback) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TYPE_ERROR = 0
        const val TYPE_EXCHANGE_RATE = 1
    }

    @StringRes
    private var error: Int? = null
    private val list = ArrayList<CurrencyRate>()

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> TYPE_ERROR
            else -> TYPE_EXCHANGE_RATE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_ERROR -> ErrorViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_error, parent, false)
            )
            else -> ExchangeHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_currency, parent, false)
            )
        }
    }

    override fun getItemCount(): Int = list.size + 1

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            TYPE_ERROR -> (holder as ErrorViewHolder).bind(error)
            TYPE_EXCHANGE_RATE -> (holder as ExchangeHolder).bind(list[position - 1], callback)
        }
    }

    fun submitError(@StringRes error: Int?) {
        if (this.error != error) {
            this.error = error
            notifyItemChanged(0)
        }
    }

    fun isErrorShown() = error != null

    fun submitList(list: List<CurrencyRate>) {
        if (this.list.isEmpty()) {
            this.list.addAll(list)
            notifyDataSetChanged()
        } else {
            val diffResult = DiffUtil.calculateDiff(RatesDiffCallback(this.list, list))
            this.list.clear()
            this.list.addAll(list)
            diffResult.dispatchUpdatesTo(this)
        }
    }

    class ExchangeHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var watcher: RatesTextWatcher? = null

        fun bind(currencyRate: CurrencyRate, callback: ExchangeItemCallback) {
            itemView.amount.removeTextChangedListener(watcher)
            itemView.currencyTitle.text = currencyRate.currency.code
            itemView.currencyName.setText(CurrencyUtils.getCurrencyName(currencyRate.currency))
            itemView.currencyIcon.setImageResource(CurrencyUtils.getCurrencyFlag(currencyRate.currency))
            if (!itemView.amount.isFocused) {
                itemView.amount.setText(CurrencyUtils.formatDecimal(currencyRate.exchangeRate.toDouble()))
            }

            itemView.setOnClickListener {
                itemView.requestFocus()
                callback.onResponderChanged(currencyRate)
            }

            watcher = RatesTextWatcher(currencyRate, callback, itemView)
            itemView.amount.addTextChangedListener(watcher)

            itemView.amount.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    callback.onAmountEdited(currencyRate, currencyRate.exchangeRate)
                }
            }
        }

        class RatesTextWatcher(
            private val currencyRate: CurrencyRate,
            private val callback: ExchangeItemCallback,
            private val view: View
        ) :
            BaseTextWatcher() {
            override fun afterTextChanged(editable: Editable) {
                if (view.amount.isFocused) {
                    callback.onAmountEdited(
                        currencyRate,
                        editable.toString().toBigDecimalOrNull() ?: BigDecimal.ZERO
                    )
                }
            }
        }
    }

    class ErrorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val hideLayoutParams = ViewGroup.LayoutParams(0, 0)
        private val displayLayoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        fun bind(@StringRes error: Int?) {
            if (error == null) {
                if (itemView.layoutParams != hideLayoutParams) {
                    itemView.layoutParams = hideLayoutParams
                }
            } else {
                if (itemView.layoutParams != displayLayoutParams) {
                    itemView.layoutParams = displayLayoutParams
                    itemView.itemError.setText(error)
                }
            }
        }
    }

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