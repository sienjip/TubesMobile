package com.e.tubesmobile.screens

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.e.tubesmobile.R

enum class Menu(
    @StringRes val title: Int,
    val icon: ImageVector,
    val route: String
) {
    HOME(R.string.home, Icons.Default.Home, "home"),
    PENGELOLAAN_KOMPUTER(R.string.pengelolaan_komputer, Icons.Default.List, "pengelolaan-komputer"),
    SETTING(R.string.setting, Icons.Default.Settings, "setting");
    companion object {
        fun getTabFromResource(@StringRes resource: Int) : Menu
        {
            return when (resource) {
                R.string.home -> HOME
                R.string.pengelolaan_komputer ->
                    PENGELOLAAN_KOMPUTER
                else -> SETTING
            }
        }
    }
}