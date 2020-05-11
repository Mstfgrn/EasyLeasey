package com.example.easyleasey.models;

public class UsersBireysel {
    private String Id,Adi,Soyadi,Tckimlik,E_posta,Ehliyet_No,ImageURL;

    public UsersBireysel() {
    }

    public UsersBireysel(String id, String adi, String soyadi, String tckimlik, String e_posta, String ehliyet_No,String imageURL) {
        Id = id;
        Adi = adi;
        Soyadi = soyadi;
        Tckimlik = tckimlik;
        E_posta = e_posta;
        Ehliyet_No = ehliyet_No;
        ImageURL = imageURL;

    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getAdi() {
        return Adi;
    }

    public void setAdi(String adi) {
        Adi = adi;
    }

    public String getSoyadi() {
        return Soyadi;
    }

    public void setSoyadi(String soyadi) {
        Soyadi = soyadi;
    }

    public String getTckimlik() {
        return Tckimlik;
    }

    public void setTckimlik(String tckimlik) {
        Tckimlik = tckimlik;
    }

    public String getE_posta() {
        return E_posta;
    }

    public void setE_posta(String e_posta) {
        E_posta = e_posta;
    }

    public String getEhliyet_No() {
        return Ehliyet_No;
    }

    public void setEhliyet_No(String ehliyet_No) {
        Ehliyet_No = ehliyet_No;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }
}
