package com.example.medicine_detect.utils.apiutils;

import android.content.Context;

import com.example.medicine_detect.model.MedicinePlant;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ApiUtils {

    private static ApiUtils instance;
    private final Context context;

    private ApiUtils(Context context) {
        this.context = context;
    }

    public static ApiUtils getInstance(Context context) {
        if (instance == null) {
            instance = new ApiUtils(context);
        }
        return instance;
    }

    public Single<List<MedicinePlant>> loadDataFromApi() {
        return Single.create(emitter -> {
            APIClient apiClient = new APIClient();
            apiClient.getAllMedicinePlants()
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<MedicinePlant>>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NonNull List<MedicinePlant> medicinePlants) {
                            emitter.onSuccess(medicinePlants);
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            emitter.onError(e);
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        });
    }
}
