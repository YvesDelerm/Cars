package fr.ydelerm.drivy.ui.list

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import fr.ydelerm.drivy.R
import fr.ydelerm.drivy.model.Car
import fr.ydelerm.drivy.ui.detail.DetailActivity

class CarsAdapter(private val cars: List<Car>, private val context: Context) : RecyclerView.Adapter<CarsAdapter.CarViewHolder>() {

    class CarViewHolder(@NonNull v: View) : RecyclerView.ViewHolder(v) {
        internal var carsLayout: LinearLayout = v.findViewById(R.id.cars_layout)
        internal var carName: TextView = v.findViewById(R.id.car_name)
        internal var dailyPrice: TextView = v.findViewById(R.id.daily_price)
        internal var rating: RatingBar = v.findViewById(R.id.rating)
        internal var ratingCount: TextView = v.findViewById(R.id.rating_count)
        internal var carImage: ImageView = v.findViewById(R.id.car_image)
    }


    @NonNull
    override fun onCreateViewHolder(
        @NonNull parent: ViewGroup,
        viewType: Int
    ): CarViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.template_car_list_item, parent, false)
        return CarViewHolder(view)

    }

    override fun onBindViewHolder(@NonNull holder: CarViewHolder, position: Int) {
        val car = cars[position]
        Picasso.get()
            .load(car.pictureUrl)
            .placeholder(R.drawable.ic_car_black_48dp)
            .error(R.drawable.ic_car_black_48dp)
            .into(holder.carImage)
        holder.carName.text = context.getString(R.string.car_name_format, car.brand, car.model)
        holder.dailyPrice.text = context.getString(R.string.price_per_day_format, car.pricePerDay)
        holder.rating.rating = car.rating.average
        holder.ratingCount.text = car.rating.count.toString()
        holder.carsLayout.setOnClickListener{_ -> onCarSelected(holder)}
    }

    private fun onCarSelected(@NonNull holder:CarViewHolder) {
        val car = cars[holder.adapterPosition]
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra(DetailActivity.PARAM_CAR_DATA, Gson().toJson(car))
        intent.flags=Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }


    override fun getItemCount(): Int {
        return cars.size
    }
}