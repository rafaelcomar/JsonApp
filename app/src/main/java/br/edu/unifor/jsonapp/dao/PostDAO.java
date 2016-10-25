package br.edu.unifor.jsonapp.dao;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import br.edu.unifor.jsonapp.model.Post;

/**
 * Created by Ygor on 22/10/2016.
 */

//Singleton
public class PostDAO {

    public interface DataListener<V extends RecyclerView.ViewHolder> {
        void onDataChanged(Post post);
    }

    private static PostDAO instance;

    private Context context;

    private ArrayList<Post> posts;
    private DataListener<RecyclerView.ViewHolder> listener;

    private PostDAO(Context context){
        this.context = context;
        posts = new ArrayList<>();
    }

    public static PostDAO getInstance(Context context){
        if(instance == null){
            instance = new PostDAO(context);
        }

        return instance;
    }

    public void setListener(DataListener<RecyclerView.ViewHolder> listener){
        this.listener = listener;
    }

    public ArrayList<Post> getPosts(){
        return posts;
    }

    public void addPost(Post post){
        posts.add(post);
        if(listener != null){
                listener.onDataChanged(post);
        }
    }

    public Post getPostByIndex(int pos){
        if(pos >= posts.size() || pos < 0){
            return null;
        } else {
            return posts.get(pos);
        }
    }

    public Post getPostById(int id){
        Post post = null;

        for(Post p : posts){
            if(p.id == id){
                post = p;
                break;
            }
        }

        return post;
    }

}
