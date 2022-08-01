package com.barkote.mynotes.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.barkote.mynotes.MainActivity
import com.barkote.mynotes.const.NOTE_POSITION
import com.barkote.mynotes.data.NoteInfo
import com.barkote.mynotes.databinding.LayoutItemBinding

class NoteRecyclerAdapter(private val context: Context,private val notes: List<NoteInfo>) : RecyclerView.Adapter<NoteRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(notes[position]){
                binding.textCourse.text = this.course?.title
                binding.textTitle.text = this.title
            }
            holder.notePosition = position
        }
    }

    override fun getItemCount()= notes.size

   inner class ViewHolder(val binding: LayoutItemBinding) : RecyclerView.ViewHolder(binding.root){

       var notePosition = 0

        init {
            binding.root.setOnClickListener{
                val intent = Intent(context,MainActivity::class.java)
                intent.putExtra(NOTE_POSITION,notePosition)
                context.startActivity(intent)
            }
        }

   }

}