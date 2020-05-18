package com.spacex_mvvm.test.custommatchers

import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher

object RecyclerViewCustomMatchers {

    fun hasItemCount(expectedCount: Int): Matcher<View> {
        return object: BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("itemCount == $expectedCount")
            }

            override fun matchesSafely(recyclerView: RecyclerView): Boolean {
                return recyclerView.adapter?.itemCount == expectedCount
            }
        }
    }

    fun atPosition(
        position: Int,
        @IdRes viewHolderChildId: Int,
        itemMatcher: Matcher<View>
    ): Matcher<View> {
        return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("has item at position $position: ")
                itemMatcher.describeTo(description)
            }

            override fun matchesSafely(view: RecyclerView): Boolean {
                val viewHolder = view.findViewHolderForAdapterPosition(position) ?: return false
                val viewHolderChild = viewHolder.itemView.findViewById<View>(viewHolderChildId)
                return itemMatcher.matches(viewHolderChild)
            }
        }
    }
}