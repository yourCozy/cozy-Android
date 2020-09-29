package com.yourcozy.cozy.views.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.yourcozy.cozy.views.main.bookstore.BookstoreFragment
import com.yourcozy.cozy.views.main.event.EventFragment
import com.yourcozy.cozy.views.main.review.ReviewFragment


class TabViewPagerAdapter(fm: FragmentManager, bookstoreIdx: Int): FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val titleList = arrayListOf("책방", "활동","후기")
    var bookstoreIdx = bookstoreIdx

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> BookstoreFragment().newInstance(bookstoreIdx)
            1 -> EventFragment().newInstance(bookstoreIdx)
            else -> ReviewFragment().newInstance(bookstoreIdx)
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titleList[position]
    }

    override fun getCount(): Int = 3
}