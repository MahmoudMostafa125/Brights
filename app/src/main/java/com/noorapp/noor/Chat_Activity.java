package com.noorapp.noor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.firebase.iid.FirebaseInstanceId;
import com.noorapp.noor.adapter.MessagingAdapter;
import com.noorapp.noor.api.RetrofitClient;
import com.noorapp.noor.models.ChatModel.ChatResponse;
import com.noorapp.noor.models.ChatModel.Message;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Chat_Activity extends AppCompatActivity {
    RecyclerView recyclerChat;
    View divider;
    EditText etMessage;
    ImageView imgSend;
    RelativeLayout rlltTextBox;
    LinearLayout contentChat;
    private int roomId = 0;
    private int userId = 0;
    private String roomName;
    private String username;
    private String chatID = null;
    private MessagingAdapter adapter;
    private List<Message> messages;
    /**
     * BroadCast Receiver message receiver when register it called when broadcast sent for the intent
     * and execute code in the onReceive Method
     */
    private BroadcastReceiver messageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // get message sent from broadcast
            Message message = intent.getParcelableExtra("msg");

            // check if message is null
            if (message != null) {
                // add message to messages list
                messages.add(message);
                // notify adapter that new message inserted to list in the last position (list size-1)
                adapter = new MessagingAdapter(messages, Chat_Activity.this);
                recyclerChat.setAdapter(adapter);
                adapter.notifyItemInserted(messages.size() - 1);
                // scroll to bottom of recycler show last message
                recyclerChat.scrollToPosition(messages.size() - 1);
            }

        }
    };
    @Override
    protected void onResume() {
        super.onResume();
        // register receiver to handle "" UPDATE CHAT ACTIVITY "" Filter
        registerReceiver(messageReceiver, new IntentFilter("UpdateChateActivity"));
    }
    @Override
    protected void onStop() {
        super.onStop();
        // unregister receiver
        unregisterReceiver(messageReceiver);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // set room name as toolbar title
        if (getSupportActionBar() != null) getSupportActionBar().setTitle("Bright Chat support");
        // display back button in toolbar
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(Color.WHITE);


        recyclerChat = findViewById(R.id.recycler_chat);
        divider = findViewById(R.id.divider);
        etMessage = findViewById(R.id.et_message);
        imgSend = findViewById(R.id.img_send);
        rlltTextBox = findViewById(R.id.rllt_text_box);
        contentChat = findViewById(R.id.content_chat);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerChat.setLayoutManager(layoutManager);
        messages = new ArrayList<>();
        imgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etMessage.getText().toString().isEmpty()) return;

                // get msg from edit text
                //   messages = new ArrayList<>();
                String msg = etMessage.getText().toString();


                Log.e("Message123", msg);
                // create new message
                Message message = new Message();
                // set type to 1 (text message)
                message.setType("1");
                // set user id int
                message.setUserId(String.valueOf(userId));
                // set user name
                message.setUsername(username);
                // set message content
                message.setContent(msg);
                // add message to messages list
                messages.add(message);
                adapter = new MessagingAdapter(messages, Chat_Activity.this);
                recyclerChat.setAdapter(adapter);
                // notify adapter that there is new message in this position
                adapter.notifyItemInserted(messages.size() - 1);
                // scroll to last item in recycler
                recyclerChat.scrollToPosition(messages.size() - 1);
                // set message box empty
                etMessage.setText("");
                addMessage(message);
                // x="2";

            }
        });


    }

    private void addMessage(Message message) {
        String token = FirebaseInstanceId.getInstance().getToken();
        if (chatID != null) {
            Call<ChatResponse> call = RetrofitClient.getInstance().getApi().AddMessageAPI(token, message.getContent(), chatID);
            call.enqueue(new Callback<ChatResponse>() {
                @Override
                public void onResponse(Call<ChatResponse> call, Response<ChatResponse> response) {
                    if (response.body() != null) {
                        if (response.body().getResponse()) {
                            chatID = response.body().getChatId();
                            Log.d("rechildID", chatID);
                        }
                    }
                }

                @Override
                public void onFailure(Call<ChatResponse> call, Throwable t) {
                    Log.e("fail", "FAILL");
                }
            });
        } else {
            Call<ChatResponse> call = RetrofitClient.getInstance().getApi().AddMessageAPIWithoutID(token, message.getContent());
            call.enqueue(new Callback<ChatResponse>() {
                @Override
                public void onResponse(Call<ChatResponse> call, Response<ChatResponse> response) {
                    if (response.body() != null) {
                        if (response.body().getResponse()) {
                            chatID = response.body().getChatId();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ChatResponse> call, Throwable t) {
                    Log.e("fail", "FAILL");
                }
            });
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }
}
