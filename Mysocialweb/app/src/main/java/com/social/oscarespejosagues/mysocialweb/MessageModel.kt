package com.social.oscarespejosagues.mysocialweb

import java.util.*

data class MessageModel(
    var text:String? = null,
    var createdAd: Date? = null,
    var username: String? = null,
    var userId: String? = null,
    var likes: Int? = null,
    var avatarUrl: String? = null
)