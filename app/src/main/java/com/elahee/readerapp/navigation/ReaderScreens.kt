package com.elahee.readerapp.navigation

enum class ReaderScreens {
    SplashScreen,
    JetReraderApp,
    LoginScreen,
    CreateAccountScree,
    SearchScreen,
    DetailScreen,
    UpdateScreen,
    ReaderHomeScreen,
    ReaderStatsScreen;

    companion object{
        fun fromRoute(route:String): ReaderScreens= when(route?.substringBefore("/")){
            SplashScreen.name-> SplashScreen
            JetReraderApp.name-> JetReraderApp
            LoginScreen.name-> LoginScreen
            CreateAccountScree.name-> CreateAccountScree
            ReaderHomeScreen.name-> ReaderHomeScreen
            SearchScreen.name-> SearchScreen
            DetailScreen.name-> DetailScreen
            UpdateScreen.name-> UpdateScreen
            ReaderStatsScreen.name-> ReaderStatsScreen
            null-> ReaderHomeScreen
            else-> throw IllegalArgumentException("Route $route is not recognized")

        }
    }

}