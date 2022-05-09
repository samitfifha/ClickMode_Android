package tn.esprit.myapplication.Fragements

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import tn.esprit.myapplication.R
import tn.esprit.myapplication.adapter.produitAdapter
import tn.esprit.myapplication.viewModel.viewModelProduit

lateinit var adapter2:produitAdapter
lateinit var recyclerViewequipe: RecyclerView

class Home_Fragement : Fragment(R.layout.fragment_home__fragement) {
    // TODO: Rename and change types of parameters

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_home__fragement, container, false)
        recyclerViewequipe = rootView.findViewById(R.id.recycleHome)
        recyclerViewequipe.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        adapter2 = produitAdapter(this)
        recyclerViewequipe.adapter = adapter2
        initViewModel()
        return rootView
    }
    private fun initViewModel() {
        val viewModel: viewModelProduit = ViewModelProvider(this).get(viewModelProduit::class.java)
        viewModel.getLiveDataObserver().observe(viewLifecycleOwner, Observer {
            if(it != null) {
                adapter2.setLigueList(it)
                adapter2.notifyDataSetChanged()
            } else {
                Toast.makeText(context, "Error in getting list", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.makeApiCall2(context)
    }


}
