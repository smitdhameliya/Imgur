package android.com.imgur;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomeActivity extends AppCompatActivity {
    EditText imageId, writeComments;
    Button search, commentsPost;
    ImageView imageView;
    RecyclerView comments;
    TextView tvComments;
    private static final String TAG = "HomeActivity";

    String imgur_url = "https://api.imgur.com/3/gallery/image/";
    String comments_url = "https://api.imgur.com/3/gallery/";
    String ClientID = "5574ba3cfe4ad56";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: "+"setContentVIew startup");
        setContentView(R.layout.activity_home);
        Log.d(TAG, "onCreate: "+"setContentVIew endup");


        getSupportActionBar().setTitle("Imgur");

        findviews();
    }

    private void findviews() {
        imageId = findViewById(R.id.imageId);
        imageView = findViewById(R.id.imageView);
        search = findViewById(R.id.search);
        //comments = findViewById(R.id.comments);
        //tvComments = findViewById(R.id.tvcomments);
        //comments.setLayoutManager(new LinearLayoutManager(HomeActivity.this));


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideKeyboard(HomeActivity.this);
                OkHttpClient client = new OkHttpClient();

                final Request request = new Request.Builder()
                        .url(imgur_url+imageId.getText().toString())
                        .addHeader("Authorization", "Client-ID "+ClientID)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {

                        JSONObject jsonObject = null;
                        final String myResponse = response.body().string();
                        try {

                        if (response.isSuccessful()){

                            jsonObject = new JSONObject(myResponse);

                            JSONObject item = jsonObject.getJSONObject("data");

                            final String Response = item.getString("link");

                           // final String [] comm = item.getString("comment");

                            HomeActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    Picasso.with(HomeActivity.this).load(Response).into(imageView);
                                    }
                            });
                        }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                });


                final Request comments = new Request.Builder()
                        .url(comments_url+imageId.getText().toString()+"/comments")
                        .addHeader("Authorization", "Client-ID "+ClientID)
                        .build();

                client.newCall(comments).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        JSONObject jsonObject = null;
                        final String myNewResponse = response.body().string();
                        try {
                            if (response.isSuccessful()){
                                jsonObject = new JSONObject(myNewResponse);
                                JSONArray comm  = jsonObject.getJSONArray("data");

                                for (int i=0 ; i<comm.length() ; i++){
                                    JSONObject object = comm.getJSONObject(i);
                                    final String author = object.getString("author");
                                    final String store = object.getString("comment");

                                    tvComments.setText("Author:"+author+"\n"+"Comments:"+store);


                                }

                            }

                        }catch (JSONException e){
                            e.printStackTrace();
                        }

                    }
                });

            }
        });

    }
    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);

        View view = activity.getCurrentFocus();
        if (view == null) view = new View(activity);

        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
