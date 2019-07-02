package fr.ydelerm.drivy.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import fr.ydelerm.drivy.R
import fr.ydelerm.drivy.model.Car
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    private var carImage: ImageView? = null
    private var carName: TextView? = null
    private var dailyPrice: TextView? = null
    private var carRating: RatingBar? = null
    private var ratingCount: TextView? = null
    private var ownerImage: ImageView? = null
    private var ownerName: TextView? = null
    private var ownerRating: RatingBar? = null

    companion object {
        const val PARAM_CAR_DATA = "DetailActivity.CarData"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val carData = intent.getStringExtra(PARAM_CAR_DATA)
        if (carData==null) {
            throw IllegalArgumentException("carData parameter is required for activity detailActivity")
        }

        val car = Gson().fromJson(carData, Car::class.java)

        carImage = findViewById(R.id.car_image)
        carName = findViewById(R.id.car_name)
        dailyPrice = findViewById(R.id.daily_price)
        carRating = findViewById(R.id.car_rating_bar)
        ratingCount = findViewById(R.id.car_rating_count)

        ownerImage = findViewById(R.id.owner_image)
        ownerName = findViewById(R.id.owner_name)
        ownerRating = findViewById(R.id.owner_rating_bar)

        displayInfos(car)
    }

    private fun displayInfos(car: Car?) {
        if (car==null) {
            throw IllegalArgumentException("carData parameter could not be parse on detailActivity")
        }

        Picasso.get()
            .load(car.pictureUrl)
            .placeholder(R.drawable.ic_car_black_48dp)
            .error(R.drawable.ic_car_black_48dp)
            .into(carImage)
        carName!!.text = getString(R.string.car_name_format, car.brand, car.model)
        dailyPrice!!.text = getString(R.string.price_per_day_format, car.pricePerDay)
        carRating!!.rating = car.rating.average
        ratingCount!!.text = car.rating.count.toString()

        Picasso.get()
            .load(car.owner.pictureUrl)
            .placeholder(R.drawable.ic_person_black_48dp)
            .error(R.drawable.ic_person_black_48dp)
            .into(ownerImage)
        ownerName!!.text = car.owner.name
        ownerRating!!.rating = car.owner.rating.average
    }
}
