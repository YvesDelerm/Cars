package fr.ydelerm.drivy.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import fr.ydelerm.drivy.repositories.NetworkCarRepositoryImpl

class CarsViewModel(application: Application) : AndroidViewModel(application) {
    fun refresh() {
        repository.refreshCars();
    }

    private var repository = NetworkCarRepositoryImpl() //TODO injection dépendances
    val allCars = repository.getCars()
}