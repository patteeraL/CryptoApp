package com.example.cryptoranking;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface CryptoApiService {

    @GET("coins")
    @Headers("x-access-token: coinranking66247790bbfb3ed8dc82bc32fe0c8ce647eb37fe964cff71")
    Call<CoinResponse> getCoinResponse();

}
