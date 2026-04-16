package io.siddharth.myapplication.data.remote;

import io.siddharth.myapplication.domain.model.LoginRequestModel;
import io.siddharth.myapplication.data.remote.response.StudentListResponse;
import io.siddharth.myapplication.domain.model.LoginResponseModel;
import io.siddharth.myapplication.domain.model.OrganizationModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiService {
    @POST("login")
    Call<LoginResponseModel> login(@Body LoginRequestModel request);

    @GET("api/v2/nodeIds")
    Call<Object> getNodeIds(@Header("x-select") String xSelectJson);

    @GET("api/v2/organization")
    Call<Object> getOrganizations(@Header("x-select") String xSelectJson);

    @GET("api/v2/assessment/key/meta")
    Call<Object> getAssessmentMeta();

    @GET("api/v2/download/omr/flat/v2")
    Call<StudentListResponse> getStudentList(@Header("x-select") String xSelectJson);



    @POST("api/v2/user")
    Call<Object> updateUser(@Body Object userRequest);

    @POST("api/v2/upload/app/user")
    Call<Object> uploadStudentData(@Body Object studentRequest);

    @POST("api/v2/upload/omr")
    Call<Object> uploadOMRData(@Body Object omrRequest);

    @GET("api/s3/uploadaccess")
    Call<Object> getImageUploadAccess();

    @POST("api/v2/content")
    Call<Object> uploadImageContent(@Body Object contentRequest);

    @POST("api/v2/content/association")
    Call<Object> uploadImageAssociation(@Body Object associationRequest);

    @POST("api/v2/event")
    Call<Object> trackEvent(@Body Object eventRequest);

    @POST("api/v2/assessment/reporting")
    Call<Object> reportEvent(@Body Object reportRequest);
}