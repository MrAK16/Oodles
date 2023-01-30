package com.ias.oodles.network.response.myaccount

import com.ias.oodles.network.response.OodlesApiResponse
import com.ias.oodles.network.response.home.LatestCourses


data class UpdateProfileResponse (
    var status: Boolean,
    var message: String,
    var error:String
)