package delta.mdsr.jumbled_words

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val wordField = findViewById<EditText>(R.id.etword)
        val clueField = findViewById<EditText>(R.id.etclue)
        var wordEntered = ""
        var clueEntered = ""
        val startButton = findViewById<Button>(R.id.btnstart)

        startButton.setOnClickListener{
            var wordEntered = wordField.text.toString()
            var clueEntered = clueField.text.toString()

            if (wordEntered == ""){
                Toast.makeText(this@MainActivity,"Enter the word of the game",Toast.LENGTH_SHORT).show() //forgot the .show() part
            }
            else if(wordEntered.length > 16){
                Toast.makeText(this@MainActivity,"Your word has exceeded the limit of 16 characters",Toast.LENGTH_SHORT).show()
            }
            else if(clueEntered == ""){
                Toast.makeText(this@MainActivity,"Enter the clue for the word",Toast.LENGTH_SHORT).show()
            }
            else {
                val intent = Intent(this, second_activity::class.java)
                intent.putExtra("Word",wordEntered)
                intent.putExtra("Clue",clueEntered)
                startActivity(intent)
           }

            }
        }
    }



