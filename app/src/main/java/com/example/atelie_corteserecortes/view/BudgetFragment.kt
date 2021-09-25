package com.example.atelie_corteserecortes.view

import android.app.AlertDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.atelie_corteserecortes.MyDatabase
import com.example.atelie_corteserecortes.MyViewModel
import com.example.atelie_corteserecortes.R
import com.example.atelie_corteserecortes.ViewModelFactory
import com.example.atelie_corteserecortes.databinding.FragmentBudgetBinding
import com.example.atelie_corteserecortes.db.Repository

class BudgetFragment : Fragment() {

    private lateinit var binding: FragmentBudgetBinding
    lateinit var viewModel: MyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_budget, container, false)

        val dao = MyDatabase.getInstance(requireContext()).budgetDAO
        val repository = Repository(dao)
        val factory = ViewModelFactory(repository)

        viewModel = ViewModelProvider(requireActivity(), factory).get(MyViewModel::class.java)

        binding.viewModelTag = viewModel
        binding.lifecycleOwner = activity

        binding.btnSeeList.setOnClickListener{
            findNavController().navigate(R.id.action_budgetFragment_to_budgetListFragment)
        }

        displayList()
        return binding.root
    }

    private fun displayList(){
        viewModel.budget.observe(viewLifecycleOwner, Observer {
            Log.i("MYTAG", it.toString())
        })

        viewModel.onClear.observe(viewLifecycleOwner, Observer {
            if(it == true) {
                vibratePhone()
                alertaLimparLista()
            }
        })
    }

    fun alertaLimparLista(){
        var alert = AlertDialog.Builder(context)
        alert.setTitle("Tem certeza de que deseja limpar a lista de peças para orçamento?")
        alert.setMessage("Toda a sua lista anteriormente salva será perdida!")
        alert.setCancelable(false)
        alert.setPositiveButton(
            "Confirmar"
        ) { dialog, which ->
            viewModel.clearAll()
            Toast.makeText(context, "Toda sua lista foi removida.", Toast.LENGTH_LONG).show()
            viewModel.setOnClearState(false)
        }
        alert.setNegativeButton(
            "Cancelar"
        ) { dialog, which ->
            viewModel.setOnClearState(false)
        }
        val alertDialog = alert.create()
        alertDialog.show()
    }

    fun vibratePhone() {
        val vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(200)
        }
    }

}