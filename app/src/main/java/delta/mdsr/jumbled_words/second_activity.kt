package delta.mdsr.jumbled_words

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View.INVISIBLE
import android.widget.Button
import android.widget.EditText
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class second_activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val timerTextView = findViewById<TextView>(R.id.tvtimer)
        fun playGame() {
            setContentView(R.layout.activity_second)
            var clueEntered = intent.getStringExtra("Clue")
            var wordEntered1: String = intent.getStringExtra("Word").toString()
            var wordEntered = wordEntered1.replace("\\s".toRegex(), "")
            val hintString = "___   ".repeat(wordEntered.length)


            val heart1 = findViewById<ImageView>(R.id.heart1)
            val heart2 = findViewById<ImageView>(R.id.heart2)
            val heart3 = findViewById<ImageView>(R.id.heart3)

            val answerSpace = findViewById<EditText>(R.id.anspace)
            answerSpace.setBackgroundResource(android.R.color.transparent)
            var answerChars = answerSpace.text.toString()
            answerSpace.hint = hintString


            var infoBtn: Button = findViewById<Button>(R.id.infobtn)
            var infoBuilder = AlertDialog.Builder(this)
            infoBtn.setOnClickListener() {
                infoBuilder.setTitle("Clue: ")
                    .setMessage(clueEntered)
                    .setCancelable(true)
                    .setNegativeButton("Okay") { dialogInterface, it ->
                        dialogInterface.cancel()
                    }
                    .show()
            }

            fun gridSetter() {
                var word = wordEntered
                val shuffledLetters = word.toCharArray().toList().shuffled()
                //Converted to .toList().shuffled() from .shuffled() since .shuffled() alone is not present in earlier versions of Kotlin
                val alphabet = "abcdefghijklmnopqrstuvwxyz"
                val remainingLetters =
                    (alphabet.toCharArray().toSet() - shuffledLetters.toSet()).shuffled()
                val allLetters = shuffledLetters + remainingLetters.subList(0, 16 - shuffledLetters.size)
                val gridLayout = findViewById<GridLayout>(R.id.gridLayout)
                gridLayout.rowCount = 4
                gridLayout.columnCount = 4

                // Iterate through each Button in the GridLayout and add a TextView with a letter
                var index = 0
                for (i in 0 until gridLayout.rowCount) {
                    for (j in 0 until gridLayout.columnCount) {
                        val button = gridLayout.getChildAt(index) as Button
                        button.text = allLetters[index].toString()
                        button.setOnClickListener {
                            val letter = button.text.toString()
                            //  button.setBackgroundColor()
                            if (answerSpace.text.toString()
                                    .replace("\\s".toRegex(), "").length == wordEntered.length
                            ) {
                                Toast.makeText(
                                    this@second_activity,
                                    "Word limit reached!!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                answerSpace.append(letter + "   ")
                            }

                        }
                        index++
                    }
                }
            }

            val resetBtn = findViewById<Button>(R.id.resetBtn)
            resetBtn.setOnClickListener() {
                Toast.makeText(
                    this@second_activity,
                    "Your selected word has been reset!",
                    Toast.LENGTH_SHORT
                ).show()
                answerSpace.text.clear()
                gridSetter()
            }


            gridSetter()

            fun checkDialogGenerator(score: Int) {
                val checkBuilder = AlertDialog.Builder(this)
                checkBuilder.setTitle("GAME OVER")
                    .setMessage("Your score: $score")
                    .setCancelable(true)
                    .setNegativeButton("Home") { dalogInterface, it ->
                        finish()
                    }
                    .setNeutralButton("Play Again") { dialogInterface, it ->
                        playGame()
                    }
                    .show()
            }

            var countOfAttempts = 0

            fun Timer() {


                val countdownTimer = object : CountDownTimer(30000, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        val secondsRemaining = millisUntilFinished / 1000
                        timerTextView.text = "Time: $secondsRemaining seconds"
                    }

                    override fun onFinish() {
                        timerTextView.text = "Time's up!"
                        Toast.makeText(
                            this@second_activity,
                            "You ran outta time!",
                            Toast.LENGTH_SHORT
                        ).show()
                        countOfAttempts += 1
                        when (countOfAttempts) {
                            1 -> {
                                heart1.visibility = INVISIBLE
                                Timer()
                            }

                            2 -> {
                                heart2.visibility = INVISIBLE
                                Timer()
                            }

                            3 -> {
                                heart3.visibility = INVISIBLE
                                Timer()
                            }
                        }
                    }
                }
                countdownTimer.start()
            }


            val checkBtn = findViewById<Button>(R.id.checkBtn)
            checkBtn.setOnClickListener() {
                val answerSpace = findViewById<EditText>(R.id.anspace)
                var answerChars = answerSpace.text.toString().replace("\\s".toRegex(), "")
                if (answerChars == wordEntered) {
                    Toast.makeText(
                        this@second_activity,
                        "You guessed it correct!",
                        Toast.LENGTH_SHORT
                    ).show()
                    when (countOfAttempts) {
                        0 -> checkDialogGenerator(300)
                        1 -> checkDialogGenerator(200)
                        2 -> checkDialogGenerator(100)
                    }
                } else {
                    countOfAttempts += 1
                    when (countOfAttempts) {
                        1 -> {
                            heart1.visibility = INVISIBLE
                         //   Timer()
                        }

                        2 -> {
                            heart2.visibility = INVISIBLE
                        //    Timer()
                        }

                        3 -> {
                            heart3.visibility = INVISIBLE
                         //   Timer()
                        }
                    }
                    Toast.makeText(
                        this@second_activity,
                        "OOps,You guessed it wrong!",
                        Toast.LENGTH_SHORT
                    ).show()
                    if (countOfAttempts == 3) {
                        val checkBuilder = AlertDialog.Builder(this)
                        checkBuilder.setTitle("GAME OVER")
                            .setMessage("You lost!")
                            .setCancelable(true)
                            .setNegativeButton("Home") { dalogInterface, it ->
                                finish()
                            }
                            .setNeutralButton("Play Again") { dialogInterface, it ->
                                playGame()
                            }
                            .show()

                    } else {
                        gridSetter()
                    }
                }
            }

           // Timer()
        }

        playGame()
    }

}

