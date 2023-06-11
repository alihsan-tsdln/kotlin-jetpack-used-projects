package com.tasdelen.besinlerkitabi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.tasdelen.besinlerkitabi.databinding.FragmentBesinDetayiBinding
import com.tasdelen.besinlerkitabi.util.getImage
import com.tasdelen.besinlerkitabi.viewModels.BesinDetayiViewModel

class BesinDetayiFragment : Fragment() {

    private lateinit var binding : FragmentBesinDetayiBinding
    private lateinit var besinVM : BesinDetayiViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBesinDetayiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        besinVM = ViewModelProvider(this)[BesinDetayiViewModel::class.java]
        var besinId = 0

        arguments?.let {
            besinId = BesinDetayiFragmentArgs.fromBundle(it).besinId
        }
        besinVM.takeRoomData(besinId)
        observeLiveData()

    }

    private fun observeLiveData() {
        besinVM.besin.observe(viewLifecycleOwner) { besin ->
            besin?.let {
                binding.besin = it
            }
        }
    }
}