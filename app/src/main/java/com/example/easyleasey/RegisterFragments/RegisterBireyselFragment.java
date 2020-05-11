package com.example.easyleasey.RegisterFragments;
// Created by MstfGrgn on 21.04.2020.
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.easyleasey.MainActivity;
import com.example.easyleasey.Purpose_page;
import com.example.easyleasey.R;
import com.example.easyleasey.Register_pages;
import com.example.easyleasey.UsersBireysel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterBireyselFragment extends Fragment {
    FirebaseUser mCurrentuser;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference myRef;

    private Button btn_onayla;
    private Spinner spinner;
    String item;
    private View view;
    private TextInputEditText ad,soyad,tcKimlik,eposta,ehliyetno;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_register_bireysel, container, false);
        mAuth = FirebaseAuth.getInstance();
        mCurrentuser = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();

        spinner =view.findViewById(R.id.Bspinner);
        ad=view.findViewById(R.id.txt_BAdi);
        soyad =view.findViewById(R.id.txt_BSoyAdi);
        tcKimlik = view.findViewById(R.id.txt_Tckimlik);
        eposta = view.findViewById(R.id.txt_BEposta);
        btn_onayla=view.findViewById(R.id.btn_Bsonraki);
        ehliyetno=view.findViewById(R.id.txt_BehliyetNo);



        String purpose1 = "Araç kiralamak istiyorum";
        String purpose2 = "Aracımı kiralamak istiyorum";

        final List<String> purpose = new ArrayList<>();
        purpose.add(0,"Kullanım Amacınız");
        purpose.add(purpose1);
        purpose.add(purpose2);

        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,purpose);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Kullanım Amacınız"))
                {
                }
                else
                {
                    item = parent.getItemAtPosition(position).toString();
                    Toast.makeText(parent.getContext(), item,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getContext(),"Lütfen kullanım amacınızı seçiniz!!",Toast.LENGTH_SHORT).show();
            }
        });
        btn_onayla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ad.getText().toString().trim();
                String surname = soyad.getText().toString().trim();
                String tcID = tcKimlik.getText().toString().trim();
                String epost = eposta.getText().toString().trim();
                String ehno = ehliyetno.getText().toString().trim();



                if (name.isEmpty()||surname.isEmpty()||tcID.isEmpty()||epost.isEmpty()||ehno.isEmpty())
                {
                    Toast.makeText(getContext(),"Lütfen Boş alan bırakmayınız",Toast.LENGTH_SHORT).show();


                }
                else if (tcID.length() != 11)
                {
                    Toast.makeText(getContext(),"Tc kimlik numarası 11 haneden oluşmalıdır!",Toast.LENGTH_SHORT).show();
                }
                else if (ehno.length() != 6)
                {
                    Toast.makeText(getContext(),"Ehliyet numarası 6 haneden oluşmaktadır!",Toast.LENGTH_SHORT).show();
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(epost).matches())
                {
                    Toast.makeText(getContext(),"Lütfen geçerli bir E-Posta adresi giriniz!",Toast.LENGTH_SHORT).show();
                }

                else
                {

                    String uıd = mCurrentuser.getUid();
                    UsersBireysel buser = new UsersBireysel(name,surname,tcID,epost,uıd,ehno,"gs://easyleasy-a66c7.appspot.com/pikpng.png");

                    myRef= database.getReference("Kullanicilar").child("Bireysel").child(uıd);


                    /*HashMap<String ,Object> hashMap = new HashMap<>();


                    hashMap.put("Adi",name.substring(0).toUpperCase());
                    hashMap.put("Soyadi",surname.substring(0).toUpperCase());
                    hashMap.put("Tckimlik",tcID);
                    hashMap.put("E_posta",epost);
                    hashMap.put("Id",uıd);
                    hashMap.put("Ehliyet_No",ehno);
                    hashMap.put("ImageUrl","gs://easyleasy-a66c7.appspot.com/pikpng.png");*/

                    myRef.setValue(buser).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(getContext(),"Kayıt Başarılı",Toast.LENGTH_LONG).show();
                                Intent gecis = new Intent(getActivity(), Purpose_page.class);
                                startActivity(gecis);
                                getActivity().finish();
                            }
                            else
                            {
                                Toast.makeText(getContext(),"Kullanıcı kaydınız başarısız!",Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }
            }
        });

        return view;

    }

}
