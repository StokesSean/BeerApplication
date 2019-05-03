package com.example.beercraft.activities

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.beercraft.R
import com.example.beercraft.main.MainApp
import com.example.beercraft.models.AlcoholicUsers
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.loginorsignupview.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import java.util.*

class RegisterActivity : AppCompatActivity(), AnkoLogger {

    lateinit var app: MainApp
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.loginorsignupview)
        app = application as MainApp
        already.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

        }

        btnlogin.setOnClickListener {
            performRegister();
        }
            select_photo_btn.setOnClickListener {

                info("uploading a photo")
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                startActivityForResult(intent, 0)

            }
        }
        var selectedPhotoUri: Uri? = null
        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
                super.onActivityResult(requestCode, resultCode, data)
                if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
                    info { "photo was picked" }
                    selectedPhotoUri = data.data
                    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUri)
                    userphoto.setImageBitmap(bitmap)
                    select_photo_btn.alpha = 0f
                //    val bitmapDrawable = BitmapDrawable(bitmap)
                  //  select_photo_btn.setBackgroundDrawable(bitmapDrawable)
                }
        }

    private fun performRegister(){
        val email = email.text.toString()
        val password = password.text.toString()
        info("Email entered was: $email and the password that was entered was $password")
        if (email.isEmpty()) {

            toast("Email is empty")
            return
        }
        if (password.isEmpty()) {
            toast("You have not entered a password")
            return

        }

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)

            .addOnCompleteListener {

                if (it.isSuccessful) {
                    val intent = Intent(this, AlcoholicListActivity::class.java)
                    startActivity(intent)
                    uploadImageToFirebaseStorage()
                } else {
                    return@addOnCompleteListener
                }


            }
            .addOnFailureListener {
                toast("Failed to create user : ${it.message}")
            }


    }
    private fun uploadImageToFirebaseStorage() {
        if (selectedPhotoUri == null) return

        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")

        ref.putFile(selectedPhotoUri!!)
            .addOnSuccessListener {
                info { "succefully uploaded image" }

                ref.downloadUrl.addOnSuccessListener {
                    info ( "File Location: $it")

                    saveUserToFirebaseDatabase(it.toString())
                }
            }
            .addOnFailureListener {
                info("Failed to upload image to storage: ${it.message}")
            }
    }
    private fun saveUserToFirebaseDatabase(profileImageUrl: String) {
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")

        val user = AlcoholicUsers(uid, username_edittext.text.toString(), profileImageUrl)

        ref.setValue(user)
            .addOnSuccessListener {
              info { "it fucking worked" }
            }
            .addOnFailureListener {

            }
    }

}


