package tn.esprit.myapplication

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.myapplication.Models.User
import tn.esprit.myapplication.api.RetrofiteInstance

class Register : AppCompatActivity() {
    lateinit var signin : TextView

    lateinit var  imageViewR: ImageView
    lateinit var firstEditText: TextInputEditText
    lateinit var firstLayoutRegister: TextInputLayout

    lateinit var lastEditText: TextInputEditText
    lateinit var lastLayoutRegister: TextInputLayout

    lateinit var addressEditText: TextInputEditText
    lateinit var addressLayoutRegister: TextInputLayout

    lateinit var passwordEditText: TextInputEditText
    lateinit var passwordLayoutRegister: TextInputLayout

    lateinit var confirmePasswordEditText: TextInputEditText
    lateinit var confirmePasswordLayoutRegister: TextInputLayout

    lateinit var emailEditText: TextInputEditText
    lateinit var emailLayoutRegister: TextInputLayout

    lateinit var phoneEditText: TextInputEditText
    lateinit var phoneLayoutRegister: TextInputLayout

    lateinit var  buttonR: Button

    lateinit var  takeImage: Button

    private var selectedImageUri: Uri? = null

    companion object {
        val IMAGE_REQUEST_CODE = 1_000;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar?.hide();
        signin=findViewById(R.id.textView)



        buttonR =findViewById(R.id.registerButton)

        takeImage =findViewById(R.id.registerImageButton)

        imageViewR =findViewById(R.id.imageView4)

        firstEditText=findViewById(R.id.firstRegister)
        firstLayoutRegister=findViewById(R.id.textInputLayout6)

        phoneEditText=findViewById(R.id.phoneRegister)
        phoneLayoutRegister=findViewById(R.id.textInputLayout3)

        lastEditText=findViewById(R.id.lastRegister)
        lastLayoutRegister=findViewById(R.id.textInputLayout4)

        addressEditText=findViewById(R.id.adressRegister)
        addressLayoutRegister=findViewById(R.id.txtLayoutAdress)

        passwordEditText=findViewById(R.id.passwordregister)
        passwordLayoutRegister=findViewById(R.id.textInputLayout5)

        confirmePasswordEditText=findViewById(R.id.confirmePaswordRegister)
        confirmePasswordLayoutRegister=findViewById(R.id.textInputLayout7)

        emailEditText=findViewById(R.id.emailRegister)
        emailLayoutRegister=findViewById(R.id.txtLayoutLogin)

        takeImage.setOnClickListener {
            pickImageFromGallery()

        }
        signin.setOnClickListener{
            val i = Intent(this,Login::class.java)
            startActivity(i)

        }
        buttonR.setOnClickListener {
            val firstName = firstEditText.text.toString().trim()
            val lastName = lastEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val phone = phoneEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val confirmPassword = confirmePasswordEditText.text.toString().trim()
            val address = addressEditText.text.toString().trim()
            val role = "hhhhhhhh"


            val parcelFileDescriptor = contentResolver.openFileDescriptor(selectedImageUri!!, "r", null) ?: return@setOnClickListener
            doRegister(
                firstName,lastName,
                email,
                password,
                phone,address,role



            )
            print(parcelFileDescriptor);

        }
    }

    private fun doRegister(firstname: String, lastName: String, email: String, adress: String,password : String,phone : String,role : String){
        if (validate()) {

            if (selectedImageUri == null) {
                println("image null")

                return
            }
            val stream = contentResolver.openInputStream(selectedImageUri!!)
            println("-------------------------------------" + stream)
            val request =
                stream?.let {
                    RequestBody.create(
                        "image/jpg".toMediaTypeOrNull(),
                        it.readBytes()
                    )
                } // read all bytes using kotlin extension
            val image = request?.let {
                MultipartBody.Part.createFormData(
                    "image",
                    "image.jpg",
                    it
                )
            }
            Log.d("MyActivity", "on finish upload file")

            val apiInterface = RetrofiteInstance.api(this)
            val data: LinkedHashMap<String, RequestBody> = LinkedHashMap()

            data["firstname"] = firstname.toRequestBody(MultipartBody.FORM)
            data["lastname"] = lastName.toRequestBody(MultipartBody.FORM)
            data["email"] = email.toRequestBody(MultipartBody.FORM)
            data["adress"] = adress.toRequestBody(MultipartBody.FORM)
            data["role"] = role.toRequestBody(MultipartBody.FORM)
            data["password"] = password.toRequestBody(MultipartBody.FORM)
            data["phone"] = phone.toRequestBody(MultipartBody.FORM)
            if (image?.body != null) {

                println("++++++++++++++++++++++++++++++++++++" + image)
                apiInterface.register(data, image).enqueue(object :
                    Callback<User> {
                    override fun onResponse(
                        call: Call<User>,
                        response: Response<User>
                    ) {
                        if (response.isSuccessful) {
                            Log.i("onResponse goooood", response.body().toString())
                            //showAlertDialog()
                            Toast.makeText(this@Register, "welcome ", Toast.LENGTH_SHORT).show()

                        } else {
                            Log.i("OnResponse not good", response.body().toString())
                        }
                    }

                    override fun onFailure(call: Call<User>, t: Throwable) {
                        //progress_bar.progress = 0
                        Toast.makeText(this@Register, "Connexion error!", Toast.LENGTH_SHORT).show()

                        Log.w("response:",t.localizedMessage.toString())
                        Log.w("response:","t.localizedMessage.toString()")
                    }

                })
            }
            else{
                Toast.makeText(this@Register, "Choisir une image!", Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun validate(): Boolean {
        firstLayoutRegister.error = null
        phoneLayoutRegister.error = null
        emailLayoutRegister.error = null
        confirmePasswordLayoutRegister.error = null
        passwordLayoutRegister.error = null
        lastLayoutRegister.error = null
        addressLayoutRegister.error = null


        if (firstEditText.text!!.isEmpty()) {
            firstLayoutRegister.error = getString(R.string.mustNotBeEmpty)
            return false
        }
        if (phoneEditText.text!!.isEmpty()) {
            phoneLayoutRegister.error = getString(R.string.mustNotBeEmpty)
            return false
        }
        if (emailEditText.text!!.isEmpty()) {
            emailLayoutRegister.error = getString(R.string.mustNotBeEmpty)
            return false
        }
        if (confirmePasswordEditText.text!!.isEmpty()) {
            confirmePasswordLayoutRegister.error = getString(R.string.mustNotBeEmpty)
            return false
        }

        if (passwordEditText.text!!.isEmpty()) {
            passwordLayoutRegister.error = getString(R.string.mustNotBeEmpty)
            return false
        }
        if (addressEditText.text!!.isEmpty()) {
            addressLayoutRegister.error = getString(R.string.mustNotBeEmpty)
            return false
        }
        if (lastEditText.text!!.isEmpty()) {
            lastLayoutRegister.error = getString(R.string.mustNotBeEmpty)
            return false
        }
        /*
        val checkedRadioButton = group?.findViewById(group.checkedRadioButtonId) as? RadioButton
        checkedRadioButton?.let {
            if (checkedRadioButton.isChecked)
                Toast.makeText(applicationContext, "RadioGroup: ${group?.contentDescription} RadioButton: ${checkedRadioButton?.text}", Toast.LENGTH_LONG).show()
        }
*/

        return true
    }
    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {
            selectedImageUri = data?.data

            imageViewR?.setImageURI(selectedImageUri)
            //takeImage.hide()

        }
    }


}