package org.mifos.mobile.ui.activities

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.lifecycle.lifecycleScope
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import dagger.hilt.android.AndroidEntryPoint
import org.mifos.mobile.R
import org.mifos.mobile.core.common.Constants
import org.mifos.mobile.ui.activities.base.BaseActivity
import org.mifos.mobile.core.datastore.PreferencesHelper
import org.mifos.mobile.core.model.entity.client.Client
import org.mifos.mobile.utils.TextDrawable
import org.mifos.mobile.utils.Toaster
import org.mifos.mobile.utils.fcm.RegistrationIntentService
import org.mifos.mobile.ui.user_profile.UserProfileActivity
import org.mifos.mobile.core.common.utils.ParcelableAndSerializableUtils.getCheckedParcelable
import org.mifos.mobile.core.ui.theme.MifosMobileTheme
import org.mifos.mobile.feature.guarantor.navigation.GuarantorNavigation
import org.mifos.mobile.feature.guarantor.navigation.GuarantorRoute
import org.mifos.mobile.feature.home.navigation.HomeNavigation
import org.mifos.mobile.feature.user_profile.viewmodel.UserDetailUiState
import org.mifos.mobile.feature.user_profile.viewmodel.UserDetailViewModel
import org.mifos.mobile.navigation.RootNavGraph
import javax.inject.Inject
import org.mifos.mobile.ui.settings.SettingsActivity

/**
 * @author Vishwajeet
 * @since 14/07/2016
 */
@AndroidEntryPoint
class HomeActivity : BaseActivity() {

    @Inject
    var preferencesHelper: PreferencesHelper? = null
    private val viewModel: UserDetailViewModel by viewModels()
    private var isReceiverRegistered = false
    private var doubleBackToExitPressedOnce = false

    private lateinit var navHostController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (checkPlayServices() && preferencesHelper?.sentTokenToServerState() == false) {
            // Start IntentService to register this application with GCM.
            val intent = Intent(this, RegistrationIntentService::class.java)
            startService(intent)
        }

        enableEdgeToEdge()
        setContent {
            MifosMobileTheme {
                navHostController = rememberNavController()
                RootNavGraph(
                    startDestination = HomeNavigation.HomeBase.route,
                    navController = navHostController,
                    nestedStartDestination = HomeNavigation.HomeScreen.route,
                    navigateBack = { finish() }
                )
            }
        }
    }

    override fun onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(registerReceiver)
        isReceiverRegistered = false
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        if (!isReceiverRegistered) {
            LocalBroadcastManager.getInstance(this).registerReceiver(
                registerReceiver,
                IntentFilter(Constants.REGISTER_ON_SERVER),
            )
            isReceiverRegistered = true
        }
    }

//    /**
//     * Called whenever any item is selected in [NavigationView]
//     *
//     * @param item [MenuItem] which is selected by the user
//     */
//    override fun onNavigationItemSelected(item: MenuItem): Boolean {
//        // select which item to open
//        setToolbarElevation()
//        checkedItem = item.itemId
//        if (checkedItem != R.id.item_settings && checkedItem != R.id.item_share && checkedItem != R.id.item_about_us && checkedItem != R.id.item_help) {
//            // If we have clicked something other than settings or share
//            // we can safely clear the back stack as a new fragment will replace
//            // the current fragment.
//            clearFragmentBackStack()
//        }
//        when (item.itemId) {
//            R.id.item_home -> {
//                hideToolbarElevation()
//                replaceFragment(HomeOldFragment.newInstance(), true, R.id.container)
//            }
//
//            R.id.item_accounts -> {
//                hideToolbarElevation()
//                replaceFragment(
//                    ClientAccountsComposeFragment.newInstance(AccountType.SAVINGS),
//                    true,
//                    R.id.container,
//                )
//            }
//
//            R.id.item_recent_transactions -> replaceFragment(
//                RecentTransactionsComposeFragment.newInstance(),
//                true,
//                R.id.container,
//            )
//
//            R.id.item_charges -> replaceFragment(
//                ClientChargeComposeFragment.newInstance(clientId, ChargeType.CLIENT),
//                true,
//                R.id.container,
//            )
//
//            R.id.item_third_party_transfer -> replaceFragment(
//                ThirdPartyTransferComposeFragment.newInstance(),
//                true,
//                R.id.container,
//            )
//
//            R.id.item_beneficiaries -> replaceFragment(
//                BeneficiaryListComposeFragment.newInstance(),
//                true,
//                R.id.container,
//            )
//
//            R.id.item_settings -> startActivity(
//                Intent(
//                    this@HomeActivity,
//                    SettingsActivity::class.java,
//                ),
//            )
//
//            R.id.item_about_us -> startActivity(
//                Intent(
//                    this@HomeActivity,
//                    AboutUsActivity::class.java,
//                ),
//            )
//
//            R.id.item_help -> startActivity(Intent(this@HomeActivity, HelpActivity::class.java))
//            R.id.item_app_info -> {
//                val intent = Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
//                intent.data = Uri.parse("package:$packageName")
//                startActivity(intent)
//            }
//
//            R.id.item_share -> {
//                val i = Intent(Intent.ACTION_SEND)
//                i.type = "text/plain"
//                i.putExtra(
//                    Intent.EXTRA_TEXT,
//                    getString(
//                        R.string.playstore_link,
//                        getString(R.string.share_msg),
//                        application.packageName,
//                    ),
//                )
//                startActivity(Intent.createChooser(i, getString(R.string.choose)))
//            }
//
//            R.id.item_logout -> showLogoutDialog()
//        }
//
//        // close the drawer
//        return true
//    }

//    /**
//     * Asks users to confirm whether he want to logout or not
//     */
//    private fun showLogoutDialog() {
//        MaterialAlertDialogBuilder(this, R.style.RedDialog)
//            .setTitle(R.string.dialog_logout)
//            .setIcon(R.drawable.ic_logout)
//            .setPositiveButton(getString(R.string.logout)) { _, _ ->
//                preferencesHelper?.clear()
//                val i = Intent(this@HomeActivity, LoginActivity::class.java)
//                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                startActivity(i)
//                finish()
//                Toast.makeText(this, R.string.logged_out_successfully, Toast.LENGTH_SHORT).show()
//            }
//            .setNegativeButton(getString(R.string.cancel)) { dialogInterface, _ -> dialogInterface.dismiss() }
//            .create()
//            .show()
//    }

    /**
     * Handling back press
     */
    override fun onBackPressed() {
        val currentRoute = navHostController.currentBackStackEntry?.destination?.route

        if (currentRoute == HomeNavigation.HomeScreen.route) {
            if (doubleBackToExitPressedOnce && stackCount() == 0) {
                finish()
                return
            }
            doubleBackToExitPressedOnce = true
            Toaster.show(findViewById(android.R.id.content), getString(R.string.exit_message))
            Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
        }

        if (stackCount() != 0) {
            super.onBackPressed()
        }
    }


    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private fun checkPlayServices(): Boolean {
        val apiAvailability = GoogleApiAvailability.getInstance()
        val resultCode = apiAvailability.isGooglePlayServicesAvailable(this)
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                    ?.show()
            } else {
                Log.i(HomeActivity::class.java.name, "This device is not supported.")
                finish()
            }
            return false
        }
        return true
    }

    private val registerReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val token = intent.getStringExtra(Constants.TOKEN)
            token?.let { viewModel.registerNotification(it) }
        }
    }

    companion object {
        private const val PLAY_SERVICES_RESOLUTION_REQUEST = 9000
    }
}