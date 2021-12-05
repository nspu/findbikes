package com.ns.pu.findbikenantes.repository.dto

data class Fields(
    val adresse: String,
    val capacite: String,
    val capacite_num: Int,
    val categorie: String,
    val commune: String,
    val conditions: String,
    val cp: String,
    val exploitant: String,
    val geo_shape: GeoShape,
    val gid: String,
    val insee: String,
    val lien: String,
    val ligne_tc: String,
    val localisation: String,
    val nom: String,
    val ouverture: String,
    val source: String,
    val ss_categorie: String,
    val tel: String,
)