package com.example.beercraft.models
import android.content.Context
import com.example.beercraft.helpers.exists
import com.example.beercraft.helpers.read
import com.example.beercraft.helpers.write
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import java.util.*

val JSON_FILE = "alcoholics.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<java.util.ArrayList<AlcoholicModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class AlcoholicJSONStore: AlcoholicStore, AnkoLogger {
    val context: Context
    var Alcoholics = mutableListOf<AlcoholicModel>()
    constructor (context: Context) {
        this.context = context
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }
    override fun findAll(): MutableList<AlcoholicModel> {
        return Alcoholics
    }
    override fun create(Alcoholic: AlcoholicModel) {
        Alcoholic.id = generateRandomId()
        Alcoholics.add(Alcoholic)
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(Alcoholics, listType)
        write(context, JSON_FILE, jsonString)
    }
    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        Alcoholics = Gson().fromJson(jsonString, listType)
    }
    override fun update(Alcoholic: AlcoholicModel) {
        val AlcoholicList = findAll() as ArrayList<AlcoholicModel>
        var foundAlcoholic: AlcoholicModel? = AlcoholicList.find { a -> a.id == Alcoholic.id }
        if (foundAlcoholic != null) {
            foundAlcoholic.BeerTitle = Alcoholic.BeerTitle
            foundAlcoholic.BeerDesc = Alcoholic.BeerDesc
            foundAlcoholic.image = Alcoholic.image

        }
        serialize()
    }
    override fun delete(Alcoholic: AlcoholicModel) {
        Alcoholics.remove(Alcoholic)
        serialize()
    }
}