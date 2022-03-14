package com.example.pickimage

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imagePick.setOnClickListener {
            //val gal = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            //startActivityForResult(gal,100)


            if (Build.VERSION.SDK_INT >= 23){
                if (checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                    requestPermissions(arrayOf(android.Manifest.permission.CAMERA) ,2000)
                    return@setOnClickListener
                }else {
                    val camera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(camera,200)
                }
            }else{
                val camera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(camera,200)
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 100){
            Log.e("a",data!!.data.toString())
            imagePick.setImageURI(data!!.data)
        }else if(resultCode == Activity.RESULT_OK && requestCode == 200){
            val bitmap = data!!.extras!!.get(("data"))
            imagePick.setImageBitmap(bitmap as Bitmap)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            2000 -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    val camera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(camera,200)
                }else {
                    finish()
                }
            }
        }
    }
}