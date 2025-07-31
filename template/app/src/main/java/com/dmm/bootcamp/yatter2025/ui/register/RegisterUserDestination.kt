package com.dmm.bootcamp.yatter2025.ui.register

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dmm.bootcamp.yatter2025.common.navigation.Destination
import com.dmm.bootcamp.yatter2025.ui.timeline.PublicTimelinePage

class RegisterUserDestination : Destination(ROUTE)
{
    companion object {
        private const val ROUTE = "register"

        fun createNode(builder: NavGraphBuilder) {
            builder.composable(ROUTE) {
                RegisterUserPage()
            }
        }
    }
}