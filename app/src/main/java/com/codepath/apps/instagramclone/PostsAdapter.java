package com.codepath.apps.instagramclone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    private Context context;
    private List<Post> posts;

    // Pass in the context and list of posts
    public PostsAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    // For each row, inflate a layout
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    // Bind values based on teh position of the element
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the data at position
        Post post = posts.get(position);
        // Bind the tweet with view holder
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    // Clean all elements of the recycler
    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items
    public void addAll(List<Post> postList) {
        posts.addAll(postList);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvUsername;
        private ImageView ivImage;
        private TextView tvDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvDescription = itemView.findViewById(R.id.tvDescription);
        }

        public void bind(Post post) {
            // Bind the post data to the view elements
            tvUsername.setText(post.getUser().getUsername());
            tvDescription.setText(post.getDescription());
            ParseFile image = post.getImage();

            if (image != null) {
                Glide.with(context).load(image.getUrl()).into(ivImage);
            }
        }
    }
}
