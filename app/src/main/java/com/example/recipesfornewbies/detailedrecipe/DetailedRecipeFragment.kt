package com.example.recipesfornewbies.detailedrecipe

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.recipesfornewbies.R
import com.example.recipesfornewbies.databinding.FragmentDetailedRecipeBinding
import com.example.recipesfornewbies.recipes.Recipe
import com.example.recipesfornewbies.wishlistDatabase.WishlistDatabase
import com.example.recipesfornewbies.wishlistDatabase.WishlistDatabaseDAO
import com.example.recipesfornewbies.wishlistDatabase.WishlistRecipe
import kotlinx.coroutines.*


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
                inflater, R.layout.fragment_detailed_recipe, container, false)

        binding.viewModel = viewModel

        (activity as AppCompatActivity).supportActionBar?.title= argument.recipe.title
        val adapter = DetailedRecipeAdapter(viewModel)
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


        viewModel.navigateTo.observe(this, Observer{
            findNavController().navigate(DetailedRecipeFragmentDirections.actionDetailedRecipeFragmentSelf(it))
        })
        setHasOptionsMenu(true)
        return binding.root


        TODO("Add a function that parses instructions and converts units according to settings")

    }

    private var fragmentJob = Job()
    private val coroutineScope = CoroutineScope(fragmentJob + Dispatchers.Main)
    private lateinit var menu_detailed_recipe: Menu


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        val argument = DetailedRecipeFragmentArgs.fromBundle(arguments!!)

        val application = requireNotNull(this.activity).application
        val dataSource = WishlistDatabase.getInstance(application).wishlistDatabaseDAO
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.detailed_recipe_overflow_menu, menu)

        menu_detailed_recipe = menu!!

       isRecipeInWishlist(argument.recipe, dataSource)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val argument = DetailedRecipeFragmentArgs.fromBundle(arguments!!)

        val application = requireNotNull(this.activity).application
        val dataSource = WishlistDatabase.getInstance(application).wishlistDatabaseDAO

        val itemEmpty = menu_detailed_recipe.findItem(R.id.favorite_false)
        val itemFull = menu_detailed_recipe.findItem(R.id.favorite_true)


        when (item?.itemId){
            R.id.favorite_false ->{
                addToWishlist(argument.recipe, dataSource)
                itemEmpty.isVisible = false
                itemFull.isVisible = true
                return true
            }
            R.id.favorite_true ->{
                deleteFromWishlist(argument.recipe, dataSource)
                itemEmpty.isVisible = true
                itemFull.isVisible = false

                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun addToWishlist(recipe: Recipe, dataSource : WishlistDatabaseDAO){
        coroutineScope.launch{
            withContext(Dispatchers.IO){
                val newWishlistRecipe = WishlistRecipe(
                    recipe_image = recipe.image!!,
                    recipe_title = recipe.title!!,
                    recipe_servings = recipe.servings,
                    recipe_ready_in_minutes = recipe.readyInMinutes,
                    _id = recipe.id
                )

                dataSource.insert(newWishlistRecipe)
            }
        }
        Toast.makeText(context, "Recipe has been added to your wishlist!", Toast.LENGTH_SHORT)
            .show()
    }

    private fun deleteFromWishlist(recipe: Recipe, dataSource : WishlistDatabaseDAO) {
        coroutineScope.launch{
            withContext(Dispatchers.IO){
                dataSource.clearRecipe(recipe.id)
            }
        }
        Toast.makeText(context, "Recipe has been deleted from your wishlist!", Toast.LENGTH_SHORT)
            .show()
    }

    private fun isRecipeInWishlist(recipe: Recipe, dataSource : WishlistDatabaseDAO){
        coroutineScope.launch {
            val recipeIsInWishList = withContext(Dispatchers.IO) {
                try {
                    recipe.title?.let {
                        val databaseRecipe = dataSource.getRecipeWithName(it)
                        return@withContext (areRecipesTheSame(recipe, databaseRecipe))
                    }
                    false
                } catch (e: Exception) {
                    Log.e("VM", "Error: $e")
                    false
                }
            }

            menu_detailed_recipe.findItem(R.id.favorite_false).isVisible = !recipeIsInWishList
            menu_detailed_recipe.findItem(R.id.favorite_true).isVisible = recipeIsInWishList
        }
    }

    private fun areRecipesTheSame(recipe: Recipe, databaseRecipe: WishlistRecipe?) : Boolean {
        databaseRecipe?.let{
            if(recipe.title == it.recipe_title &&
                recipe.image == it.recipe_image &&
                recipe.servings == it.recipe_servings &&
                recipe.readyInMinutes == it.recipe_ready_in_minutes &&
                recipe.id == it._id) {
                return@areRecipesTheSame true
            }
        }
        return false
    }

    override fun onDestroy() {
        fragmentJob.cancel()
        super.onDestroy()
    }
}