package com.tasdelen.besinlerkitabi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tasdelen.besinlerkitabi.adapters.BesinRecyclerAdapter
import com.tasdelen.besinlerkitabi.databinding.FragmentBesinListesiBinding
import com.tasdelen.besinlerkitabi.viewModels.BesinListesiViewModel

class BesinListesiFragment : Fragment() {

    private lateinit var binding : FragmentBesinListesiBinding
    private lateinit var viewModel : BesinListesiViewModel
    private val recyclerAdapter =  BesinRecyclerAdapter(arrayListOf())


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentBesinListesiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[BesinListesiViewModel::class.java]
        viewModel.refreshData()

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = recyclerAdapter
        observeLiveData()

        binding.swipeRefresh.setOnRefreshListener {
            binding.besinListLoading.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.GONE
            binding.errorMessage.visibility = View.GONE
            viewModel.takeDataFromInternet()
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun observeLiveData() {
        viewModel.besinler.observe(viewLifecycleOwner) {
            binding.recyclerView.visibility = View.VISIBLE
            recyclerAdapter.updateBesinListesi(it)
            binding.besinListLoading.visibility = View.INVISIBLE
            binding.errorMessage.visibility = View.INVISIBLE
        }

        viewModel.isBesinLoading.observe(viewLifecycleOwner) {
            binding.besinListLoading.isVisible = it
            if(it) {
                binding.errorMessage.visibility = View.INVISIBLE
                binding.recyclerView.visibility = View.INVISIBLE
            }
        }

        viewModel.isBesinError.observe(viewLifecycleOwner) {
            binding.errorMessage.isVisible = it
            if(it) {
                binding.recyclerView.visibility = View.INVISIBLE
                binding.besinListLoading.visibility = View.INVISIBLE
            }
        }
    }
}