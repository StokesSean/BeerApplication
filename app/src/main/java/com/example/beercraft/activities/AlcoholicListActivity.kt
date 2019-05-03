package com.example.beercraft.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import kotlinx.android.synthetic.main.activity_beercraft_list.*
import com.example.beercraft.R
import com.example.beercraft.main.MainApp
import com.example.beercraft.models.AlcoholicModel
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivityForResult


class AlcoholicListActivity : AppCompatActivity(),AlcoholicListener {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beercraft_list)
        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = AlcoholicAdapter(app.Alcoholics.findAll(), this)
        loadAlcoholics()

        toolbarMain.title = title
        setSupportActionBar(toolbarMain)

        }

    override fun onAlcoholicClick(Alcoholic: AlcoholicModel) {

        startActivityForResult(intentFor<AlcoholicActivity>().putExtra("Alcoholic_edit",Alcoholic), 0)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_add -> startActivityForResult<AlcoholicActivity>(0)
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        loadAlcoholics()
        recyclerView.adapter?.notifyDataSetChanged()
        super.onActivityResult(requestCode, resultCode, data)
    }
    private fun loadAlcoholics() {
        showAlcoholics(app.Alcoholics.findAll())
    }

    fun showAlcoholics (Alcoholic: List<AlcoholicModel>) {
        recyclerView.adapter = AlcoholicAdapter(Alcoholic, this)
        recyclerView.adapter?.notifyDataSetChanged()
    }

}



