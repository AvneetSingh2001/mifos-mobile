package org.mifos.mobile.feature.help.navigation

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import org.mifos.mobile.feature.help.HelpScreen
import org.mifos.mobile.feature.help.R

fun NavController.navigateToHelpScreen() {
    navigate(HelpNavigation.HelpBase.route)
}

fun NavGraphBuilder.helpNavGraph(
    findLocations: () -> Unit,
    navigateBack: () -> Unit,
) {
    navigation(
        startDestination = HelpNavigation.HelpBase.route,
        route = HelpNavigation.HelpScreen.route,
    ) {
        helpScreenRoute(
            findLocations = findLocations,
            navigateBack = navigateBack,
        )
    }
}

fun NavGraphBuilder.helpScreenRoute(
    findLocations: () -> Unit,
    navigateBack: () -> Unit,
) {
    composable(
        route = HelpNavigation.HelpScreen.route,
    ) {
        val context = LocalContext.current
        HelpScreen(
            callNow = { callHelpline(context) },
            leaveEmail = { mailHelpline(context) },
            findLocations = findLocations,
            navigateBack = navigateBack,
        )
    }
}

private fun callHelpline(context: Context) {
    val intent = Intent(Intent.ACTION_DIAL)
    intent.data = Uri.parse("tel:" + context.getString(R.string.help_line_number))
    context.startActivity(intent)
}

private fun mailHelpline(context: Context) {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:")
        putExtra(Intent.EXTRA_EMAIL, arrayOf(context.getString(R.string.contact_email)))
        putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.user_query))
    }
    try {
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(
            context,
            context.getString(R.string.no_app_to_support_action),
            Toast.LENGTH_SHORT,
        ).show()
    }
}