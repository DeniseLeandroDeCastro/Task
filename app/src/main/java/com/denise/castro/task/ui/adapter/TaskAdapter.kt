package com.denise.castro.task.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.denise.castro.task.databinding.ItemAdapterBinding
import com.denise.castro.task.model.Task

class TaskAdapter(
    private val taskList: List<Task>,
    val taskSelected: (Task, Int) -> Unit
): RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    companion object{
        val SELECT_BACK: Int = 0
        val SELECT_EDIT: Int = 1
        val SELECT_REMOVE: Int = 2
        val SELECT_NEXT: Int = 3
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            ItemAdapterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TaskAdapter.TaskViewHolder, position: Int) {
        val task = taskList[position]
        holder.binding.textTitleCardToDo.text = task.description

        holder.binding.textRemove.setOnClickListener { taskSelected(task, SELECT_REMOVE) }
        holder.binding.textEdit.setOnClickListener { taskSelected(task, SELECT_EDIT) }

        when (task.status) {
            0 -> {
                holder.binding.btnBack.isVisible = false
                holder.binding.btnNext.isVisible = true
                holder.binding.btnNext.setOnClickListener { taskSelected(task, SELECT_NEXT) }
            }
            1 -> {
                holder.binding.btnBack.isVisible = true
                holder.binding.btnNext.isVisible = true
                holder.binding.btnBack.setOnClickListener { taskSelected(task, SELECT_BACK) }
                holder.binding.btnNext.setOnClickListener { taskSelected(task, SELECT_NEXT) }
            }
            else -> {
                holder.binding.btnBack.isVisible = true
                holder.binding.btnNext.isVisible = false
                holder.binding.btnBack.setOnClickListener { taskSelected(task, SELECT_BACK) }
            }
        }
    }

    override fun getItemCount() = taskList.size


    inner class TaskViewHolder(
        val binding: ItemAdapterBinding
    ) : RecyclerView.ViewHolder(binding.root)
}
