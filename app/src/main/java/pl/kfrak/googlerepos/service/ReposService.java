package pl.kfrak.googlerepos.service;

import java.util.List;
import java.util.Observable;

import pl.kfrak.googlerepos.model.Repos;
import retrofit2.http.GET;

/**
 * Created by RENT on 2017-07-03.
 */

public class ReposService {

    public interface ReposApi{
        @GET("google/repos")
        io.reactivex.Observable<List<Repos>> fetchRepos();
    }

}
