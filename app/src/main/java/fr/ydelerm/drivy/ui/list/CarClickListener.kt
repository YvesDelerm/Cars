package fr.ydelerm.drivy.ui.list

import android.widget.ImageView
import fr.ydelerm.drivy.model.Car

interface CarClickListener {
    fun onCarClicked(position: Int, car: Car, sharedImageView: ImageView)
}