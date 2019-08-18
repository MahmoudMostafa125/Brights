package com.noorapp.noor.api;


import com.noorapp.noor.models.AboutusModel.AboutResponse;
import com.noorapp.noor.models.AddFavouriteModel.AddFavouriteResponse;
import com.noorapp.noor.models.CategoryModel.CategoryResponse;
import com.noorapp.noor.models.ChatModel.ChatResponse;
import com.noorapp.noor.models.CityModel.CityResponse;
import com.noorapp.noor.models.ContactusModel.ContactusResponse;
import com.noorapp.noor.models.DefaultResponse;
import com.noorapp.noor.models.FavouriteModel.FavouriteResponse;
import com.noorapp.noor.models.GuideModel.GuideResponse;
import com.noorapp.noor.models.LoginFaceModel.LoginFaceResponse;
import com.noorapp.noor.models.LoginGoogleLogin.GoogleLoginResponse;
import com.noorapp.noor.models.LoginModel.LoginResponse;
import com.noorapp.noor.models.PaymentModel.PayTokenResponse;
import com.noorapp.noor.models.PlacesDetailsModel.PlaceDetailsResponse;
import com.noorapp.noor.models.ProfileModel.ProfileResponse;
import com.noorapp.noor.models.PromoModel.PromoResponse;
import com.noorapp.noor.models.RegisterModel.Register_response;
import com.noorapp.noor.models.ReservationModel.ReservationResponse;
import com.noorapp.noor.models.ReservationsModel.ReservationsResponse;
import com.noorapp.noor.models.StateModel.StateResponse;
import com.noorapp.noor.models.TransplaceModel.transresponse;
import com.noorapp.noor.models.TransportationModel.TransportationRespose;
import com.noorapp.noor.models.TripModel.TripResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

    /* @FormUrlEncoded
     @POST("guide")
     Call<DefaultResponses> noorApi(
             @Field("lang") String lang
     );*/
    @FormUrlEncoded
    @POST("apiv1/register")
    Call<Register_response> createUser(
            @Field("username") String username,
            @Field("email") String email,
            @Field("phone") String phone,
            @Field("password") String password,

            @Field("device_token") String device_token,
            @Field("device_model") String device_model,
            @Field("device_serial") String device_serial,
            @Field("device_versionRelease") String device_versionRelease,
            @Field("device_manufacture") String device_manufacture,
            @Field("device_brand") String device_brand,
            @Field("device_phone_id") String device_phone_id,
            @Field("device_product") String device_product
    );

    @FormUrlEncoded
    @POST("apiv1/login/facebook")
    Call<LoginFaceResponse> userLoginFacebook(
            @Field("username") String username,
            @Field("facebook") String facebook,
            @Field("gender") String gender,
            @Field("device_token") String device_token,
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("apiv1/login/google")
    Call<GoogleLoginResponse> userLoginGoogle(
            @Field("username") String username,
            @Field("google") String google,
            @Field("gender") String gender,
            @Field("device_token") String device_token,
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("apiv1/book")
    Call<ReservationResponse> ReservationApi(
            @Field("place_id") String place_id,
            @Field("ticket") String ticket,
            @Field("trip_id") String trip_id,
            @Field("trip_amount") String trip_amount,
            @Field("place_amount") String place_amount,
            @Field("date") String date,
            @Field("time") String time,
            @Field("api_token") String api_token,
            @Field("coupon") String coupon

    );

    @FormUrlEncoded
    @POST("apiv1/favorites/create")
    Call<AddFavouriteResponse> userAddFav(
            @Field("api_token") String api_token,
            @Field("item_id") String item_id,
            @Field("type") String type,
            @Field("status") String status

    );

    /* @FormUrlEncoded
    @POST("noorapp/apiv1/login/email")
    Call<LoginResponse> userLogin(
            @Field("email") String email,
            @Field("password") String password
    );*/
    @FormUrlEncoded
    @POST("apiv1/login/email")
    Call<LoginResponse> userLogin(
            @Field("email") String email,
            @Field("password") String password);


    @GET("apiv1/user/profile?")
    Call<ProfileResponse> userProfile(@Query("api_token") String api_token);

    @GET("apiv1/countries/{id}")
    Call<CityResponse> CityResponseAPI(@Path("id") String id, @Query("lang") String lang, @Query("status") String status, @Query("ticket") String ticket);

    //////////////////////////////////
    @GET("apiv1/states?")
    Call<StateResponse> getStateApi(@Query("lang") String lang);

    /////////////////////
    @GET("apiv1/guide?")
    Call<GuideResponse> getGuideApi(@Query("lang") String lang, @Query("status") String status);

    @GET("apiv1/places/{id}")
    Call<PlaceDetailsResponse> PlaceDetailsAPI(@Path("id") String id, @Query("lang") String lang, @Query("status") String status);

    @GET("apiv1/guide/{id}")
    Call<GuideResponse> GuideDetailsAPI(@Path("id") String id, @Query("lang") String lang, @Query("status") String status);

    @GET("apiv1/categories?")
    Call<CategoryResponse> CategoryAPI(@Query("lang") String lang);


    @GET("apiv1/places?")
    Call<DefaultResponse> noorApi(
            @Query("location") String location, @Query("lang") String lang, @Query("status") String status);

    @GET("apiv1/places?")
    Call<DefaultResponse> noorApi2(
            @Query("location") String location, @Query("lang") String lang, @Query("status") String status, @Query("ticket") String ticket);

    @GET("apiv1/transportation?")
    Call<TransportationRespose> TransportationApi(
            @Query("lang") String lang, @Query("status") String status);

    @GET("apiv1/trips/{id}")
    Call<TripResponse> TripsAPI(@Path("id") String id, @Query("lang") String lang, @Query("status") String status);


    ////////// search
    @GET("apiv1/places?")
    Call<DefaultResponse> SearchApi(
            @Query("search") String search, @Query("lang") String lang, @Query("status") String status);

    ////////////////////////////
    @GET("apiv1/favorites?")
    Call<FavouriteResponse> FavouriteApi(
            @Query("api_token") String api_token, @Query("lang") String lang, @Query("type") String type);
    /////////////////////////////

    @GET("apiv1/reservations?")
    Call<ReservationsResponse> ReservationApi(
            @Query("api_token") String api_token, @Query("lang") String lang);


    @GET("apiv1/transportation_places")
    Call<transresponse> TransplaceApi();
    /////////////////////////////////// Payment token
    @GET("http://noor.mgcoder.com/apiv1/payment/token")
    Call<PayTokenResponse> PaymenttokenApi();

    //////////////////////////////////// promo
    @GET("/apiv1/coupon/check/{code}?")
    Call<PromoResponse> PromoApi(@Path("code") String id,
                                 @Query("api_token") String api_token);

    ////////////////////////////////////////chat-addmessage
    @FormUrlEncoded
    @POST("apiv1/chat/send")
    Call<ChatResponse> AddMessageAPI(
            @Field("device_token") String device_token,
            @Field("message") String message,
            @Field("chat_id") String chat_id);

    @FormUrlEncoded
    @POST("apiv1/chat/send")
    Call<ChatResponse> AddMessageAPIWithoutID(
            @Field("device_token") String device_token,
            @Field("message") String message);

    ///////////////////////////////////////// about us
    @GET("apiv1/about?")
    Call<List<AboutResponse>> AboutUsApi(
            @Query("lang") String lang);

    ///////////////////////////////////////////contact us
    @FormUrlEncoded
    @POST("apiv1/contact")
    Call<ContactusResponse> ContactUsApi(
            @Field("message") String message,
            @Field("email") String email);
    /////////////////////////////////////////book car

    @FormUrlEncoded
    @POST("apiv1/trip/book")
    Call<ContactusResponse> BookCarApi(
            @Field("api_token") String api_token,
            @Field("trip_id") String trip_id,
            @Field("amount") String amount,
            @Field("date") String date,
            @Field("time") String time,
            @Field("phone") String phone,
            @Field("trip_from") String trip_from,
            @Field("trip_to") String trip_to
            );
    ///////////////////////////////////////////////edit profile


    @FormUrlEncoded
    @POST("apiv1/profile/update?")
    Call<ContactusResponse> EditProfileApi(
            @Field("api_token") String api_token,
            @Field("phone") String phone,
            @Field("avatar") String avatar,
            @Field("username") String username
    );

}

