package org.mifos.mobile.ui.activities

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import org.mifos.mobile.core.common.Constants
import org.mifos.mobile.core.ui.theme.MifosMobileTheme
import org.mifos.mobile.feature.guarantor.navigation.GuarantorNavigation
import org.mifos.mobile.feature.guarantor.navigation.GuarantorRoute
import org.mifos.mobile.feature.loan.navigation.LoanNavigation
import org.mifos.mobile.feature.loan.navigation.LoanRoute
import org.mifos.mobile.navigation.RootNavGraph
import org.mifos.mobile.ui.activities.base.BaseActivity


/*
~This project is licensed under the open source MPL V2.
~See https://github.com/openMF/self-service-app/blob/master/LICENSE.md
*/
class LoanAccountContainerActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val loanId = intent.getLongExtra(Constants.LOAN_ID, -1)
        enableEdgeToEdge()
        setContent {
            MifosMobileTheme {
                val navController = rememberNavController()
                RootNavGraph(
                    startDestination = LoanNavigation.LoanBase.route,
                    navController = navController,
                    nestedStartDestination = LoanNavigation.LoanDetail.passArguments(loanId = loanId),
                    navigateBack = { finish() }
                )
            }
        }
    }
}
