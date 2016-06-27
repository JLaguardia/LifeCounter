package com.prismsoftworks.lifecounter;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.prismsoftworks.lifecounter.adapter.PlayerAdapter;
import com.prismsoftworks.lifecounter.adapter.SettingsAdapter;
import com.prismsoftworks.lifecounter.object.PlayerObject;
import com.prismsoftworks.lifecounter.object.SettingsObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static RecyclerView recyclerView;
    private static RelativeLayout settingsOverlay = null;// settingsRec = null;
    private static boolean settingsShown = false;
    public static MainActivity instance = null;
    private static ImageButton btnSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        instance = this;
        initBlank(false);
    }

    private void initBlank(boolean refresh) {
        Log.i(TAG, "initializing...");
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        btnSettings = (ImageButton) findViewById(R.id.settings_button);
        btnSettings.setOnClickListener(this);
        btnSettings.bringToFront();
        List<PlayerObject> items = new ArrayList<>();
        SharedPreferences prefs = getSharedPreferences("pobjs", MODE_PRIVATE);
        for (int i = 0; i < 3; i++) {
            String key = "" + i;
            String vals[] = prefs.getString(key, "").split("\\|");
            if (vals.length > 1) {
                int tot = Integer.parseInt(vals[1]);
                items.add(new PlayerObject(i, vals[0], tot));
            }
        }

        if (items.size() < 2) {
            items.add(new PlayerObject(0, "Player 1", 20));
            items.add(new PlayerObject(1, "Player 2", 20));
        }

        PlayerAdapter adapt = new PlayerAdapter(items);
        MutuableLLM llm = new MutuableLLM(this);
        recyclerView.setLayoutManager(llm);
        recyclerView.swapAdapter(adapt, false);
        if(!refresh) {
            recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                           RecyclerView.State state) {
                    outRect.bottom = 100;
//                  super.getItemOffsets(outRect, view, parent, state);
                }
            });
        }
    }

    public void reset(){
        SharedPreferences prefs = getSharedPreferences("pobjs", MODE_PRIVATE);
        prefs.edit().putString("0", "").commit();
        prefs.edit().putString("1", "").commit();
        prefs.edit().putString("2", "").commit();
        prefs.edit().putString("3", "").commit();
        initBlank(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onpause. Saving info...");
        saveCrap();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void saveCrap() {
        SharedPreferences prefs = getSharedPreferences("pobjs", MODE_PRIVATE);
        for (PlayerObject obj : ((PlayerAdapter) recyclerView.getAdapter()).getItemList()) {
            String key = "" + obj.id;
            String vals = obj.playerName + "|" + obj.lifeTotal;
            prefs.edit().putString(key, vals).apply();
        }
    }

    public void hideSettings(){
        settingsShown = false;
        ((ViewGroup)settingsOverlay.getParent()).removeView(settingsOverlay);
    }

    @Override
    public void onClick(View v) {
        Log.e(TAG, "clicked.");
        if(!settingsShown) {
            settingsShown = true;
            if (settingsOverlay == null)
                settingsOverlay = new RelativeLayout(this);

            RecyclerView settingsRec = new RecyclerView(this);
            String[] strs = getResources().getStringArray(R.array.settings_items);
            List<SettingsObject> leest = new ArrayList<>();
            leest.add(new SettingsObject(R.drawable.singleplayer, strs[0]));//add
            leest.add(new SettingsObject(R.drawable.orientationarrow, strs[1]));///orientation
            leest.add(new SettingsObject(R.drawable.trashcan, strs[2]));//reset
            leest.add(new SettingsObject(R.drawable.question, strs[3]));//credz

            SettingsAdapter adapt = new SettingsAdapter(leest);
            settingsRec.setAdapter(adapt);
            MutuableLLM llm = new MutuableLLM(this);
            settingsRec.setLayoutManager(llm);
            settingsOverlay.addView(settingsRec);
            RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) settingsRec.getLayoutParams();
            rlp.width = ViewGroup.LayoutParams.MATCH_PARENT;
            rlp.height = ViewGroup.LayoutParams.WRAP_CONTENT;

            ((ViewGroup) v.getParent()).addView(settingsOverlay);
            settingsRec.setBackgroundColor(0xeeeeeeee);
            rlp = (RelativeLayout.LayoutParams) settingsOverlay.getLayoutParams();
            rlp.addRule(RelativeLayout.BELOW, v.getId());
            rlp.addRule(RelativeLayout.ALIGN_PARENT_END);
            rlp.height = 900;
            settingsOverlay.setBackgroundColor(0x5f2fff);
            settingsOverlay.bringToFront();
        } else {
            hideSettings();
        }
    }

    protected static class MutuableLLM extends LinearLayoutManager {

        public MutuableLLM(Context context) {
            super(context);
        }

        @Override
        public boolean canScrollVertically() {
            return false;
        }

        @Override
        public boolean canScrollHorizontally() {
            return false;
        }
    }
}
