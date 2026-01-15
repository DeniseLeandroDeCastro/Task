package com.denise.castro.task.ui.form

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.denise.castro.task.R
import com.denise.castro.task.databinding.FragmentFormTaskBinding
import com.denise.castro.task.helper.FirebaseHelper
import com.denise.castro.task.model.Task
import com.google.android.material.snackbar.Snackbar

class FormTaskFragment : Fragment() {

    private val args: FormTaskFragmentArgs by navArgs()

    private var _binding: FragmentFormTaskBinding? = null
    private val binding get() = _binding!!

    private lateinit var task: Task
    private var newTask: Boolean = true
    private var statusTask: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFormTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getArgs()
        initListeners()
    }

    private fun getArgs() {
        args.task.let {
            if (it != null) {
                task = it
                configTask()
            }
        }
    }

    private fun configTask() {
        newTask = false
        statusTask = task.status
        binding.textTaskNewOrEdit.text = "Editando tarefa"

        binding.edtDescription.setText(task.description)
        setStatus()
    }

    private fun setStatus() {
        when(task.status) {
            0 -> { binding.radioGroup.check(R.id.rbToDo) }
            1 -> { binding.radioGroup.check(R.id.rbDoing) }
            else -> { binding.radioGroup.check(R.id.rbDone) }
        }
    }

    private fun initListeners() {
        binding.apply {
            btnSaveNewTask.setOnClickListener { validateTask() }
            radioGroup.setOnCheckedChangeListener { _, id ->
                statusTask = when(id) {
                    R.id.rbToDo -> 0
                    R.id.rbDoing -> 1
                    else -> 2
                }
            }
        }
    }

    private fun validateTask() {
        val description = binding.edtDescription.text.toString().trim()

        if (description.isNotEmpty()) {
            binding.btnSaveNewTask.setLoading()

            if (newTask) {
                task = Task(description = description, status = statusTask)
            } else {
                task.description = description
                task.status = statusTask
            }

            saveTask()

        } else {
            binding.edtDescription.error = "Preencha a descrição"
        }
    }


    private fun saveTask() {
        val databaseRef = FirebaseHelper
            .getDataBase()
            .child("task")
            .child(FirebaseHelper.getIdUser() ?: "")

        // 2. SE FOR TAREFA NOVA, GERA UM ID NOVO
        if (newTask) {
            task.id = databaseRef.push().key ?: ""
        }

        databaseRef
            .child(task.id)
            .setValue(task)
            .addOnCompleteListener { result ->
                // 3. A LÓGICA AGORA ESTÁ DENTRO DO LISTENER
                binding.btnSaveNewTask.setNormal()

                if (result.isSuccessful) {
                    val message = if (newTask) "Tarefa salva com sucesso!" else "Tarefa atualizada com sucesso!"
                    Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()

                    if (newTask) {
                        findNavController().popBackStack()
                    }
                } else {
                    Snackbar.make(binding.root, "Erro ao salvar a tarefa!", Snackbar.LENGTH_SHORT).show()
                }
            }.addOnFailureListener { exception ->
                binding.btnSaveNewTask.setNormal()
                Snackbar.make(binding.root, "Erro: ${exception.message}", Snackbar.LENGTH_SHORT).show()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}