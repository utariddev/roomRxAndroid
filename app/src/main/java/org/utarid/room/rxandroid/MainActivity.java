package org.utarid.room.rxandroid;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import org.utarid.room.rxandroid.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.MaybeObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private DatabaseWorker databaseWorker;
    private List<EntityWorker> globalWorkersList;
    private AdapterWorker mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        databaseWorker = DatabaseWorker.getInstance(this);

        globalWorkersList = new ArrayList<>();
        RecyclerView recyclerView = binding.recyclerView;
        mAdapter = new AdapterWorker(this, globalWorkersList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        binding.btnInsertWorker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertWorker();
            }
        });

        binding.btnGetAllWorkers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAllWorkers();
            }
        });

        binding.btnDeleteWorker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteWorker();
            }
        });
    }

    public void insertWorker() {

        EntityWorker worker = new EntityWorker(binding.txtInsertWorkerName.getText().toString(), binding.txtInsertWorkerSurname.getText().toString(), binding.txtInsertWorkerAge.getText().toString());
        databaseWorker.daoWorker().insertWorker(worker)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MaybeObserver<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Long newInsertedID) {
                        Snackbar snackbar = Snackbar.make(binding.mainLayout, "id : " + newInsertedID + " is added", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void deleteWorker() {

        EntityWorker worker = new EntityWorker(Integer.parseInt(binding.txtDeleteWorkderID.getText().toString()));
        databaseWorker.daoWorker().deleteWorker(worker)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MaybeObserver<Integer>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Integer deletedRow) {
                        Snackbar snackbar;
                        if (deletedRow == 1) {
                            snackbar = Snackbar.make(binding.mainLayout, "id : " + binding.txtDeleteWorkderID.getText().toString() + " is deleted", Snackbar.LENGTH_LONG);
                        } else {
                            snackbar = Snackbar.make(binding.mainLayout, "deletion is not successful", Snackbar.LENGTH_LONG);
                        }
                        snackbar.show();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void getAllWorkers() {

        globalWorkersList.clear();

        databaseWorker.daoWorker().getAllWorkers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MaybeObserver<List<EntityWorker>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<EntityWorker> workersList) {
                        globalWorkersList.addAll(workersList);
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}