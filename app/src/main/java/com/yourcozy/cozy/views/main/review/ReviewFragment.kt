package com.yourcozy.cozy.views.main.review

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import com.yourcozy.cozy.R
import com.yourcozy.cozy.network.RequestToServer
import com.yourcozy.cozy.network.customEnqueue
import kotlinx.android.synthetic.main.fragment_review.*
import kotlinx.android.synthetic.main.fragment_review.view.*
import kotlin.properties.Delegates

class ReviewFragment : Fragment() {

    var bookstoreIdx by Delegates.notNull<Int>()
    val service = RequestToServer.service
    lateinit var fragView: View
    lateinit var sharedPref : SharedPreferences
    lateinit var token : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragView = inflater.inflate(R.layout.fragment_review, container, false)

        val extra = arguments
        bookstoreIdx = extra!!.getInt("bookstoreIdx")

        sharedPref = activity!!.getSharedPreferences("TOKEN", Context.MODE_PRIVATE)
        token = sharedPref.getString("token", "token").toString()

        fragView.btn_simple_review.setOnClickListener {
            if(token != "token") {
                val intent = Intent(activity!!, SimpleReviewActivity::class.java)
                intent.putExtra("bookstoreIdx", bookstoreIdx)
                startActivity(intent)
            }
            else{
                Toast.makeText(context, "로그인 후 이용해주세요.", Toast.LENGTH_SHORT)
            }
        }

        return fragView
    }

    override fun onResume() {
        super.onResume()
        initSimpleReview()
    }

    fun initSimpleReview(){
        service.requestSimpleReview(bookstoreIdx).customEnqueue(
            onError = { Toast.makeText(context, "올바르지 않은 요청입니다.", Toast.LENGTH_SHORT)},
            onSuccess = {
                if(it.body()!!.success){
                    val data = it.body()!!.data
                    when(data.avg_fac){
                        1 -> {
                            fragView.iv_facility.setImageResource(R.drawable.icon_bad)
                            fragView.tv_facility.text = "별로예요"
                        }
                        2 -> {
                            fragView.iv_facility.setImageResource(R.drawable.icon_soso)
                            fragView.tv_facility.text = "보통이에요"
                        }
                        3 -> {
                            fragView.iv_facility.setImageResource(R.drawable.icon_good)
                            fragView.tv_facility.text = "만족해요"
                        }
                    }
                    when(data.avg_book){
                        1 -> {
                            fragView.iv_book.setImageResource(R.drawable.icon_bad)
                            fragView.tv_book.text = "별로예요"
                        }
                        2 -> {
                            fragView.iv_book.setImageResource(R.drawable.icon_soso)
                            fragView.tv_book.text = "보통이에요"
                        }
                        3 -> {
                            fragView.iv_book.setImageResource(R.drawable.icon_good)
                            fragView.tv_book.text = "만족해요"
                        }
                    }
                    when(data.avg_act){
                        1 -> {
                            fragView.iv_activity.setImageResource(R.drawable.icon_bad)
                            fragView.tv_activity.text = "별로예요"
                        }
                        2 -> {
                            fragView.iv_activity.setImageResource(R.drawable.icon_soso)
                            fragView.tv_activity.text = "보통이에요"
                        }
                        3 -> {
                            fragView.iv_activity.setImageResource(R.drawable.icon_good)
                            fragView.tv_activity.text = "만족해요"
                        }
                        4 -> {
                            fragView.iv_activity.setImageResource(R.drawable.icon_no)
                            fragView.tv_activity.text = "해당없음"
                        }
                    }
                    when(data.avg_food){
                        1 -> {
                            fragView.iv_dessert.setImageResource(R.drawable.icon_bad)
                            fragView.tv_dessert.text = "별로예요"
                        }
                        2 -> {
                            fragView.iv_dessert.setImageResource(R.drawable.icon_soso)
                            fragView.tv_dessert.text = "보통이에요"
                        }
                        3 -> {
                            fragView.iv_dessert.setImageResource(R.drawable.icon_good)
                            fragView.tv_dessert.text = "만족해요"
                        }
                        4 -> {
                            fragView.iv_dessert.setImageResource(R.drawable.icon_no)
                            fragView.tv_dessert.text = "해당없음"
                        }
                    }
                }
                if(it.body()!!.status == 200 && !it.body()!!.success){
                    fragView.simple_gridlayout.visibility = View.INVISIBLE
                    fragView.none_review.visibility = View.VISIBLE
                }
            }
        )
    }

    fun newInstance(bookstoreIdx: Int): ReviewFragment {
        val args = Bundle()
        val fragment = ReviewFragment()
        args.putInt("bookstoreIdx", bookstoreIdx)
        fragment.arguments = args
        return fragment
    }
}