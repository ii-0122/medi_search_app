package com.example.test1

import retrofit2.http.Query

data class HTTP_GET_Model(
    @Query("something") var get_result: String? = null
)

data class PostModel(
    var id : String? =null ,
    var pwd : String?=null,
    var nick : String? =null
)

data class PostResult(
    var result: String? = null
)