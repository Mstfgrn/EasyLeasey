package com.example.easyleasey.models;

import java.io.Serializable;

public class NewCarIlan implements Serializable {

    private String Ilan_Id, Kullanıcı_Id, Arac_Adi, Arac_Model, Vites_Turu, Yakit_Turu, Ilan_foto,Ilan_Konum;
    private Long Kiralama_Başlangic,Kiralama_Bitis;
    private double Enlem,Boylam,Fiyat;

    public NewCarIlan() {
    }

    public NewCarIlan(String ilan_Id, String kullanıcı_Id, String arac_Adi, String arac_Model, String vites_Turu, String yakit_Turu, double fiyat, Long kiralama_Başlangic, Long kiralama_Bitis, double enlem, double boylam, String ilan_foto,String ilan_Konum) {
        Ilan_Id = ilan_Id;
        Kullanıcı_Id = kullanıcı_Id;
        Arac_Adi = arac_Adi;
        Arac_Model = arac_Model;
        Vites_Turu = vites_Turu;
        Yakit_Turu = yakit_Turu;
        Fiyat = fiyat;
        Kiralama_Başlangic = kiralama_Başlangic;
        Kiralama_Bitis = kiralama_Bitis;
        Enlem = enlem;
        Boylam = boylam;
        Ilan_foto = ilan_foto;
        Ilan_Konum = ilan_Konum;
    }

    public String getIlan_Id() {
        return Ilan_Id;
    }

    public void setIlan_Id(String ilan_Id) {
        Ilan_Id = ilan_Id;
    }

    public String getKullanıcı_Id() {
        return Kullanıcı_Id;
    }

    public void setKullanıcı_Id(String kullanıcı_Id) {
        Kullanıcı_Id = kullanıcı_Id;
    }

    public String getArac_Adi() {
        return Arac_Adi;
    }

    public void setArac_Adi(String arac_Adi) {
        Arac_Adi = arac_Adi;
    }

    public String getArac_Model() {
        return Arac_Model;
    }

    public void setArac_Model(String arac_Model) {
        Arac_Model = arac_Model;
    }

    public String getVites_Turu() {
        return Vites_Turu;
    }

    public void setVites_Turu(String vites_Turu) {
        Vites_Turu = vites_Turu;
    }

    public String getYakit_Turu() {
        return Yakit_Turu;
    }

    public void setYakit_Turu(String yakit_Turu) {
        Yakit_Turu = yakit_Turu;
    }

    public double getFiyat() {
        return Fiyat;
    }

    public void setFiyat(double fiyat) {
        Fiyat = fiyat;
    }

    public Long getKiralama_Başlangic() {
        return Kiralama_Başlangic;
    }

    public void setKiralama_Başlangic(Long kiralama_Başlangic) {
        Kiralama_Başlangic = kiralama_Başlangic;
    }

    public Long getKiralama_Bitis() {
        return Kiralama_Bitis;
    }

    public void setKiralama_Bitis(Long kiralama_Bitis) {
        Kiralama_Bitis = kiralama_Bitis;
    }

    public double getEnlem() {
        return Enlem;
    }

    public void setEnlem(double enlem) {
        Enlem = enlem;
    }

    public double getBoylam() {
        return Boylam;
    }

    public void setBoylam(double boylam) {
        Boylam = boylam;
    }

    public String getIlan_foto() {
        return Ilan_foto;
    }

    public void setIlan_foto(String ilan_foto) {
        Ilan_foto = ilan_foto;
    }

    public String getIlan_Konum() {
        return Ilan_Konum;
    }

    public void setIlan_Konum(String ilan_Konum) {
        Ilan_Konum = ilan_Konum;
    }
}
