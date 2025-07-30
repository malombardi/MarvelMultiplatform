package com.mlombardi.marvelcharacters.core.data.network

import com.mlombardi.marvelcharacters.BuildKonfig
import com.mlombardi.marvelcharacters.core.domain.Constants.LIMIT_KEY
import com.mlombardi.marvelcharacters.core.domain.Constants.OFFSET_KEY
import com.mlombardi.marvelcharacters.core.domain.Constants.PAGE_SIZE
import com.mlombardi.marvelcharacters.core.domain.Constants.STARTING_OFFSET
import kotlinx.datetime.Clock
import okio.ByteString.Companion.encodeUtf8

class NetworkFactory {
    companion object {
        private const val ORDER_BY_KEY = "orderBy"
        private const val ORDER_BY_NAME = "name"
        private const val ORDER_BY_TITLE = "title"
        private const val TS_KEY = "ts"
        private const val API_KEY = "apikey"
        private const val HASH_KEY = "hash"

        fun getNetworkOptions(
            offset: Int? = STARTING_OFFSET,
            isCharacter: Boolean
        ): Map<String, String> {
            val ts = Clock.System.now().toEpochMilliseconds()
            val options = mutableMapOf<String, String>()
            if (isCharacter) {
                options[ORDER_BY_KEY] = ORDER_BY_NAME
            } else {
                options[ORDER_BY_KEY] = ORDER_BY_TITLE
            }
            options[TS_KEY] = ts.toString()
            options[LIMIT_KEY] = PAGE_SIZE.toString()
            options[API_KEY] = BuildKonfig.public_key
            options[HASH_KEY] = generateHash(ts.toString() + BuildKonfig.private_key + BuildKonfig.public_key)
            options[OFFSET_KEY] = offset.toString()
            options["orderBy"] = "-name"

            return options
        }

        private fun generateHash(s: String): String {
            return s.encodeUtf8().md5().hex()
        }
    }
}
