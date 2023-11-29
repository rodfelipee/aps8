package com.example.aps;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.util.Log;
import android.view.View;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PostAdapter postAdapter;

    private List<Post> lastFetchedPosts;
    private static final int CREATE_POST_REQUEST = 1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CREATE_POST_REQUEST) {
            if (resultCode == RESULT_OK) {
                getRecentPosts();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        postAdapter = new PostAdapter(new ArrayList<>());
        FloatingActionButton fab = findViewById(R.id.fab);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(postAdapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreatePostActivity.class);
                startActivityForResult(intent, CREATE_POST_REQUEST);

                ApiService apiService = ApiClient.getApiService();
                Call<List<Post>> call = apiService.getPosts();

                call.enqueue(new Callback<List<Post>>() {
                    @Override
                    public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                        if (response.isSuccessful()) {
                            List<Post> posts = response.body();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Post>> call, Throwable t) {
                    }
                });
            }
        });
        getRecentPosts();
    }

    private void getRecentPosts() {
        ApiService apiService = ApiClient.getApiService();
        Call<List<Post>> call = apiService.getPosts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful()) {
                    List<Post> newPosts = response.body();
                    if (newPosts != null) {
                        for (Post newPost : newPosts) {
                            boolean postExists = false;
                            if (lastFetchedPosts != null) {
                                for (Post lastFetchedPost : lastFetchedPosts) {
                                    if (newPost.getId() != null && lastFetchedPost.getId() != null && newPost.getId().equals(lastFetchedPost.getId())) {
                                        postExists = true;
                                        break;
                                    }
                                }
                            }
                            if (!postExists) {
                                postAdapter.addPostAtTop(newPost);
                            }
                        }
                        lastFetchedPosts = newPosts;
                    }
                } else {
                    Log.e("MainActivity", "Erro ao obter os posts: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Log.e("MainActivity", "Falha na comunicação: " + t.getMessage());
            }
        });
    }

}
