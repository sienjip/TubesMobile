package com.e.tubesmobile.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Periferal (
    @PrimaryKey
    val id:String,
    val merk:String,
    val jenis:JenisPeriferal,
    val harga:Int
        )

