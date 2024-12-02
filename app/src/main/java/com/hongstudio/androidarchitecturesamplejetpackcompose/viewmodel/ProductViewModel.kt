package com.hongstudio.androidarchitecturesamplejetpackcompose.viewmodel

import androidx.lifecycle.ViewModel
import com.hongstudio.androidarchitecturesamplejetpackcompose.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProductViewModel : ViewModel() {

    private val _product: MutableStateFlow<ProductUiModel?> = MutableStateFlow(null)
    val product = _product.asStateFlow()

    private val _errorMessage: MutableStateFlow<String?> = MutableStateFlow(null)
    val errorMessage = _errorMessage.asStateFlow()

    fun onCalculateButtonClick(name: String, price: Double?) {
        if (name.isBlank() || price == null || price <= 0) {
            _errorMessage.value = "유효한 상품명과 가격을 입력하세요."
            return
        }

        // 비즈니스 로직 호출: 할인 금액 계산
        val product = Product(name, price)
        val discountedPrice = product.discountedPrice
        // 그 후 상태값 변경
        _product.update { ProductUiModel(name, discountedPrice) }
    }
}