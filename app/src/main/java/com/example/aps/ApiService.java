package com.example.aps;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @GET("/information")
    Call<List<Post>> getPosts();

    @POST("/information")
    Call<Post> createPost(@Body Post post);
}
