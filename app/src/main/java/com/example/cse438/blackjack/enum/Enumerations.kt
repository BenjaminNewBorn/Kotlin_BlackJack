package com.example.cse438.blackjack.enum

enum class UserInterfaceState {
    NETWORK_ERROR,
    NO_DATA,
    RESET,
    HOME,
    RESULTS,
    FAVORITES,
    REVIEW
}

enum class GameResult{
    Win,
    Lose,
    Draw,
    InComplete
}