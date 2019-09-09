package com.example.formapplication.data.db.dao

import androidx.room.*
import com.example.formapplication.data.db.entity.UserEntity

/**
 * Dao is a method access the database, using annotation to bind SQL to each method.
 */
@Dao
interface UserDao {

    @Query("SELECT * FROM user_details")
    fun getAllUserDetails(): List<UserEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUser(userDetails: UserEntity)

    @Delete
    fun delete(userDetails: UserEntity)

    @Update
    fun updateUserDetails(vararg userDetails: UserEntity)
}