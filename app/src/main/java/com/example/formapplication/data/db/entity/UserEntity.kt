package com.example.formapplication.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity represents data for a single table row, constructed using an annotated.
 * It is basically the model class
 */
@Entity(tableName = "user_details")
data class UserEntity (

    @PrimaryKey
    var cust_id: Int = 0,

    @ColumnInfo(name= "cust_name")
    var cust_name: String = "",
    @ColumnInfo(name= "address")
    var cust_add: String = "",
    @ColumnInfo(name= "phone")
    var cust_phn: String = "",
    @ColumnInfo(name= "type")
    var cust_type: String = ""
)