package org.mifos.mobile.ui.recent_transactions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.mifos.mobile.R
import org.mifos.mobile.core.ui.component.mifosComposeView
import org.mifos.mobile.core.ui.theme.MifosMobileTheme
import org.mifos.mobile.ui.fragments.base.BaseFragment

/**
 * @author Vishwwajeet
 * @since 09/08/16
 */
@AndroidEntryPoint
class RecentTransactionsFragment : BaseFragment() {

    private val recentTransactionViewModel: RecentTransactionViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        setToolbarTitle(getString(R.string.recent_transactions))
        return mifosComposeView(requireContext()) {
            RecentTransactionScreen(
                recentTransactionViewModel,
                navigateBack = { activity?.onBackPressedDispatcher?.onBackPressed() })
        }
    }

    companion object {
        fun newInstance(): RecentTransactionsFragment {
            val fragment = RecentTransactionsFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
