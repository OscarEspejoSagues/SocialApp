package com.social.oscarespejosagues.mysocialweb

import java.util.*

data class NewsModel(
    var author:String? = null,
    var title:String? = null,
    //var createdAt: Date? = null,
    var urlImage: String? = null,
    var description:String? = null
)