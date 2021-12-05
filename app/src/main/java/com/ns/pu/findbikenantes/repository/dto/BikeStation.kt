package com.ns.pu.findbikenantes.repository.dto

data class BikeStation(
    val facet_groups: List<FacetGroup>,
    val nhits: Int,
    val parameters: Parameters,
    val records: List<Record>,
)