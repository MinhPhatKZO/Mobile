package com.example.projecttng.activity.sp;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projecttng.BuildConfig;
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
    private ImageButton btnSend;

    private List<Message> messageList;
    private ChatAdapter adapter;
    private ChatDao chatDao;

    // 🔐 API KEY từ file keys.properties thông qua BuildConfig
    private static final String GEMINI_API_KEY = BuildConfig.GEMINI_API_KEY;

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

        sendMessageToGemini(msg);
    }

    private void sendMessageToGemini(String userMessage) {
        OkHttpClient client = new OkHttpClient();

        JSONObject jsonBody = new JSONObject();
        try {
            JSONArray contents = new JSONArray();

            // ✅ Gemini yêu cầu format "role: user" và "parts" là mảng các đoạn text
            JSONObject content = new JSONObject();
            content.put("role", "user");

            JSONArray parts = new JSONArray();
            JSONObject part = new JSONObject();
            part.put("text", userMessage);
            parts.put(part);

            content.put("parts", parts);
            contents.put(content);

            jsonBody.put("contents", contents);
        } catch (JSONException e) {
            e.printStackTrace();
            runOnUiThread(() -> addBotMessage("❌ Lỗi tạo request Gemini"));
            return;
        }

        RequestBody body = RequestBody.create(
                jsonBody.toString(),
                MediaType.parse("application/json")
        );

        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent?key=" + BuildConfig.GEMINI_API_KEY;

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> addBotMessage("⚠️ Không thể kết nối Gemini: " + e.getMessage()));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    String responseBody = response.body().string();
                    runOnUiThread(() -> addBotMessage("❌ Gemini lỗi: " + response.code() + "\n" + responseBody));
                    return;
                }

                try {
                    String responseBody = response.body().string();
                    JSONObject jsonResponse = new JSONObject(responseBody);
                    JSONArray candidates = jsonResponse.getJSONArray("candidates");
                    JSONObject first = candidates.getJSONObject(0);
                    JSONArray parts = first.getJSONObject("content").getJSONArray("parts");
                    String botReply = parts.getJSONObject(0).getString("text");

                    runOnUiThread(() -> addBotMessage(botReply));
                } catch (JSONException e) {
                    runOnUiThread(() -> addBotMessage("❌ Lỗi xử lý phản hồi Gemini"));
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
