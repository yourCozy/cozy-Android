package com.yourcozy.cozy.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.yourcozy.cozy.ItemDecoration
import com.yourcozy.cozy.R
import com.yourcozy.cozy.network.RequestToServer
import com.yourcozy.cozy.network.customEnqueue
import com.yourcozy.cozy.views.main.RecommendDetailActivity
import com.yourcozy.cozy.views.mypage.MypageFragment
import kotlinx.android.synthetic.main.activity_search.*


class SearchActivity :AppCompatActivity(), View.OnClickListener {
    val service = RequestToServer.service
    private lateinit var searchAdapter: SearchAdapter
    lateinit var keyword: String
    lateinit var imm : InputMethodManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        setSupportActionBar(tb_search)
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.icon_before_17_x_14)

        tb_search.elevation = 5F
        et_search_bar.elevation = 5F
        iv_search.elevation = 5F
        iv_x.elevation = 5F

        imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

        tag1.setOnClickListener {
            keyword = tag1.text.toString()
            showResult(keyword)
        }

        tag2.setOnClickListener {
            keyword = tag2.text.toString()
            showResult(keyword)
        }

        tag3.setOnClickListener {
            keyword = tag3.text.toString()
            showResult(keyword)
        }

        tag4.setOnClickListener {
            keyword = tag4.text.toString()
            showResult(keyword)
        }

        tag5.setOnClickListener{
            keyword = tag5.text.toString()
            showResult(keyword)
        }

        tag6.setOnClickListener {
            keyword = tag6.text.toString()
            showResult(keyword)
        }

        tag7.setOnClickListener{
            keyword = tag7.text.toString()
            showResult(keyword)
        }

        tag8.setOnClickListener{
            keyword = tag8.text.toString()
            showResult(keyword)
        }

        tag9.setOnClickListener {
            keyword = tag9.text.toString()
            showResult(keyword)
        }

        tag10.setOnClickListener {
            keyword = tag10.text.toString()
            showResult(keyword)
        }

        iv_search.setOnClickListener {
            showResult(et_search_bar.text.toString())
            imm.hideSoftInputFromWindow(getCurrentFocus()!!.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS)
        }

        iv_x.setOnClickListener {
            emptyView()
        }
        rv_search_result.addItemDecoration(ItemDecoration(this, 0, 32))

        et_search_bar.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    showResult(et_search_bar.text.toString())
                    imm.hideSoftInputFromWindow(getCurrentFocus()!!.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS)
                }
                else -> {
                    return@OnEditorActionListener false
                }
            }
            true
        })

    }

    fun showResult(keyword: String){
        val sharedPref = getSharedPreferences("TOKEN", Context.MODE_PRIVATE)
        val header = mutableMapOf<String, String?>()
        header["Context-Type"] = "application/json"
        val token = sharedPref.getString("token", "token")
        if(token != "token"){
            header["token"] = token
        }
        service.requestSearch(keyword,header).customEnqueue(
            onError = {
                Toast.makeText(applicationContext, "올바르지 않은 요청입니다.", Toast.LENGTH_SHORT).show()
            },
            onSuccess = {
                if (it.body()!!.success) {
                    searchAdapter = SearchAdapter(
                        applicationContext,
                        it.body()!!.data.toMutableList()
                    ) { SearchData, View ->
                        val intent = Intent(applicationContext, RecommendDetailActivity::class.java)
                        intent.putExtra("bookstoreIdx", SearchData.bookstoreIdx)
                        startActivity(intent)
                    }
                    rv_search_result.adapter = searchAdapter
                    searchAdapter.notifyDataSetChanged()
                    if (it.body()!!.data[0].count != null) {
                        tv_search_result_cnt.text = it.body()!!.data[0].count.toString()
                    }

                    tv_search_result.visibility = View.VISIBLE
                    tv_search_result_cnt.visibility = View.VISIBLE
                    tv_search_result_amount.visibility = View.VISIBLE
                    rv_search_result.visibility = View.VISIBLE

                    iv_search.visibility = View.GONE
                    iv_x.visibility = View.VISIBLE

                    tv_search_result_null.visibility = View.GONE

                    tv_search_query.visibility = View.GONE
                    tag1.visibility = View.GONE
                    tag2.visibility = View.GONE
                    tag3.visibility = View.GONE
                    tag4.visibility = View.GONE
                    tag5.visibility = View.GONE
                    tag6.visibility = View.GONE
                    tag7.visibility = View.GONE
                    tag8.visibility = View.GONE
                    tag9.visibility = View.GONE
                    tag10.visibility = View.GONE

                    et_search_bar.setText(keyword)
                    et_search_bar.clearFocus()//키보드 내려버리기

                } else {
                    //검색 결과가 0건
                    tv_search_result_cnt.setText("0")
                    tv_search_result.visibility = View.VISIBLE
                    tv_search_result_cnt.visibility = View.VISIBLE
                    tv_search_result_amount.visibility = View.VISIBLE
                    rv_search_result.visibility = View.GONE

                    iv_search.visibility = View.GONE
                    iv_x.visibility = View.VISIBLE

                    tv_search_query.visibility = View.GONE
                    tag1.visibility = View.GONE
                    tag2.visibility = View.GONE
                    tag3.visibility = View.GONE
                    tag4.visibility = View.GONE
                    tag5.visibility = View.GONE
                    tag6.visibility = View.GONE
                    tag7.visibility = View.GONE
                    tag8.visibility = View.GONE
                    tag9.visibility = View.GONE
                    tag10.visibility = View.GONE

                    tv_search_result_null.visibility = View.VISIBLE

                    et_search_bar.setText(keyword)
                    et_search_bar.clearFocus()
                }
            }
        )
    }

    fun emptyView(){
        tv_search_query.visibility = View.VISIBLE
        tag1.visibility = View.VISIBLE
        tag2.visibility = View.VISIBLE
        tag3.visibility = View.VISIBLE
        tag4.visibility = View.VISIBLE
        tag5.visibility = View.VISIBLE
        tag6.visibility = View.VISIBLE
        tag7.visibility = View.VISIBLE
        tag8.visibility = View.VISIBLE
        tag9.visibility = View.VISIBLE
        tag10.visibility = View.VISIBLE

        tv_search_result.visibility = View.GONE
        tv_search_result_cnt.visibility = View.GONE
        tv_search_result_amount.visibility = View.GONE
        rv_search_result.visibility = View.GONE
        iv_x.visibility = View.GONE
        iv_search.visibility = View.VISIBLE

        tv_search_result_null.visibility = View.GONE
        et_search_bar.setText("")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                val intent = Intent(applicationContext, MypageFragment::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.tag1 -> {
                keyword = tag1.text.toString()
            }
            R.id.tag2 ->{
                keyword = tag2.text.toString()
            }
            R.id.tag3 ->{
                keyword = tag3.text.toString()
            }
            R.id.tag4 -> {
                keyword = tag4.text.toString()
            }
            R.id.tag5 -> {
                keyword = tag5.text.toString()
            }
            R.id.tag6 -> {
                keyword = tag6.text.toString()
            }
            R.id.tag7 -> {
                keyword = tag7.text.toString()
            }
            R.id.tag8 -> {
                keyword = tag8.text.toString()
            }
            R.id.tag9 -> {
                keyword = tag9.text.toString()
            }
            R.id.tag10 -> {
                keyword = tag10.text.toString()
            }
            R.id.iv_search -> {

            }
            R.id.iv_x -> {

            }
        }
        showResult(keyword)
    }
}