package com.example.beercraft.models
import android.os.Parcelable
import com.google.firebase.database.DatabaseReference
import kotlinx.android.parcel.Parcelize

private lateinit var database:  DatabaseReference
@Parcelize
data class AlcoholicModel(var id: Long = 0, var BeerTitle: String = "", var BeerDesc: String = "", var image: String =""):Parcelable {
    private fun writeNewAlcoholic(id: Long , BeerTitle: String, BeerDesc: String,image: String){
        val alcohol = AlcoholicModel(id,BeerTitle,BeerDesc,image)


    }
}


