package com.example.cameraapplication.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cameraapplication.R

class DashboardAdapter(private val profile: List<NameProfile>) : RecyclerView.Adapter<DashboardHolder>() {


  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): DashboardHolder {
    val inflater = LayoutInflater.from(parent.context)
    val view = inflater.inflate(R.layout.system_layout, parent, false)
    return DashboardHolder(view)
  }

  override fun onBindViewHolder(
    holder: DashboardHolder,
    position: Int
  ) {
    holder.showPhoto?.setImageResource(profile[position].image)
    holder.showName?.text = profile[position].title
    holder.showTime?.text = profile[position].time
    holder.showDesc?.text = profile[position].desc
//    holder.showLikeCount?.text = profile[position].likeCount
//    holder.showCommentCount?.text = profile[position].commentCount
  }

  override fun getItemCount(): Int {
   return profile.size
  }
}

class DashboardHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView){
  val showPhoto: ImageView? = itemView.findViewById<ImageView>(R.id.item_gallery_post_image_imageview)
  val showName: TextView? = itemView.findViewById(R.id.display_name_text_view)
  val showTime: TextView? = itemView.findViewById(R.id.post_time_text_view)
  val showDesc: TextView? = itemView.findViewById(R.id.description_textview)
  val showLikeCount: TextView? = itemView.findViewById(R.id.like_count_textview)
  val showCommentCount: TextView? = itemView.findViewById(R.id.comment_count_textview)
}