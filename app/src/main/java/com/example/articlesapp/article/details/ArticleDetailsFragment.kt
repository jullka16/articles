package com.example.articlesapp.article.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.example.articlesapp.databinding.FragmentArticleDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ArticleDetailsFragment : Fragment() {

    private lateinit var viewModel: ArticleDetailsViewModel

    @Inject
    lateinit var factory: ArticleDetailsViewModelFactory

    private var _binding: FragmentArticleDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArticleDetailsBinding.inflate(inflater, container, false)
        setupViewModel()

        arguments?.let {
            val args = ArticleDetailsFragmentArgs.fromBundle(it)
            val id = args.id
            viewModel.getArticleDetails(id)
        }
        observeArticleDetails()

        return binding.root
    }

    private fun observeArticleDetails() {
        viewModel.articleDetails.observe(this as LifecycleOwner) { article ->
            if (article.isSuccess) {
                binding.articleTitle.text = article.getOrNull()?.title
                binding.articleDetails.text = article.getOrNull()?.body
            }
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, factory).get(ArticleDetailsViewModel::class.java)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = ArticleDetailsFragment()
    }
}