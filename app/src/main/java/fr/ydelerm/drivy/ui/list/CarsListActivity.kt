package fr.ydelerm.drivy.ui.list

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import fr.ydelerm.drivy.R
import fr.ydelerm.drivy.model.Car
import fr.ydelerm.drivy.ui.detail.DetailActivity
import fr.ydelerm.drivy.viewmodel.CarsViewModel


class CarsListActivity : AppCompatActivity(), CarClickListener {

    private lateinit var refreshButton: Button
    private lateinit var errorTextView: TextView
    private lateinit var swipeLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cars_list)

        val carsViewModel = ViewModelProviders.of(this).get(CarsViewModel::class.java)

        refreshButton = findViewById(R.id.button_refresh)
        errorTextView = findViewById(R.id.textview_error)
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        swipeLayout = findViewById(R.id.swipeContainer)

        carsViewModel.allCars.observe(this, Observer<List<Car>> { cars: List<Car> ->
            if (cars.isEmpty()) {
                refreshButton.visibility = View.VISIBLE
                errorTextView.visibility = View.VISIBLE
            } else {
                recyclerView.swapAdapter(CarsAdapter(cars, this, this), true)
            }
            swipeLayout.isRefreshing = false
        })

        refreshButton.setOnClickListener { refresh(carsViewModel) }
        swipeLayout.setOnRefreshListener { refresh(carsViewModel) }


        refresh(carsViewModel)
    }

    private fun refresh(carsViewModel: CarsViewModel) {
        refreshButton.visibility = View.GONE
        errorTextView.visibility = View.GONE
        swipeLayout.isRefreshing = true
        carsViewModel.refresh()
    }

    override fun onCarClicked(position: Int, car: Car, sharedImageView: ImageView) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.PARAM_CAR_DATA, car)
        intent.putExtra(DetailActivity.PARAM_CAR_TRANSITION_NAME, ViewCompat.getTransitionName(sharedImageView))
        val options = ViewCompat.getTransitionName(sharedImageView)?.let {
            ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                sharedImageView,
                it
            )
        }
        startActivity(intent, options?.toBundle())
    }
}
