package com.example.data.shoppingapp.local.room

import android.content.Context
import androidx.room.*
import com.example.data.shoppingapp.local.model.FavouriteProductDbo
import com.example.data.shoppingapp.local.model.UserDbo
import com.example.data.shoppingapp.local.model.UserWithFavProductsDbo
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {


    @Insert
    suspend fun insertUser(userDbo: UserDbo): Long

    @Query("SELECT * FROM users WHERE email = :email")
    suspend fun getUserByEmail(email: String): UserDbo?


    @Query("SELECT * FROM users WHERE id = :id")
    fun getUserFlowById(id: Long): Flow<UserDbo?>

    @Query("SELECT name FROM fav_products WHERE user_id = :id")
    fun getUserFavouritesFlow(id: Long): Flow<List<String>>

    @Query("UPDATE users SET photoUri = :photoUri WHERE id = :userId")
    suspend fun addUserPhoto(userId: Long, photoUri: String)

    @Insert(entity = FavouriteProductDbo::class)
    suspend fun addToFavourite(productDbo: FavouriteProductDbo)

    @Query("DELETE FROM fav_products WHERE user_id = :userId AND name = :productName")
    suspend fun removeFromFavourite(userId: Long, productName:String)

    companion object{
        fun getInstance(context: Context): UserDao {
            val room = AppDatabase.getInstance(context)
            return room.userDao()
        }
    }


}