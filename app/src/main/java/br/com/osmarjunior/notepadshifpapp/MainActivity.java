package br.com.osmarjunior.notepadshifpapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Stetho.initializeWithDefaults(this);
    }

    private Retrofit getRetrofit(){
        OkHttpClient client = new OkHttpClient.Builder()
                                    .addNetworkInterceptor(new StethoInterceptor())
                                    .build();

        return new Retrofit.Builder()
                .baseUrl("https://notepadcloudshiftosmarjunior.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }
}
