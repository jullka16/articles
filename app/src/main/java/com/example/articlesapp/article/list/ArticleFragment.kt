package com.example.articlesapp.article.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.articlesapp.article.network.Article
import com.example.articlesapp.databinding.FragmentArticleBinding
import com.example.articlesapp.tools.ImageLoader
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ArticleFragment : Fragment() {

    private lateinit var viewModel: ArticleViewModel

    @Inject
    lateinit var factory: ArticleViewModelFactory

    @Inject
    lateinit var imageLoader: ImageLoader

    private var _binding: FragmentArticleBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArticleBinding.inflate(inflater, container, false)

        setupViewModel()
        observeArticles()
        observeLoader()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeArticles() {
        viewModel.articles.observe(this as LifecycleOwner) { articles ->
            if (articles.getOrNull() != null) {
                setupList(articles.getOrNull()!!)
            } else {
                //TODO
            }
        }
    }

    private fun observeLoader() {
        viewModel.loader.observe(this as LifecycleOwner) { show ->
            binding.articleLoader.isVisible = show
        }
    }

    private fun setupList(
        articles: List<Article>
    ) {
        with(binding.articlesList) {
            adapter = ArticleRecyclerViewAdapter(articles, imageLoader) { id ->
                val action =
                    ArticleFragmentDirections.actionArticleFragmentToArticleDetailsFragment(id)
                findNavController().navigate(action)
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