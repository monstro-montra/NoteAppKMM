package com.javierlabs.noteappkmm

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform