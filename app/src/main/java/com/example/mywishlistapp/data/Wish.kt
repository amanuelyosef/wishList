package com.example.mywishlistapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wish-table")
data class Wish(
    @PrimaryKey(autoGenerate = true)
    val id:Long = 0L,
    @ColumnInfo(name = "wish-title")
    val title:String="",
    @ColumnInfo(name = "wish-desc")
    val description:String=""
)

object dummyWish{
    val wishList= listOf(
        Wish(title="pixel watch", description = "buy a pixel watch"),
        Wish(title = "Oculus VR", description = "buy an Oculus VR"),
        Wish(title = "Airport", description = "Buy an international airport")
    )
}
