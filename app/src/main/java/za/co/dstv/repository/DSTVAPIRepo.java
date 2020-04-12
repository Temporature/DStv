package za.co.dstv.repository;

import za.co.dstv.model.TodoModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DSTVAPIRepo {

    @GET("/TODO")
    Call<TodoModel> getData();
}
