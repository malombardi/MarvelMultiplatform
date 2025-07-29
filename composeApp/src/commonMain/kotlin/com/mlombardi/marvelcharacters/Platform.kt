package com.mlombardi.marvelcharacters

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform