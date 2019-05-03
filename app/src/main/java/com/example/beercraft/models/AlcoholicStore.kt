package com.example.beercraft.models

interface AlcoholicStore {
    fun findAll(): List<AlcoholicModel>
    fun create(alcoholic: AlcoholicModel)
    fun update(alcoholic: AlcoholicModel)
    fun delete(alcoholic: AlcoholicModel)

}