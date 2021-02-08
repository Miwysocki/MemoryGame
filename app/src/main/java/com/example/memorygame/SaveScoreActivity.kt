package com.example.memorygame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.memorygame.models.User
import com.example.memorygame.utils.DataBaseHandler

class SaveScoreActivity : AppCompatActivity() {
    private lateinit var saveButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save_score)

        val context = this
        var db = DataBaseHandler(context)

        saveButton =  findViewById(R.id.done);
        var inputText = findViewById(R.id.username) as EditText

        val time =intent.getStringExtra("time")

        saveButton.setOnClickListener {
        var name = inputText.text

        if(name.isNotEmpty()) {
            var human = intent.getSerializableExtra("EXTRA_User") as User
            if (human != null) {
                human.name = name.toString()
            }

            db.insertData(human)

            val intent = Intent(this, ScoreboardActivity::class.java).apply {
            }
            startActivity(intent)

        } else{
            Toast.makeText(context,"Please write your name",Toast.LENGTH_SHORT).show()
        }



        }
    }
}