package org.mifos.mobile.ui.loan_account_application

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.mifos.mobile.R
import org.mifos.mobile.core.ui.theme.MifosMobileTheme
import org.mifos.mobile.models.accounts.loan.LoanAccount
import org.mifos.mobile.models.accounts.loan.LoanWithAssociations
import org.mifos.mobile.models.payload.LoansPayload
import org.mifos.mobile.ui.activities.base.BaseActivity
import org.mifos.mobile.ui.enums.LoanState
import org.mifos.mobile.ui.loan_review.ReviewLoanApplicationFragment.Companion.newInstance
import org.mifos.mobile.ui.fragments.base.BaseFragment
import org.mifos.mobile.utils.*
import org.mifos.mobile.utils.ParcelableAndSerializableUtils.getCheckedParcelable
import org.mifos.mobile.utils.ParcelableAndSerializableUtils.getCheckedSerializable

/**
 * Created by Rajan Maurya on 06/03/17.
 */
@AndroidEntryPoint
class LoanApplicationFragment : BaseFragment() {

    private val viewModel: LoanApplicationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as? BaseActivity)?.hideToolbar()

        if (arguments != null) {
            viewModel.loanState = arguments?.getCheckedSerializable(LoanState::class.java, Constants.LOAN_STATE) as? LoanState ?: LoanState.CREATE
            if (viewModel.loanState == LoanState.UPDATE) {
                viewModel.loanWithAssociations = arguments?.getCheckedParcelable(LoanWithAssociations::class.java, Constants.LOAN_ACCOUNT)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        viewModel.loadLoanTemplate()
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MifosMobileTheme {
                    LoanApplicationScreen(
                        viewModel = viewModel,
                        navigateBack = { activity?.finish() },
                        review = { onReviewLoanApplication() }
                    )
                }
            }
        }
    }

    private fun onReviewLoanApplication() {
        if (viewModel.loanState == LoanState.CREATE) reviewNewLoanApplication()
        else submitUpdateLoanApplication()
    }

    private fun reviewNewLoanApplication() {
        val loansPayload = LoansPayload()
        loansPayload.clientId = viewModel.loanTemplate?.clientId
        loansPayload.loanPurpose = viewModel.loanApplicationScreenData.value.selectedLoanPurpose
        loansPayload.productName = viewModel.loanApplicationScreenData.value.selectedLoanProduct
        loansPayload.currency = viewModel.loanApplicationScreenData.value.currencyLabel
        if (viewModel.purposeId > 0) loansPayload.loanPurposeId = viewModel.purposeId
        loansPayload.productId = viewModel.productId
        loansPayload.principal = viewModel.loanApplicationScreenData.value.principalAmount?.toDouble() ?: 0.0
        loansPayload.loanTermFrequency = viewModel.loanTemplate?.termFrequency
        loansPayload.loanTermFrequencyType = viewModel.loanTemplate?.interestRateFrequencyType?.id
        loansPayload.loanType = "individual"
        loansPayload.numberOfRepayments = viewModel.loanTemplate?.numberOfRepayments
        loansPayload.repaymentEvery = viewModel.loanTemplate?.repaymentEvery
        loansPayload.repaymentFrequencyType = viewModel.loanTemplate?.interestRateFrequencyType?.id
        loansPayload.interestRatePerPeriod = viewModel.loanTemplate?.interestRatePerPeriod
        loansPayload.expectedDisbursementDate = DateHelper
            .getSpecificFormat(DateHelper.FORMAT_dd_MMMM_yyyy, viewModel.loanApplicationScreenData.value.disbursementDate)
        loansPayload.submittedOnDate = DateHelper
            .getSpecificFormat(DateHelper.FORMAT_dd_MMMM_yyyy, viewModel.loanApplicationScreenData.value.submittedDate)
        loansPayload.transactionProcessingStrategyId = viewModel.loanTemplate?.transactionProcessingStrategyId
        loansPayload.amortizationType = viewModel.loanTemplate?.amortizationType?.id
        loansPayload.interestCalculationPeriodType = viewModel.loanTemplate?.interestCalculationPeriodType?.id
        loansPayload.interestType = viewModel.loanTemplate?.interestType?.id
        (activity as BaseActivity?)?.replaceFragment(
            newInstance(
                viewModel.loanState,
                loansPayload,
                getString(
                    R.string.string_and_string,
                    getString(R.string.new_loan_application) + " ",
                    viewModel.loanApplicationScreenData.value.clientName ?: "",
                ),
                getString(
                    R.string.string_and_string,
                    getString(R.string.account_number) + " ",
                    viewModel.loanApplicationScreenData.value.accountNumber ?: "",
                ),
            ),
            true,
            R.id.container,
        )
    }

    private fun submitUpdateLoanApplication() {
        val loansPayload = LoansPayload()
        loansPayload.principal =
            viewModel.loanApplicationScreenData.value.principalAmount?.toDouble() ?: 0.0
        loansPayload.productId = viewModel.productId
        loansPayload.loanPurpose = viewModel.loanApplicationScreenData.value.selectedLoanPurpose
        loansPayload.productName = viewModel.loanApplicationScreenData.value.selectedLoanProduct
        loansPayload.currency = viewModel.loanApplicationScreenData.value.currencyLabel
        if (viewModel.purposeId > 0) loansPayload.loanPurposeId = viewModel.purposeId
        loansPayload.loanTermFrequency = viewModel.loanTemplate?.termFrequency
        loansPayload.loanTermFrequencyType = viewModel.loanTemplate?.interestRateFrequencyType?.id
        loansPayload.numberOfRepayments = viewModel.loanTemplate?.numberOfRepayments
        loansPayload.repaymentEvery = viewModel.loanTemplate?.repaymentEvery
        loansPayload.repaymentFrequencyType = viewModel.loanTemplate?.interestRateFrequencyType?.id
        loansPayload.interestRatePerPeriod = viewModel.loanTemplate?.interestRatePerPeriod
        loansPayload.interestType = viewModel.loanTemplate?.interestType?.id
        loansPayload.interestCalculationPeriodType =
            viewModel.loanTemplate?.interestCalculationPeriodType?.id
        loansPayload.amortizationType = viewModel.loanTemplate?.amortizationType?.id
        loansPayload.transactionProcessingStrategyId =
            viewModel.loanTemplate?.transactionProcessingStrategyId
        loansPayload.expectedDisbursementDate = DateHelper
            .getSpecificFormat(DateHelper.FORMAT_dd_MMMM_yyyy, viewModel.loanApplicationScreenData.value.disbursementDate)
        (activity as BaseActivity?)?.replaceFragment(
            newInstance(
                viewModel.loanState,
                loansPayload,
                viewModel.loanWithAssociations?.id?.toLong(),
                getString(
                    R.string.string_and_string,
                    getString(R.string.new_loan_application) + " ",
                    viewModel.loanApplicationScreenData.value.clientName ?: "",
                ),
                getString(
                    R.string.string_and_string,
                    getString(R.string.account_number) + " ",
                    viewModel.loanApplicationScreenData.value.accountNumber ?: "",
                ),
            ),
            true,
            R.id.container,
        )
    }

    companion object {
        /**
         * Used when we want to apply for a Loan
         *
         * @param loanState [LoanState] is set to `LoanState.CREATE`
         * @return Instance of [LoanApplicationFragment]
         */
        fun newInstance(loanState: LoanState?): LoanApplicationFragment {
            val fragment = LoanApplicationFragment()
            val args = Bundle()
            args.putSerializable(Constants.LOAN_STATE, loanState)
            fragment.arguments = args
            return fragment
        }

        /**
         * Used when we want to update a Loan Application
         *
         * @param loanState            [LoanState] is set to `LoanState.UPDATE`
         * @param loanWithAssociations [LoanAccount] to modify
         * @return Instance of [LoanApplicationFragment]
         */
        fun newInstance(
            loanState: LoanState?,
            loanWithAssociations: LoanWithAssociations?,
        ): LoanApplicationFragment {
            val fragment = LoanApplicationFragment()
            val args = Bundle()
            args.putSerializable(Constants.LOAN_STATE, loanState)
            args.putParcelable(Constants.LOAN_ACCOUNT, loanWithAssociations)
            fragment.arguments = args
            return fragment
        }
    }
}
