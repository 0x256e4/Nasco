package com.capstone.nasco.ui

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.capstone.nasco.R
import com.capstone.nasco.databinding.ActivityDetailsBinding
import com.capstone.nasco.utils.rotateFile
import java.io.File

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == CAMERA_X_RESULT) {
                val myFile = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    it.data?.getSerializableExtra("picture", File::class.java)
                } else {
                    @Suppress("DEPRECATION")
                    it.data?.getSerializableExtra("picture")
                } as? File
                val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean
                myFile?.let { file ->
                    rotateFile(file, isBackCamera)
                    binding.ivPhoto.setImageBitmap(BitmapFactory.decodeFile(file.path))
                }
            }
        }.launch(Intent(this, CameraActivity::class.java))

    }


    companion object {
        const val CAMERA_X_RESULT = 200
    }
}