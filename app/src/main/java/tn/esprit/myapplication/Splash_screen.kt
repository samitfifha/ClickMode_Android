package tn.esprit.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class Splash_screen : AppCompatActivity() {

    lateinit var handler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen2)

//splash screen 3s
        handler= Handler()
        supportActionBar?.hide();

        handler.postDelayed({
            val intent= Intent(this,Get_started::class.java)
            startActivity(intent)
            finish()
        },3000)

    }
}