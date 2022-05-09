package tn.esprit.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import tn.esprit.myapplication.Models.Product
import tn.esprit.myapplication.R
import tn.esprit.myapplication.api.RetrofiteInstance

class produitAdapter(val activity : Fragment): RecyclerView.Adapter<produitAdapter.produitViewHolder>() {

    private var produitListe: List<Product>? = null


     class produitViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val imageProduit : ImageView = itemView.findViewById(R.id.imageView6)
        val nomProduit : TextView = itemView.findViewById(R.id.nomProduit)
        val prixProduit : TextView = itemView.findViewById(R.id.prixProduit)
        fun bind(data: Product) {

            nomProduit.text = data.nameproduct
            prixProduit.text = data.price



            Glide.with(itemView).load(RetrofiteInstance.BASE_URL + data.image).into(imageProduit)
            //GlideToVectorYou.justLoadImage(activity, Uri.parse(data.flag), stadePic)

        }


    }
    fun setLigueList(produitListe: List<Product>?) {
        this.produitListe = produitListe
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): produitViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_produit, parent, false)
        return produitViewHolder(view)
    }

    override fun onBindViewHolder(holder: produitViewHolder, position: Int) {
        holder.bind(produitListe?.get(position)!!)
    }

    override fun getItemCount(): Int {
        if(produitListe == null)return 0
        else return produitListe?.size!!
    }
}