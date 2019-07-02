package fr.ydelerm.drivy.repositories

import androidx.lifecycle.LiveData
import fr.ydelerm.drivy.model.Car

interface CarRepository {
    fun getCars(): LiveData<List<Car>>
    fun refreshCars()
}