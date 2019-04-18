package com.jorchi.arc.data.remote;

import com.jorchi.arc.data.remote.model.GirlData;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    @GET("api/data/福利/10/{index}")
    Observable<GirlData> getGirlsData(@Path("index") int index);
}
