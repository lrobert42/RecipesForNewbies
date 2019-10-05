package com.example.recipesfornewbies.wishlistview

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipesfornewbies.R
import com.example.recipesfornewbies.UtilsClass.RecyclerItemClickListener
import com.example.recipesfornewbies.UtilsClass.SwipeToDeleteCallback
import com.example.recipesfornewbies.databinding.FragmentWishlistBinding
import com.example.recipesfornewbies.recipes.Recipe
import com.example.recipesfornewbies.wishlistDatabase.WishlistDatabase

class WishlistFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val application = requireNotNull(this.activity).application
        val dataSource = WishlistDatabase.getInstance(application).wishlistDatabaseDAO

        val viewModelFactory = WishlistViewModelFactory(dataSource)

        val viewModel =
            ViewModelProviders.of(
                this, viewModelFactory
            ).get(WishlistViewModel::class.java)

        val binding: FragmentWishlistBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_wishlist, container, false
        )
        binding.viewModel = viewModel
        val manager = LinearLayoutManager(activity)
        binding.wishlistRecyclerView.layoutManager = manager

        val adapter = WishlistAdapter()
        binding.wishlistRecyclerView.adapter = adapter
        (activity as AppCompatActivity).supportActionBar?.title="Your wishlist"


        viewModel.wishlist.observe(this, Observer {
            adapter.submitList(it)
        })

        val swipeDelete = object : SwipeToDeleteCallback(application) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                viewModel.remove(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeDelete)
        itemTouchHelper.attachToRecyclerView(binding.wishlistRecyclerView)

        binding.wishlistRecyclerView.addOnItemTouchListener(
            RecyclerItemClickListener(this.context!!,
            binding.wishlistRecyclerView, object : RecyclerItemClickListener.OnItemClickListener {

                override fun onItemClick(view: View, position: Int) {
                    viewModel.wishlist.value?.let {
                        val recipe : Recipe = it[position]!!
                        findNavController().navigate(
                            WishlistFragmentDirections.actionWishlistFragmentToDetailedRecipeFragment(recipe))
                    }
                }
                override fun onItemLongClick(view: View?, position: Int) {
                    viewModel.wishlist.value?.let {
                        val recipe : Recipe = it[position]!!
                        findNavController().navigate(
                            WishlistFragmentDirections.actionWishlistFragmentToDetailedRecipeFragment(recipe))
                    }
                }
            })
        )

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.wishlist_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)

        val menuItem = menu!!.findItem(R.id.build_grocery_list)
        val application = requireNotNull(this.activity).application
        val dataSource = WishlistDatabase.getInstance(application).wishlistDatabaseDAO
        val viewModelFactory = WishlistViewModelFactory(dataSource)
        val viewModel =
            ViewModelProviders.of(
                this, viewModelFactory
            ).get(WishlistViewModel::class.java)

        viewModel.wishlist.observe(this, Observer{
            menuItem.isVisible = !it.isEmpty()
        })
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId){
            R.id.build_grocery_list ->{
                this.findNavController().navigate(WishlistFragmentDirections.actionWishlistFragmentToGroceryListFragment())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}