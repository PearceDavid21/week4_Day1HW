package com.example.week4_day1


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), InputFragment.FragmentListener {

    lateinit var HotelDB: HotelDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Glide.with(this)
            .load(getString(R.string.logo_uri))
            .into(logo_imageview)

        HotelDB = Room.databaseBuilder(
            this,
            HotelDatabase::class.java,
            "Hotel.db"
        )
            .allowMainThreadQueries()
            .build()

        insert_guest.setOnClickListener {
            val fragment = InputFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_framelayout, fragment)
                .addToBackStack(fragment.tag)
                .commit()
        }
        setUpAdapter()

        val itemDecoration =  DividerItemDecoration(this, VERTICAL)
        guest_recyclerview.addItemDecoration(itemDecoration)
        guest_recyclerview.addItemDecoration(itemDecoration)
    }

    override fun onAttachFragment(fragment: Fragment) {
        super.onAttachFragment(fragment)
        if (fragment is InputFragment)
            (fragment).setListener(this)
    }

    private fun setUpAdapter() {
        val adapter = GuestAdapter(HotelDB.HotelDAO().retrieveAllRooms())
        guest_recyclerview.adapter = adapter
        guest_recyclerview.layoutManager = LinearLayoutManager(this)
    }


    override fun updateResults() {
        setUpAdapter()
    }
}