package tn.esprit.myapplication.Fragements

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import retrofit2.Response
import tn.esprit.myapplication.Models.User
import tn.esprit.myapplication.R
import tn.esprit.myapplication.api.RetrofiteInstance
import retrofit2.Call
import retrofit2.Callback


class Profile_Fragement : Fragment() {
    lateinit var nom : TextView
    lateinit var prenom : TextView
    //lateinit var address : TextView
    //lateinit var phone : TextView
    lateinit var button: Button
    lateinit var email : TextView
    lateinit var imageView: ImageView
    lateinit var mSharedPref : SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_profile__fragement, container, false)
        nom = rootView.findViewById(R.id.profileNom)
        prenom = rootView.findViewById(R.id.profilePrenom)
        email = rootView.findViewById(R.id.profileEmail)
        //phone = rootView.findViewById(R.id.profilePhone)
        //address = rootView.findViewById(R.id.profileadress)
        button = rootView.findViewById(R.id.button23)
        imageView = rootView.findViewById(R.id.profileImage)


        mSharedPref = requireActivity().getSharedPreferences("LOGIN_PREF", AppCompatActivity.MODE_PRIVATE);
        val idUser: String = mSharedPref.getString("USER_ID", null).toString()
        println("***********************************")
        println(idUser)
        println("***********************************")

        /*
        val nomStr: String = mSharedPref.getString("NOM", null).toString()
        val prenomStr: String = mSharedPref.getString("PRENOM", null).toString()
        val emailStr: String = mSharedPref.getString("EMAIL", null).toString()
        val imageStr: String = mSharedPref.getString("IMAGE2", null).toString()
        println("***********************************")
        println(imageStr)
        println("***********************************")

        val index = 7

        val imageStr1: String = imageStr.substring(0, index) + '/' + imageStr.substring(index + 1)
*/
        val apiInterface = RetrofiteInstance.api(context)
        apiInterface.getProfile(idUser).enqueue(object : Callback<User> {


            override fun onFailure(call: Call<User>, t: Throwable) {
                print(idUser)
                print("dqsdqsd")

                Toast.makeText(context, "Probleme de connection", Toast.LENGTH_SHORT).show()

            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                print("sdqsdqsdqsdqsd");
                if (response.isSuccessful){
                    val user : User = response.body()!!
                    print("sdqsdqsdqsdqsd")
                    nom.text = user.lastname

                    prenom.text = user.firstname
                    email.text = user.email
                    //address.text = user.adress
                    //phone.text = user.phone

                    val replaced = user.image!!.replace("\\", "/")

                    Glide.with(this@Profile_Fragement).load(RetrofiteInstance.BASE_URL + replaced).into(imageView)


                }

            }


        })


        // Inflate the layout for this fragment
        return rootView
    }

}