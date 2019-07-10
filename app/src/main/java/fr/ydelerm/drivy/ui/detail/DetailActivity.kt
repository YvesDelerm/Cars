package fr.ydelerm.drivy.ui.detail

import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import fr.ydelerm.drivy.R
import fr.ydelerm.drivy.model.Car
import jp.wasabeef.picasso.transformations.CropCircleTransformation


class DetailActivity : AppCompatActivity() {

    private lateinit var carImage: ImageView
    private lateinit var carName: TextView
    private lateinit var dailyPrice: TextView
    private lateinit var carRating: RatingBar
    private lateinit var ratingCount: TextView
    private lateinit var ownerImage: ImageView
    private lateinit var ownerName: TextView
    private lateinit var ownerRating: RatingBar

    companion object {
        const val PARAM_CAR_TRANSITION_NAME = "DetailActivity.CarTransitionName"
        const val PARAM_CAR_DATA = "DetailActivity.CarData"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportPostponeEnterTransition()

        val car = intent.getParcelableExtra(PARAM_CAR_DATA) as Car? ?: throw IllegalArgumentException("carData parameter is required for activity detailActivity")

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home) {
            finishAfterTransition()
            return true
        }
        return false
    }

    private fun displayInfos(car: Car?) {
        if (car==null) {
            throw IllegalArgumentException("carData parameter could not be parse on detailActivity")
        }

        val imageTransitionName = intent.extras?.getString(PARAM_CAR_TRANSITION_NAME)
        carImage.transitionName = imageTransitionName

        val callback = object: Callback {
            override fun onSuccess() {
                supportStartPostponedEnterTransition()
            }
            override fun onError(e: Exception?) {
                supportStartPostponedEnterTransition()
            }
        }

        Picasso.get()
            .load(car.pictureUrl)
            .placeholder(R.drawable.ic_car_black_48dp)
            .error(R.drawable.ic_car_black_48dp)
            .into(carImage, callback)
        carName.text = getString(R.string.car_name_format, car.brand, car.model)
        dailyPrice.text = getString(R.string.price_per_day_format, car.pricePerDay)
        carRating.rating = car.rating.average
        ratingCount.text = car.rating.count.toString()

        Picasso.get()
            .load(car.owner.pictureUrl)
            .placeholder(R.drawable.ic_person_black_48dp)
            .error(R.drawable.ic_person_black_48dp)
            .transform(CropCircleTransformation())
            .into(ownerImage)
        ownerName.text = car.owner.name
        ownerRating.rating = car.owner.rating.average
    }
}
