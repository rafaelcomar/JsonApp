package br.edu.unifor.jsonapp.util;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import br.edu.unifor.jsonapp.dao.PostDAO;
import br.edu.unifor.jsonapp.model.Post;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Ygor on 22/10/2016.
 */

public class PostLoader {

    private Context context;
    private  PostDAO dao;

    public PostLoader(Context context) {
        this.context = context;
        this.dao = PostDAO.getInstance(context);
    }

    public void loadPosts(){
        LoadTask task = new LoadTask();
        task.execute("http://nutritime.azurewebsites.net/?json=1");
    }

    public class LoadTask extends AsyncTask<String, Post, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            String url = strings[0];

            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                String body = response.body().string();
                JSONObject jsonBody = new JSONObject(body);

                JSONArray jsonPosts = jsonBody.getJSONArray("posts");

                for (int i = 0; i < jsonPosts.length(); i++) {
                    JSONObject jsonPost = jsonPosts.getJSONObject(i);
                    Post post = new Post();
                    post.id = jsonPost.getInt("id");
                    post.title = jsonPost.getString("title_plain");
                    post.content = jsonPost.getString("content");
                    post.date = jsonPost.getString("date");
                    post.thumbnail = jsonPost.getString("thumbnail");
                    publishProgress(post);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Post... values) {
            Post post = values[0];
            if(post != null){
                dao.addPost(post);
            }
        }
    }
}
