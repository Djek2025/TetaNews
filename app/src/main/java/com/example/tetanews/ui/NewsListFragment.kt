package com.example.tetanews.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.tetanews.App
import com.example.tetanews.R
import com.example.tetanews.databinding.FragmentListNewsBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsListFragment : Fragment(R.layout.fragment_list_news) {

    private var _binding: FragmentListNewsBinding? = null
    private val binding get() = checkNotNull(_binding)
    @Inject lateinit var adapter: NewsListAdapter
    @Inject lateinit var vm: NewsListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as App).getDaggerComponent().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListNewsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            vm.news.collect {
                binding.newsListRecycler.adapter = adapter.apply { setNewsList(it.articles) }
                binding.refresh.isRefreshing = false
                binding.shimmer?.stopShimmer()
                binding.shimmer?.visibility = View.GONE
            }
        }
    }

    override fun onStart() {
        super.onStart()
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter.filterObj.filter(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filterObj.filter(newText)
                return true
            }
        })
    }

    override fun onResume() {
        super.onResume()
        binding.refresh.setOnRefreshListener {
            vm.refresh()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}