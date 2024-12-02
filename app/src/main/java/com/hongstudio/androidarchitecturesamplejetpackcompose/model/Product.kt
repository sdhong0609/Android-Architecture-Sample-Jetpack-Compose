package com.hongstudio.androidarchitecturesamplejetpackcompose.model

data class Product(
    val name: String,
    val price: Double
) {
    val discountedPrice = if (price >= 1000) {
        price * 0.9 // 10% 할인 적용
    } else {
        price // 할인 없음
    }
}