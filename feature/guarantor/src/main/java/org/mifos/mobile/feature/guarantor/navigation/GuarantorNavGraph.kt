package org.mifos.mobile.feature.guarantor.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import org.mifos.mobile.core.common.Constants
import org.mifos.mobile.feature.guarantor.screens.guarantor_add.AddGuarantorScreen
import org.mifos.mobile.feature.guarantor.screens.guarantor_details.GuarantorDetailScreen
import org.mifos.mobile.feature.guarantor.screens.guarantor_list.GuarantorListScreen
import org.mifos.mobile.core.common.Constants.INDEX
import org.mifos.mobile.core.common.Constants.LOAN_ID
import org.mifos.mobile.feature.guarantor.navigation.GuarantorRoute.GUARANTOR_NAVIGATION_ROUTE_BASE


fun NavController.navigateToGuarantorList(loanId: Long) {
    navigate("$GUARANTOR_NAVIGATION_ROUTE_BASE/$loanId")
}

fun NavGraphBuilder.guarantorNavGraph(
    startDestination: String,
    navController: NavHostController,
    navigateBack: () -> Unit,
) {
    navigation(
        startDestination = startDestination,
        route = GUARANTOR_NAVIGATION_ROUTE_BASE,
        arguments = listOf(navArgument(LOAN_ID) { type = NavType.LongType })
    ) {
        addGuarantorRoute(
            navigateBack = { navController.popBackStack() }
        )

        listGuarantorRoute(
            navigateBack = navigateBack,
            addGuarantor = { loanId ->
                navController.navigate(
                    GuarantorNavigation.GuarantorAdd.passArguments(
                        index = -1,
                        loanId = loanId
                    )
                )
            },
            onGuarantorClicked = { index, loanId ->
                navController.navigate(
                    GuarantorNavigation.GuarantorDetails.passArguments(
                        index = index,
                        loanId = loanId
                    )
                )
            }
        )

        detailGuarantorRoute(
            navigateBack = { navController.popBackStack() },
            updateGuarantor = { index, loanId ->
                navController.navigate(
                    GuarantorNavigation.GuarantorAdd.passArguments(
                        index = index,
                        loanId = loanId
                    )
                )
            }
        )
    }
}

fun NavGraphBuilder.listGuarantorRoute(
    navigateBack: () -> Unit,
    addGuarantor: (Long) -> Unit,
    onGuarantorClicked: (Int, Long) -> Unit
) {
    composable(
        route = GuarantorNavigation.GuarantorList.route,
        arguments = listOf(
            navArgument(name = LOAN_ID) { type = NavType.LongType }
        )
    ) {
        GuarantorListScreen(
            navigateBack = navigateBack,
            addGuarantor = addGuarantor,
            onGuarantorClicked = onGuarantorClicked
        )
    }
}

fun NavGraphBuilder.detailGuarantorRoute(
    navigateBack: () -> Unit,
    updateGuarantor: (index: Int, loanId: Long) -> Unit
) {
    composable(
        route = GuarantorNavigation.GuarantorDetails.route,
        arguments = listOf(
            navArgument(name = INDEX) { type = NavType.IntType },
            navArgument(name = LOAN_ID) { type = NavType.LongType }
        )
    ) {
        GuarantorDetailScreen(
            navigateBack = navigateBack,
            updateGuarantor = updateGuarantor
        )
    }
}

fun NavGraphBuilder.addGuarantorRoute(
    navigateBack: () -> Unit
) {
    composable(
        route = GuarantorNavigation.GuarantorAdd.route,
        arguments = listOf(
            navArgument(name = INDEX) { type = NavType.IntType; defaultValue = -1 },
            navArgument(name = LOAN_ID) { type = NavType.LongType }
        )
    ) {
        AddGuarantorScreen(
            navigateBack = navigateBack,
        )
    }
}

