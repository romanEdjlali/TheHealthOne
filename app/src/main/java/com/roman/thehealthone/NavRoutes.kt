package com.roman.thehealthone

/**
* Sync sleep/exercise logs from Google Health SyncScreen
* View all logs (sleep + exercise) LogsScreen
* Detect and highlight conflicts ConflictsScreen
* Resolve conflicts	ResolutionsScreen
* Manual logging (sleep + exercise)	ManualInputScreen
**/

sealed class NavRoute(val route: String, val label: String, val icon: String) {
    data object Sync : NavRoute("sync", "Sync", "sync")
    data object Logs : NavRoute("logs", "Logs", "logs")
    data object Conflicts : NavRoute("conflicts", "Conflicts", "errors")
    data object Resolutions : NavRoute("resolution", "Resolution", "fixed")
    data object Manual : NavRoute("manual", "Manual", "add")

    companion object { val bottom = listOf(Sync, Logs, Conflicts, Resolutions, Manual) }
}