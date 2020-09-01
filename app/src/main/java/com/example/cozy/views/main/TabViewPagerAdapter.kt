package com.example.cozy.views.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.cozy.views.main.bookstore.BookstoreFragment
import com.example.cozy.views.main.event.EventFragment

class TabViewPagerAdapter(fm: FragmentManager): FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val titleList = arrayListOf("책방", "활동")

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> BookstoreFragment()
            else -> EventFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titleList[position]
    }

    override fun getCount(): Int = 2
}