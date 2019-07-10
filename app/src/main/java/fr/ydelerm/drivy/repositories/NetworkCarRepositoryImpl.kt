package fr.ydelerm.drivy.repositories

import android.util.Log
import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import fr.ydelerm.drivy.model.Car
import fr.ydelerm.drivy.network.CarApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkCarRepositoryImpl : CarRepository {
    private val BASE_URL = "https://raw.githubusercontent.com/drivy/jobs/master/android/api/"
    private var carApiService: CarApiService

    init {
        carApiService =  Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CarApiService::class.java)
    }

    private var cars = MutableLiveData<List<Car>>()

    private val LOGTAG = "NetworkCarRepositoryImp"

    override fun getCars(): LiveData<List<Car>> {
        return cars
    }

    override fun refreshCars() {
        val callback = object : Callback<List<Car>> {
            override fun onResponse(@NonNull call: Call<List<Car>>, @NonNull response: Response<List<Car>>) {
                cars.postValue(response.body()?:ArrayList())
            }

            override fun onFailure(@NonNull call: Call<List<Car>>, @NonNull throwable: Throwable) {
                Log.e(LOGTAG, "error while calling webservice", throwable)
                cars.postValue(ArrayList())
            }
        }

        val call = carApiService.getCars()
        call.enqueue(callback)
    }
}