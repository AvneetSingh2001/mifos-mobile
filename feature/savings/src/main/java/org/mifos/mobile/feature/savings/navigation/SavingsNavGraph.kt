package org.mifos.mobile.feature.savings.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import org.mifos.mobile.core.model.entity.accounts.savings.SavingsWithAssociations
import org.mifos.mobile.feature.savings.savings_account.SavingsAccountDetailScreen
import org.mifos.mobile.feature.savings.savings_account_application.SavingsAccountApplicationScreen
import org.mifos.mobile.feature.savings.savings_account_transaction.SavingsAccountTransactionScreen
import org.mifos.mobile.feature.savings.savings_account_withdraw.SavingsAccountWithdrawScreen
import org.mifos.mobile.feature.savings.savings_make_transfer.SavingsMakeTransferScreen

fun NavGraphBuilder.savingsNavGraph(
    startDestination: String,
    navigateBack: () -> Unit
) {
    navigation(
        startDestination = startDestination,
        route = SavingsNavigation.SavingsBase.route,
    ) {
        savingsDetailRoute(
        )

        savingsApplication(
            navigateBack = navigateBack,
        )

        savingsTransaction(
            navigateBack = navigateBack,
        )

        savingsWithdraw(
            navigateBack = { navigateBack() }
        )

        savingsMakeTransfer(
            navigateBack = navigateBack,
        )
    }
}

fun NavGraphBuilder.savingsDetailRoute(
    navigateBack: () -> Unit,
    updateSavingsAccount: (SavingsWithAssociations?) -> Unit,
    withdrawSavingsAccount: (SavingsWithAssociations?) -> Unit,
    makeTransfer: (Boolean) -> Unit,
    viewTransaction: () -> Unit,
    viewCharges: () -> Unit,
    viewQrCode: (SavingsWithAssociations) -> Unit,
    callUs: () -> Unit,
    deposit: (Boolean) -> Unit
) {
    composable(
        route = SavingsNavigation.SavingsDetail.route,
    ) {
        SavingsAccountDetailScreen(
            navigateBack = navigateBack,
            updateSavingsAccount = updateSavingsAccount,
            withdrawSavingsAccount = withdrawSavingsAccount,
            makeTransfer = makeTransfer,
            viewTransaction = viewTransaction,
            viewCharges = viewCharges,
            viewQrCode = viewQrCode,
            callUs = callUs,
            deposit = deposit,
        )
    }
}

fun NavGraphBuilder.savingsApplication(
    navigateBack: () -> Unit
) {
    composable(
        route = SavingsNavigation.SavingsApplication.route,
    ) {
        SavingsAccountApplicationScreen(
            navigateBack = navigateBack,
            submit = { /* Implement submit logic here */ },
            retryConnection = { /* Implement retry logic here */ }
        )
    }
}

fun NavGraphBuilder.savingsTransaction(
    navigateBack: () -> Unit
) {
    composable(
        route = SavingsNavigation.SavingsTransaction.route,
    ) {
        SavingsAccountTransactionScreen(
            navigateBack = navigateBack,
            retryConnection = { /* Implement retry logic here */ },
            filterList = { /* Implement filter list logic here */ }
        )
    }
}

fun NavGraphBuilder.savingsWithdraw(
    navigateBack: (withdrawSuccess: Boolean) -> Unit
) {
    composable(
        route = SavingsNavigation.SavingsWithdraw.route
    ) {
        SavingsAccountWithdrawScreen(
            navigateBack = navigateBack,
            withdraw = { /* Implement withdraw logic here */ }
        )
    }
}

fun NavGraphBuilder.savingsMakeTransfer(
    navigateBack: () -> Unit
) {
    composable(
        route = SavingsNavigation.SavingsMakeTransfer.route
    ) {
        SavingsMakeTransferScreen(
            navigateBack = navigateBack,
            onCancelledClicked = { /* Implement cancel logic here */ },
            reviewTransfer = { /* Implement review transfer logic here */ }
        )
    }
}
