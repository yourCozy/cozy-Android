package com.yourcozy.cozy.views.main.bookstore

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.yourcozy.cozy.ItemDecoration
import com.yourcozy.cozy.R
import com.yourcozy.cozy.network.RequestToServer
import com.yourcozy.cozy.network.customEnqueue
import com.yourcozy.cozy.network.responseData.BookstoreFeedData
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
                if(it.body()!!.success){
                    Log.d("success message >>>> ",it.body()!!.message)
                    bookstoreFeedData.clear()
                    for(i in it.body()!!.data){
                        if(i.image != "null"){
                            bookstoreFeedData.add(i)
                        }
                        else break
                    }
                    bookstoreFeedAdapter.datas = bookstoreFeedData
                    view.rv_feed.addItemDecoration(ItemDecoration(this.context!!, 0, 36))
                    bookstoreFeedAdapter.notifyDataSetChanged()
                }else{
                    Log.d("fail message >>>> ",it.body()!!.message)
                    view.tv_nonBookstorefeed.visibility = View.VISIBLE
                }
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