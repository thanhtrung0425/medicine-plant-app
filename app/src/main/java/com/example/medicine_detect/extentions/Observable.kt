package com.example.medicine_detect.extentions

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

internal fun <T> Observable<T>.observeOnUiThread(): Observable<T> =
    this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

internal fun <T> Single<T>.observeOnUiThread(): Single<T> =
    this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

internal fun Completable.observeOnUiThread(): Completable =
    this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

internal fun <T> Observable<T>.observeOnIOThread(): Observable<T> =
    this.subscribeOn(Schedulers.io()).observeOn(Schedulers.io())

internal fun <T> Single<T>.observeOnIOThread(): Single<T> =
    this.subscribeOn(Schedulers.io()).observeOn(Schedulers.io())

internal fun Completable.observeOnIOThread(): Completable =
    this.subscribeOn(Schedulers.io()).observeOn(Schedulers.io())
