package com.example.memorygame

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Chronometer
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.memorygame.models.BoardSize
import com.example.memorygame.models.Game
import com.example.memorygame.models.User

class MainActivity : AppCompatActivity() {

    private var START_TIME: Long = 6000000
    private var timeFloat = 0
    private lateinit var adapter: MemoryBoardAdapter
    private lateinit var game: Game
    private lateinit var Board: RecyclerView
    private lateinit var viewNumberOfMoves: TextView
    private lateinit var mCountDownTimer: CountDownTimer
    private var boardSize = BoardSize.NORMAL
    private lateinit var chronometer: Chronometer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        chronometer = findViewById(R.id.chronometer)
        chronometer.start()
        chronometer.setFormat("Time: %s");
        Board = findViewById(R.id.Board)
        viewNumberOfMoves = findViewById(R.id.viewNumberOfMoves)

        newGame()
    }

    private fun newGame() {
        when (boardSize) {
            BoardSize.NORMAL -> {
                viewNumberOfMoves.text = "Moves: 0"
            }
            BoardSize.BIG -> {
                viewNumberOfMoves.text = "Moves: 0"
            }
        }
        game = Game(boardSize)
        adapter = MemoryBoardAdapter(this, boardSize, game.cards, object : MemoryBoardAdapter.CardClickListener {
            override fun onCardClicked(position: Int) {
                updateGame(position)
            }

        })
        Board.adapter = adapter

        Board.setHasFixedSize(true);
        Board.layoutManager = GridLayoutManager(this, boardSize.getWidth())

        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.stop();
        chronometer.start();
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.refresh -> {
                newGame()
            }
            R.id.choose_size -> {
                chooseSizeDialog()
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }

    private fun getTime(): String {
        val elapsedMillis: Long = SystemClock.elapsedRealtime() - chronometer.getBase()
        var seconds: Int = (elapsedMillis / 1000).toInt()
        timeFloat = seconds
        var minutes: Int = seconds / 60
        seconds = seconds - minutes * 60
        val time = minutes.toString() + ":" + seconds.toString()
        return time
    }

    private fun chooseSizeDialog() {
        val boardSizeView = LayoutInflater.from(this).inflate(R.layout.dialog_board_size, null)
        val radioGroupSize = boardSizeView.findViewById<RadioGroup>(R.id.radioGroup)
        when (boardSize) {
            BoardSize.NORMAL -> radioGroupSize.check(R.id.normalButton)
            BoardSize.BIG -> radioGroupSize.check(R.id.bigButton)
        }
        showAlertDialog("Choose size of the board", boardSizeView, View.OnClickListener {
            boardSize = when (radioGroupSize.checkedRadioButtonId) {
                R.id.normalButton -> BoardSize.NORMAL
                else -> BoardSize.BIG
            }
            newGame()
        })
    }

    private fun showAlertDialog(title: String, view: View?, positiveClickListener: View.OnClickListener) {
        AlertDialog.Builder(this)
                .setTitle(title)
                .setView(view)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK") { _, _ ->
                    positiveClickListener.onClick(null)
                }.show()
    }


    private fun updateGame(position: Int) {
        if (game.haveWonGame()) {
            return
        }
        if (game.isCardFaceUp(position)) { //clicked the same

            return
        }
        if (game.flipCard(position)) {
            if (game.haveWonGame()) {
                chronometer.stop()
                var time = getTime()
                showAlertDialog(getString(R.string.Congratulations) + "\n" +
                        getString(R.string.Moves) + " " + "${game.getNumberOFMoves()}" + " " + getString(R.string.Time) + ": " + time + " " + getString(R.string.Save)
                        , null, View.OnClickListener {
                        saveScore(time)
                })
            }
        }
        viewNumberOfMoves.text = getString(R.string.Moves) + "${game.getNumberOFMoves()}"
        adapter.notifyDataSetChanged()
    }

    private fun saveScore( time : String){
        val intent = Intent(this,SaveScoreActivity::class.java)
        intent.putExtra("time",time)

        var user  = User("a",timeFloat.toFloat(),boardSize, time)

        intent.putExtra("EXTRA_User", user)

        startActivity(intent)
    }
}