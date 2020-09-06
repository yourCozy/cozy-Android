package com.example.cozy.network.responseData

import com.example.cozy.views.category.CategoryData

data class ResponseCategoryActivity (
    val status : Int,
    val success : Boolean,
    val message : String,
    val data : List<CategoryData>
)