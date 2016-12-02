package com.epicodus.pettracker.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.epicodus.pettracker.R;
import com.epicodus.pettracker.Vet;
import com.epicodus.pettracker.ui.VetDetailActivity;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by joannaanderson on 11/30/16.
 */

public class VetListAdapter extends RecyclerView.Adapter<VetListAdapter.VetViewHolder> {
    private ArrayList<Vet> mVets = new ArrayList<>();
    private Context mContext;

    public VetListAdapter(Context context, ArrayList<Vet> vets){
        mContext = context;
        mVets = vets;
    }

    @Override
    public VetListAdapter.VetViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vet_list_item, parent, false);
        VetViewHolder viewHolder = new VetViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(VetListAdapter.VetViewHolder holder, int position){
        holder.bindVet(mVets.get(position));
    }

    @Override
    public int getItemCount() {
        return mVets.size();
    }

    public class VetViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @Bind(R.id.vetImageView) ImageView mVetImageView;
        @Bind(R.id.vetNameTextView) TextView mVetNameText;
        @Bind(R.id.ratingTextView) TextView mRatingTextView;

        private Context mContext;

        public VetViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){
            int itemPosition = getLayoutPosition();
            Intent intent = new Intent(mContext, VetDetailActivity.class);
            intent.putExtra("position", itemPosition);
            intent.putExtra("vets", Parcels.wrap(mVets));
            mContext.startActivity(intent);
        }

        public void bindVet(Vet vet){
            mVetNameText.setText(vet.getName());
            mRatingTextView.setText("Rating: " + vet.getRating() + "/5");
            Picasso.with(mContext).load(vet.getImageUrl())
                    .into(mVetImageView);
        }
    }
}
