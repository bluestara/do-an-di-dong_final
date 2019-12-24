package com.example.nomorepleze;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CreateActivity extends AppCompatActivity {

    private static final int HAT_ACTIVITY_REQUEST_CODE = 1;
    private static final int SHIRT_ACTIVITY_REQUEST_CODE = 2;
    private static final int TROUSERS_ACTIVITY_REQUEST_CODE = 3;
    private static final int SHOE_ACTIVITY_REQUEST_CODE = 4;
    private static final int BAG_ACTIVITY_REQUEST_CODE = 5;

    private static final int TAKE_IMAGE_REQUEST = 20;

    private static final String LOG_TAG = CreateActivity.class.getSimpleName();

    ImageView imageHat;
    ImageView imageShirt;
    ImageView imageTrousers;
    ImageView imageShoe;
    ImageView imageBag;
    ArrayList<item> itemChosen;
    private ImageButton img;

    private FirebaseUser mUser;
    private String Uid;

    private ImageView mShoot;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        //ask for storage permission of screenshot
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
        }

        imageHat = findViewById(R.id.hat);
        imageShirt = findViewById(R.id.shirt);
        imageTrousers = findViewById(R.id.trousers);
        imageShoe = findViewById(R.id.shoe);
        imageBag = findViewById(R.id.bag);
        mShoot = findViewById(R.id.shoot);
        img = findViewById(R.id.button_go_to_order_activity);
        itemChosen =new ArrayList<>();

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CreateActivity.this, OrderActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("ITEM_ORDER",itemChosen);
                i.putExtras(bundle);
                startActivity(i);
            }
        });


        mShoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mUser = FirebaseAuth.getInstance().getCurrentUser();

                if (mUser != null) {

                    Toast.makeText(CreateActivity.this, "Shot <3", Toast.LENGTH_SHORT).show();

                    Random rd = new Random();
                    Uid = mUser.getUid();

                    mStorageRef = FirebaseStorage.getInstance().getReference("uploads/" + rd.nextInt(2000000000));
                    mDatabaseRef = FirebaseDatabase.getInstance().getReference(Uid);

                    View rootView = getWindow().getDecorView().findViewById(android.R.id.content);
                    Bitmap bitmap = getScreenShot(rootView);

                    ByteArrayOutputStream bao = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bao);
                    byte[] data = bao.toByteArray();

                    UploadTask uploadTask = mStorageRef.putBytes(data);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                            while (!uri.isComplete()) ;
                            Uri url = uri.getResult();

                            Upload newUpload = new Upload("", url.toString());
                            mDatabaseRef.push().setValue(newUpload);

                        }
                    });
                } else {
                    Toast.makeText(CreateActivity.this, "You have to sign in first!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "Permission Granted!", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "Permission Not Granted!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static Bitmap getScreenShot(View view) {
        View screenView = view.getRootView();

        screenView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(screenView.getDrawingCache());
        screenView.setDrawingCacheEnabled(false);

        return bitmap;
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

       if (requestCode == HAT_ACTIVITY_REQUEST_CODE)
            if (resultCode == RESULT_OK)
            {
                item hatItem = data.getParcelableExtra("keyHat");

                Picasso.get().load(hatItem.mImageURI)
                        .fit()
                        .placeholder(R.mipmap.ic_launcher)
                        .centerCrop()
                        .into(imageHat);
                itemChosen.add(hatItem);

            }

       if (requestCode == SHIRT_ACTIVITY_REQUEST_CODE)
           if (resultCode == RESULT_OK)
           {
                item shirtItem = data.getParcelableExtra("keyShirt");

               Picasso.get().load(shirtItem.mImageURI)
                       .fit()
                       .placeholder(R.mipmap.ic_launcher)
                       .centerCrop()
                       .into(imageShirt);
               itemChosen.add(shirtItem);
           }

       if (requestCode == TROUSERS_ACTIVITY_REQUEST_CODE)
           if (resultCode == RESULT_OK)
           {
                item trousersItem = data.getParcelableExtra("keyTrousers");

               Picasso.get().load(trousersItem.mImageURI)
                       .fit()
                       .placeholder(R.mipmap.ic_launcher)
                       .centerCrop()
                       .into(imageTrousers);
               itemChosen.add(trousersItem);
           }

       if (requestCode == SHOE_ACTIVITY_REQUEST_CODE)
            if (resultCode == RESULT_OK)
            {
                item shoeItem = data.getParcelableExtra("keyShoe");

                Picasso.get().load(shoeItem.mImageURI)
                        .fit()
                        .placeholder(R.mipmap.ic_launcher)
                        .centerCrop()
                        .into(imageShoe);
                itemChosen.add(shoeItem);
            }

       if (requestCode == BAG_ACTIVITY_REQUEST_CODE)
           if (resultCode == RESULT_OK)
           {
                item bagItem = data.getParcelableExtra("keyBag");

               Picasso.get().load(bagItem.mImageURI)
                       .fit()
                       .placeholder(R.mipmap.ic_launcher)
                       .centerCrop()
                       .into(imageBag);
               itemChosen.add(bagItem);
           }

    }


    public void launchHatActivity(View view) {
        Log.d(LOG_TAG, "Button Clicked!");
        Intent intent = new Intent(this, HatActivity.class);
        startActivityForResult(intent, HAT_ACTIVITY_REQUEST_CODE);
    }

    public void launchShirtActivity(View view) {
        Log.d(LOG_TAG, "Button Clicked!");
        Intent intent = new Intent(this, ShirtActivity.class);
        startActivityForResult(intent, SHIRT_ACTIVITY_REQUEST_CODE);
    }

    public void launchTrousersActivity(View view) {
        Log.d(LOG_TAG, "Button Clicked!");
        Intent intent = new Intent(this, TrousersActivity.class);
        startActivityForResult(intent, TROUSERS_ACTIVITY_REQUEST_CODE);
    }

    public void launchShoeActivity(View view) {
        Log.d(LOG_TAG, "Button Clicked!");
        Intent intent = new Intent(this, ShoeActivity.class);
        startActivityForResult(intent, SHOE_ACTIVITY_REQUEST_CODE);
    }

    public void launchBagActivity(View view) {
        Log.d(LOG_TAG, "Button Clicked!");
        Intent intent = new Intent(this, BagActivity.class);
        startActivityForResult(intent, BAG_ACTIVITY_REQUEST_CODE);
    }
}
