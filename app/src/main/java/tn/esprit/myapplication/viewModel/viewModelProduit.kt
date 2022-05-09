package tn.esprit.myapplication.viewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.myapplication.api.RetrofiteInstance
import tn.esprit.myapplication.Models.Product

class viewModelProduit : ViewModel() {
    lateinit var lifeDataList: MutableLiveData<List<Product>?>
    init {
        lifeDataList = MutableLiveData()

    }
    fun getLiveDataObserver(): MutableLiveData<List<Product>?> {
        return lifeDataList
    }
    fun makeApiCall2(context: Context?){
        val apiInterface = RetrofiteInstance.api(context)
        apiInterface.getProduct().enqueue(object : Callback<List<Product>> {
            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                lifeDataList.postValue(null)
            }

            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                lifeDataList.postValue(response.body())
            }


        })

    }


}