package model

import kotlinx.serialization.Serializable

@Serializable
data class ItemImage(
    val URL: String,
    val name: String
)