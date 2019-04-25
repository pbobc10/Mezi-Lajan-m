package com.example.mezilajanm.models;

import java.util.Date;

public class TauxDeChange {
    private String logo;
    private String bank;

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    private String achat;
    private String vente;
    private String date;

    public TauxDeChange(String achat, String vente, String date,String bank,String logo) {
        this.achat = achat;
        this.vente = vente;
        this.date = date;
        this.bank=bank;
        this.logo=logo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

   /* public String getDevise() {
        return devise;
    }

    public void setDevise(String devise) {
        this.devise = devise;
    }*/

    public String getAchat() {
        return achat;
    }

    public void setAchat(String achat) {
        this.achat = achat;
    }

    public String getVente() {
        return vente;
    }

    public void setVente(String vente) {
        this.vente = vente;
    }
}
