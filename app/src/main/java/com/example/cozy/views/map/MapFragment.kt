package com.example.cozy.views.map

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.cozy.ItemDecoration
import com.example.cozy.MainActivity
import com.example.cozy.R
import com.example.cozy.views.main.RecommendDetailActivity
import kotlinx.android.synthetic.main.fragment_map.view.*


/**
 * A simple [Fragment] subclass.
 */
class MapFragment : Fragment() {

    val data = mutableListOf<MapData>()
    lateinit var mapAdapter: MapAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_map, container, false)
        setHasOptionsMenu(true)
        mapAdapter = MapAdapter(view.context){
            MapData -> val intent = Intent(activity as MainActivity, RecommendDetailActivity::class.java)
            startActivity(intent)
        }
        view.rv_map.adapter = mapAdapter
        loadData(view)


        return view
    }


    fun loadData(v : View){
        data.apply{
            for ( i in 0..7) {
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
        }
        mapAdapter.data = data
        v.rv_map.addItemDecoration(ItemDecoration(this.context!!, 0,36))
        mapAdapter.notifyDataSetChanged()
    }

}
