package com.example.newsapp.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.NavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.presentation.items.NewItem
import com.google.android.material.tabs.TabItem
import com.squareup.picasso.Picasso

class RecycleAdapter(
    val onClick: (NewItem) -> Unit,
    val onShareClick: (NewItem) -> Unit,
    val onSaveClick: (NewItem) -> Unit
) : ListAdapter<NewItem, RecycleAdapter.ViewHolder>(DiffCallback) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivImage: ImageView
        val tvTitle: TextView
        val tvSubtitle: TextView
        val tvWeb: TextView
        val root: CardView
        val ivSave: ImageView
        val ivShare: ImageView


        init {
            ivImage = view.findViewById(R.id.ivImage)
            tvTitle = view.findViewById(R.id.tvTitle)
            tvSubtitle = view.findViewById(R.id.tvSubtitle)
            tvWeb = view.findViewById(R.id.tvWebLink)
            root = view.findViewById(R.id.root)
            ivSave = view.findViewById(R.id.ivSave)
            ivShare = view.findViewById(R.id.ivShare)

        }
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_new, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        with(viewHolder) {
            root.setOnClickListener {
                onClick(getItem(position))
            }

            ivSave.setOnClickListener { onSaveClick(getItem(position)) }
            ivShare.setOnClickListener { onShareClick(getItem(position)) }
            Picasso.with(viewHolder.itemView.context).load(getItem(position).iconUrl).fit()
                .into(ivImage)
            tvTitle.text = getItem(position).title
            tvSubtitle.text = getItem(position).subtitle
            tvWeb.text = getItem(position).web
        }
    }

    override fun getItemCount() = currentList.size

    object DiffCallback : DiffUtil.ItemCallback<NewItem>() {

        override fun areItemsTheSame(oldItem: NewItem, newItem: NewItem) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: NewItem, newItem: NewItem) =
            oldItem.iconUrl == newItem.iconUrl &&
                    oldItem.title == newItem.title &&
                    oldItem.subtitle == newItem.subtitle &&
                    oldItem.web == newItem.web
    }
}

