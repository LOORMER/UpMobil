package ru.btpit.nmedia

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.btpit.nmedia.databinding.PostCardBinding

typealias OnLikeListener = (post : Post) -> Unit
typealias OnRemoveListener = (post : Post) -> Unit
typealias OnShareListener = (post : Post) -> Unit

class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }
}

class PostViewHolder(
    private val binding: PostCardBinding,
    private val onLikeListener: OnLikeListener,
    private val onShareListener: OnShareListener,
    private val onRemoveListener: OnRemoveListener
)  : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        binding.apply {
            header.text = post.header
            Content.text = post.content
            dateTime.text = post.dataTime
            like.text = numberRangeSwitch(post.amountlike)
            repost.text = numberRangeSwitch(post.amountrepost)
            likeButton.setBackgroundResource(
                if (post.isLike)
                    R.drawable.likeon
                else
                    R.drawable.likeoff
            )
            likeButton.setOnClickListener{
                onLikeListener(post)
            }
            repostButton.setOnClickListener{
                onShareListener(post)
            }
            menu.setOnClickListener{
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.options_post)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                onRemoveListener(post)
                                true
                            }
                            else -> false
                        }
                    }
                }.show()
            }
        }
    }
}

class PostsAdapter(
    private val onLikeListener: OnLikeListener,
    private val onShareListener: OnShareListener,
    private val onRemoveListener: OnRemoveListener
) : ListAdapter<Post, PostViewHolder>(PostDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = PostCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding,
            onLikeListener,
            onShareListener,
            onRemoveListener
        )
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }
}

private fun numberRangeSwitch(value: Int): String { // Switches display based on the size of the number
    val v1: String = value.toString()
    return if (value > 999)
        when(value) {
            in 1000..1099  -> v1[0].toString() + "K"
            in 1100 .. 9999 -> v1[0].toString() + "." + v1[1].toString() + "K"
            in 10000 .. 99999 -> v1[0].toString() + v1[1].toString() + "K"
            in 100_000 .. 999_999 -> v1[0].toString() + v1[1].toString() + v1[2].toString() + "K"
            in 1_000_000 .. 999_999_999 -> v1[0].toString() + "." + v1[1].toString() + "M"
            else -> "ꚙ"
        } else value.toString()
}