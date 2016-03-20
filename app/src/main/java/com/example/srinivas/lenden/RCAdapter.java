package com.example.srinivas.lenden;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.srinivas.testlogin.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by sushantc on 3/15/16.
 */
public class RCAdapter extends RecyclerView.Adapter<RCAdapter.RCViewHolder> {

    List<Info> data = Collections.emptyList();//what is this collections func
    private LayoutInflater inflater;

    public RCAdapter(Context context, List<Info> data) {

        inflater=LayoutInflater.from(context);
        this.data=data;
    }

    class RCViewHolder extends RecyclerView.ViewHolder {

        TextView title; ImageView icon;
        public RCViewHolder(View itemView) {
            super(itemView);

            title=(TextView) itemView.findViewById(R.id.listText);
            icon=(ImageView) itemView.findViewById(R.id.listIcon);

        }
    }

    @Override
    public RCAdapter.RCViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.custom_row, parent, false);
        RCViewHolder holder = new RCViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RCAdapter.RCViewHolder holder, int position) {
        Info current = data.get(position);
        holder.title.setText(current.title);
        holder.icon.setImageResource(current.iconId);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}