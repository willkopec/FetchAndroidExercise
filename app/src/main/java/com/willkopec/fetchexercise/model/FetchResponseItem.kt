package com.willkopec.fetchexercise.model

data class FetchResponseItem(
    val id: Int?,
    val listId: Int?,
    val name: String?,
    val nameWithoutItem: Int? = name?.replace("Item ", "")?.toIntOrNull()
)