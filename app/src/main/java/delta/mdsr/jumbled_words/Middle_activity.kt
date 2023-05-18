package delta.mdsr.jumbled_words

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class Middle_activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_middle)

        var clueEntered = intent.getStringExtra("Clue")
        var wordEntered1: String = intent.getStringExtra("Word").toString()

        val classicBtn = findViewById<Button>(R.id.classic_btn)
        val speedRun = findViewById<Button>(R.id.speedbtn)
        val scoreDisplayer = findViewById<TextView>(R.id.scoredisplayer)

        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        val username = sharedPreferences.getString("username", "Default value")
        val score = sharedPreferences.getInt("score", 0)

        scoreDisplayer.text = "Previous game:\n User: $username \nScore:$score"

        classicBtn.setOnClickListener(){
            val userName = findViewById<EditText>(R.id.etusername).toString()
            val intent = Intent(this, second_activity::class.java)
            intent.putExtra("Word",wordEntered1)
            intent.putExtra("Clue",clueEntered)
            intent.putExtra("Username",userName)
            startActivity(intent)
        }
    }

}