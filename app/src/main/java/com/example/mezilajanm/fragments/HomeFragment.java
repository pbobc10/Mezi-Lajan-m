package com.example.mezilajanm.fragments;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.mezilajanm.AppConfig;
import com.example.mezilajanm.AppVolleySingleton;
import com.example.mezilajanm.R;
import com.example.mezilajanm.adapters.TauxAdapter;
import com.example.mezilajanm.models.TauxDeChange;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private TauxDeChange item;
    private TauxAdapter tauxAdapter;
    private ArrayList<TauxDeChange> arrayListTaux;
    private RecyclerView recyclerView;
    private DateFormat dateFormat;
    private Date date;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Check for the internet connection
        if (checkNetworkConnection()) {
            if (isOnline()) {
                populate(view);
            } else {
                Toast.makeText(getContext(), "You Don't have Network Connection ...", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getContext(), "Check if your Data or Wifi is Opened ...", Toast.LENGTH_LONG).show();
        }

        //Date
        dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.FRANCE);
        date = new Date();
        Log.d("soup", dateFormat.format(date));
    }

    public void populate(View view) {
        recyclerView = view.findViewById(R.id.rvBanks);
        // recyclerView.setHasFixedSize(true);
        //TauxDeChange item = new TauxDeChange("86", "84", "21-avr-19");
        arrayListTaux = new ArrayList<>();
        // arrayListTaux.add(new TauxDeChange("2", "3", "2019"));
        tauxAdapter = new TauxAdapter(arrayListTaux, getContext());
        tauxAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(tauxAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        //Bank StringRequest Volley
        unibankStringRequest(AppConfig.URL_UNIBANK);
        sogebankStringRequest(AppConfig.URL_SOGEBANK);
        bncStringRequest(AppConfig.URL_BNC);
        buhStringRequest(AppConfig.URL_BUH);
        capitalBankStringRequest(AppConfig.URL_CAPITALBANK);
        bphBankStringRequest(AppConfig.URL_BPH);
    }

    public void unibankStringRequest(String url) {
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                tauxUnibank(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        AppVolleySingleton.getInstance(getContext()).addToRequestQueue(request);
    }

    public void sogebankStringRequest(String url) {
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                tauxSogebank(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        AppVolleySingleton.getInstance(getContext()).addToRequestQueue(request);
    }

    public void bncStringRequest(String url) {
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                tauxBNC(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        AppVolleySingleton.getInstance(getContext()).addToRequestQueue(request);
    }

    public void buhStringRequest(String url) {
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                tauxBUH(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        AppVolleySingleton.getInstance(getContext()).addToRequestQueue(request);
    }

    public void capitalBankStringRequest(String url) {
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                tauxCapitalBank(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        AppVolleySingleton.getInstance(getContext()).addToRequestQueue(request);
    }

    public void bphBankStringRequest(String url) {
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                tauxBPH(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        AppVolleySingleton.getInstance(getContext()).addToRequestQueue(request);
    }


    public void tauxUnibank(String html) {
        String bank = "UNIBANK";
        String logo = "https://www.unibankonline.com/iBank2/images/logo.jpg";

        Document document = Jsoup.parse(html);
        Log.d("html", document.toString());
        Elements elements = document.getElementsByClass("taux");


        for (Element e : elements) {
            Log.d("soup", "UNIBANK");
            Log.d("soup", e.getElementsByTag("p").first().getElementsByClass("connexion").text());
            Log.d("soup", e.getElementsByTag("tr").last().getElementsByTag("td").select("td:nth-child(2)").text());
            Log.d("soup", e.getElementsByTag("tr").last().getElementsByTag("td").last().text());

            String date = e.getElementsByTag("p").first().getElementsByClass("connexion").text();
            String achat = e.getElementsByTag("tr").last().getElementsByTag("td").select("td:nth-child(2)").text();
            String vente = e.getElementsByTag("tr").last().getElementsByTag("td").last().text();
            //break;

            item = new TauxDeChange(achat, vente, date, bank, logo);
            // t.add(item);
            break;
        }
        try {
            if (arrayListTaux == null)
                arrayListTaux = new ArrayList<>();

            arrayListTaux.add(item);
            tauxAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void tauxSogebank(String html) {
        String bank = "SOGEBANK";
        String logo = "https://www.sogebank.com/wp-content/uploads/2017/12/sogebank.png";

        Document document = Jsoup.parse(html);
        Log.d("html", document.toString());
        Elements elements = document.select("#tab-en-succursale .wpb_text_column .wpb_wrapper .tg tr:nth-child(2)");


        for (Element e : elements) {
            Log.d("soup", "SOGEBANK");
            Log.d("soup", e.select("td:nth-child(2)").text().substring(0, 5) + " HTG");
            Log.d("soup", e.getElementsByTag("td").last().text().substring(0, 5) + " HTG");

            String date2 = dateFormat.format(date);
            String achat = e.select("td:nth-child(2)").text().substring(0, 5) + " HTG";
            String vente = e.getElementsByTag("td").last().text().substring(0, 5) + " HTG";
            //break;

            item = new TauxDeChange(achat, vente, date2, bank, logo);
            break;
        }
        try {
            if (arrayListTaux == null)
                arrayListTaux = new ArrayList<>();

            arrayListTaux.add(item);
            tauxAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void tauxBNC(String html) {
        String bank = "BNC";
        String logo = "https://www.bnconline.com/templates/tpl_bnc_07_2013/images/logo_petit2.png";

        Document document = Jsoup.parse(html);
        Log.d("html", document.toString());
        Elements elements = document.select(".moduletable_taux table");


        for (Element e : elements) {
            Log.d("soup", "BNC");
            Log.d("soup", e.getElementsByTag("tr").last().select("td:nth-child(2)").text());
            Log.d("soup", e.getElementsByTag("tr").last().select("td:nth-child(3)").text());

            String date2 = dateFormat.format(date);
            String achat = e.getElementsByTag("tr").last().select("td:nth-child(2)").text();
            String vente = e.getElementsByTag("tr").last().select("td:nth-child(3)").text();
            //break;

            item = new TauxDeChange(achat, vente, date2, bank, logo);
            break;
        }
        try {
            if (arrayListTaux == null)
                arrayListTaux = new ArrayList<>();

            arrayListTaux.add(item);
            tauxAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void tauxBUH(String html) {
        String bank = "BUH";
        String logo = "https://buh.ht/wp-content/uploads/logo.png";

        Document document = Jsoup.parse(html);
        Log.d("html", document.toString());
        Elements elements = document.select(".iconbox_content_container").first().getElementsByTag("p");


        for (Element e : elements) {
            Log.d("soup", "BUH");
            Log.d("soup", e.getElementsByTag("p").first().text().substring(13, 22));
            Log.d("soup", e.getElementsByTag("p").first().text().substring(36, 45));

            String date2 = dateFormat.format(date);
            String achat = e.getElementsByTag("p").first().text().substring(13, 22);
            String vente = e.getElementsByTag("p").first().text().substring(36, 45);


            item = new TauxDeChange(achat, vente, date2, bank, logo);

            break;
        }
        try {
            if (arrayListTaux == null)
                arrayListTaux = new ArrayList<>();

            arrayListTaux.add(item);
            tauxAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void tauxCapitalBank(String html) {
        String bank = "CAPITAL BANK";
        String logo = "https://www.capitalbankhaiti.biz/media/images/logo.png";

        Document document = Jsoup.parse(html);
        Log.d("html", document.toString());
        Elements elements = document.select(".sectionRPage .taux tbody");


        for (Element e : elements) {
            Log.d("soup", "CAPITAL BANK");
            Log.d("soup", e.select("tr:nth-child(2)").select("td:nth-child(2)").text());
            Log.d("soup", e.select("tr:nth-child(2)").select("td:nth-child(3)").text());

            String date2 = dateFormat.format(date);
            String achat = e.select("tr:nth-child(2)").select("td:nth-child(2)").text() + " HTG";
            String vente = e.select("tr:nth-child(2)").select("td:nth-child(3)").text() + " HTG";


            item = new TauxDeChange(achat, vente, date2, bank, logo);

            break;
        }

        try {
            if (arrayListTaux == null)
                arrayListTaux = new ArrayList<>();

            arrayListTaux.add(item);
            tauxAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void tauxBPH(String html) {
        String bank = "BPH";
        String logo = "http://www.bphhaiti.com/bk/wp-content/uploads/2013/06/logo.jpg";

        Document document = Jsoup.parse(html);
        Log.d("html", document.toString());
        Elements elements = document.select(".table");


        for (Element e : elements) {
            Log.d("soup", "BPH");
            Log.d("soup", e.select("tr:nth-child(2)").select("td:nth-child(2)").text());
            Log.d("soup", e.select("tr:nth-child(2)").select("td:nth-child(3)").text());

            String date2 = dateFormat.format(date);
            String achat = e.select("tr:nth-child(2)").select("td:nth-child(2)").text().substring(0, 2) + " HTG";
            String vente = e.select("tr:nth-child(2)").select("td:nth-child(3)").text().substring(0, 2) + " HTG";


            item = new TauxDeChange(achat, vente, date2, bank, logo);
            break;
        }
        try {
            if (arrayListTaux == null)
                arrayListTaux = new ArrayList<>();

            arrayListTaux.add(item);
            tauxAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Check if wifi or data is enable
     */
    public boolean checkNetworkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        Log.d("networkcheck", "pass1");
        return (networkInfo != null && networkInfo.isConnected());
    }

    public boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.4.4");
            Integer exitValue = ipProcess.waitFor();
            Log.d("networkcheck", exitValue.toString());
            return (exitValue == 0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

}
