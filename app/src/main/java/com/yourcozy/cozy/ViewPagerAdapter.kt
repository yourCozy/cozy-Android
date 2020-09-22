package com.yourcozy.cozy

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.yourcozy.cozy.views.category.CategoryFragment
import com.yourcozy.cozy.views.main.MainFragment
import com.yourcozy.cozy.views.map.MapFragment
import com.yourcozy.cozy.views.mypage.MypageFragment

class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> MainFragment()
            1 -> MapFragment()
            2 -> CategoryFragment()
            else -> MypageFragment()
        }
    }

    override fun getCount(): Int = 4
}