package com.example.easyleasey;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easyleasey.Adapters.Spinner_adapter;
import com.example.easyleasey.models.NewCarIlan;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class Addcar_page extends AppCompatActivity implements AdapterView.OnItemSelectedListener,View.OnClickListener {

    FirebaseUser mCurrentuser;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference myRef;
    StorageReference upload_fotoRef;
    StorageTask upload_foto;
    String myUri="";

    String[] vitesturu={"Vites Türünü Seçiniz","Manuel","Triptonik","Otomatik"};
    String[] yakitturu={"Yakıt Türünü Seçiniz","Dizel","Benzin","Hibrit"};
    String[] aracmarka = {"Araç Markası Seçiniz","Alfa Romeo", "Audi", "BMW", "Chevrolet", "Chrysler", "Citroën", "Dacia", "Daewoo", "Dodge", "Fiat", "Ford", "Honda", "Hummer", "Hyundai", "Infiniti", "Jaguar", "Jeep", "Kia", "Land Rover", "Lexus", "Mazda", "Mercedes-Benz", "MINI", "Mitsubishi", "Nissan", "Opel", "Peugeot", "Porsche", "Renault", "Rover", "Saab", "Seat", "Škoda", "Smart", "Subaru", "Suzuki", "Toyota", "Volkswagen", "Volvo"};
    String[] aracmodel = {};
    String selectedAracMarka,selectedVites,selectedYakit,selectedAracModel;
    double selectedLong,selectedLat;
    Spinner vites_turu,yakit_turu,arac_marka,arac_model;
    EditText setLoc,baslangic_tarihi,bitis_tarihi,fiyat;
    TextView ilanigonder;
    ImageView gotohome,added_foto;
    Uri foto_uri;
    AlertDialog.Builder builder;
    private int mYear, mMonth, mDay;
    final DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcar_page);

        mAuth = FirebaseAuth.getInstance();
        mCurrentuser = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        upload_fotoRef = FirebaseStorage.getInstance().getReference("İlan_Foto");

        fiyat =findViewById(R.id.fiyat_ilan);

        added_foto = findViewById(R.id.ilanfoto);

        baslangic_tarihi = findViewById(R.id.baslangic_tarihi);
        baslangic_tarihi.setOnClickListener(this);

        bitis_tarihi = findViewById(R.id.bitis_tarihi);
        bitis_tarihi.setOnClickListener(this);

        vites_turu = findViewById(R.id.vites_turu_ilan);
        vites_turu.setOnItemSelectedListener(this);
        Spinner_adapter vitesturuAdapter=new Spinner_adapter(getApplicationContext(),vitesturu);
        vites_turu.setAdapter(vitesturuAdapter);

        yakit_turu = findViewById(R.id.yakit_turu_ilan);
        yakit_turu.setOnItemSelectedListener(this);
        Spinner_adapter yakitturuAdapter=new Spinner_adapter(getApplicationContext(),yakitturu);
        yakit_turu.setAdapter(yakitturuAdapter);

        arac_marka = findViewById(R.id.arac_marka_ilan);
        arac_marka.setOnItemSelectedListener(this);
        Spinner_adapter aracmarkaAdapter=new Spinner_adapter(getApplicationContext(),aracmarka);
        arac_marka.setAdapter(aracmarkaAdapter);

        arac_model = findViewById(R.id.arac_model_ilan);
        arac_model.setOnItemSelectedListener(this);

        //ilan gönderme
        builder = new AlertDialog.Builder(this);
        ilanigonder = findViewById(R.id.ilanigonder);
        ilanigonder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                builder.setMessage("İlanınız oluşturulacak. Onaylıyor musunuz?");
                builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            ilanigonder();

                    }
                });
                builder.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        //close page
        gotohome= findViewById(R.id.close_ilan);
        gotohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Addcar_page.this,Market_page.class));
            }
        });

        //location selection
        setLoc = findViewById(R.id.konum_ilan);
        setLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Addcar_page.this,Location_page.class);
                startActivityForResult(intent,1);
            }
        });
        added_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("11234");
                CropImage.activity().setAspectRatio(6,4).start(Addcar_page.this);
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.vites_turu_ilan:
                selectedVites = vitesturu[position];
                break;
            case R.id.yakit_turu_ilan:
                selectedYakit = yakitturu[position];
                break;
            case R.id.arac_marka_ilan:
                selectedAracMarka = aracmarka[position];
                aracmodel = get_json(selectedAracMarka);
                Spinner_adapter aracmodelAdapter = new Spinner_adapter(getApplicationContext(),aracmodel);
                arac_model.setAdapter(aracmodelAdapter);
                break;
            case R.id.arac_model_ilan:
                selectedAracModel = aracmodel[position];
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public String[] get_json(String brandName){

        String json = null;
        try {
            InputStream inputStream = getApplicationContext().getAssets().open("car-list.json");

            int size = inputStream.available();

            byte[] buffer = new byte[size];

            inputStream.read(buffer);

            inputStream.close();

            json = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(json);

            int i = 0;
            while( !brandName.equals(jsonArray.getJSONObject(i).getString("brand"))){
                i++;
            }
            JSONArray model_array = jsonArray.getJSONObject(i).getJSONArray("models");

            String array[] = new String[model_array.length()];
            for(i = 0;i<model_array.length();i++){
                array[i] = model_array.getString(i);
                System.out.println(array[i]);
            }
            return array;

        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onClick(View v) {
         if(v == baslangic_tarihi)
            { try {
                Date date = dateFormat.parse(bitis_tarihi.getText().toString());
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                baslangic_tarihi.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
                datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis()-1000);
                datePickerDialog.show();
            } catch (ParseException e) {
                e.printStackTrace();
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                baslangic_tarihi.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        }
         if(v==bitis_tarihi){
             try {
                 Date date = dateFormat.parse(baslangic_tarihi.getText().toString());
                 Calendar calendar = Calendar.getInstance();
                 calendar.setTime(date);
                 final Calendar c = Calendar.getInstance();
                 mYear = c.get(Calendar.YEAR);
                 mMonth = c.get(Calendar.MONTH);
                 mDay = c.get(Calendar.DAY_OF_MONTH);
                 DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                         new DatePickerDialog.OnDateSetListener() {

                             @Override
                             public void onDateSet(DatePicker view, int year,
                                                   int monthOfYear, int dayOfMonth) {

                                 bitis_tarihi.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                             }
                         }, mYear, mMonth, mDay);
                 datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
                 datePickerDialog.show();

             } catch (ParseException e) {
                 e.printStackTrace();
                 final Calendar c = Calendar.getInstance();
                 mYear = c.get(Calendar.YEAR);
                 mMonth = c.get(Calendar.MONTH);
                 mDay = c.get(Calendar.DAY_OF_MONTH);


                 DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                         new DatePickerDialog.OnDateSetListener() {

                             @Override
                             public void onDateSet(DatePicker view, int year,
                                                   int monthOfYear, int dayOfMonth) {

                                 bitis_tarihi.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                             }
                         }, mYear, mMonth, mDay);
                 datePickerDialog.show();
             }
         }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==1){
            if (resultCode==RESULT_OK){
                setLoc.setText(data.getStringExtra("address"));
                selectedLat = Double.parseDouble(data.getStringExtra("latitude"));
                selectedLong = Double.parseDouble(data.getStringExtra("longitude"));
            }
        }
        if (requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            if (resultCode==RESULT_OK){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            foto_uri = result.getUri();
            added_foto.setImageURI(foto_uri);
            }
            else{
                Toast.makeText(this, "Fotoğraf Seçilemedi", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public String getMimeType(Uri uri) {
        String mimeType = null;
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            ContentResolver cr = getContentResolver();
            mimeType = cr.getType(uri);
        } else {
            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri
                    .toString());
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                    fileExtension.toLowerCase());
        }
        return mimeType;
    }


    private void ilanigonder(){


        final String[] url = new String[1];

        if ((foto_uri!=null) && (!selectedAracMarka.equals("Araç Markası Seçiniz")) && (!selectedAracModel.equals("Araç Modeli Seçiniz"))
                && (!selectedYakit.equals("Yakıt Türünü Seçiniz")) && (!selectedVites.equals("Vites Türünü Seçiniz"))
                && (!baslangic_tarihi.getText().toString().isEmpty()) && (!bitis_tarihi.getText().toString().isEmpty())
                && (!fiyat.getText().toString().isEmpty()) && (!setLoc.getText().toString().isEmpty()))  {

            final StorageReference dosyayolu = upload_fotoRef.child(System.currentTimeMillis()+"."+getMimeType(foto_uri));
            upload_foto = dosyayolu.putFile(foto_uri);
            upload_foto.continueWithTask(new Continuation() {
                @Override
                    public Object then(@NonNull Task task) throws Exception {
                        if(!task.isSuccessful()){
                            throw task.getException();
                        }
                        return dosyayolu.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful()){
                            Uri downloaduri = task.getResult();
                            myUri = downloaduri.toString();
                            double price = Double.parseDouble(fiyat.getText().toString());
                            Long beginning = converttime(baslangic_tarihi.getText().toString().trim());
                            Long ending = converttime(bitis_tarihi.getText().toString().trim());
                            String uid = mCurrentuser.getUid();

                            myRef= database.getReference("İlanlar");
                            String ilan_id = myRef.push().getKey();

                            NewCarIlan newCarIlan = new NewCarIlan(ilan_id,uid,selectedAracMarka,selectedAracModel
                                    ,selectedVites,selectedYakit,price,beginning,ending,selectedLat,selectedLong,myUri,setLoc.getText().toString());

                            myRef.child(ilan_id).setValue(newCarIlan).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(getApplicationContext(),"İlanınız kaydedilmiştir. " +
                                                "İlanlarım sekmesinden değişiklik yapabilirsiniz.",Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(Addcar_page.this,Market_page.class);
                                        finish();
                                        startActivity(intent);
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(),"Nope.",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                        }
                        else{
                            Toast.makeText(Addcar_page.this, "Yok", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Addcar_page.this, "Fotoğraf Yüklenemedi.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else{
                Toast.makeText(Addcar_page.this, "Lütfen Tüm Bilgileri Doldurunuz", Toast.LENGTH_SHORT).show();
            }

        }

    public Long converttime(String strDate){
        try {
            DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            // you can change format of date
            Date date = formatter.parse(strDate);
            Timestamp timeStampDate = new Timestamp(date.getTime());
            return timeStampDate.getTime();
        } catch (ParseException e) {
            System.out.println("Exception :" + e);
            return null;
        }
    }
}
