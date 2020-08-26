package com.example.cozy.views.mypage

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.cozy.R

/**
 * A simple [Fragment] subclass.
 */
class MypageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_mypage, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.setting, menu)
    }

}
