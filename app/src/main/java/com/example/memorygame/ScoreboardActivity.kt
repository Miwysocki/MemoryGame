package com.example.memorygame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.TextView
import androidx.core.app.ActivityCompat
import com.example.memorygame.models.User
import com.example.memorygame.utils.DataBaseHandler
import org.w3c.dom.Text

class ScoreboardActivity : AppCompatActivity() {

    private lateinit var top1text: TextView
    private lateinit var top2text: TextView
    private lateinit var top3text: TextView
    private lateinit var backButton: Button

    val context = this
    var db = DataBaseHandler(context)
    var list : MutableList<User> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scoreboard)

        top1text = findViewById(R.id.viewTop1);
        top2text = findViewById(R.id.viewTop2);
        top3text = findViewById(R.id.viewTop3);
        backButton = findViewById(R.id.back)

        backButton.setOnClickListener {
            val intent = Intent(this, StartActivity::class.java).apply {
            }
            startActivity(intent)
        }

        list  = db.readData()

        var comparedTime = 9999f
        var secondTime = 9999f
        var thirdTime = 9999f
        var  winner : User = User()
        var  second : User = User()
        var  third : User = User()

        list.forEach() {
            if(it.timeFloat < comparedTime) {
                winner = it
                comparedTime = it.timeFloat
            }   else if(it.timeFloat < secondTime){
                second = it
                secondTime = it.timeFloat
            }   else if(it.timeFloat < thirdTime){
                third = it
                thirdTime = it.timeFloat
            }

            var time1 = getTimeText(winner.timeFloat)
            var time2 = getTimeText(second.timeFloat)
            var time3 = getTimeText(third.timeFloat)

            top1text.setText("1. " + winner.name +" time: "+ time1)
            top2text.setText("2. " + second.name +" time: "+ time2)
            top3text.setText("3. " + third.name +" time: "+ time3)


        }
    }

    private fun getTimeText( timeFloat: Float): String {
        var minutes: Int = timeFloat.toInt() / 60
        var seconds = timeFloat - minutes * 60
        val time = minutes.toString() + ":" + seconds.toString()
        return time
    }
}