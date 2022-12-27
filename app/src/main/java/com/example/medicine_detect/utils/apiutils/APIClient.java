package com.example.medicine_detect.utils.apiutils;

import com.example.medicine_detect.model.MedicinePlant;

import java.util.List;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    private static final String URL = "http://chun-server.shop";
    private final APIInterface instance;

    public APIClient() {
        instance = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
                .create(APIInterface.class);
    }

    public Observable<List<MedicinePlant>> getAllMedicinePlants() {
        return instance.getAllMedicinePlants();
    }

    public Observable<MedicinePlant> getMedicinePlantByID(int idItem) {
        return instance.getMedicinePlantByID(idItem);
    }
}
