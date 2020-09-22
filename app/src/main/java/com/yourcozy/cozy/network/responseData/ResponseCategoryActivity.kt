package com.yourcozy.cozy.network.responseData

import com.yourcozy.cozy.views.category.CategoryData

data class ResponseCategoryActivity (
    val status : Int,
    val success : Boolean,
    val message : String,
    val data : List<CategoryData>
)