package com.epicodus.pettracker.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.epicodus.pettracker.R;
import com.epicodus.pettracker.Vet;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class VetDetailFragment extends Fragment {
    private static final int MAX_WIDTH = 400;
    private static final int MAX_HEIGHT = 300;
    @Bind(R.id.vetImageView) ImageView mImageLabel;
    @Bind(R.id.vetNameTextView) TextView mNameLabel;
    @Bind(R.id.ratingTextView) TextView mRatingLabel;
    @Bind(R.id.websiteTextView) TextView mWebsiteLabel;
    @Bind(R.id.phoneTextView) TextView mPhoneLabel;
    @Bind(R.id.addressTextView) TextView mAddressLabel;

    private Vet mVet;

    public static VetDetailFragment newInstance(Vet vet) {
        VetDetailFragment vetDetailFragment = new VetDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("vet", Parcels.wrap(vet));
        vetDetailFragment.setArguments(args);
        return vetDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mVet = Parcels.unwrap(getArguments().getParcelable("vet"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_vet_detail, container, false);
        ButterKnife.bind(this, view);

        Picasso.with(view.getContext())
                .load(mVet.getImageUrl())
                .resize(MAX_WIDTH, MAX_HEIGHT)
                .centerCrop()
                .into(mImageLabel);

        mNameLabel.setText(mVet.getName());
        mRatingLabel.setText(Double.toString(mVet.getRating()) + "/5");
        mPhoneLabel.setText(mVet.getPhone());
        mAddressLabel.setText(android.text.TextUtils.join(", ", mVet.getAddress()));

        return view;
    }

}
