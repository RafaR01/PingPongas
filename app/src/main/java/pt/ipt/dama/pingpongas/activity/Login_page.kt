package pt.ipt.dama.pingpongas.activity

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.widget.TextView
import pt.ipt.dama.pingpongas.R

class login_page : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        val textView6 = findViewById<TextView>(R.id.textView2)
        textView6.setOnTouchListener { view, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // Set the color when the touch is initially pressed down
                    textView6.setTextColor(Color.parseColor("#6996C6")) // Change the color to your desired color
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    // Reset the color when the touch is released or canceled
                    textView6.setTextColor(Color.parseColor("#2990FF")) // Change the color to your original color
                }
            }
            // Return false to allow the event to continue to other listeners (e.g., click listener)
            false
        }
        textView6.setOnClickListener {
            val intent = Intent(this, register_page::class.java)
            startActivity(intent)
        }
    }
}