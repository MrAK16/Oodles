package com.ias.oodles.network.response.myaccount

import com.ias.oodles.network.response.OodlesApiResponse
import com.ias.oodles.network.response.home.LatestCourses


data class ProfileResponse (
    var user_profile: UserProfile
): OodlesApiResponse()