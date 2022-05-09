package tn.esprit.myapplication.Models

data class User(    val _id: String ? = null,
                    val phone: String ? = null,
                    var email: String ? = null,
                    val image: String ? = null,
                    var password: String ? = null,
                    val firstname: String ? = null,
                    val lastname: String ? = null,
                    val adress: String ? = null,
                    val role:String?=null,
                    val token:String?=null,




)
