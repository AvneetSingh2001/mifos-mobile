package org.mifos.mobile.ui.savings_make_transfer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.mifos.mobile.R
import org.mifos.mobile.core.ui.component.mifosComposeView
import org.mifos.mobile.models.payload.TransferPayload
import org.mifos.mobile.ui.activities.base.BaseActivity
import org.mifos.mobile.ui.enums.TransferType
import org.mifos.mobile.ui.fragments.TransferProcessFragment
import org.mifos.mobile.ui.fragments.base.BaseFragment
import org.mifos.mobile.utils.Constants
import org.mifos.mobile.utils.DateHelper
import org.mifos.mobile.utils.getTodayFormatted


@AndroidEntryPoint
class SavingsMakeTransferComposeFragment : BaseFragment() {

    private val viewModel: SavingsMakeTransferViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as? BaseActivity)?.hideToolbar()
        if (arguments != null) {
            viewModel.initArgs(
                accountId = arguments?.getLong(Constants.ACCOUNT_ID),
                transferType =  arguments?.getString(Constants.TRANSFER_TYPE),
                outstandingBalance = arguments?.getDouble(Constants.OUTSTANDING_BALANCE)
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return mifosComposeView(requireContext()) {
            SavingsMakeTransferScreen(
                navigateBack = { activity?.onBackPressedDispatcher?.onBackPressed() },
                onCancelledClicked = { activity?.onBackPressedDispatcher?.onBackPressed() },
                reviewTransfer = { reviewTransfer(payload = it) }
            )
        }
    }

    private fun reviewTransfer(payload: ReviewTransferPayload) {
        val transferPayload = TransferPayload()
        val template = viewModel.savingsMakeTransferUiData.value.accountOptionsTemplate
        transferPayload.fromAccountId = payload.payFromAccount?.accountId
        transferPayload.fromClientId = payload.payFromAccount?.clientId
        transferPayload.fromAccountType = payload.payFromAccount?.accountType?.id
        transferPayload.fromOfficeId =  payload.payFromAccount?.officeId
        transferPayload.toOfficeId =  payload.payFromAccount?.officeId
        transferPayload.toAccountId = payload.payToAccount?.accountId
        transferPayload.toClientId = payload.payToAccount?.clientId
        transferPayload.toAccountType = payload.payToAccount?.accountType?.id
        transferPayload.transferDate =  DateHelper.getSpecificFormat(DateHelper.FORMAT_dd_MMMM_yyyy, getTodayFormatted(),)
        transferPayload.transferAmount = payload.amount.toDoubleOrNull()
        transferPayload.transferDescription = payload.review
        transferPayload.fromAccountNumber = payload.payFromAccount?.accountNo
        transferPayload.toAccountNumber = payload.payToAccount?.accountNo
        (activity as BaseActivity?)?.replaceFragment(
            TransferProcessFragment.newInstance(transferPayload, TransferType.SELF),
            true,
            R.id.container,
        )
    }


    companion object {
        /**
         * Provides an instance of [SavingsMakeTransferFragment], use `transferType` as
         * `Constants.TRANSFER_PAY_TO` when we want to deposit and
         * `Constants.TRANSFER_PAY_FROM` when we want to make a transfer
         *
         * @param accountId    Saving account Id
         * @param transferType Type of transfer i.e. `Constants.TRANSFER_PAY_TO` or
         * `Constants.TRANSFER_PAY_FROM`
         * @return Instance of [SavingsMakeTransferFragment]
         */
        fun newInstance(accountId: Long?, transferType: String?): SavingsMakeTransferComposeFragment {
            val transferFragment = SavingsMakeTransferComposeFragment()
            val args = Bundle()
            if (accountId != null) args.putLong(Constants.ACCOUNT_ID, accountId)
            args.putString(Constants.TRANSFER_TYPE, transferType)
            transferFragment.arguments = args
            return transferFragment
        }

        fun newInstance(
            accountId: Long?,
            outstandingBalance: Double?,
            transferType: String?,
        ): SavingsMakeTransferComposeFragment {
            val transferFragment = SavingsMakeTransferComposeFragment()
            val args = Bundle()
            if (accountId != null) args.putLong(Constants.ACCOUNT_ID, accountId)
            args.putString(Constants.TRANSFER_TYPE, transferType)
            if (outstandingBalance != null) {
                args.putDouble(
                    Constants.OUTSTANDING_BALANCE,
                    outstandingBalance,
                )
            }
            args.putBoolean(Constants.LOAN_REPAYMENT, true)
            transferFragment.arguments = args
            return transferFragment
        }
    }
}
