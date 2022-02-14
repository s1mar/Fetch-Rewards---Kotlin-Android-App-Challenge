package com.s1.fetchrewardschallenge.interfaces


interface ViewModelTarget {
    enum class ActivityStates {
        MAKING_API_CALL,
        HAS_DATA,
        NO_INTERNET,
        NO_DATA,
        BACK_PRESSED
    }

    fun processState(state: ActivityStates)
}