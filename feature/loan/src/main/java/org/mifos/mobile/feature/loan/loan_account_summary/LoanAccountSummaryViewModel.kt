package org.mifos.mobile.feature.loan.loan_account_summary

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.mifos.mobile.core.model.entity.accounts.loan.LoanWithAssociations
import org.mifos.mobile.feature.loan.loan_account.LoanAccountDetailUiState

@HiltViewModel
class LoanAccountSummaryViewModel: ViewModel() {

    private var _loanWithAssociations: MutableStateFlow<LoanWithAssociations?> = MutableStateFlow(null)
    val loanWithAssociations: StateFlow<LoanWithAssociations?> get() = _loanWithAssociations

    fun setArgs(loan: LoanWithAssociations?) {
        _loanWithAssociations.value = loan
    }
}