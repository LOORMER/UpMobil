package ru.btpit.nmedia

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.btpit.nmedia.databinding.ActivityMainBinding
import ru.btpit.nmedia.databinding.PostCardBinding

class MainActivity : AppCompatActivity() {
    var present_value_int = 0


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewModel:PostViewModel by viewModels()
        val adapter = PostAdapter{
            viewModel.likeById(it.id)
        }
        binding.list.adapter = adapter
        viewModel.data.observe(this) {post ->
            adapter.list = post


          }
        }


    }

    fun toStringFromNumb(count: Int): String
    {
        return when(count){
            in 0..<1_000 -> count.toString()
            in 1000..<1_100-> "1K"
            in 1_100..<10_000 -> ((count/100).toFloat()/10).toString() + "K"
            in 10_000..<1_000_000 -> (count/1_000).toString() + "K"
            in 1_000_000..<1_100_000 -> "1M"
            in 1_100_000..<10_000_000 -> ((count/100_000).toFloat()/10).toString() + "M"
            in 10_000_000..<1_000_000_000 -> (count/1_000_000).toString() + "M"
            else -> "ꚙ"
        }
    }
