package com.example.beercraft.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.beercraft.R
import com.example.beercraft.helpers.readImage
import com.example.beercraft.helpers.readImageFromPath
import com.example.beercraft.helpers.showImagePicker
import com.example.beercraft.main.MainApp
import com.example.beercraft.models.AlcoholicModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.BeerDesc
import kotlinx.android.synthetic.main.activity_main.BeerTitle
import kotlinx.android.synthetic.main.cardview_beercraft.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast


class AlcoholicActivity : AppCompatActivity(),AnkoLogger {

var alcoholic = AlcoholicModel()
    val IMAGE_REQUEST=1

lateinit var app : MainApp
    var edit = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        app = application as MainApp
        //function confirms to the user that  button has been pressed
        toolbarAdd.title = title


        setSupportActionBar(toolbarAdd)

        if (intent.hasExtra("Alcoholic_edit")) {
            edit = true
            alcoholic = intent.extras.getParcelable<AlcoholicModel>("Alcoholic_edit")
            BeerTitle.setText(alcoholic.BeerTitle)
            BeerDesc.setText(alcoholic.BeerDesc)
            btnAdd.setText(R.string.save_beer)
            BeerPic.setImageBitmap(readImageFromPath(this, alcoholic.image))
            if(alcoholic.image != null) {
                chooseImage.setText(R.string.change_alcohlic_image)
            }
        }


        btnAdd.setOnClickListener() {


            alcoholic.BeerDesc = BeerDesc.text.toString()
            alcoholic.BeerTitle  = BeerTitle.text.toString()
            if(alcoholic.BeerTitle.isEmpty()) {
                toast("no_title_entered")
            }
            else {
                if(edit) {
                    app.Alcoholics.update(alcoholic.copy())

                }else{
                    app.Alcoholics.create(alcoholic.copy())
                }
            }
            info("add button pressed: $BeerTitle")
                app.Alcoholics.findAll().forEach {info("Button pressed : ${it}") }
                setResult(AppCompatActivity.RESULT_OK)
                finish()


            }
        chooseImage.setOnClickListener {
            showImagePicker(this, IMAGE_REQUEST)
            info ("Select image")
        }

            }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_alcoholicactive, menu)
        if(edit && menu !=null)menu.getItem(0).setVisible(true)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_delete -> {
                app.Alcoholics.delete(alcoholic)
                finish()
            }
            R.id.item_cancel -> {
                finish()
            }

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            IMAGE_REQUEST -> {
                if (data != null) {
                    alcoholic.image = data.getData().toString()
                    BeerPic.setImageBitmap(readImage(this,resultCode,data))
                    chooseImage.setText(R.string.change_alcohlic_image)
                }
            }
        }
    }


        }




