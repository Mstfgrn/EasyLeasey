package com.example.easyleasey.RegisterFragments;
// Created by MstfGrgn on 24.04.2020.
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.easyleasey.MainActivity;
import com.example.easyleasey.Purpose_page;
import com.example.easyleasey.R;
import com.example.easyleasey.UsersBireysel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterKurumsalFragment extends Fragment {
    private View view ;
    FirebaseUser mCurrentuser;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference myRef;
    TextInputEditText sahisadi,sirketadi,sirketunvan,eposta,mersisno;
    Button btn_sonraki;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_register_kurumsal, container, false);

        mAuth = FirebaseAuth.getInstance();
        mCurrentuser = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();

        sahisadi=view.findViewById(R.id.txt_Ksahisadi);
        sirketadi = view.findViewById(R.id.text_K);
        sirketunvan = view.findViewById(R.id.txt_KUnvan);
        eposta = view.findViewById(R.id.txt_Keposta);
        mersisno = view.findViewById(R.id.txt_KmersisNo);
        btn_sonraki = view.findViewById(R.id.btn_Ksonraki);

        btn_sonraki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sahisadı = sahisadi.getText().toString().trim();
                String sirketadı = sirketadi.getText().toString().trim();
                String sirketnvan = sirketunvan.getText().toString().trim();
                String epost = eposta.getText().toString().trim();
                String mersis = mersisno.getText().toString().trim();

                if (sahisadı.isEmpty()||sirketadı.isEmpty()||sirketnvan.isEmpty()||epost.isEmpty()||mersis.isEmpty())
                {
                    Toast.makeText(getContext(),"Lütfen Boş alan bırakmayınız",Toast.LENGTH_SHORT).show();

                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(epost).matches())
                {
                    Toast.makeText(getContext(),"Lütfen geçerli bir E-Posta adresi giriniz!",Toast.LENGTH_SHORT).show();
                }
                else if(mersis.length() != 16)
                {
                    Toast.makeText(getContext(),"Geçerli bir Mersis numarası giriniz!",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String uıd = mCurrentuser.getUid();

                    myRef= database.getReference("Kullanicilar").child("Kurumsal").child(uıd);

                    HashMap<String ,Object> hashMap = new HashMap<>();

                    hashMap.put("Id",uıd);
                    hashMap.put("Şahıs Adı",sahisadı.substring(0).toUpperCase());
                    hashMap.put("Şirket Adı",sirketadı.substring(0).toUpperCase());
                    hashMap.put("Şirket Ünvanı",sirketnvan);
                    hashMap.put("E-posta",epost);
                    hashMap.put("Mersis No",mersis);

                    myRef.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
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
                                Toast.makeText(getContext(),"Kurumsal kullanıcı kaydınız başarısız!",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });


        return view;
    }

}
