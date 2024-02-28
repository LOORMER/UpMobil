package ru.btpit.nmedia

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.btpit.nmedia.databinding.PostCardBinding

typealias OnLikeListner = (post : Post) -> Unit

class PostViewHolder(
    private val binding: PostCardBinding,
    private val onLikeListner: OnLikeListner
)  : RecyclerView.ViewHolder(binding.root) {
    fun  bind(post: Post) {
        binding.apply {
            textViewHeader.text = post.header
            textViewContent.text = post.content
            textViewDateTime.text = post.dataTime
            textViewLike.text = post.amountlike.toString()
            textViewRepost.text = post.amountrepost.toString()
            imagebutnlike.setImageResource(
                if (post.isLike) R.drawable.redlikes else R.drawable.likes
            )
            imagebutnlike.setOnClickListener{
                onLikeListner(post)
            }
        }
    }
}

class PostAdapter (private val onLikeListner: OnLikeListner) : RecyclerView.Adapter<PostViewHolder>() {
    var list = emptyList<Post>()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = PostCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  PostViewHolder(binding, onLikeListner)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = list[position]
        holder.bind(post)
    }

    override fun getItemCount(): Int = list.size

    }
