package com.example.cozy.views.main.bookstore

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.cozy.ItemDecoration
import com.example.cozy.R
import com.example.cozy.network.RequestToServer
import com.example.cozy.network.customEnqueue
import com.example.cozy.network.responseData.BookstoreFeedData
import kotlinx.android.synthetic.main.fragment_bookstore.view.*
import kotlin.properties.Delegates

class BookstoreFragment : Fragment() {

    val service = RequestToServer.service
    var bookstoreIdx by Delegates.notNull<Int>()
    var bookstoreFeedData = mutableListOf<BookstoreFeedData>()
    lateinit var bookstoreFeedAdapter: BookstoreFeedAdapter

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
        bookstoreFeedAdapter = BookstoreFeedAdapter(view.context)
        view.rv_feed.adapter = bookstoreFeedAdapter
        service.requestBookstoreFeed(bookstoreIdx).customEnqueue(
            onError = { Toast.makeText(context, "올바르지 않은 요청입니다.", Toast.LENGTH_SHORT)},
            onSuccess = {
                if(it.success){
                    Log.d("success message >>>> ",it.message)
                    bookstoreFeedData.clear()
                    bookstoreFeedData.addAll(it.data)
                    bookstoreFeedAdapter.datas = bookstoreFeedData
                    view.rv_feed.addItemDecoration(ItemDecoration(this.context!!, 36, 0))
                    bookstoreFeedAdapter.notifyDataSetChanged()
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