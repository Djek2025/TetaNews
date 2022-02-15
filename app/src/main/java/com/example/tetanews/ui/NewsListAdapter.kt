package com.example.tetanews.ui

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tetanews.data.models.Article
import com.example.tetanews.databinding.NewsListItemBinding
import com.example.tetanews.ui.NewsListAdapter.*
import com.example.tetanews.utils.getTimeInHours
import com.squareup.picasso.Picasso
import kotlinx.coroutines.delay

class NewsListAdapter(private val newsList: List<Article>): RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            NewsListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.run {
            newsTitle.text = newsList[position].title
            newsTitle.isSelected = true
            newsDescription.text = newsList[position].description
            newsSource.text = newsList[position].source.name
            newsTimeTextView.text = newsList[position].getTimeInHours()

            newsOpen.setOnClickListener {
                holder.itemView.context.startActivity(
                    Intent(Intent.ACTION_VIEW, Uri.parse(newsList[position].url))
                )
            }

            Picasso
                .get()
                .load(newsList[position].urlToImage)
                .into(newsImgView)
        }
    }

    override fun getItemCount(): Int = newsList.size

    class ViewHolder(val binding: NewsListItemBinding) : RecyclerView.ViewHolder(binding.root)

}