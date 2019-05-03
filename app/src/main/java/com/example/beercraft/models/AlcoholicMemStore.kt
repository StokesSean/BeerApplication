package com.example.beercraft.models

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
var lastId= 0L
internal fun getId(): Long {
    return lastId++
}



class AlcoholicMemStore:AlcoholicStore, AnkoLogger {
    val Alcoholics = ArrayList<AlcoholicModel>()
    override fun findAll():List<AlcoholicModel>{
        return Alcoholics
    }
    override fun create(Alcoholic: AlcoholicModel){
        Alcoholic.id = getId()
        Alcoholics.add(Alcoholic)
        logAll()
    }
    override fun update(Alcoholic: AlcoholicModel){
        var foundAlcoholic: AlcoholicModel? = Alcoholics.find { a -> a.id == Alcoholic.id}
        if (foundAlcoholic != null) {
            foundAlcoholic.BeerTitle = Alcoholic.BeerTitle
            foundAlcoholic.BeerDesc = Alcoholic.BeerDesc
            foundAlcoholic.image = Alcoholic.image
            logAll()
        }

    }
    override fun delete(Alcoholic: AlcoholicModel) {
        Alcoholics.remove(Alcoholic)
    }


    fun logAll(){
        Alcoholics.forEach { info("$it") }
    }
}