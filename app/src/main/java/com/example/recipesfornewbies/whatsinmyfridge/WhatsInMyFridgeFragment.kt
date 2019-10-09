@file:Suppress("Annotator")

package com.example.recipesfornewbies.whatsinmyfridge

import WhatsInMyFridgeSearchAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipesfornewbies.R
import com.example.recipesfornewbies.databinding.FragmentWhatsInMyFridgeBinding
import com.example.recipesfornewbies.ingredientsDatabase.IngredientsDatabase


class WhatsInMyFridgeFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val application = requireNotNull(this.activity).application

        val dataSource = IngredientsDatabase.getInstance(application).ingredientsDatabaseDao

        val viewModelFactory = WhatsInMyFridgeViewModelFactory(dataSource)

        // Get a reference to the ViewModel associated with this fragment.
        val viewModel =
            ViewModelProviders.of(
                this, viewModelFactory
            ).get(WhatsInMyFridgeViewModel::class.java)

        val binding: FragmentWhatsInMyFridgeBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_whats_in_my_fridge, container, false
        )

        val manager = LinearLayoutManager(activity)


        binding.viewModel = viewModel

        val adapter = WhatsInMyFridgeAdapter(viewModel)
        binding.fridgeListRecyclerView.layoutManager = manager
        binding.fridgeListRecyclerView.adapter = adapter
        (activity as AppCompatActivity).supportActionBar?.title="Explore your fridge"


        binding.lifecycleOwner = this

        viewModel.ingredients.observe(this, Observer {
            adapter.notifyDataSetChanged()
            if (it.isNotEmpty()) viewModel.showSearchButton()
            else viewModel.hideSearchButton()
        })

        viewModel.navigateToSearchFragment.observe(this, Observer {
            if (it == true) {
                viewModel.onDoneSearching()
                viewModel.ingredients.value?.let {
                    val ingredientArray = it.toTypedArray()
                    findNavController().navigate(
                        WhatsInMyFridgeFragmentDirections
                            .actionWhatsInMyFridgeFragmentToSearchFragment(ingredientArray)
                    )
                } ?: Log.i("Fragment WIMF", "You can't go there!")
            }
        })

/**Implementation of SearchView behavior**/

        val searchView = binding.searchViewIngredient
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.length >= 3){
                    viewModel.getNameFromDB(newText)
                    viewModel.suggestion.observe(this@WhatsInMyFridgeFragment, Observer{
                        searchView.suggestionsAdapter = WhatsInMyFridgeSearchAdapter(application, it)
                    })
                }
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.onQuerySubmit(query.trim())
                searchView.clearFocus()
                return true
            }
        })
        searchView.setOnSuggestionListener(object: SearchView.OnSuggestionListener{

            override fun onSuggestionSelect(position : Int) : Boolean {
                    return true
                }

                override fun onSuggestionClick(position : Int) : Boolean{

                    val item = searchView.suggestionsAdapter.getItemId(position).toInt()
                    viewModel.onSuggestionClicked(item)
                    return true
                }
            })
        return binding.root
    }
}
