package com.example.aps;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private List<Post> postList;
    public PostAdapter(List<Post> postList) {this.postList = postList;}
    public void setPostList(List<Post> postList) {this.postList = postList;}

    public void addPostAtTop(Post post) {
        if (postList == null) {
            postList = new ArrayList<>();
        }
        postList.add(0, post);
        notifyItemInserted(0);
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        Post post = postList.get(position);
        holder.titleTextView.setText(post.getTitle());
        holder.descriptionTextView.setText(post.getDescription());
        holder.localTextView.setText(post.getLocal());
        holder.imageTextView.setText(post.getImage());
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView descriptionTextView;
        TextView localTextView;
        TextView imageTextView;

        public PostViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.textViewTitle);
            descriptionTextView = itemView.findViewById(R.id.textViewDescription);
            localTextView = itemView.findViewById(R.id.textViewLocal);
            imageTextView = itemView.findViewById(R.id.textViewImage);
        }


    }
}

