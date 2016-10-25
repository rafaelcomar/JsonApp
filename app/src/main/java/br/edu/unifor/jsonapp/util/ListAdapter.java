package br.edu.unifor.jsonapp.util;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import br.edu.unifor.jsonapp.R;
import br.edu.unifor.jsonapp.activities.DetailActivity;
import br.edu.unifor.jsonapp.dao.PostDAO;
import br.edu.unifor.jsonapp.model.Post;

/**
 * Created by Ygor on 22/10/2016.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> implements PostDAO.DataListener {

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtTitle;
        private TextView txtIntro;
        private ImageView imgThumb;
        private Button btnRead;

        public ViewHolder(View view) {
            super(view);
            txtTitle = (TextView) view.findViewById(R.id.postTitle);
            txtIntro = (TextView) view.findViewById(R.id.postIntro);
            imgThumb = (ImageView) view.findViewById(R.id.postImage);
            btnRead = (Button) view.findViewById(R.id.btnReadMore);

        }
    }

    private Context context;
    private PostDAO dao;
    private ArrayList<Post> posts = new ArrayList<>();

    public ListAdapter(Context context) {
        this.context = context;
        dao = PostDAO.getInstance(context);
        dao.setListener(this);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Post post = posts.get(position);

        holder.txtTitle.setText(post.title);
        holder.txtIntro.setText(Html.fromHtml(post.content));
        Picasso.with(context)
                .load(post.thumbnail)
                .placeholder(R.drawable.placeholder)
                .into(holder.imgThumb);

        holder.btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, DetailActivity.class);
                i.putExtra("post_index", position);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    @Override
    public void onDataChanged(Post post) {
        posts.add(post);
        notifyDataSetChanged();
    }


}
