package com.example.acronymfinder.data.model

data class AcronymResponse(
    val sf: String,
    val lfs: List<LfData>
)

data class LfData(
    val lf: String,
    val freq: Int,
    val since: Int,
    // val vars: List<VarData>
)

// not using
data class VarData(
    val lf: String,
    val freq: Int,
    val since: Int
)
