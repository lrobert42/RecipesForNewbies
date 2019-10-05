package com.example.recipesfornewbies.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipesfornewbies.R
import com.example.recipesfornewbies.UtilsClass.RecyclerItemClickListener
import com.example.recipesfornewbies.databinding.FragmentSearchBinding
import com.example.recipesfornewbies.defaultrecipelist.SearchResultsAdapter

class SearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val argument = SearchFragmentArgs.fromBundle(arguments!!)

        val viewModelFactory = SearchViewModelFactory(argument.ingredients)

        val viewModel: SearchViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(SearchViewModel::class.java)

        val binding : FragmentSearchBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_search, container, false)

        binding.viewModel = viewModel

        val manager = LinearLayoutManager(activity)
        binding.lifecycleOwner = this

        binding.searchRecyclerView.layoutManager = manager

        val adapter = SearchResultsAdapter()
        binding.searchRecyclerView.adapter = adapter
        (activity as AppCompatActivity).supportActionBar?.title="Search results"

        binding.searchRecyclerView.addOnItemTouchListener(
            RecyclerItemClickListener(this.context!!,
                binding.searchRecyclerView, object : RecyclerItemClickListener.OnItemClickListener {

                    override fun onItemClick(view: View, position: Int) {
                        viewModel.searchResultsList.value?.let {
                            viewModel.getRecipeByID(it[position].id)
                            viewModel.newRecipe.observe(this@SearchFragment, Observer {
                             findNavController().navigate(
                                 SearchFragmentDirections.actionSearchFragmentToDetailedRecipeFragment(it))
                         })
                        }
                    }
                    override fun onItemLongClick(view: View?, position: Int) {
                        viewModel.searchResultsList.value?.let {
                            viewModel.getRecipeByID(it[position].id)
                            viewModel.newRecipe.observe(this@SearchFragment, Observer {
                                findNavController().navigate(
                                    SearchFragmentDirections.actionSearchFragmentToDetailedRecipeFragment(it))
                            })
                        }
                    }
                })
        )

        viewModel.searchResultsList.observe(this, Observer {
            adapter.notifyDataSetChanged()
        })


        return binding.root
    }

}