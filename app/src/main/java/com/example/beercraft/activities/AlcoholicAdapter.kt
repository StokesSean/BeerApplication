package com.example.beercraft.activities

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.beercraft.R
import com.example.beercraft.helpers.readImageFromPath
import com.example.beercraft.models.AlcoholicModel
import kotlinx.android.synthetic.main.cardview_beercraft.view.*

class AlcoholicAdapter constructor(private var Alcoholics: List<AlcoholicModel>, private val listener: AlcoholicListener): RecyclerView.Adapter<AlcoholicAdapter.MainHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder  {
        return MainHolder(LayoutInflater.from(parent?.context).inflate(R.layout.cardview_beercraft, parent, false))
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val Alcoholic = Alcoholics[holder.adapterPosition]
        holder.bind(Alcoholic, listener)
    }

    override fun getItemCount(): Int = Alcoholics.size

    class MainHolder constructor(itemView: View) :  RecyclerView.ViewHolder(itemView){

        fun bind(Alcoholic: AlcoholicModel,listener: AlcoholicListener ) {
            itemView.BeerTitle.text = Alcoholic.BeerTitle
            itemView.BeerDesc.text = Alcoholic.BeerDesc
            itemView.imageIcon.setImageBitmap(readImageFromPath(itemView.context, Alcoholic.image))
            itemView.setOnClickListener { listener.onAlcoholicClick(Alcoholic) }

        }
    }
}
interface AlcoholicListener {
    fun onAlcoholicClick(Alcoholic: AlcoholicModel)
}