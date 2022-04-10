package com.adhitya.themovielist.genre

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.adhitya.themovielist.R
import com.adhitya.themovielist.model.GenreResponse
import com.google.android.material.chip.Chip

class GenreAdapter(private val genres: GenreResponse?, private val context: Context) : RecyclerView.Adapter<GenreAdapter.GenreViewHolder>() {

    private var selectedPosition: Int = 0

    class GenreViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView) {
        var genreText: Chip = itemView.findViewById(R.id.rv_item_genres)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_genres, parent, false)
        return GenreViewHolder(view)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.genreText.setText(genres?.genres?.get(position)?.name)
        if (selectedPosition == position) {
            holder.genreText.isChecked = true
            Toast.makeText(context, holder.genreText.text, Toast.LENGTH_SHORT).show()
        } else {
            holder.genreText.isChecked = false
        }
        holder.genreText.setOnClickListener {
            if (selectedPosition >= 0) notifyItemChanged(selectedPosition)
            selectedPosition = holder.adapterPosition
            notifyItemChanged(selectedPosition)
        }
    }

    override fun getItemCount(): Int = genres?.genres?.size!!
}