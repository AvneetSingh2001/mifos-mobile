package org.mifos.mobile.feature.loan.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import org.mifos.mobile.core.common.Constants
import org.mifos.mobile.core.model.enums.ChargeType
import org.mifos.mobile.feature.loan.loan_account.LoanAccountDetailScreen
import org.mifos.mobile.feature.loan.loan_account_application.LoanApplicationScreen
import org.mifos.mobile.feature.loan.loan_account_summary.LoanAccountSummaryScreen
import org.mifos.mobile.feature.loan.loan_account_transaction.LoanAccountTransactionScreen
import org.mifos.mobile.feature.loan.loan_account_withdraw.LoanAccountWithdrawScreen
import org.mifos.mobile.feature.loan.loan_repayment_schedule.LoanRepaymentScheduleScreen
import org.mifos.mobile.feature.loan.loan_review.ReviewLoanApplicationScreen
import org.mifos.mobile.feature.loan.navigation.LoanRoute.LOAN_NAVIGATION_ROUTE_BASE


fun NavGraphBuilder.loanNavGraph(
    startDestination: String,
    navController: NavController,
    viewGuarantor: (Long) -> Unit,
    viewCharges: (ChargeType) -> Unit,
    viewQr: () -> Unit,
    makePayment: () -> Unit,
    navigateBack: () -> Unit
) {
    navigation(
        startDestination = startDestination,
        route = LoanNavigation.LoanBase.route,
        arguments = listOf(navArgument(Constants.LOAN_ID) { type = NavType.LongType; defaultValue = -1L })
    ) {
        loanDetailRoute(
            navigateBack = navigateBack,
            viewGuarantor = viewGuarantor,
            updateLoan = { navController.navigate(LoanNavigation.LoanApplication.route) },
            withdrawLoan = { navController.navigate(LoanNavigation.LoanWithdraw.route) },
            viewLoanSummary = { navController.navigate(LoanNavigation.LoanSummary.route) },
            viewCharges = { viewCharges(ChargeType.LOAN) },
            viewRepaymentSchedule = { navController.navigate(LoanNavigation.LoanSchedule.route) },
            viewTransactions = { navController.navigate(LoanNavigation.LoanTransaction.route) },
            viewQr = viewQr,
            makePayment = makePayment
        )

        loanApplication(
            navigateBack = { navController.popBackStack() },
            reviewNewLoanApplication = { navController.navigate(LoanNavigation.LoanReview.route) },
            submitUpdateLoanApplication = { navController.navigate(LoanNavigation.LoanReview.route) }
        )

        loanSummary(
            navigateBack = { navController.popBackStack() },
        )

        loanTransaction(
            navigateBack = { navController.popBackStack() },
        )

        loanWithdraw(
            navigateBack = { navController.popBackStack() },
        )

        loanRepaymentSchedule(
            navigateBack = { navController.popBackStack() },
        )

        loanReview(
            navigateBack = { navController.popBackStack() },
        )
    }
}

fun NavGraphBuilder.loanDetailRoute(
    navigateBack: () -> Unit,
    viewGuarantor: (Long) -> Unit,
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
        arguments = listOf(navArgument(Constants.LOAN_ID) { type = NavType.LongType; defaultValue = -1L })
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