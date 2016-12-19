package com.epicodus.pettracker.ui;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.epicodus.pettracker.Constants;
import com.epicodus.pettracker.R;
import com.epicodus.pettracker.models.Pet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewPetActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.petName) EditText mPetName;
    @Bind(R.id.birthDate) DatePicker mBirthdate;
    @Bind(R.id.gender) Spinner mGender;
    @Bind(R.id.addPet) Button mAddPet;
    @Bind(R.id.cameraIcon) ImageView mCameraIcon;
    @Bind(R.id.circularImageView) CircularImageView mCircularImage;

    private static final int REQUEST_IMAGE_CAPTURE = 111;
    private Bitmap imageBitmap;
    private String imageUrl;
    private String[] gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pet);

        ButterKnife.bind(this);

        //get gender array from strings XML
        Resources res = getResources();
        gender = res.getStringArray(R.array.gender);
        //populate spinner with array data
        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(NewPetActivity.this, android.R.layout.simple_spinner_item, gender);
        mGender.setAdapter(mAdapter);

        Typeface rampung = Typeface.createFromAsset(getAssets(), "fonts/theboldfont.ttf");
        mPetName.setTypeface(rampung);
        mAddPet.setTypeface(rampung);

        mAddPet.setOnClickListener(this);
        mCameraIcon.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        if (v == mAddPet){
            String name = mPetName.getText().toString();
            String gender = mGender.getSelectedItem().toString();
            //get user input from date picker
            int day = mBirthdate.getDayOfMonth();
            int month = mBirthdate.getMonth();
            int year = mBirthdate.getYear();

            //validation to make sure fields are filled out prior to creating a new pet object
            if (name.equals("")){
                mPetName.setError("Please enter a name");
                return;
            }

            SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
            Date birthDate = new Date(year, month, day);

            //gets current user from Firebase
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();

            //adds string generated from the Bitmap encoding only if image exists
            Pet newPet = new Pet(name, birthDate, gender, uid);
            if (imageUrl != null){
                newPet.setImageUrl(imageUrl);
            }

            //create a database reference to the node where pet should be saved
            DatabaseReference petRef = FirebaseDatabase
                    .getInstance()
                    .getReference(Constants.FIREBASE_CHILD_PETS)
                    .child(uid);

            //add pet into database
            DatabaseReference pushRef = petRef.push();
            String pushId = pushRef.getKey();
            newPet.setPushId(pushId);
            pushRef.setValue(newPet);

            Toast.makeText(NewPetActivity.this, "Saved", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(NewPetActivity.this, PetListActivity.class);
            startActivity(intent);
        }

        if (v == mCameraIcon){
            onLaunchCamera();
        }
    }

    //Waits for camera intent to return a result and encodes the Bitmap if result returns OK
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == NewPetActivity.RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            mCircularImage.setImageBitmap(imageBitmap);
            imageUrl = encodeBitmap(imageBitmap);
        }
    }

    //Creates an implicit intent to launch Android camera
    public void onLaunchCamera(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    //Converts Bitmap into string so the image url can be saved to Firebase
    public String encodeBitmap(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        String imageEncoded = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
        return imageEncoded;
    }
}
