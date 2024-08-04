package org.mifos.mobile.navigation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import org.mifos.mobile.core.common.Constants.INTIAL_LOGIN
import org.mifos.mobile.core.model.enums.ChargeType
import org.mifos.mobile.feature.about.navigation.aboutUsNavGraph
import org.mifos.mobile.feature.about.navigation.navigateToAboutUsScreen
import org.mifos.mobile.feature.auth.navigation.authenticationNavGraph
import org.mifos.mobile.feature.client_charge.navigation.clientChargeNavGraph
import org.mifos.mobile.feature.client_charge.navigation.navigateToClientChargeScreen
import org.mifos.mobile.feature.beneficiary.navigation.BeneficiaryNavigation
import org.mifos.mobile.feature.beneficiary.navigation.beneficiaryNavGraph
import org.mifos.mobile.feature.guarantor.navigation.guarantorNavGraph
import org.mifos.mobile.feature.guarantor.navigation.navigateToGuarantorList
import org.mifos.mobile.feature.help.navigation.helpNavGraph
import org.mifos.mobile.feature.help.navigation.navigateToHelpScreen
import org.mifos.mobile.feature.home.navigation.HomeDestinations
import org.mifos.mobile.feature.home.navigation.homeNavGraph
import org.mifos.mobile.feature.loan.navigation.loanNavGraph
import org.mifos.mobile.feature.location.navigation.locationsNavGraph
import org.mifos.mobile.feature.location.navigation.navigateToLocationsScreen
import org.mifos.mobile.feature.notification.navigation.navigateToNotificationScreen
import org.mifos.mobile.feature.notification.navigation.notificationNavGraph
import org.mifos.mobile.feature.recent_transaction.navigation.navigateToRecentTransaction
import org.mifos.mobile.feature.recent_transaction.navigation.recentTransactionNavGraph
import org.mifos.mobile.feature.settings.navigation.navigateToSettings
import org.mifos.mobile.feature.settings.navigation.settingsNavGraph
import org.mifos.mobile.feature.third.party.transfer.third_party_transfer.navigation.navigateToThirdPartyTransfer
import org.mifos.mobile.feature.third.party.transfer.third_party_transfer.navigation.thirdPartyTransferNavGraph
import org.mifos.mobile.feature.transfer.process.navigation.transferProcessNavGraph
import org.mifos.mobile.feature.update_password.navigation.navigateToUpdatePassword
import org.mifos.mobile.feature.update_password.navigation.updatePasswordNavGraph
import org.mifos.mobile.feature.user_profile.navigation.navigateToUserProfile
import org.mifos.mobile.feature.user_profile.navigation.userProfileNavGraph
import org.mifos.mobile.feature.qr.navigation.QrNavigation
import org.mifos.mobile.feature.qr.navigation.qrNavGraph
import org.mifos.mobile.ui.activities.PassCodeActivity
import org.mifos.mobile.ui.activities.PrivacyPolicyActivity

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun RootNavGraph(
    navController: NavHostController,
    startDestination: String,
    nestedStartDestination: String,
    navigateBack: () -> Unit = {} // remove this as well in future
) {
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        authenticationNavGraph(
            navController = navController,
            navigateBack = { navController.popBackStack() },
            startDestination = nestedStartDestination,
            navigateToPasscodeScreen = { startPassCodeActivity(context = context) }
        )

        guarantorNavGraph(
            startDestination = nestedStartDestination,
            navController = navController,
            navigateBack = navigateBack,
        )

        loanNavGraph(
            startDestination = nestedStartDestination,
            navController = navController,
            navigateBack = { navController.popBackStack() },
            viewQr = { },
            viewGuarantor = navController::navigateToGuarantorList,
            viewCharges = navController::navigateToClientChargeScreen,
            makePayment = { }
        )

        homeNavGraph(
            onNavigate = { handleHomeNavigation(navController, it) }
        )

        userProfileNavGraph(
            navigateBack = { navController.popBackStack() },
            navigateToChangePassword = navController::navigateToUpdatePassword
        )

        updatePasswordNavGraph(
            navigateBack = navController::popBackStack
        )

        thirdPartyTransferNavGraph(
            navigateBack = navController::popBackStack,
            addBeneficiary = { },
            reviewTransfer = { }
        )

        settingsNavGraph(
            navigateBack = navController::popBackStack,
            changePassword = navController::navigateToUpdatePassword,
            changePasscode = {},
            navigateToLoginScreen = {},
            languageChanged = {}
        )

        recentTransactionNavGraph(
            navigateBack = navController::popBackStack
        )

        notificationNavGraph(
            navigateBack = navController::popBackStack
        )

        locationsNavGraph()

        helpNavGraph(
            findLocations = navController::navigateToLocationsScreen,
            navigateBack = navController::popBackStack
        )

        clientChargeNavGraph(
            navigateBack = navController::popBackStack
        )

        aboutUsNavGraph(
            navigateToPrivacyPolicy = { startActivity(context, PrivacyPolicyActivity::class.java) },
            navigateToOssLicense = { startActivity(context, OssLicensesMenuActivity::class.java) }
        )

        transferProcessNavGraph (
            navigateBack = navController::popBackStack
        )

        beneficiaryNavGraph(
            navController = navController,
            navigateBack =  { navController.popBackStack() },
            startDestination = nestedStartDestination,
            openQrImportScreen = {
                navController.navigate(
                    QrNavigation.Import.route
                )
            },
            openQrReaderScreen = {
                navController.navigate(
                    QrNavigation.Reader.route
                )
            }
        )

        qrNavGraph(
            navController = navController,
            navigateBack = { navController.popBackStack() },
            startDestination = nestedStartDestination,
            qrData = null,
            openBeneficiaryApplication = {
                beneficiary, beneficiaryState ->
                navController.navigate(
                BeneficiaryNavigation.BeneficiaryApplication(
                        beneficiaryState, beneficiary
                    ).route
                )
            }
        )
    }
}

