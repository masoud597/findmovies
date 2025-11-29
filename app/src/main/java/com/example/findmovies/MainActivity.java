package com.example.findmovies;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    private MainViewModel viewmodel;
    private MovieAdapter adapter;
    private ConstraintLayout success;
    private ConstraintLayout loading;
    private ConstraintLayout error;
    private TextView txtPage;
    private TextView txtErrorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.error), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        success = findViewById(R.id.success);
        loading = findViewById(R.id.loading);
        error = findViewById(R.id.error);
        txtPage = findViewById(R.id.txtPage);
        txtErrorMessage = findViewById(R.id.txtErrorMessage);

        RecyclerView recyclerView = findViewById(R.id.rvResult);
        adapter = new MovieAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        viewmodel = new ViewModelProvider(this).get(MainViewModel.class);

        observeData();
        viewmodel.fetchCurrentPage();

        findViewById(R.id.btnRetry).setOnClickListener(v -> viewmodel.fetchCurrentPage());
        findViewById(R.id.btnNext).setOnClickListener(v -> viewmodel.nextPage());
        findViewById(R.id.btnPrevious).setOnClickListener(v -> viewmodel.previousPage());
    }

    private void observeData() {
        viewmodel.getApiData().observe(this, resource -> {
            switch (resource.getStatus()) {
                case LOADING:
                    success.setVisibility(GONE);
                    error.setVisibility(GONE);
                    loading.setVisibility(VISIBLE);
                    break;
                case SUCCESS:
                    adapter.updateMovies(resource.getData().getResults());
                    txtPage.setText(String.format("Page\n%s/%s", viewmodel.getCurrent_page(), viewmodel.getTotal_pages()));
                    error.setVisibility(GONE);
                    loading.setVisibility(GONE);
                    success.setVisibility(VISIBLE);
                    break;
                case ERROR:
                    txtErrorMessage.setText(String.format("Error : %s", resource.getMessage()));
                    success.setVisibility(GONE);
                    loading.setVisibility(GONE);
                    error.setVisibility(VISIBLE);
                    break;
            }
        });
    }
}