package com.example.shibegallery.adapter

import java.util.*

fun Calendar.greeting() = this.getDisplayName(
        Calendar.DAY_OF_MONTH,
        Calendar.LONG,
        Locale.getDefault()
)

fun main() {

}