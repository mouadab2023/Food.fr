package model;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class RetrofitClient {
    private static final String BASE_URL = "https://places.googleapis.com/";
    private static Retrofit retrofit;

    public static GooglePlacesApi getApi() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(GooglePlacesApi.class);
    }
}
