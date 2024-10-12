package com.example.IGN

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var newRecylerview: RecyclerView
    private lateinit var newArrayList: ArrayList<News>
    private lateinit var tempArrayList: ArrayList<News>
    lateinit var imageId: Array<Int>
    lateinit var heading: Array<String>
    lateinit var news: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageId = arrayOf(
            R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e,
            R.drawable.f, R.drawable.g, R.drawable.h, R.drawable.i, R.drawable.j
        )

        heading = arrayOf(
            "Civilization 7 PC Specs Revealed: Here are the Requirements for Minimum, Recommended, and Ultra Settings",
            "Magic: The Gathering Developer Responds to Fears It’s Only Focused on Short-term Profits",
            "Tales of Graces F Dev Talks About Tales of the Abyss Remaster Possibilities and the Future of the Series",
            "Joker: Folie à Deux's Biggest Surprise Is a Repeat of a Controversial Marvel Moment",
            "Xbox Made The Right Call Skipping a PS5 Pro Competitor",
            "Halo Infinite Is Officially Getting a Third-Person Mode This November",
            "Joy-Con vs. Nintendo Switch Pro Controller: Which Should You Buy?",
            "Zelda Fans Are Overlaying the Echoes of Wisdom Map With A Link to the Past, and Making Some Surprising Discoveries",
            "Sword Art Online Developer Wants to Take the Series in a More Mature Direction",
            "NHL 25 - Review"
        )

        news = arrayOf(
            getString(R.string.news_a), getString(R.string.news_b), getString(R.string.news_c),
            getString(R.string.news_d), getString(R.string.news_e), getString(R.string.news_f),
            getString(R.string.news_g), getString(R.string.news_h), getString(R.string.news_i),
            getString(R.string.news_j)
        )

        newRecylerview = findViewById(R.id.recyclerView)
        newRecylerview.layoutManager = LinearLayoutManager(this)
        newRecylerview.setHasFixedSize(true)

        newArrayList = arrayListOf()
        tempArrayList = arrayListOf()
        getUserdata()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)
        val item = menu?.findItem(R.id.search_action)
        val searchView = item?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextChange(newText: String?): Boolean {
                tempArrayList.clear()
                val searchText = newText?.lowercase(Locale.getDefault()) ?: ""
                if (searchText.isNotEmpty()) {
                    newArrayList.forEach {
                        if (it.heading.lowercase(Locale.getDefault()).contains(searchText)) {
                            tempArrayList.add(it)
                        }
                    }
                    newRecylerview.adapter?.notifyDataSetChanged()
                } else {
                    tempArrayList.clear()
                    tempArrayList.addAll(newArrayList)
                    newRecylerview.adapter?.notifyDataSetChanged()
                }
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    private fun getUserdata() {
        for (i in imageId.indices) {
            val newsItem = News(imageId[i], heading[i])
            newArrayList.add(newsItem)
        }
        tempArrayList.addAll(newArrayList)
        val adapter = MyAdapter(tempArrayList)
        newRecylerview.adapter = adapter
        adapter.setOnItemClickListener(object : MyAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(this@MainActivity, NewsActivity::class.java)
                intent.putExtra("heading", newArrayList[position].heading)
                intent.putExtra("imageId", newArrayList[position].titleImage)
                intent.putExtra("news", news.getOrElse(position) { "News content not available" })
                startActivity(intent)
            }
        })
    }
}
