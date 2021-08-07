package com.example.articlesapp.article.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.articlesapp.R
import com.example.articlesapp.article.network.Article
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ArticleFragment : Fragment() {

    private lateinit var viewModel: ArticleViewModel

    @Inject
    lateinit var factory: ArticleViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_article, container, false)

        setupViewModel()
        observeArticles(view)

        return view
    }

    private fun observeArticles(view: View?) {
        viewModel.articles.observe(this as LifecycleOwner) { articles ->
            if (articles.getOrNull() != null) {
                setupList(view, articles.getOrNull()!!)
            } else {
                //TODO
            }
        }
    }

    private fun setupList(
        view: View?,
        articles: List<Article>
    ) {
        if (view is RecyclerView) {
            with(view) {
                adapter = MyArticleRecyclerViewAdapter(articles)
            }
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, factory).get(ArticleViewModel::class.java)
    }

    companion object {
        @JvmStatic
        fun newInstance() = ArticleFragment()
    }
}