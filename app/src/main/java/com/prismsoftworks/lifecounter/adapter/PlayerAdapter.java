package com.prismsoftworks.lifecounter.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.prismsoftworks.lifecounter.R;
import com.prismsoftworks.lifecounter.object.PlayerObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jameslaguardia on 6/15/16.
 *
 * @author James/CarbonDawg
 */
public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerVH> {
    private static final String TAG = PlayerAdapter.class.getSimpleName();
    List<PlayerObject> items = null;
    public static PlayerAdapter instance = null;

    public PlayerAdapter(List<PlayerObject> data) {
        items = new ArrayList<>(data);
        instance = this;
    }

    public PlayerAdapter(PlayerObject[] data) {
        items = new ArrayList<>();
        items.addAll(Arrays.asList(data));
        instance = this;
    }

    @Override
    public PlayerVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PlayerVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.player_item, parent, false));
    }

    @Override
    public void onBindViewHolder(PlayerVH holder, int position) {
        final PlayerObject obj = items.get(position);
        Log.e(TAG, "setup for " + obj.playerName);
        holder.lblName.setText(obj.playerName);
        holder.player[0] = obj;
        holder.lblLife.setText("" + obj.lifeTotal);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public List<PlayerObject> getItemList(){
        return items;
    }

    public static class PlayerVH extends RecyclerView.ViewHolder{
        protected TextView lblName;
        protected final TextView lblLife;
        protected ImageButton[] contols;
        protected final PlayerObject[] player = {null};

        public PlayerVH(View itemView) {
            super(itemView);
            lblName = (TextView) itemView.findViewById(R.id.name_label);
            lblLife = (TextView) itemView.findViewById(R.id.life_counter_label);
            contols = new ImageButton[]{
                    (ImageButton) itemView.findViewById(R.id.neg_one),
                    (ImageButton) itemView.findViewById(R.id.pos_one)
            };

            contols[0].setOnClickListener(clickListener(-1));
            contols[1].setOnClickListener(clickListener(1));
        }

        private View.OnClickListener clickListener(final int direction){
            return new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    player[0].lifeTotal += direction;
                    PlayerAdapter.instance.notifyDataSetChanged();//maybe just change the textview itself
                }
            };
        }
    }
}
