package tn.esprit.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Get_started : AppCompatActivity() {
    lateinit var  tologinButton: Button;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_started)
        supportActionBar?.hide();
        tologinButton=findViewById(R.id.inButton)
        tologinButton.setOnClickListener{
            val i = Intent(this,Login::class.java)
            startActivity(i)

    }
}}