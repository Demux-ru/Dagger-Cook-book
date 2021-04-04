package com.example.ditest.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.ditest.R
import com.example.ditest.domain.entity.Currency
import com.example.ditest.presentation.CurrenciesViewModel
import com.example.ditest.presentation.factory.ViewModelFactory
import com.example.ditest.presentation.factory.injectViewModel
import com.example.ditest.presentation.state.CurrenciesState
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.currencies_fragment.*
import javax.inject.Inject

class CurrenciesFragment : Fragment(), HasAndroidInjector {

	companion object {

		fun newInstance(): CurrenciesFragment =
			CurrenciesFragment()
	}

	@Inject
	lateinit var androidInjector: DispatchingAndroidInjector<Any>

	@Inject
	lateinit var viewModelFactory: ViewModelProvider.Factory

	lateinit var viewModel: CurrenciesViewModel

	override fun onAttach(context: Context) {
		AndroidSupportInjection.inject(this)
		super.onAttach(context)
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return inflater.inflate(R.layout.currencies_fragment, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		viewModel = injectViewModel(viewModelFactory)

		swipeRefresh.setOnRefreshListener { viewModel.refreshList() }

		viewModel.state.observe(viewLifecycleOwner, Observer { applyState(it) })
		viewModel.loadCurrencies()
	}

	private fun applyState(state: CurrenciesState) {
		when (state) {
			CurrenciesState.Loading    -> applyLoadingState()
			CurrenciesState.Refresh    -> applyRefreshState()
			is CurrenciesState.Content -> applyContentState(state.currencyList)
			CurrenciesState.Error      -> applyErrorState()
		}
	}

	private fun applyLoadingState() {
		swipeRefresh.isRefreshing = false
		swipeRefresh.isVisible = false
		progress.isVisible = true
	}

	private fun applyContentState(currencies: List<Currency>) {
		swipeRefresh.isRefreshing = false
		swipeRefresh.isVisible = true
		progress.isVisible = false

		currencyList.adapter = CurrenciesAdapter(currencies)
	}

	private fun applyRefreshState() {
		swipeRefresh.isRefreshing = true
		swipeRefresh.isVisible = true
		progress.isVisible = false
	}

	private fun applyErrorState() {
		swipeRefresh.isRefreshing = false
		swipeRefresh.isVisible = false
		progress.isVisible = false

		Toast.makeText(requireContext(), getString(R.string.error_message), Toast.LENGTH_SHORT).show()
	}

	override fun androidInjector(): AndroidInjector<Any> = androidInjector
}