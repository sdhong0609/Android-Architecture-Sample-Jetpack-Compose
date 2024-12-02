package com.hongstudio.androidarchitecturesamplejetpackcompose.view

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hongstudio.androidarchitecturesamplejetpackcompose.ui.theme.AndroidArchitectureSampleJetpackComposeTheme
import com.hongstudio.androidarchitecturesamplejetpackcompose.viewmodel.ProductUiModel
import com.hongstudio.androidarchitecturesamplejetpackcompose.viewmodel.ProductViewModel

@Composable
fun ProductScreen(
    modifier: Modifier,
    viewModel: ProductViewModel = viewModel()
) {
    val product = viewModel.product.collectAsStateWithLifecycle()
    val errorMessage = viewModel.errorMessage.collectAsStateWithLifecycle()

    val context = LocalContext.current
    LaunchedEffect(errorMessage) {
        errorMessage.value?.let {
            // 에러 메시지 표시
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    // 상태 변수 선언
    var name by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }

    // UI 레이아웃
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        // 상품 이름 입력 필드
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("상품 이름 입력") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // 상품 가격 입력 필드
        OutlinedTextField(
            value = price,
            onValueChange = { price = it },
            label = { Text("상품 가격 입력") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 할인 계산 버튼
        Button(
            onClick = {
                viewModel.onCalculateButtonClick(name, price.toDoubleOrNull())
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("할인 계산")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 결과 표시 텍스트
        ResultText(product.value)
    }
}

@Composable
fun ResultText(product: ProductUiModel?) {
    Text(
        text = if (product == null) {
            "결과가 여기에 표시됩니다."
        } else {
            "상품: ${product.name}, 할인된 가격: ${"%.2f".format(product.discountedPrice)}원"
        },
        modifier = Modifier.fillMaxWidth(),
        style = MaterialTheme.typography.bodyMedium
    )
}


@Preview(showBackground = true)
@Composable
private fun ProductScreenPreview() {
    AndroidArchitectureSampleJetpackComposeTheme {
        ProductScreen(modifier = Modifier)
    }
}