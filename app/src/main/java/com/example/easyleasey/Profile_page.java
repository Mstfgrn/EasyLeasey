package com.example.easyleasey;
// Created by MstfGrgn on 02.04.2020.
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.easyleasey.Fragments.My_Ads_fragment;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile_page extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentuser;
    private DatabaseReference myRef;
    FirebaseDatabase database;
    StorageReference storageRef;

    TextView fotodegis;
    CircleImageView photo;
    private EditText adi,tel,email,adres;
    private Button ilangöster,oturumkapat;
    public static final int IMAGE_CODE =1;
    Uri imageuri,myUri;
    StorageTask loadmission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        mAuth = FirebaseAuth.getInstance();
        mCurrentuser = mAuth.getCurrentUser();
        storageRef=FirebaseStorage.getInstance().getReference("Uploads");

        adi = findViewById(R.id.profil_adi);
        tel = findViewById(R.id.profil_tel);
        email = findViewById(R.id.profil_email);
        adres = findViewById(R.id.profil_adres);
        fotodegis = findViewById(R.id.txt_fotodegis);
        photo = findViewById(R.id.profil_resmi);
        ilangöster = findViewById(R.id.btn_ilangöster);
        oturumkapat = findViewById(R.id.btn_oturum_logout);

        String uıd= mAuth.getUid();
        myRef= database.getInstance().getReference("Kullanicilar").child("Bireysel").child(uıd);


        myRef.addValueEventListener(new ValueEventListener() {
            String tle =mCurrentuser.getPhoneNumber();
            String t = tle.substring(0,3);
            String l = tle.substring(3,6);
            String e = tle.substring(6,9);
            String f =tle.substring(9,11);
            String o = tle.substring(11,13);

            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UsersBireysel user = dataSnapshot.getValue(UsersBireysel.class);
                adi.setText(user.getAdi()+" "+user.getSoyadi());
                tel.setText(t+" "+l+" "+e+" "+f+" "+o);
                email.setText(user.getE_posta());
                adres.setText("Gaziosmanpaşa/İST");
                Glide.with(getApplicationContext()).load(user.getImageUrl()).into(photo);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        fotodegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/+");
                intent.setAction(intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Seçilen Resim"),IMAGE_CODE);
                Toast.makeText(Profile_page.this, "secim", Toast.LENGTH_SHORT).show();

            }
        });
        ilangöster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goilan = new Intent(Profile_page.this, My_Ads_fragment.class);
                startActivity(goilan);
            }
        });
        oturumkapat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent gostart = new Intent(Profile_page.this,Phone_page.class);
                startActivity(gostart);
                finish();
            }
        });
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_CODE && resultCode == RESULT_OK)
        {
            imageuri=data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imageuri);
                photo.setImageBitmap(bitmap);
                imageLoad();
                Toast.makeText(Profile_page.this, "Başarıyla Yüklendi", Toast.LENGTH_SHORT).show();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private String getFileExtention(Uri uri)
    {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
    private void imageLoad()
    {
        if(imageuri != null)
        {

            final StorageReference fileref=storageRef.child(System.currentTimeMillis()+"."+getFileExtention(imageuri));
            loadmission =fileref.putFile(imageuri);
            loadmission.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    return fileref.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri>task) {
                    if (task.isSuccessful())
                    {
                        Uri uri = task.getResult();
                        String myuri = uri.toString();
                        DatabaseReference yeah =myRef;
                        HashMap<String,Object> image = new HashMap<>();
                        image.put("imageUrl",""+myuri);

                        yeah.updateChildren(image);
                    }
                    else
                    {
                        Toast.makeText(Profile_page.this, "Yükleme Başarısız", Toast.LENGTH_SHORT).show();
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Profile_page.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        else
        {
            Toast.makeText(this, "Lütfen bir resim seçiniz!", Toast.LENGTH_SHORT).show();
        }
    }
}

