package com.codepath.apps.instagramclone.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.codepath.apps.instagramclone.LoginActivity;
import com.codepath.apps.instagramclone.Post;
import com.codepath.apps.instagramclone.PostsAdapter;
import com.codepath.apps.instagramclone.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    public static final String TAG = "ProfileFragment";
    private Button btnLogout;
    private RecyclerView rvPosts;
    protected PostsAdapter adapter;
    protected List<Post> allPosts;
    SwipeRefreshLayout swipeContainer;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Lookup the swipe container view
        swipeContainer = view.findViewById(R.id.swipeContainerProfile);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(() -> {
            Log.i(TAG, "Fetching new data!");
            queryPosts();
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        btnLogout = view.findViewById(R.id.btnLogOut);
        rvPosts = view.findViewById(R.id.rvPostsProfile);
        allPosts = new ArrayList<>();
        adapter = new PostsAdapter(getContext(), allPosts);

        // Steps to use the recycler view
        // 0. create layout for one row in the list
        // 1. create the adapter
        // 2. create the data source
        // 3. set the adapter on the recycler view
        rvPosts.setAdapter(adapter);
        // 4. set the layout manager on the recycler view
        rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));
        queryPosts();

        btnLogout.setOnClickListener(v -> {
            Log.i(TAG, "onClick logout button");
            ParseUser.logOut();
            goLoginActivity();
        });
    }

    protected void queryPosts() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
        query.setLimit(20);
        query.addDescendingOrder(Post.KEY_CREATED_KEY);
        query.findInBackground((posts, e) -> {
            if (e != null) {
                Log.e(TAG, "Issue with getting posts", e);
                return;
            }
            for (Post post : posts) {
                Log.i(TAG, "Username: " + post.getUser().getUsername() + ", Post: " + post.getDescription());
            }
            adapter.clear();
            adapter.addAll(posts);
            // Now we call setRefreshing(false) to signal refresh has finished
            swipeContainer.setRefreshing(false);
            adapter.notifyDataSetChanged();
        });
    }

    private void goLoginActivity() {
        Intent i = new Intent(getContext(), LoginActivity.class);
        startActivity(i);
        if (getActivity() != null)
            getActivity().finish();
    }
}