fun handleHomeNavigation(
    navController: NavHostController,
    homeDestinations: HomeDestinations
) {
    when (homeDestinations) {
        HomeDestinations.HOME -> Unit
        HomeDestinations.ACCOUNTS -> TODO()
        HomeDestinations.LOAN_ACCOUNT -> TODO()
        HomeDestinations.SAVINGS_ACCOUNT -> TODO()
        HomeDestinations.RECENT_TRANSACTIONS -> navController.navigateToRecentTransaction()
        HomeDestinations.CHARGES -> navController.navigateToClientChargeScreen(ChargeType.CLIENT)
        HomeDestinations.THIRD_PARTY_TRANSFER -> navController.navigateToThirdPartyTransfer()
        HomeDestinations.SETTINGS -> navController.navigateToSettings()
        HomeDestinations.ABOUT_US -> navController.navigateToAboutUsScreen()
        HomeDestinations.HELP -> navController.navigateToHelpScreen()
        HomeDestinations.SHARE -> TODO()
        HomeDestinations.APP_INFO -> TODO()
        HomeDestinations.LOGOUT -> TODO()
        HomeDestinations.TRANSFER -> TODO()
        HomeDestinations.BENEFICIARIES -> TODO()
        HomeDestinations.SURVEY -> Unit
        HomeDestinations.NOTIFICATIONS -> navController.navigateToNotificationScreen()
        HomeDestinations.PROFILE -> navController.navigateToUserProfile()
    }
}

fun <T : Activity> startActivity(context: Context, clazz: Class<T>) {
    context.startActivity(Intent(context, clazz))
}

private fun startPassCodeActivity(context: Context) {
    val intent = Intent(context, PassCodeActivity::class.java)
    intent.putExtra(INTIAL_LOGIN, true)
    context.startActivity(intent)
}