package com.yourcozy.cozy.views.category

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.yourcozy.cozy.MainActivity
import com.yourcozy.cozy.R
import com.yourcozy.cozy.views.SearchActivity

class CategoryFragment : Fragment(), View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        val view = inflater.inflate(R.layout.fragment_category, container, false)

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
                val intent = Intent(activity as MainActivity, CategoryListActivity::class.java)
                intent.putExtra("categoryIdx",1)
                startActivity(intent)
            }
            R.id.btn_writing -> {
                val intent = Intent(activity as MainActivity, CategoryListActivity::class.java)
                intent.putExtra("categoryIdx",2)
                startActivity(intent)
            }
            R.id.btn_rental -> {
                val intent = Intent(activity as MainActivity, CategoryListActivity::class.java)
                intent.putExtra("categoryIdx",3)
                startActivity(intent)
            }
            R.id.btn_readingClub -> {
                val intent = Intent(activity as MainActivity, CategoryListActivity::class.java)
                intent.putExtra("categoryIdx",4)
                startActivity(intent)
            }
            R.id.btn_night -> {
                val intent = Intent(activity as MainActivity, CategoryListActivity::class.java)
                intent.putExtra("categoryIdx",5)
                startActivity(intent)
            }
            R.id.btn_exhibit -> {
                val intent = Intent(activity as MainActivity, CategoryListActivity::class.java)
                intent.putExtra("categoryIdx",6)
                startActivity(intent)
            }
            R.id.btn_recommendation -> {
                val intent = Intent(activity as MainActivity, CategoryListActivity::class.java)
                intent.putExtra("categoryIdx",7)
                startActivity(intent)
            }
            R.id.btn_workshop -> {
                val intent = Intent(activity as MainActivity, CategoryListActivity::class.java)
                intent.putExtra("categoryIdx",8)
                startActivity(intent)
            }
            R.id.btn_bookTalk -> {
                val intent = Intent(activity as MainActivity, CategoryListActivity::class.java)
                intent.putExtra("categoryIdx",9)
                startActivity(intent)
            }
            R.id.btn_music -> {
                val intent = Intent(activity as MainActivity, CategoryListActivity::class.java)
                intent.putExtra("categoryIdx",10)
                startActivity(intent)
            }
            R.id.btn_recite -> {
                val intent = Intent(activity as MainActivity, CategoryListActivity::class.java)
                intent.putExtra("categoryIdx",11)
                startActivity(intent)
            }
            R.id.btn_silentReading -> {
                val intent = Intent(activity as MainActivity, CategoryListActivity::class.java)
                intent.putExtra("categoryIdx",12)
                startActivity(intent)
            }
            R.id.btn_transcription -> {
                val intent = Intent(activity as MainActivity, CategoryListActivity::class.java)
                intent.putExtra("categoryIdx",13)
                startActivity(intent)
            }
            R.id.btn_stay -> {
                val intent = Intent(activity as MainActivity, CategoryListActivity::class.java)
                intent.putExtra("categoryIdx",14)
                startActivity(intent)
            }
            R.id.btn_making -> {
                val intent = Intent(activity as MainActivity, CategoryListActivity::class.java)
                intent.putExtra("categoryIdx",15)
                startActivity(intent)
            }
            R.id.btn_publication -> {
                val intent = Intent(activity as MainActivity, CategoryListActivity::class.java)
                intent.putExtra("categoryIdx",16)
                startActivity(intent)
            }
            R.id.btn_class -> {
                val intent = Intent(activity as MainActivity, CategoryListActivity::class.java)
                intent.putExtra("categoryIdx",17)
                startActivity(intent)
            }
            R.id.btn_market -> {
                val intent = Intent(activity as MainActivity, CategoryListActivity::class.java)
                intent.putExtra("categoryIdx",18)
                startActivity(intent)
            }

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
}
