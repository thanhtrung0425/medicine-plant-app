package com.example.medicine_detect.helper


import com.example.medicine_detect.model.MedicinePlant
import io.reactivex.rxjava3.subjects.BehaviorSubject
import java.io.File

object RxBehaviorBus {
    val updateHomeBehaviorSubject: BehaviorSubject<MutableList<MedicinePlant>> = BehaviorSubject.create()
}
