package com.social.oscarespejosagues.mysocialweb.models

import java.util.*

data class NewsModel(
    var author:String? = null,
    var title:String? = null,
    var createdAt: Date? = null,
    var imageUrl: String? = null,
    var description:String? = null
)