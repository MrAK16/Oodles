package com.ias.oodles.network.response.myaccount

import com.ias.oodles.network.response.OodlesApiResponse
import com.ias.oodles.network.response.home.LatestCourses


data class UserProfile (
    var id: String,
    var user_id: String,
    var mobile:String,
    var email: String,
    var fullname:String,
    var contact:String,
    var user_type:String
)