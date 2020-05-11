package com.example.easyleasey;

public class UsersBireysel {
    private String Adi;
    private String Soyadi;
    private String Tckimlik;
    private String E_posta;
    private String Id;
    private String Ehliyet_No;
    private String ImageUrl;



    public UsersBireysel() {

    }

    public UsersBireysel(String adi, String soyadi, String tckimlik, String e_posta, String id, String ehliyet_No, String imageUrl) {
        Adi = adi;
        Soyadi = soyadi;
        Tckimlik = tckimlik;
        E_posta = e_posta;
        Id = id;
        Ehliyet_No = ehliyet_No;
        ImageUrl = imageUrl;
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

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getEhliyet_No() {
        return Ehliyet_No;
    }

    public void setEhliyet_No(String ehliyet_No) {
        Ehliyet_No = ehliyet_No;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }
}
