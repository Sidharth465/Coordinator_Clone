package io.siddharth.myapplication.data.remote;

import android.content.Context;

import io.siddharth.myapplication.util.NetworkConstant;
import io.siddharth.myapplication.util.UtilsSharedPreferences;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit = null;

    public static ApiService getApiService(Context context) {
        if (retrofit == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            // Interceptor to add JWT token to every request (Except Login)
            Interceptor authInterceptor = chain -> {
                Request original = chain.request();
                
                // Skip adding token for login endpoint
                if (original.url().encodedPath().contains("/login")) {
                    return chain.proceed(original);
                }
                
                // Retrieve the token from SharedPreferences
                String token = null;
                if (UtilsSharedPreferences.getInstance().restoreLoginResponseSharedPreference(context) != null) {
                    token = UtilsSharedPreferences.getInstance().restoreLoginResponseSharedPreference(context).getAuthToken();
                }
                
                if (token != null && !token.isEmpty()) {
                    Request.Builder requestBuilder = original.newBuilder()
                            .header("Authorization", "Bearer " + token)
                            .method(original.method(), original.body());
                    return chain.proceed(requestBuilder.build());
                }
                
                return chain.proceed(original);
            };

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .addInterceptor(authInterceptor)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(NetworkConstant.url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit.create(ApiService.class);
    }
    
    // Legacy method if context isn't available, but auth will fail for secondary calls
//    public static ApiService getApiService() {
//         return retrofit != null ? retrofit.create(ApiService.class) : null;
//    }
}