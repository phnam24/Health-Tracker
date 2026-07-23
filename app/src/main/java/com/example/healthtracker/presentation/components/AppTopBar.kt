package com.example.healthtracker.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import com.example.healthtracker.presentation.theme.healthColors

enum class AppTopBarAlignment {
    START,
    CENTER,
}

@Immutable
data class AppTopBarNavigation(
    val icon: ImageVector,
    val contentDescription: String,
    val onClick: () -> Unit,
    val enabled: Boolean = true,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    alignment: AppTopBarAlignment = AppTopBarAlignment.START,
    navigation: AppTopBarNavigation? = null,
    titleMaxLines: Int = 1,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    windowInsets: WindowInsets = TopAppBarDefaults.windowInsets,
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.background,
        scrolledContainerColor = MaterialTheme.healthColors.cardContainer,
    ),
    actions: @Composable RowScope.() -> Unit = {},
) {
    val titleContent: @Composable () -> Unit = {
        Column {
            Text(
                text = title,
                maxLines = titleMaxLines,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleLarge,
            )
            if (subtitle != null) {
                Text(
                    text = subtitle,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
    val navigationContent: @Composable () -> Unit = {
        if (navigation != null) {
            IconButton(
                onClick = navigation.onClick,
                enabled = navigation.enabled,
            ) {
                Icon(
                    imageVector = navigation.icon,
                    contentDescription = navigation.contentDescription,
                )
            }
        }
    }

    when (alignment) {
        AppTopBarAlignment.START -> TopAppBar(
            title = titleContent,
            modifier = modifier,
            navigationIcon = navigationContent,
            actions = actions,
            colors = colors,
            scrollBehavior = scrollBehavior,
            windowInsets = windowInsets,
        )

        AppTopBarAlignment.CENTER -> CenterAlignedTopAppBar(
            title = titleContent,
            modifier = modifier,
            navigationIcon = navigationContent,
            actions = actions,
            colors = colors,
            scrollBehavior = scrollBehavior,
            windowInsets = windowInsets,
        )
    }
}

@Composable
fun AppTopBarIconAction(
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    IconButton(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
        )
    }
}

@Composable
fun AppTopBarTextAction(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    TextButton(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier,
    ) {
        Text(
            text = text,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}
