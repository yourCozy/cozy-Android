package com.example.cozy.views.category

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.cozy.R

/**
 * A simple [Fragment] subclass.
 */
class CategoryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        var view = inflater.inflate(R.layout.fragment_category, container, false)

        /*활동 리스트 페이지 디자인 나오면 버튼 처리 추가해야함.*/
        var onClickListenter = View.OnClickListener{view ->
            when(view.id) {
                R.id.btn_movie -> {

                }
                R.id.btn_writing -> {

                }
                R.id.btn_rental -> {

                }
                R.id.btn_readingClub -> {

                }
                R.id.btn_night -> {

                }
                R.id.btn_exhibit -> {

                }
                R.id.btn_recommendation -> {

                }
                R.id.btn_workshop -> {

                }
                R.id.btn_bookTalk -> {

                }
                R.id.btn_music -> {

                }
                R.id.btn_recite -> {

                }
                R.id.btn_silentReading -> {

                }
                R.id.btn_transcription -> {

                }
                R.id.btn_stay -> {

                }
                R.id.btn_making -> {

                }
                R.id.btn_publication -> {

                }
                R.id.btn_class -> {

                }
                R.id.btn_market -> {

                }
            }

        }
        return view
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.search, menu)
//    }

}
