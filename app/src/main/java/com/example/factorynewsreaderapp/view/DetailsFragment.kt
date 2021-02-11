package com.example.factorynewsreaderapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.factorynewsreaderapp.adapters.ArticleDetailsAdapter
import com.example.factorynewsreaderapp.data.Article
import com.example.factorynewsreaderapp.databinding.FragmentDetailsBinding
import com.example.factorynewsreaderapp.utilities.BUNDLE_KEY_LIST
import com.example.factorynewsreaderapp.utilities.BUNDLE_KEY_POSITION

class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private lateinit var articleDetailsList: List<Article>
    private var itemPosition = 0
    private lateinit var adapter: ArticleDetailsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null){
            @Suppress("UNCHECKED_CAST")
            articleDetailsList = requireArguments().getSerializable(BUNDLE_KEY_LIST) as List<Article>
            itemPosition = requireArguments().getInt(BUNDLE_KEY_POSITION, 0)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        adapter = ArticleDetailsAdapter(articleDetailsList)

        with(binding) {
            viewPager.adapter = adapter
            viewPager.currentItem = itemPosition
        }

        return binding.root
    }
}