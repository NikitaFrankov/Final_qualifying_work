package com.gervant08.finalqualifyingwork.ui.main.basket

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.gervant08.finalqualifyingwork.R
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.gervant08.finalqualifyingwork.model.data.MenuBasket
import com.gervant08.finalqualifyingwork.model.data.BasketItem
import com.gervant08.finalqualifyingwork.model.data.NavigateLiveData
import com.gervant08.finalqualifyingwork.model.tools.BasketItemAnimator

class BasketFragment : Fragment(R.layout.fragment_basket) {
    private lateinit var recyclerView: RecyclerView
    private val viewModel: BasketViewModel by viewModels()
    private lateinit var orderButton: Button
    private lateinit var orderAmountTextView: TextView
    private val itemAnimator = BasketItemAnimator()
    private val adapter =
        BasketAdapter { basketItem ->  deleteItemFromBasket(basketItem)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MenuBasket.dishesList.observe(this, this::onDishesInBasketChange)
        initBasketDishesList()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.rvBasketItems)
        recyclerView.adapter = adapter
        recyclerView.itemAnimator = itemAnimator
        orderButton = view.findViewById(R.id.basketOrderButton)
        orderButton.setOnClickListener { goToOrderPage() }
        orderAmountTextView = view.findViewById(R.id.basketOrderAmount)
        orderAmountTextView.text = viewModel.calculatingOrderAmount(MenuBasket.dishesList.value!!).toString()
    }

    private fun initBasketDishesList() {
        adapter.updateDishesList(MenuBasket.dishesList.value!!)
    }

    private fun goToOrderPage() {
        if (MenuBasket.dishesList.value!!.size > 0){
            NavigateLiveData.filledBasketLiveData.value = true
        }
    }

    private fun deleteItemFromBasket(basketItem: BasketItem){
        viewModel.deleteItemFromBasket(basketItem)
        orderAmountTextView.text = viewModel.calculatingOrderAmount(MenuBasket.dishesList.value!!).toString()
    }

    private fun onDishesInBasketChange(dishesList: ArrayList<BasketItem>) {
        adapter.updateDishesList(dishesList)
    }


}