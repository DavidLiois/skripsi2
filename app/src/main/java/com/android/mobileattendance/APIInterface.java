package com.android.mobileattendance;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIInterface {

    @POST("Profile.php")
    Call<List<MobileAttendance>> getProfile();

    @POST("PresensiHistory.php")
    Call<List<MobileAttendance>> getHistory();

    @FormUrlEncoded
    @POST("AddEmployee.php")
    Call<MobileAttendance> addEmployee(
            @Field("key") String key,
            @Field("name") String name,
            @Field("species") String species,
            @Field("breed") String breed,
            @Field("gender") int gender,
            @Field("birth") String birth,
            @Field("picture") String picture);

    @FormUrlEncoded
    @POST("update_pet.php")
    Call<MobileAttendance> updatePet(
            @Field("key") String key,
            @Field("id") int id,
            @Field("name") String name,
            @Field("species") String species,
            @Field("breed") String breed,
            @Field("gender") int gender,
            @Field("birth") String birth,
            @Field("picture") String picture);

    @FormUrlEncoded
    @POST("delete_pet.php")
    Call<MobileAttendance> deletePet(
            @Field("key") String key,
            @Field("id") int id,
            @Field("picture") String picture);

    @FormUrlEncoded
    @POST("update_love.php")
    Call<MobileAttendance> updateLove(
            @Field("key") String key,
            @Field("id") int id,
            @Field("love") boolean love);
}
