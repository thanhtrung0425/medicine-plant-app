package com.example.medicine_detect.utils.apiutils;

import com.example.medicine_detect.model.MedicinePlant;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIInterface {

    @GET("/upload/")
    Single<Boolean> uploadDataBase();

    @GET("/medicine-plants/")
    Observable<List<MedicinePlant>> getAllMedicinePlants();

    @GET("/medicine-plant/id={id}")
    Observable<MedicinePlant> getMedicinePlantByID(@Path("id") int id);

    @GET("/clean-cache/")
    Single<Boolean> cleanCache();

}
