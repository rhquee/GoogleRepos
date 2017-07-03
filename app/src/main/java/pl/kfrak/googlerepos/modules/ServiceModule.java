package pl.kfrak.googlerepos.modules;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by RENT on 2017-07-03.
 */

@Module
public class ServiceModule {

    private String mBaseUrl;

    public ServiceModule(String mBaseUrl) {
        this.mBaseUrl = mBaseUrl;
    }

    @Singleton
    @Provides
    GsonConverterFactory provideGson() {
        return GsonConverterFactory.create();
    }

    @Singleton
    @Provides
    RxJava2CallAdapterFactory provideAdapterFactory() {
        return RxJava2CallAdapterFactory.create();
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit(GsonConverterFactory gson, RxJava2CallAdapterFactory rxJava2CallAdapterFactory){
        return new Retrofit.Builder()
                .addConverterFactory(gson)
                .addCallAdapterFactory(rxJava2CallAdapterFactory)
                .baseUrl(mBaseUrl)
                .build();
    }
}
