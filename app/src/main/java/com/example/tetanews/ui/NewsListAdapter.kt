package com.example.tetanews.ui

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.tetanews.R
import com.example.tetanews.data.models.Article
import com.example.tetanews.databinding.NewsListItemBinding
import com.example.tetanews.ui.NewsListAdapter.*
import com.example.tetanews.utils.getTimeInHours
import com.squareup.picasso.Picasso

class NewsListAdapter(private var newsList: List<Article> = listOf()) :
    RecyclerView.Adapter<ViewHolder>() {

    private var filteredNewsList: List<Article> = listOf()

    fun setNewsList(data: List<Article>) {
        newsList = data
    }

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
            val data = dataSelector()
            newsTitle.text = data[position].title
            cardView.setOnClickListener { newsTitle.run { isSelected = !isSelected } }
            newsDescription.text = data[position].description
            newsSource.text = data[position].source.name
            newsTimeTextView.text = data[position].getTimeInHours()

            newsOpen.setOnClickListener {
                holder.itemView.context.startActivity(
                    Intent(Intent.ACTION_VIEW, Uri.parse(data[position].url))
                )
            }

            Picasso
                .get()
                .load(data[position].urlToImage)
                .placeholder(R.drawable.ic_baseline_image_24)
                .fit().centerCrop()
                .into(newsImgView)
        }
    }

    private fun dataSelector(): List<Article> {
        return when (filteredNewsList.isNullOrEmpty()) {
            true -> newsList
            false -> filteredNewsList
        }
    }

    override fun getItemCount(): Int = dataSelector().size

    class ViewHolder(val binding: NewsListItemBinding) : RecyclerView.ViewHolder(binding.root)

    val filterObj = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList: MutableList<Article> = mutableListOf()

            constraint?.let {
                val query = constraint.toString().trim().lowercase()
                newsList.forEach {
                    if (it.run { (author + content + description + title).lowercase() }.contains(query)) {
                        filteredList.add(it)
                    }
                }
            }
            return FilterResults().apply { values = filteredList }
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            filteredNewsList = results?.values as List<Article>
            notifyDataSetChanged()
        }
    }
}