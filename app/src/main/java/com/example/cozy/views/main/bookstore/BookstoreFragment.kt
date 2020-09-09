package com.example.cozy.views.main.bookstore

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.cozy.R
import com.example.cozy.network.RequestToServer
import com.example.cozy.network.customEnqueue
import kotlinx.android.synthetic.main.fragment_bookstore.*
import kotlin.properties.Delegates

class BookstoreFragment : Fragment() {

    val service = RequestToServer.service
    var bookstoreIdx by Delegates.notNull<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_bookstore, container, false)

        val extra = arguments
        bookstoreIdx = extra!!.getInt("bookstoreIdx")
        Log.d("feed >>>>> ",bookstoreIdx.toString())

        initView(view)

        return view
    }

    private fun initView(view : View) {
        service.requestBookstoreFeed(bookstoreIdx).customEnqueue(
            onError = { Toast.makeText(context, "올바르지 않은 요청입니다.", Toast.LENGTH_SHORT)},
            onSuccess = {
                if(it.success){
                    Log.d("success message >>>> ",it.message)
                    val data = it.data
                    Glide.with(view).load("https://media.vlpt.us/images/cocoa/post/5b1179ca-b79f-41dc-b910-76ee6150c1a4/cat10.jpg").into(bookstore_img1)
                    bookstore_text1.text = data.description
                    Glide.with(view).load("https://media.vlpt.us/images/cocoa/post/5b1179ca-b79f-41dc-b910-76ee6150c1a4/cat10.jpg").into(bookstore_img2)
                }else(Log.d("fail message >>>> ",it.message))
            }
        )
    }

    fun newInstance(bookstoreIdx: Int): BookstoreFragment{
        val args = Bundle()
        val fragment = BookstoreFragment()
        args.putInt("bookstoreIdx", bookstoreIdx)
        fragment.arguments = args
        return fragment
    }

}