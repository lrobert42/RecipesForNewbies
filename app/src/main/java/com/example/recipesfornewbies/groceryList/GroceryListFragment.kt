package com.example.recipesfornewbies.groceryList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipesfornewbies.R
import com.example.recipesfornewbies.databinding.FragmentGroceryListBinding
import com.example.recipesfornewbies.wishlistDatabase.WishlistDatabase

class GroceryListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val application = requireNotNull(this.activity).application
        val dataSource = WishlistDatabase.getInstance(application).wishlistDatabaseDAO

        (activity as AppCompatActivity).supportActionBar?.title="Your grocery list"
        val binding: FragmentGroceryListBinding = DataBindingUtil.inflate(layoutInflater,
            R.layout.fragment_grocery_list, container, false)

        val viewModelFactory = GroceryListViewModelFactory(dataSource)

        val viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(GroceryListViewModel::class.java)

        binding.viewModel = viewModel

        val manager = LinearLayoutManager(context)

        val adapter = Adapter(viewModel)
        binding.groceryListRecyclerView.layoutManager = manager
        binding.groceryListRecyclerView.adapter = adapter

        viewModel.ingredientList.observe(this, Observer {
            if(it.isNotEmpty()){
                adapter.submitList( it)
                Log.i("Adapter", "List submitted")
            }
        })

        return binding.root
    }
}