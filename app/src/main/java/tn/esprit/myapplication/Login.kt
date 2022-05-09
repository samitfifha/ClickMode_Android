package tn.esprit.myapplication

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.myapplication.Models.User
import tn.esprit.myapplication.api.RetrofiteInstance

const val PREF_NAME = "LOGIN_PREF"
const val EMAIL = "EMAIL"
const val FIRST_NAME = "FIRST_NAME"
const val LAST_NAME = "LAST_NAME"
const val IMAGE2 = "IMAGE2"
const val PASSWORD = "PASSWORD"
const val ADRESS = "ADRESS"
const val ROLE = "ROLE"
const val TOKEN = "TOKEN"
const val PANIER = "PANIER"
const val USER_ID = "USER_ID"
const val IS_REMEMBRED = "IS_REMEMBRED"


class Login : AppCompatActivity() {
    lateinit var emailEditText: TextInputEditText
    lateinit var emailLayoutLogin: TextInputLayout

    lateinit var passwordEditText: TextInputEditText
    lateinit var passwordLayoutLogin: TextInputLayout

    lateinit var loginButton: Button
    lateinit var signup : TextView

    lateinit var  sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide();
        signup=findViewById(R.id.signuptxt)
        signup.setOnClickListener{
            val i = Intent(this,Register::class.java)
            startActivity(i)

        }
        emailEditText = findViewById(R.id.loginEmail)
        emailLayoutLogin = findViewById(R.id.txtLayoutLogin)

        passwordEditText = findViewById(R.id.loginPassword)
        passwordLayoutLogin = findViewById(R.id.txtLayoutAdress)


        loginButton = findViewById(R.id.registerButton)

        loginButton.setOnClickListener {
            doLogin()
        }
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        //progBar = findViewById(R.id.progressBar)

        if (sharedPreferences.getBoolean(IS_REMEMBRED, true)){
            navigate()
        }

    }
    private fun doLogin() {
        if (validate()) {
            //val apiInterface = UserApi.create()
            val apiInterface = RetrofiteInstance.api(this)
            //progBar.visibility = View.VISIBLE

            window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
            var user = User()
            user.email = emailEditText.text.toString()
            user.password = passwordEditText.text.toString()
            apiInterface.seConnecter(
                user
            ).enqueue(object :
                Callback<User> {

                override fun onResponse(call: Call<User>, response: Response<User>) {

                    val user = response.body()

                    if (user != null) {

                        Toast.makeText(this@Login, "Login Success", Toast.LENGTH_SHORT).show()
                        navigate()
                        println("DDDDDDDDDDDDDDDDDDDDDDD")
                        println(user._id)
                        println("DDDDDDDDDDDDDDDDDDDDDDD")
                        //TODO 4 "Edit the SharedPreferences by putting all the data"
                        sharedPreferences.edit().apply{
                            putBoolean("IS_REMEMBRED", true)
                            putString("EMAIL",user.email)
                            putString("TOKEN", user.token)
                            putString("USER_ID", user._id)
                            putString("NOM", user.lastname)
                            putString("PRENOM", user.firstname)
                            putString("IMAGE2", user.image)
                            println("DDDDDDDDDDDDDDDDDDDDDDD")
                            println(user.lastname)
                            println(user.firstname)

                        }.apply()


                    } else {
                        Toast.makeText(this@Login, "User not found", Toast.LENGTH_SHORT).show()
                    }

                    //progBar.visibility = View.INVISIBLE
                    window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                }

                override fun onFailure(call: Call<User>, t: Throwable) {

                    Toast.makeText(this@Login, "Connexion error!", Toast.LENGTH_SHORT).show()
                    Log.w("response:", t.localizedMessage.toString())

                    //progBar.visibility = View.INVISIBLE
                    window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                }

            })

        }



    }
    fun validate(): Boolean {
        emailLayoutLogin.error = null
        passwordLayoutLogin.error = null

        if (emailEditText.text!!.isEmpty()) {
            emailLayoutLogin.error = getString(R.string.mustNotBeEmpty)
            return false
        }

        if (passwordEditText.text!!.isEmpty()) {
            passwordLayoutLogin.error = getString(R.string.mustNotBeEmpty)
            return false
        }

        return true
    }
    private fun navigate(){
        val i = Intent(this,MainActivity::class.java)
        startActivity(i)
    }
}