package com.epicodus.pettracker.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.epicodus.pettracker.Constants;
import com.epicodus.pettracker.R;
import com.epicodus.pettracker.models.Vet;
import com.epicodus.pettracker.ui.VetDetailActivity;
import com.epicodus.pettracker.ui.VetDetailFragment;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by joannaanderson on 11/30/16.
 */

public class VetListAdapter extends RecyclerView.Adapter<VetListAdapter.VetViewHolder> {
    private static final int MAX_WIDTH = 200;
    private static final int MAX_HEIGHT= 200;

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

        private int mOrientation;
        private Context mContext;

        public VetViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
            mOrientation = itemView.getResources().getConfiguration().orientation;

            if (mOrientation == Configuration.ORIENTATION_LANDSCAPE){
                createDetailFragment(0);
            }

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){
            int itemPosition = getLayoutPosition();
            if (mOrientation == Configuration.ORIENTATION_LANDSCAPE){
                createDetailFragment(itemPosition);
            } else{
                Intent intent = new Intent(mContext, VetDetailActivity.class);
                intent.putExtra(Constants.EXTRA_KEY_POSITION, itemPosition);
                intent.putExtra(Constants.EXTRA_KEY_VETS, Parcels.wrap(mVets));
                mContext.startActivity(intent);
            }
        }

        public void bindVet(Vet vet){


            mVetNameText.setText(vet.getName());
            mRatingTextView.setText("Rating: " + vet.getRating() + "/5");
            Picasso.with(mContext)
                    .load(vet.getImageUrl())
                    .resize(MAX_WIDTH, MAX_HEIGHT)
                    .centerCrop()
                    .into(mVetImageView);
        }

        private void createDetailFragment(int position){
            VetDetailFragment detailFragment = VetDetailFragment.newInstance(mVets, position);
            FragmentTransaction ft = ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.vetDetailContainer, detailFragment);
            ft.commit();
        }
    }
}
