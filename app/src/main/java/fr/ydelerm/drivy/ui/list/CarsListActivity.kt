package fr.ydelerm.drivy.ui.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import fr.ydelerm.drivy.R
import fr.ydelerm.drivy.model.Car
import fr.ydelerm.drivy.viewmodel.CarsViewModel

class CarsListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cars_list)

        val carsViewModel = ViewModelProviders.of(this).get(CarsViewModel::class.java)

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val swipeLayout: SwipeRefreshLayout = findViewById(R.id.swipeContainer)

        carsViewModel.allCars.observe(this, Observer<List<Car>> { cars: List<Car> ->
            recyclerView.swapAdapter(CarsAdapter(cars, applicationContext), true)
            swipeLayout.isRefreshing = false
        })

        swipeLayout.setOnRefreshListener { carsViewModel.refresh() }

        carsViewModel.refresh()
    }
}
