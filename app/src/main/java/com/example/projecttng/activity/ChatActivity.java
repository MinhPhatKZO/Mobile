package com.example.projecttng.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projecttng.R;
import com.example.projecttng.adapter.ChatAdapter;
import com.example.projecttng.dao.ChatDao;
import com.example.projecttng.model.Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText edtMessage;
    private Button btnSend;

    private List<Message> messageList;
    private ChatAdapter adapter;
    private ChatDao chatDao;

    private static final String OPENAI_API_KEY = "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"; // Thay bằng key thật

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        recyclerView = findViewById(R.id.recyclerViewChat);
        edtMessage = findViewById(R.id.edtMessage);
        btnSend = findViewById(R.id.btnSend);

        chatDao = new ChatDao(this);
        messageList = chatDao.getAllMessages();

        adapter = new ChatAdapter(messageList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        btnSend.setOnClickListener(v -> {
            String msg = edtMessage.getText().toString().trim();
            if (!TextUtils.isEmpty(msg)) {
                sendUserMessage(msg);
            }
        });
    }

    private void sendUserMessage(String msg) {
        edtMessage.setText("");

        Message userMsg = new Message(msg, true);
        messageList.add(userMsg);
        chatDao.insertMessage(msg, true);
        adapter.notifyItemInserted(messageList.size() - 1);
        recyclerView.scrollToPosition(messageList.size() - 1);

        sendMessageToGPT(msg);
    }

    private void sendMessageToGPT(String userMessage) {
        OkHttpClient client = new OkHttpClient();

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("model", "gpt-3.5-turbo");

            JSONArray messages = new JSONArray();
            JSONObject userMsg = new JSONObject();
            userMsg.put("role", "user");
            userMsg.put("content", userMessage);
            messages.put(userMsg);

            jsonBody.put("messages", messages);
        } catch (JSONException e) {
            e.printStackTrace();
            addBotMessage("\u274c Lỗi tạo request");
            return;
        }

        RequestBody body = RequestBody.create(
                jsonBody.toString(),
                MediaType.parse("application/json")
        );

        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .post(body)
                .addHeader("Authorization", "Bearer " + OPENAI_API_KEY)
                .addHeader("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> addBotMessage("\u26a0\ufe0f Không thể kết nối GPT: " + e.getMessage()));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    runOnUiThread(() -> addBotMessage("\u274c GPT lỗi: " + response.code()));
                    return;
                }

                try {
                    String responseBody = response.body().string();
                    JSONObject jsonResponse = new JSONObject(responseBody);
                    JSONArray choices = jsonResponse.getJSONArray("choices");
                    String botReply = choices.getJSONObject(0)
                            .getJSONObject("message")
                            .getString("content")
                            .trim();

                    runOnUiThread(() -> addBotMessage(botReply));

                } catch (JSONException e) {
                    runOnUiThread(() -> addBotMessage("\u274c Lỗi xử lý phản hồi GPT"));
                }
            }
        });
    }

    private void addBotMessage(String text) {
        Message botMsg = new Message(text, false);
        messageList.add(botMsg);
        chatDao.insertMessage(text, false);
        adapter.notifyItemInserted(messageList.size() - 1);
        recyclerView.scrollToPosition(messageList.size() - 1);
    }
}
