package com.spacex_mvvm.extensions

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// Scrolls the recycler view to the top when an item is inserted in the 0 position to make it visible to the user
fun <VH: RecyclerView.ViewHolder> RecyclerView.Adapter<VH>.scrollToTopOnItemInserted(layoutManager: LinearLayoutManager) {
    this.registerAdapterDataObserver(object: RecyclerView.AdapterDataObserver() {
        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            val firstVisiblePosition = layoutManager.findFirstCompletelyVisibleItemPosition()
            if (firstVisiblePosition == 0 && positionStart == 0) {
                layoutManager.scrollToPosition(0)
            } else {
                super.onItemRangeInserted(positionStart, itemCount)
            }
        }
    })
}
