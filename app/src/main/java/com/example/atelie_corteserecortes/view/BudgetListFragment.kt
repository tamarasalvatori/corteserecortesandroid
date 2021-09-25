package com.example.atelie_corteserecortes.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.atelie_corteserecortes.MyDatabase
import com.example.atelie_corteserecortes.MyViewModel
import com.example.atelie_corteserecortes.R
import com.example.atelie_corteserecortes.ViewModelFactory
import com.example.atelie_corteserecortes.databinding.FragmentBudgetBinding
import com.example.atelie_corteserecortes.databinding.FragmentBudgetListBinding
import com.example.atelie_corteserecortes.db.Repository
import android.content.Intent

class BudgetListFragment : Fragment() {
    private lateinit var binding: FragmentBudgetListBinding
    lateinit var viewModel: MyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_budget_list, container, false)

        val dao = MyDatabase.getInstance(requireContext()).budgetDAO
        val repository = Repository(dao)
        val factory = ViewModelFactory(repository)

        viewModel = ViewModelProvider(requireActivity(), factory).get(MyViewModel::class.java)

        viewModel.budget.observe(viewLifecycleOwner, Observer {
            var strBudgetList = ""
            var addGarment = 0
            it.forEach{
                addGarment++
                strBudgetList += "${it.garment} - ${it.description} \n"
            }
            binding.idList.text = strBudgetList
        })

        binding.viewModelList = viewModel

        binding.btnShare.setOnClickListener {
            shareList()
        }

        return binding.root
    }

    fun shareList(){
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        val shareBody = binding.idList.text
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Solicitação de Orçamento")
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
        startActivity(Intent.createChooser(sharingIntent, "Compartilhar por"))
    }
}