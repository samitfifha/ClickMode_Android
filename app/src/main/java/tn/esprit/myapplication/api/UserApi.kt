package tn.esprit.myapplication.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

import tn.esprit.myapplication.Models.User
import tn.esprit.myapplication.Models.Product

interface UserApi {
    @POST("/api/login")
    fun seConnecter(@Body user: User): Call<User>

    @GET("/api/users/profile/{id}")
    fun getProfile(@Path("id") id : String):Call<User>



    @Multipart
    @POST("/api/users")
    fun register(@PartMap data : LinkedHashMap<String, RequestBody>,
                 @Part image: MultipartBody.Part
    ): Call<User>

    @GET("/api/products")
    fun getProduct():Call<List<Product>>




/*

    companion object {

         val  BASE_URL = "http://192.168.56.1:3000/"

        fun create() : UserApi {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(UserApi::class.java)
        }
    }
    */

}