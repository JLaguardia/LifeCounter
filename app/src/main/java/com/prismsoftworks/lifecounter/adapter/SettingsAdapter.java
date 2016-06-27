package com.prismsoftworks.lifecounter.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.prismsoftworks.lifecounter.MainActivity;
import com.prismsoftworks.lifecounter.R;
import com.prismsoftworks.lifecounter.object.SettingsObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jameslaguardia on 6/27/16.
 *
 * @author James/CarbonDawg
 */
public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.SettingsVH>{
    List<SettingsObject> items;

    public SettingsAdapter(List<SettingsObject> data){
        items = new ArrayList<>(data);
    }

    @Override
    public SettingsVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SettingsVH(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.settings_item, parent, false));
    }

    @Override
    public void onBindViewHolder(SettingsVH holder, int position) {
        final SettingsObject obj = items.get(position);
        final int index = position;
        holder.img.setImageResource(obj.iconRes);
        holder.label.setText(obj.label);
        holder.btnSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (index){
                    case 0://add player
                        Toast.makeText(v.getContext(), "3 and 4 player functionality coming soon!", Toast.LENGTH_SHORT).show();
                        break;
                    case 1://orientation
                        Toast.makeText(v.getContext(), "Flip orientation (player 2 upside down and on top) functionality coming soon!", Toast.LENGTH_SHORT).show();
                        break;
                    case 2://reset
                        Toast.makeText(v.getContext(), "Players have been reset.", Toast.LENGTH_SHORT).show();
                        MainActivity.instance.reset();
                        break;
                    case 3://credits
                        Toast.makeText(v.getContext(), "App written by James Laguardia\n" +
                                "Icons obtained from Kenny.nl\n" +
                                "Author will update frequently! Email: laguardia.james@gmail.com",
                                Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    protected static class SettingsVH extends RecyclerView.ViewHolder{
        protected ImageView img;
        protected TextView label;
        protected ImageButton btnSelector;

        public SettingsVH(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.settings_icon);
            label = (TextView) itemView.findViewById(R.id.settings_label);
            btnSelector = (ImageButton) itemView.findViewById(R.id.settings_selector);
            btnSelector.setBackground(null);
        }
    }
}
