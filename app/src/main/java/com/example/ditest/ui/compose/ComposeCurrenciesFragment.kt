package com.example.ditest.ui.compose

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.example.ditest.R
import com.example.ditest.domain.entity.Currency
import com.example.ditest.presentation.CurrenciesViewModel
import com.example.ditest.presentation.factory.injectViewModel
import com.example.ditest.presentation.state.CurrenciesState
import com.example.ditest.ui.base.BaseFragment
import javax.inject.Inject

class ComposeCurrenciesFragment : BaseFragment() {

    companion object {

        fun newInstance(): ComposeCurrenciesFragment =
            ComposeCurrenciesFragment()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewModel: CurrenciesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        ComposeView(requireContext()).apply {
            setContent {
                AppTheme {
                    CurrenciesScreen()
                }
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = injectViewModel(viewModelFactory)

        viewModel.loadCurrencies()
    }

    @Composable
    fun CurrenciesScreen() {
        Scaffold(
            topBar = { CurrenciesToolbar() },
        ) {

            val state = viewModel.state.collectAsState().value
            when (state) {
                is CurrenciesState.Content -> CurrencyList(currencies = state.currencyList)
                else -> Unit
            }
        }
    }

    @Composable
    fun CurrenciesToolbar() {
        TopAppBar(
            title = { Text(text = getString(R.string.app_name)) },
            backgroundColor = Color.Blue,
            contentColor = Color.White,
            elevation = 12.dp,
        )
    }

    @Composable
    fun CurrencyList(currencies: List<Currency>) {
        LazyColumn {
            items(items = currencies) { currency ->
                CurrencyItem(item = currency)
            }
        }
    }

    @Composable
    fun CurrencyItem(item: Currency) {
        Card(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            shape = shapes.small,
            elevation = 2.dp,
        ) {

            Column {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .padding(top = 8.dp)
                ) {
                    Text(
                        text = item.charCode,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                    )

                    Text(
                        text = item.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 4.dp),
                        fontSize = 14.sp,
                    )
                }

                Row(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .padding(top = 8.dp)
                ) {
                    Text(
                        text = getString(R.string.previous_value),
                        fontSize = 12.sp,
                    )

                    Text(
                        text = item.previous.toString(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 4.dp),
                        fontSize = 12.sp,
                    )
                }

                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = getString(R.string.current_value),
                        fontSize = 12.sp,
                    )

                    Text(
                        text = item.value.toString(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 4.dp),
                        fontSize = 12.sp,
                    )
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        AppTheme {
            CurrenciesScreen()
        }
    }
}