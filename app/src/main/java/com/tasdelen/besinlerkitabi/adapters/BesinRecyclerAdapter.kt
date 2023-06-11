package com.tasdelen.besinlerkitabi.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.tasdelen.besinlerkitabi.data.Besin
import com.tasdelen.besinlerkitabi.R
import com.tasdelen.besinlerkitabi.databinding.RecyclerRowBinding
import com.tasdelen.besinlerkitabi.util.getImage
import com.tasdelen.besinlerkitabi.view.BesinListesiFragmentDirections

class BesinRecyclerAdapter(val besinListesi : ArrayList<Besin>) : RecyclerView.Adapter<BesinRecyclerAdapter.BesinVH>() {
    class BesinVH(var view : RecyclerRowBinding) : ViewHolder(view.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BesinVH {
        val inflater = LayoutInflater.from(parent.context)
        return BesinVH(
            DataBindingUtil.inflate(inflater, R.layout.recycler_row, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return besinListesi.size
    }

    override fun onBindViewHolder(holder: BesinVH, position: Int) {

        holder.view.besin = besinListesi[position]


        holder.itemView.setOnClickListener {
            val action = BesinListesiFragmentDirections.actionBesinListesiFragmentToBesinDetayiFragment(besinListesi[position].uuid)
            Navigation.findNavController(it).navigate(action)
        }
    }

    fun updateBesinListesi(yeniBesinListesi : List<Besin>) {
        besinListesi.clear()
        besinListesi.addAll(yeniBesinListesi)
        notifyDataSetChanged()
    }
}