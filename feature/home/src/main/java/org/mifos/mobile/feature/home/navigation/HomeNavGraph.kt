package org.mifos.mobile.feature.home.navigation

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import org.mifos.mobile.feature.home.R
import org.mifos.mobile.feature.home.screens.HomeScreen
import org.mifos.mobile.feature.home.viewmodel.HomeCardItem

fun NavGraphBuilder.homeNavGraph(
    onNavigate: (HomeDestinations) -> Unit
) {
    navigation(
        startDestination = HomeNavigation.HomeScreen.route,
        route = HomeNavigation.HomeBase.route,
    ) {
        homeRoute(
            onNavigate = onNavigate,
        )
    }
}

fun NavGraphBuilder.homeRoute(
    onNavigate: (HomeDestinations) -> Unit
) {
    composable(
        route = HomeNavigation.HomeScreen.route,
    ) {
        val context = LocalContext.current
        HomeScreen(
            callHelpline = { callHelpline(context) },
            mailHelpline = { mailHelpline(context) },
            onNavigate = onNavigate
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