package com.example.cozy.views.map

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.cozy.ItemDecoration
import com.example.cozy.MainActivity
import com.example.cozy.R
import com.example.cozy.views.main.RecommendDetailActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.fragment_map.*
import kotlinx.android.synthetic.main.fragment_map.view.*


class MapFragment : Fragment() {

    val data = mutableListOf<MapData>()
    lateinit var mapAdapter: MapAdapter
    private lateinit var persistentBottomSheetBehavior : BottomSheetBehavior<*>
    private var sectionIdx = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_map, container, false)
        setHasOptionsMenu(true)
        mapAdapter = MapAdapter(view.context){
            MapData, View -> val intent = Intent(activity as MainActivity, RecommendDetailActivity::class.java)
            startActivity(intent)
        }
        view.rv_map.adapter = mapAdapter
        Log.d("sectionIdx" , "$sectionIdx")
        //setSection(sectionIdx)
        loadData(view)



        return view
    }

    override fun onResume() {
        super.onResume()
        setSection(sectionIdx)
        Log.d("sectionIdx" , "$sectionIdx")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sectionIdx = { num : Int ->
            sectionIdx = num
        }

        tv_location.setOnClickListener {
            val bottomsheet = BottomSheetFragment(sectionIdx)
            fragmentManager?.let { it1 -> bottomsheet.show(it1, bottomsheet.tag) }
        }
    }


    fun loadData(v : View){
        data.apply{

                add(
                    MapData(
                        bookstoreIdx = 1,
                        img = "https://cdn.pixabay.com/photo/2015/11/19/21/11/knowledge-1052014__340.jpg",
                        bookstoreName = "코지책방",
                        location = "서울특별시 용산구 한강대로102길 용산구 한강 서울특별시 용산구 한강대로102길 용산구 한강 서울특별시 용산구 한강대로102길 용산구 한강",
                        tag1 = "베이커리",
                        tag2 = "베이커리",
                        tag3 = "베이커리"
                    )
                )
            add(
                MapData(
                    bookstoreIdx = 1,
                    img = "https://cdn.pixabay.com/photo/2015/11/19/21/11/knowledge-1052014__340.jpg",
                    bookstoreName = "코지책방",
                    location = "서울특별시 용산구 한강대로102길 용산구 한강",
                    tag1 = "베이커리",
                    tag2 = "베이커리",
                    tag3 = "베이커리"
                )
            )
            add(
                MapData(
                    bookstoreIdx = 1,
                    img = "https://cdn.pixabay.com/photo/2015/11/19/21/11/knowledge-1052014__340.jpg",
                    bookstoreName = "코지책방",
                    location = "서울특별시 용산구 한강대로102길 용산구 한강",
                    tag1 = "베이커리",
                    tag2 = "베이커리",
                    tag3 = "베이커리"
                )
            )
            add(
                MapData(
                    bookstoreIdx = 1,
                    img = "https://cdn.pixabay.com/photo/2015/11/19/21/11/knowledge-1052014__340.jpg",
                    bookstoreName = "코지책방",
                    location = "서울특별시 용산구 한강대로102길 용산구 한강",
                    tag1 = "베이커리",
                    tag2 = "베이커리",
                    tag3 = "베이커리"
                )
            )

        }
        mapAdapter.data = data
        v.rv_map.addItemDecoration(ItemDecoration(this.context!!, 0,36))
        mapAdapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.search, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.search -> Toast.makeText(context,"검색",Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    fun setSection(sectionIdx : Int){
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
