package com.wellaxsa.gamerplay.utills

sealed class PlatformFilter(val label: String) {
    data object All : PlatformFilter("All")
    data object PC : PlatformFilter("PC")
    data object Android : PlatformFilter("Android")
    data object iOS : PlatformFilter("iOS")
    data object Playstation : PlatformFilter("Playstation 4")
    data object Xbox : PlatformFilter("Xbox")
    data object Nintendo : PlatformFilter("Nintendo")
}
