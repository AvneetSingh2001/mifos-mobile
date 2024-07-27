package org.mifos.mobile.feature.loan.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import org.mifos.mobile.core.common.Constants
import org.mifos.mobile.feature.loan.loan_account.LoanAccountDetailScreen
import org.mifos.mobile.feature.loan.loan_account_application.LoanApplicationScreen
import org.mifos.mobile.feature.loan.loan_account_summary.LoanAccountSummaryScreen
import org.mifos.mobile.feature.loan.loan_account_transaction.LoanAccountTransactionScreen
import org.mifos.mobile.feature.loan.loan_account_withdraw.LoanAccountWithdrawScreen
import org.mifos.mobile.feature.loan.loan_repayment_schedule.LoanRepaymentScheduleScreen
import org.mifos.mobile.feature.loan.loan_review.ReviewLoanApplicationScreen

fun NavGraphBuilder.loanNavGraph(
    startDestination: String,
    navController: NavHostController,
    navigateBack: () -> Unit,
    viewGuarantor: () -> Unit,
    viewCharges: () -> Unit,
    viewRepaymentSchedule: () -> Unit,
    viewTransactions: () -> Unit,
    viewQr: () -> Unit,
    makePayment: () -> Unit
) {
    navigation(
        startDestination = startDestination,
        route = LoanRoute.LOAN_NAVIGATION_ROUTE
    ) {
        loanDetailRoute(
            navigateBack = navigateBack,
            viewGuarantor = viewGuarantor,
            updateLoan = {  },
            withdrawLoan = {  },
            viewLoanSummary = {  },
            viewCharges = viewCharges,
            viewRepaymentSchedule = viewRepaymentSchedule,
            viewTransactions = viewTransactions,
            viewQr = viewQr,
            makePayment = makePayment
        )

        loanApplication(
            navigateBack = navigateBack,
            reviewNewLoanApplication = {  },
            submitUpdateLoanApplication = {  }
        )

        loanSummary(
            navigateBack = navigateBack
        )

        loanTransaction(
            navigateBack = navigateBack
        )

        loanWithdraw(
            navigateBack = navigateBack
        )

        loanRepaymentSchedule(
            navigateBack = navigateBack
        )

        loanReview(
            navigateBack = navigateBack
        )
    }
}
fun NavGraphBuilder.loanDetailRoute(
    navigateBack: () -> Unit,
    viewGuarantor: () -> Unit,
    updateLoan: () -> Unit,
    withdrawLoan: () -> Unit,
    viewLoanSummary: () -> Unit,
    viewCharges: () -> Unit,
    viewRepaymentSchedule: () -> Unit,
    viewTransactions: () -> Unit,
    viewQr: () -> Unit,
    makePayment: () -> Unit
) {
    composable(
        route = LoanNavigation.LoanDetail.route,
    ) {
        LoanAccountDetailScreen(
            navigateBack = navigateBack,
            viewGuarantor = viewGuarantor,
            updateLoan = updateLoan,
            withdrawLoan = withdrawLoan,
            viewLoanSummary = viewLoanSummary,
            viewCharges = viewCharges,
            viewRepaymentSchedule = viewRepaymentSchedule,
            viewTransactions = viewTransactions,
            viewQr = viewQr,
            makePayment = makePayment
        )
    }
}

fun NavGraphBuilder.loanApplication(
    navigateBack: () -> Unit,
    reviewNewLoanApplication: () -> Unit,
    submitUpdateLoanApplication: () -> Unit
) {
    composable(
        route = LoanNavigation.LoanApplication.route,
    ) {
        LoanApplicationScreen(
            navigateBack = navigateBack,
            reviewNewLoanApplication = reviewNewLoanApplication,
            submitUpdateLoanApplication = submitUpdateLoanApplication
        )
    }
}

fun NavGraphBuilder.loanSummary(
    navigateBack: () -> Unit,
) {
    composable(
        route = LoanNavigation.LoanSummary.route,
    ) {
        LoanAccountSummaryScreen(
            navigateBack = navigateBack
        )
    }
}

fun NavGraphBuilder.loanTransaction(
    navigateBack: () -> Unit,
) {
    composable(
        route = LoanNavigation.LoanTransaction.route,
    ) {
        LoanAccountTransactionScreen(
            navigateBack = navigateBack
        )
    }
}

fun NavGraphBuilder.loanWithdraw(
    navigateBack: () -> Unit
) {
    composable(
        route = LoanNavigation.LoanWithdraw.route
    ) {
        LoanAccountWithdrawScreen (
            navigateBack = navigateBack
        )
    }
}

fun NavGraphBuilder.loanRepaymentSchedule(
    navigateBack: () -> Unit
) {
    composable(
        route = LoanNavigation.LoanSchedule.route
    ) {
        LoanRepaymentScheduleScreen(
            navigateBack = navigateBack
        )
    }
}

fun NavGraphBuilder.loanReview(
    navigateBack: () -> Unit,
) {
    composable(
        route = LoanNavigation.LoanReview.route
    ) {
        ReviewLoanApplicationScreen(
            navigateBack = { navigateBack() },
        )
    }
}