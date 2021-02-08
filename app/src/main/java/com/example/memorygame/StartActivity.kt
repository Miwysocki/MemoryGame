package com.example.memorygame

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView

class StartActivity : AppCompatActivity() {

    private lateinit var newGameButton: Button
    private lateinit var scoreboardButton: Button
    private lateinit var exitButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        newGameButton =  findViewById(R.id.new_game);
        scoreboardButton =  findViewById(R.id.scoreboard);
        exitButton =  findViewById(R.id.quit);

        newGameButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {
            }
            startActivity(intent)
        }

        scoreboardButton.setOnClickListener {
            val intent = Intent(this, ScoreboardActivity::class.java).apply {
            }
            startActivity(intent)
        }

        exitButton.setOnClickListener {
            ActivityCompat.finishAffinity(this)
        }

    }


}