package com.example.recipesfornewbies.UtilsClass
import android.content.Context
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.recipesfornewbies.R

abstract class SwipeCallback(context: Context) : ItemTouchHelper.Callback() {

    private val icon = ContextCompat.getDrawable(context, R.drawable.ic_android_primary_24dp)
    private val intrinsicWidth = icon!!.intrinsicWidth
    private val intrinsicHeight = icon!!.intrinsicHeight
    private val background = ColorDrawable()
    private val redBackgroundColor = Color.parseColor("#f44336")
    private val greenBackgroundColor = Color.parseColor("#339966")
    private val clearPaint = Paint().apply { xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR) }


    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        return makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return false
    }

    override fun onChildDraw(
        c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
        dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean
    ) {
        val itemView = viewHolder.itemView
        val itemHeight = itemView.bottom - itemView.top
        val isCanceled = dX == 0f && !isCurrentlyActive

        if (isCanceled) {
            if (dX < 0){

                   clearCanvas(c, itemView.right + dX, itemView.top.toFloat(), itemView.right.toFloat(), itemView.bottom.toFloat())
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    return
                } else if (dX > 0) {
                    clearCanvas(c, itemView.left + dX, itemView.top.toFloat(), itemView.left.toFloat(), itemView.bottom.toFloat())
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    return
                }
                else return
            }
        if (dX < 0){
        // Draw the red delete background
            background.color = redBackgroundColor
            background.setBounds(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
            background.draw(c)
            //Draw the green background
        } else if (dX > 0) {
            background.color = greenBackgroundColor
            background.setBounds(itemView.left, itemView.top, itemView.left + dX.toInt(), itemView.bottom)
            background.draw(c)
        }

        var iconTop = 0
        var iconLeft = 0
        var iconRight = 0
        var iconBottom = 0

        // Calculate position of icon
    if (dX < 0){
         iconTop = itemView.top + (itemHeight - intrinsicHeight) / 2
         val iconMargin = (itemHeight - intrinsicHeight) / 2
         iconLeft = itemView.right - iconMargin - intrinsicWidth
         iconRight = itemView.right - iconMargin
         iconBottom = iconTop + intrinsicHeight
    } else if (dX > 0) {
         iconTop = itemView.top + (itemHeight - intrinsicHeight) / 2
         val iconMargin = (itemHeight - intrinsicHeight) / 2
         iconLeft = itemView.left + iconMargin - intrinsicWidth
         iconRight = itemView.left + iconMargin
         iconBottom = iconTop + intrinsicHeight
    }

        // Draw the icon
        icon!!.setBounds(iconLeft, iconTop, iconRight, iconBottom)
        icon.draw(c)

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    private fun clearCanvas(c: Canvas?, left: Float, top: Float, right: Float, bottom: Float) {
        c?.drawRect(left, top, right, bottom, clearPaint)
    }
}

abstract class SwipeToDelete(context: Context) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

    private val wishlistIcon = ContextCompat.getDrawable(context, R.drawable.ic_android_primary_24dp)
    private val intrinsicWidth = wishlistIcon!!.intrinsicWidth
    private val intrinsicHeight = wishlistIcon!!.intrinsicHeight
    private val background = ColorDrawable()
    private val backgroundColor = Color.parseColor("#f44336")
    private val clearPaint = Paint().apply { xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR) }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return false
    }

    override fun onChildDraw(
        c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
        dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean
    ) {

        val itemView = viewHolder.itemView
        val itemHeight = itemView.bottom - itemView.top
        val isCanceled = dX == 0f && !isCurrentlyActive

        if (isCanceled) {
            clearCanvas(c, itemView.left + dX, itemView.top.toFloat(), itemView.left.toFloat(), itemView.bottom.toFloat())
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            return
        }

        // Draw the green wishlist background
        background.color = backgroundColor
        background.setBounds(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
        background.draw(c)

        // Calculate position of wishlist icon
        val wishlistIconTop = itemView.top + (itemHeight - intrinsicHeight) / 2
        val wishlistIconMargin = (itemHeight - intrinsicHeight) / 2
        val wishlistIconLeft = itemView.right - wishlistIconMargin - intrinsicWidth
        val wishlistIconRight = itemView.right - wishlistIconMargin
        val wishlistIconBottom = wishlistIconTop + intrinsicHeight

        // Draw the wishlist icon
        wishlistIcon!!.setBounds(wishlistIconLeft, wishlistIconTop, wishlistIconRight, wishlistIconBottom)
        wishlistIcon.draw(c)

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    private fun clearCanvas(c: Canvas?, left: Float, top: Float, right: Float, bottom: Float) {
        c?.drawRect(left, top, right, bottom, clearPaint)
    }
}