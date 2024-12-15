package com.wellaxsa.gamerplay.utills

sealed class PlatformFilter(val label: String) {
    object All : PlatformFilter("All")
    object PC : PlatformFilter("PC")
    object Browser : PlatformFilter("Browser")
}
