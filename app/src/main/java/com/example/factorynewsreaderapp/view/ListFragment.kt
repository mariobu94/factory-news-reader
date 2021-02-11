package com.example.factorynewsreaderapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.factorynewsreaderapp.adapters.ArticleListAdapter
import com.example.factorynewsreaderapp.contract.ContractInterface
import com.example.factorynewsreaderapp.databinding.FragmentListBinding

class ListFragment : Fragment(), ContractInterface.View.ListFragmentInterface {

    private lateinit var binding: FragmentListBinding
    private lateinit var mainActivity: MainActivity
    private lateinit var itemAdapter: ArticleListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentListBinding.inflate(inflater, container, false)
        mainActivity = activity as MainActivity
        updateUI()

        return binding.root
    }

    override fun updateUI() {
        itemAdapter = ArticleListAdapter(mainActivity.presenter)
        binding.recyclerViewItems.adapter = itemAdapter
    }
}