package com.example.recipesfornewbies.detailedrecipe

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.recipesfornewbies.R
import com.example.recipesfornewbies.databinding.FragmentDetailedRecipeBinding


class DetailedRecipeFragment: Fragment(){


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val argument = DetailedRecipeFragmentArgs.fromBundle(arguments!!)

        val viewModelFactory = DetailedRecipeViewModelFactory(argument.recipe)

        val viewModel: DetailedRecipeViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(DetailedRecipeViewModel::class.java)

        val binding : FragmentDetailedRecipeBinding = DataBindingUtil.inflate(
                inflater, com.example.recipesfornewbies.R.layout.fragment_detailed_recipe, container, false)

        binding.viewModel = viewModel

        activity!!.setTitle(argument.recipe.title)

        val adapter = DetailedRecipeAdapter(viewModel)
//        val manager = LinearLayoutManager(activity)
        val manager = GridLayoutManager(activity, 3)

        manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {

            override fun getSpanSize(position: Int): Int {
                try {
                    val type = adapter.getItemViewType(position)
                    return if (type == 2)
                        1
                    else
                        3
                } catch(e: Exception){
                    Log.e("Fragment", "Error in setSpanSize: $e")
                }
                return 3
            }
        }

        binding.detailedRecipeRecyclerView.layoutManager = manager

        binding.detailedRecipeRecyclerView.adapter = adapter

        binding.lifecycleOwner = this

//        viewModel.recipe.observe(this, Observer {detailedRecipe ->
//            binding.detailedRecipe = detailedRecipe
//        })

        viewModel.navigateTo.observe(this, Observer{
            findNavController().navigate(DetailedRecipeFragmentDirections.actionDetailedRecipeFragmentSelf(it))
        })
        return binding.root
        setHasOptionsMenu(true)

        TODO("Add a function that parses instructions and converts units according to settings")

    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.detailed_recipe_overflow_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        return super.onOptionsItemSelected(item)
    }
}