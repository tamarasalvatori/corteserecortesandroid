package com.example.atelie_corteserecortes.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.atelie_corteserecortes.MyViewModel
import com.example.atelie_corteserecortes.R
import com.example.atelie_corteserecortes.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth

class HomeFragment : Fragment() {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding : FragmentHomeBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_home, container, false)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.emailUser.text = firebaseAuth.currentUser?.displayName.toString()

        binding.btnBudget.setOnClickListener{view : View ->
            view.findNavController().navigate(R.id.action_homeFragment_to_budgetFragment)
        }

        binding.btnContact.setOnClickListener{
            sendMessage()
        }

        binding.btnLogout.setOnClickListener{ view : View->
            firebaseAuth.signOut()
            view.findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
        }

        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            Toast.makeText(context, "Não é possível retornar à tela anterior", Toast.LENGTH_LONG).show()
        }

        return binding.root
    }

    private fun sendMessage(){

        try{
            val whatsappIntent = Intent(Intent.ACTION_SEND)
            whatsappIntent.type = "text/plain"
            whatsappIntent.setPackage("com.whatsapp")
            whatsappIntent.putExtra(Intent.EXTRA_TEXT, "Olá. Gostaria de conversar sobre o meu pedido")

            val smsNumber = "51981031472"

            whatsappIntent.putExtra("Tamara", "$smsNumber@s.whatsapp.net")

            startActivity(whatsappIntent)
        } catch (e: Exception) {
            e.printStackTrace()
            val appPackageName = "com.whatsapp"
            try {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName")))
            } catch (e :android.content.ActivityNotFoundException) {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")))
            }
        }
    }
}