package com.lvddr.coronacounter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lvddr.coronacounter.models.Corona
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_counter_list_item.view.*

class RecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<Corona> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    return CoronaViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.layout_counter_list_item, parent, false)
    )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is CoronaViewHolder -> {
                holder.bind(items.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size

    }

    fun submitList(blogList: List<Corona>) {
        items = blogList
    }

    class CoronaViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val blogTitle = itemView.blog_title
        val blogAuthor = itemView.blog_author

        fun bind(blogPost:Corona) {
            blogTitle.setText(blogPost.title)
            blogAuthor.setText(blogPost.username)


        }
    }

}