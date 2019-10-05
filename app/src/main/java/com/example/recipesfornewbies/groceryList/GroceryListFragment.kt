package com.example.recipesfornewbies.groceryList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
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

        return binding.root
    }
}