package com.e.tubesmobile.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Smarthphone(
    @PrimaryKey
    val id:String,
    val model:String,
    val warna:String,
    val storage:Int,
    val tanggalRilis:Date,
    val sistemOperasi:JenisSmarthphone,
)
