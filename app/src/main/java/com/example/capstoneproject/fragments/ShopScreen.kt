package com.example.capstoneproject.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.capstoneproject.R
import com.example.capstoneproject.databinding.FragmentMainMenuBinding
import com.example.capstoneproject.databinding.FragmentShopScreenBinding
import com.example.capstoneproject.viewmodel.GameViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ShopScreen.newInstance] factory method to
 * create an instance of this fragment.
 */
class ShopScreen : Fragment() {
    private var binding: FragmentShopScreenBinding? = null
    private val sharedViewModel: GameViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentShopScreenBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        // Inflate the layout for this fragment
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = sharedViewModel
            shopScreenFragment = this@ShopScreen
        }
    }

    fun returnToMenu() {
        findNavController().navigate(R.id.action_shopScreen_to_mainMenu)
    }
}