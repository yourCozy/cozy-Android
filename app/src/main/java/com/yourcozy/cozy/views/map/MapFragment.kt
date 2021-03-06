package com.yourcozy.cozy.views.map

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.yourcozy.cozy.ItemDecoration
import com.yourcozy.cozy.R
import com.yourcozy.cozy.network.RequestToServer
import com.yourcozy.cozy.network.customEnqueue
import com.yourcozy.cozy.views.SearchActivity
import com.yourcozy.cozy.views.main.RecommendDetailActivity
import kotlinx.android.synthetic.main.fragment_map.*


class MapFragment : Fragment() {

    val service = RequestToServer.service
    val data = mutableListOf<MapData>()
    lateinit var mapAdapter: MapAdapter
    lateinit var detailData : MapData
    lateinit var fragView: View
    private var sectionIdx = 2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragView = inflater.inflate(R.layout.fragment_map, container, false)
        showMapList(fragView, sectionIdx)
        setHasOptionsMenu(true)

        return fragView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sectionIdx = { num : Int ->
            showMapList(view,num)
            sectionIdx = num
        }

        tv_location.setOnClickListener {
            val bottomsheet = BottomSheetFragment(sectionIdx)
            fragmentManager?.let { it1 -> bottomsheet.show(it1, bottomsheet.tag) }
        }

        rv_map.addItemDecoration(ItemDecoration(this.context!!, 0,32))
    }

    override fun onResume() {
        super.onResume()
        showMapList(fragView, sectionIdx)
        Log.d("sectionIdx" , "$sectionIdx")
    }


    private fun showMapList(view : View, num : Int){
        val sharedPref = activity!!.getSharedPreferences("TOKEN", Context.MODE_PRIVATE)
        val header = mutableMapOf<String, String?>()
        val token = sharedPref.getString("token", "token")
        header["Context-Type"] = "application/json"
        header["token"] = token
        if(header["token"] == "token") {//로그인하지 않았을 때
            service.requestMap(num).customEnqueue(
                onError = { Toast.makeText(context!!, "올바르지 않은 요청입니다.", Toast.LENGTH_SHORT) },
                onSuccess = {
                    setSection(num)
                    Log.d("maplist message >> ", it.body()!!.message)
                    if (it.body()!!.success) {
                        Log.d("maplist", "성공")
                        detailData = it.body()!!.data.elementAt(0)
                        rv_map.visibility = View.VISIBLE
                        mapAdapter =
                            MapAdapter(view.context, it.body()!!.data.toMutableList()) { MapData, View ->
                                val intent = Intent(activity, RecommendDetailActivity::class.java)
                                intent.putExtra("bookstoreIdx", MapData.bookstoreIdx)
                                startActivity(intent)
                            }
                        rv_map.adapter = mapAdapter
                        map_no_bookstore.visibility = View.GONE
                    }
                    if (it.body()!!.message == "서점 리스트가 없습니다.") {
                        rv_map.visibility = View.GONE
                        map_no_bookstore.visibility = View.VISIBLE
                    }
                }
            )
        }
        else {
            service.requestMapLogin(num, header).customEnqueue(
                onError = { Toast.makeText(context!!, "올바르지 않은 요청입니다.", Toast.LENGTH_SHORT) },
                onSuccess = {
                    setSection(num)
                    Log.d("오류", it.body()!!.message)
                    if (it.body()!!.success) {
                        Log.d("maplist", "성공")
                        detailData = it.body()!!.data.elementAt(0)
                        rv_map.visibility = View.VISIBLE
                        mapAdapter =
                            MapAdapter(view.context, it.body()!!.data.toMutableList()) { MapData, View ->
                                val intent = Intent(activity, RecommendDetailActivity::class.java)
                                intent.putExtra("bookstoreIdx", MapData.bookstoreIdx)
                                startActivity(intent)
                            }
                        rv_map.adapter = mapAdapter
                        map_no_bookstore.visibility = View.GONE
                    }
                    if (it.body()!!.message == "서점 리스트가 없습니다.") {
                        rv_map.visibility = View.GONE
                        map_no_bookstore.visibility = View.VISIBLE
                    }
                }
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.search -> {
                val intent = Intent(context, SearchActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) //해보고 이상하면 지울것
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setSection(sectionIdx : Int){
        when(sectionIdx){
            1 -> {tv_location.text = "용산구"}
            2 -> {tv_location.text = "마포구"}
            3 -> {tv_location.text = "관악구,영등포구,강서구"}
            4 -> {tv_location.text = "광진구,노원구,성북구"}
            5 -> {tv_location.text = "서초구,강남구,송파구"}
            6 -> {tv_location.text = "서대문구,종로구"}
        }
    }

}
