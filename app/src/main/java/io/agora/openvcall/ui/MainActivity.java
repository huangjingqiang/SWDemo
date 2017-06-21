package io.agora.openvcall.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hanzi.apirestful.ApiFactory;
import com.hanzi.apirestful.ApiRESTful.ApiCallback;
import com.hanzi.apirestful.Beans.RowrawRes;

import java.util.HashMap;
import java.util.Map;

import io.agora.openvcall.AGApplication;
import io.agora.openvcall.AppConfig;
import io.agora.openvcall.R;
import io.agora.openvcall.model.ChannelKeyBean;
import io.agora.openvcall.model.ConstantApp;
import io.agora.openvcall.signal.ActivityLogin;
import io.agora.rtc.Constants;

public class MainActivity extends BaseActivity {
    private String mChannelKey;
    private boolean isIntoLive;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isIntoLive = false;
    }

    @Override
    protected void initUIandEvent() {
        EditText v_channel = (EditText) findViewById(R.id.channel_name);
        v_channel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                boolean isEmpty = TextUtils.isEmpty(s.toString());
                findViewById(R.id.button_join_call).setEnabled(!isEmpty);
            }
        });

        Spinner encryptionSpinner = (Spinner) findViewById(R.id.encryption_mode);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.encryption_mode_values, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        encryptionSpinner.setAdapter(adapter);

        encryptionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                vSettings().mEncryptionModeIndex = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        encryptionSpinner.setSelection(vSettings().mEncryptionModeIndex);

        String lastChannelName = vSettings().mChannelName;
        if (!TextUtils.isEmpty(lastChannelName)) {
            v_channel.setText(lastChannelName);
            v_channel.setSelection(lastChannelName.length());
        }

        EditText v_encryption_key = (EditText) findViewById(R.id.encryption_key);
        String lastEncryptionKey = vSettings().mEncryptionKey;
        if (!TextUtils.isEmpty(lastEncryptionKey)) {
            v_encryption_key.setText(lastEncryptionKey);
        }
        findViewById(R.id.button_join_live).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isIntoLive = true;
                //getChannelKey();
                joinLiveChannel();

            }
        });
        findViewById(R.id.button_join_call).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isIntoLive = false;
                //getChannelKey();
                forwardToRoom();
            }
        });
        findViewById(R.id.btn_signal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ActivityLogin.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_get_channel_key).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getChannelKey();
            }
        });
    }
    private void joinLiveChannel() {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage("选择身份")
                .setPositiveButton("主播", (dialog, which) -> {
                    String channelName = ((EditText) findViewById(R.id.channel_name)).getText().toString();
                    Intent i = new Intent(MainActivity.this, LiveRoomActivity.class);
                    i.putExtra(ConstantApp.ACTION_KEY_CROLE, Constants.CLIENT_ROLE_BROADCASTER);
                    i.putExtra(ConstantApp.ACTION_KEY_ROOM_NAME, channelName);
                    //i.putExtra(ConstantApp.ACTION_KEY_ROOM_NAME, mChannelKey);
                    startActivity(i);

                })
                .setNegativeButton("观众", (dialog, which) -> {
                    String channelName = ((EditText) findViewById(R.id.channel_name)).getText().toString();
                    Intent i = new Intent(MainActivity.this, LiveRoomActivity.class);
                    i.putExtra(ConstantApp.ACTION_KEY_CROLE, Constants.CLIENT_ROLE_AUDIENCE);
                    i.putExtra(ConstantApp.ACTION_KEY_ROOM_NAME, channelName);
                    //i.putExtra(ConstantApp.ACTION_KEY_ROOM_NAME, mChannelKey);
                    startActivity(i);

                }).setTitle("选择身份")
                .show();
    }
    @Override
    protected void deInitUIandEvent() {
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_settings:
                forwardToSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void forwardToRoom() {
        EditText v_channel = (EditText) findViewById(R.id.channel_name);
        String channel = v_channel.getText().toString();
        vSettings().mChannelName = channel;

        EditText v_encryption_key = (EditText) findViewById(R.id.encryption_key);
        String encryption = v_encryption_key.getText().toString();
        vSettings().mEncryptionKey = encryption;

        Intent i = new Intent(MainActivity.this, ChatActivity.class);
        i.putExtra(ConstantApp.ACTION_KEY_CHANNEL_NAME, channel);
        i.putExtra(ConstantApp.ACTION_KEY_ENCRYPTION_KEY, encryption);
        i.putExtra(ConstantApp.ACTION_KEY_ENCRYPTION_MODE, getResources().getStringArray(R.array.encryption_mode_values)[vSettings().mEncryptionModeIndex]);
        startActivity(i);
    }

    public void forwardToSettings() {
        Intent i = new Intent(this, SettingsActivity.class);
        startActivity(i);
    }

    private void getChannelKey() {
        String channelName = ((TextView) findViewById(R.id.channel_name)).getText().toString();
        String type = "ChannelKey";
        Map<String, Object> params = new HashMap<>();
        params.put("channelName", channelName);
        params.put("type", type);
        ApiFactory.createApi().get(AppConfig.GET_CHANNEL_KEY, params, new ApiCallback() {
            @Override
            public void success(RowrawRes rowrawRes) {
                ChannelKeyBean bean = new Gson().fromJson(rowrawRes.bodyToString(), ChannelKeyBean.class);
                if (bean == null) {
                    Toast.makeText(MainActivity.this, "json decode error", Toast.LENGTH_LONG).show();
                } else {
                    ((TextView) findViewById(R.id.channel_key_tv)).setText("channel_key:" + bean.getData().getKey());
                    mChannelKey = bean.getData().getKey();
                    AGApplication.channelKey = bean.getData().getKey();
                    AGApplication.uid = bean.getData().getUid();
                    if (!isIntoLive) {
                        forwardToRoom();
                    } else {
                        joinLiveChannel();
                    }

                }
            }

            @Override
            public void fail(RowrawRes rowrawRes) {
                Toast.makeText(MainActivity.this, rowrawRes.getErrorMsg(), Toast.LENGTH_LONG).show();
            }
        });


    }
}
