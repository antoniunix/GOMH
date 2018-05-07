package com.go_sharp.gomh.model;

import android.content.Context;
import android.location.Location;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import com.go_sharp.gomh.R;
import com.go_sharp.gomh.ReportPublicity;
import com.go_sharp.gomh.adapter.MySpinnerAdapter;
import com.go_sharp.gomh.dao.DaoReportCensus;
import com.go_sharp.gomh.dao.DaoSepomex;
import com.go_sharp.gomh.dao.DaoTypePublicity;
import com.go_sharp.gomh.dto.DtoAgenda;
import com.go_sharp.gomh.dto.DtoCatalog;
import com.go_sharp.gomh.dto.DtoReportCensus;
import com.go_sharp.gomh.dto.DtoSepomex;
import com.go_sharp.gomh.listener.OnFinishLocation;

import net.panamiur.geolocation.Geolocation;
import net.panamiur.geolocation.interfaces.OnApiGeolocation;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.regex.Pattern;

/**
 * Created by leo on 4/05/18.
 */

public class ModelReportPublicity implements OnApiGeolocation {

    private DaoSepomex daoSepomex;
    private DaoReportCensus daoReportCensus;
    private Geolocation geolocation;
    private Context context;
    private OnFinishLocation onFinishLocation;
    private float bestAcuracy = 0;
    private Timer timer;
    private WeakReference<ReportPublicity> weakReference;
    private ReportPublicity reportPublicity;
    private List<DtoSepomex> lstSepomexes;
    private List<DtoCatalog> lstTypePublicity;

    public ModelReportPublicity(Context context, OnFinishLocation onFinishLocation, ReportPublicity reportPublicity) {
        this.context = context;
        this.onFinishLocation = onFinishLocation;
        this.reportPublicity = reportPublicity;
        daoSepomex = new DaoSepomex();
        daoReportCensus = new DaoReportCensus();
        timer = new Timer();
        weakReference = new WeakReference<>(reportPublicity);
    }

    public ModelReportPublicity() {
        daoReportCensus = new DaoReportCensus();
    }

    public void onStart() {
        geolocation = new Geolocation(ModelCensus.class);
        geolocation.setOnApiGeolocationListener(this)
                .setContext(context)
                .setTimeUpdateLocation(context.getResources().getInteger(R.integer.geolocation_time_update));
        geolocation.stopGeo();
        geolocation.startGeo();
    }


    @Override
    public void onApiGeolocationChange(Location location) {
        if (bestAcuracy == 0 || bestAcuracy > location.getAccuracy()) {
            bestAcuracy = location.getAccuracy();
            onFinishLocation.onFinishLocation(location);
        }
    }

    public void onStopGeo() {
        geolocation.stopGeo();
    }

    public SpinnerAdapter getAdapterSuburb(String postalCode) {
        lstSepomexes = new DaoSepomex().Select(postalCode);
        List<String> lst = new ArrayList<>(lstSepomexes.size());
        for (DtoSepomex dto : lstSepomexes) {
            lst.add(dto.getSuburb().trim());
        }
        return new MySpinnerAdapter(context, R.layout.spinner_simple_list, lst);
    }

    public SpinnerAdapter getAdapterTypePublicity() {
        lstTypePublicity = new DaoTypePublicity().select();
        List<String> lst = new ArrayList<>(lstTypePublicity.size());
        for (DtoCatalog dto : lstTypePublicity) {
            lst.add(dto.getValue().trim());
        }
        return new MySpinnerAdapter(context, R.layout.spinner_simple_list, lst);
    }

    public SpinnerAdapter getAdapterDelegacion(String postalcode) {
        lstSepomexes = new DaoSepomex().SelectTown(postalcode);
        List<String> lst = new ArrayList<>(lstSepomexes.size());
        for (DtoSepomex dto : lstSepomexes) {
            lst.add(dto.getTown().trim());
        }
        return new MySpinnerAdapter(context, R.layout.spinner_simple_list, lst);
    }

    public ArrayAdapter getAdapterCp() {
        List<String> lst = daoSepomex.SelectCp();
        return new MySpinnerAdapter(context, R.layout.spinner_simple_list, lst);
    }


    public void addNewReportPublicity(DtoReportCensus dto) {
        daoReportCensus.deleteByIdReport(dto.getIdReporteLocal());
        daoReportCensus.insert(dto);
    }

    public DtoSepomex getItemSuburb(int position) {
        return lstSepomexes.get(position);
    }
    public DtoCatalog getItemTypePublicity(int position){
        return  lstTypePublicity.get(position);
    }

    public boolean isValidateEmail(String email){
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String emailPattern2 =  "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+\\.+[a-z]+";
       if(email.matches(emailPattern) || email.matches(emailPattern2)){
           return true;
       }else {
           return false;
       }

    }

    public boolean isValidateNumberPhone(String numberPhone){
        return (numberPhone.length()>=8)?true:false;
    }

}
