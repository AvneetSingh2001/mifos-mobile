package org.mifos.mobile.feature.about.navigation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import org.mifos.mobile.core.common.Constants
import org.mifos.mobile.core.model.enums.AboutUsListItemId
import org.mifos.mobile.feature.about.ui.AboutUsScreen

fun NavController.navigateToAboutUsScreen() {
    navigate(AboutUsNavigation.AboutUsBase.route)
}

fun NavGraphBuilder.aboutUsNavGraph(
    navigateToPrivacyPolicy: () -> Unit,
    navigateToOssLicense: () -> Unit
) {
    navigation(
        startDestination = AboutUsNavigation.AboutUsBase.route,
        route = AboutUsNavigation.AboutUsScreen.route,
    ) {
        aboutUsScreenRoute(
            navigateToPrivacyPolicy = navigateToPrivacyPolicy,
            navigateToOssLicense = navigateToOssLicense
        )
    }
}

fun NavGraphBuilder.aboutUsScreenRoute(
    navigateToPrivacyPolicy: () -> Unit,
    navigateToOssLicense: () -> Unit
) {
    composable(
        route = AboutUsNavigation.AboutUsScreen.route,
    ) {
        val context = LocalContext.current

        AboutUsScreen(
            navigateToItem = {
                navigateToItem(
                    context = context,
                    aboutUsItem = it.itemId,
                    navigateToOssLicense = navigateToOssLicense,
                    navigateToPrivacyPolicy = navigateToPrivacyPolicy
                )
            }
        )
    }
}

private fun navigateToItem(
    context: Context,
    aboutUsItem: AboutUsListItemId,
    navigateToPrivacyPolicy: () -> Unit,
    navigateToOssLicense: () -> Unit
) {
    when (aboutUsItem) {
        AboutUsListItemId.OFFICE_WEBSITE -> {
            startActivity(context, Constants.WEBSITE_LINK)
        }

        AboutUsListItemId.LICENSES -> {
            startActivity(context, Constants.LICENSE_LINK)
        }

        AboutUsListItemId.PRIVACY_POLICY -> {
            navigateToPrivacyPolicy()
        }

        AboutUsListItemId.SOURCE_CODE -> {
            startActivity(context, Constants.SOURCE_CODE_LINK)
        }

        AboutUsListItemId.LICENSES_STRING_WITH_VALUE -> {
            navigateToOssLicense()
        }

        AboutUsListItemId.APP_VERSION_TEXT -> Unit
    }
}

fun startActivity(context: Context, uri: String) {
    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(uri)))
}