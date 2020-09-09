package com.example.cozy.views.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.cozy.views.main.bookstore.BookstoreFragment
import com.example.cozy.views.main.event.EventFragment

class TabViewPagerAdapter(val bookstoreIdx : Int,fm: FragmentManager): FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val titleList = arrayListOf("책방", "활동")
    val eventFragment = EventFragment()

    fun passEventFragWithBundle() : EventFragment{
        val bundle = Bundle()
        bundle.putInt("bookstoreIdx", bookstoreIdx)
        eventFragment.setArguments(bundle)
        Log.d("CHECK_BUNDLE: " , "번들 체크")
        return eventFragment
    }
    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> BookstoreFragment()
            else -> {
                passEventFragWithBundle()
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titleList[position]
    }

    override fun getCount(): Int = 2
}