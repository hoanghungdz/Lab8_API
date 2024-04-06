package com.hunghhph44272.lab7_api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface InterInsertPrd {
    @FormUrlEncoded
    @POST("create_product.php")
    Call<SvrResponsePrd> insertPrd(
            @Field("name") String name,
            @Field("price") String price,
            @Field("description") String description
    );
}
