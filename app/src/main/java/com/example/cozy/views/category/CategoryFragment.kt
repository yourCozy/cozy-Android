package com.example.cozy.views.category

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.cozy.R

/**
 * A simple [Fragment] subclass.
 */
class CategoryFragment : Fragment(), View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_category, container, false)

        //버튼 onClickListener 설정
        view.findViewById<View>(R.id.btn_movie).setOnClickListener(this)
        view.findViewById<View>(R.id.btn_writing).setOnClickListener(this)
        view.findViewById<View>(R.id.btn_rental).setOnClickListener(this)

        view.findViewById<View>(R.id.btn_readingClub).setOnClickListener(this)
        view.findViewById<View>(R.id.btn_night).setOnClickListener(this)
        view.findViewById<View>(R.id.btn_exhibit).setOnClickListener(this)

        view.findViewById<View>(R.id.btn_recommendation).setOnClickListener(this)
        view.findViewById<View>(R.id.btn_workshop).setOnClickListener(this)
        view.findViewById<View>(R.id.btn_bookTalk).setOnClickListener(this)

        view.findViewById<View>(R.id.btn_music).setOnClickListener(this)
        view.findViewById<View>(R.id.btn_recite).setOnClickListener(this)
        view.findViewById<View>(R.id.btn_silentReading).setOnClickListener(this)

        view.findViewById<View>(R.id.btn_transcription).setOnClickListener(this)
        view.findViewById<View>(R.id.btn_stay).setOnClickListener(this)
        view.findViewById<View>(R.id.btn_making).setOnClickListener(this)

        view.findViewById<View>(R.id.btn_publication).setOnClickListener(this)
        view.findViewById<View>(R.id.btn_class).setOnClickListener(this)
        view.findViewById<View>(R.id.btn_market).setOnClickListener(this)

        return view
    }

    //버튼 처리는 여기서 하면 됨.
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_movie -> {
                Toast.makeText(context, "[활동] 영화 상영 버튼을 선택하셨습니다.", Toast.LENGTH_SHORT).show()
                val intent = Intent(activity, EventListActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_writing -> Toast.makeText(context, "[활동] 글쓰기 모임 버튼을 선택하셨습니다.", Toast.LENGTH_SHORT).show()
            R.id.btn_rental -> {
                Toast.makeText(context, "[활동] 공간 대여 버튼을 선택하셨습니다.", Toast.LENGTH_SHORT).show()
            }
            R.id.btn_readingClub -> {
                Toast.makeText(context, "[활동] 독서 모임 버튼을 선택하셨습니다.", Toast.LENGTH_SHORT).show()
            }
            R.id.btn_night -> {
                Toast.makeText(context, "[활동] 심야 책방 버튼을 선택하셨습니다.", Toast.LENGTH_SHORT).show()
            }
            R.id.btn_exhibit -> {
                Toast.makeText(context, "[활동] 전시, 공연 버튼을 선택하셨습니다.", Toast.LENGTH_SHORT).show()
            }
            R.id.btn_recommendation -> {
                Toast.makeText(context, "[활동] 책 추천 버튼을 선택하셨습니다.", Toast.LENGTH_SHORT).show()
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

}
