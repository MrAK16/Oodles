package com.ias.oodles.network.response.myaccount

import com.ias.oodles.network.response.OodlesApiResponse
import com.ias.oodles.network.response.home.LatestCourses


data class MyCourseListResponse (
    var latest_courses: ArrayList<LatestCourses>?,
    var package_list: List<PackageList>?,
    var package_logo:String="",
): OodlesApiResponse()