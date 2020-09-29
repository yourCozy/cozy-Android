package com.yourcozy.cozy.views.main.review

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yourcozy.cozy.R
import kotlinx.android.synthetic.main.fragment_review.view.*
import kotlin.properties.Delegates

class ReviewFragment : Fragment() {

    var bookstoreIdx by Delegates.notNull<Int>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_review, container, false)

        val extra = arguments
        bookstoreIdx = extra!!.getInt("bookstoreIdx")

        view.btn_simple_review.setOnClickListener {
            val intent = Intent(activity!!, SimpleReviewActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    fun newInstance(bookstoreIdx: Int): ReviewFragment {
        val args = Bundle()
        val fragment = ReviewFragment()
        args.putInt("bookstoreIdx", bookstoreIdx)
        fragment.arguments = args
        return fragment
    }
}