package com.ajit.mangodistributionsystem.viewholders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ajit.mangodistributionsystem.R;

public class MangoViewHolder extends RecyclerView.ViewHolder {

    public TextView mangoName;
    public ImageView mangoImage;

    public MangoViewHolder(@NonNull View itemView) {
        super(itemView);

        mangoName = itemView.findViewById(R.id.mango_name_recycler);
        mangoImage = itemView.findViewById(R.id.image_view_recycler);

    }
}
