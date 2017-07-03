package pl.kfrak.googlerepos;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import pl.kfrak.googlerepos.component.DaggerServiceComponent;
import pl.kfrak.googlerepos.component.ServiceComponent;
import pl.kfrak.googlerepos.model.Repos;
import pl.kfrak.googlerepos.modules.ServiceModule;
import pl.kfrak.googlerepos.service.ReposService;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.list_view_repos)
    ListView listView;

    private ReposService.ReposApi reposApi;
    private List<String> reposNames = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter;
    private ProgressDialog progressDialog;

    ServiceComponent serviceComponent;
    @Inject
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        injectServiceComponent();
        fetchRepos();
        configureListView();
    }

    //metoda która zaindżektuje nam nasze rzeczy
    private void injectServiceComponent(){
        String url = "https://api.github.com/users/";
        serviceComponent = DaggerServiceComponent.builder()
                .serviceModule(new ServiceModule(url))
                .build();
        serviceComponent.inject(this);
        reposApi = retrofit.create(ReposService.ReposApi.class);
    }

    private void fetchRepos(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("loading data");
        progressDialog.show();
        reposApi.fetchRepos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Repos>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull List<Repos> reposes) {
                        for (int i = 0; i < reposes.size() - 1; i++) {
                            reposNames.add(reposes.get(i).getName());
                        }
                        arrayAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("LOOOG", e.getMessage());
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onComplete() {
                        progressDialog.dismiss();
                        
                    }
                });
    }
    private void configureListView(){
        arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_view_black_text, R.id.list_content, reposNames );
        listView.setAdapter(arrayAdapter);
    }
}
