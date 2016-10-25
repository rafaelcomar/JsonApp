package br.edu.unifor.jsonapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.edu.unifor.jsonapp.R;
import br.edu.unifor.jsonapp.dao.PostDAO;
import br.edu.unifor.jsonapp.model.Post;

/**
 * Created by Ygor on 22/10/2016.
 */

public class DetailActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int index = getIntent().getIntExtra("post_index", -1);

        if(index == -1){
            finish();
            return;
        }

        PostDAO dao = PostDAO.getInstance(getApplicationContext());

        Post post = dao.getPostByIndex(index);

        TextView txtTitle = (TextView) findViewById(R.id.txtTitle);
        TextView txtContent = (TextView) findViewById(R.id.txtContent);
        ImageView imgThumb = (ImageView) findViewById(R.id.imgThumbnail);

        txtTitle.setText(post.title);
        txtContent.setText(Html.fromHtml(post.content));
        Picasso.with(getApplicationContext())
                .load(post.thumbnail)
                .placeholder(R.drawable.placeholder)
                .into(imgThumb);
    }
}
