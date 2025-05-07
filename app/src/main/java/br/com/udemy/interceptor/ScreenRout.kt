package br.com.udemy.interceptor

enum class ScreenRoute(val route: String) {
    LOGIN("/login"),
    CHARACTER_SCREEN("/characters"),
    CHARACTER_DETAIL_SCREEN("/detail/{id}")
}