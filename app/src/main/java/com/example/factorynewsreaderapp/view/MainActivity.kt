package com.example.factorynewsreaderapp.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.factorynewsreaderapp.R
import com.example.factorynewsreaderapp.contract.ContractInterface
import com.example.factorynewsreaderapp.data.Article
import com.example.factorynewsreaderapp.data.Injection
import com.example.factorynewsreaderapp.databinding.ActivityMainBinding
import com.example.factorynewsreaderapp.presenter.ArticlesPresenter
import com.example.factorynewsreaderapp.utilities.BACK_STACK_NAME
import com.example.factorynewsreaderapp.utilities.BUNDLE_KEY_LIST
import com.example.factorynewsreaderapp.utilities.BUNDLE_KEY_POSITION
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), ContractInterface.View {

    private lateinit var binding: ActivityMainBinding
    private val listFragment = ListFragment()
    lateinit var presenter: ArticlesPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializePresenter()

        GlobalScope.launch(Dispatchers.IO) {
            presenter.requestType()
        }

        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fragment_container_view, listFragment)
        ft.commit()
    }

    private fun initializePresenter(){
        Injection.setUp(this)
        presenter = ArticlesPresenter(this, Injection.provideArticlesInteractor())
    }

    override fun callFragment() {
        listFragment.updateUI()
    }

    override fun showProgress() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        binding.progressBar.visibility = View.GONE
    }

    override fun onResponseFailure(throwable: Throwable) {

        val builder = AlertDialog.Builder(this)

        with(builder)
        {
            setTitle(R.string.error_title)
            setMessage(R.string.error_description)
            setNegativeButton(R.string.error_btn, null)
            show()
        }
    }

    override fun setSingleViewFragment(itemPosition: Int, articlesList: ArrayList<Article>) {

        val bundle = Bundle()
        bundle.putInt(BUNDLE_KEY_POSITION, itemPosition)
        bundle.putSerializable(BUNDLE_KEY_LIST, articlesList)

        val detailsFragment = DetailsFragment()
        detailsFragment.arguments = bundle

        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fragment_container_view, detailsFragment)
        ft.addToBackStack(BACK_STACK_NAME)
        ft.commit()
    }
}