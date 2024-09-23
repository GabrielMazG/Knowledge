package com.example.knowledge.compose.jetreaderapp.model

data class MUser(
    val id: String?,
    val userId: String,
    val displayName: String,
    val avatarUrl: String,
    val quote: String,
    val profession: String,
) {
    fun toMap(): MutableMap<String, Any> = mutableMapOf(
        "user_id" to this.userId,
        "display_name" to this.displayName,
        "avatar_url" to this.avatarUrl,
        "quote" to this.quote,
        "profession" to this.profession,
    )
}
