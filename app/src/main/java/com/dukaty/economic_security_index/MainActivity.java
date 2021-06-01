package com.dukaty.economic_security_index;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static com.dukaty.economic_security_index.R.layout.main;

public class MainActivity extends Activity {
    private ProgressDialog pDialog;
    public static final int progress_bar_type = 0;
    private Files IOUtils;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(main);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        final Spinner countries = (Spinner) findViewById(R.id.countries);

        String [] list_of_countries = {"Aruba","Afghanistan","Angola","Albania","Andorra","Arab World","United Arab Emirates","Argentina","Armenia","American Samoa","Antigua and Barbuda","Australia","Austria","Azerbaijan","Burundi","Belgium","Benin","Burkina Faso","Bangladesh","Bulgaria","Bahrain","Bahamas, The","Bosnia and Herzegovina","Belarus","Belize","Bermuda","Bolivia","Brazil","Barbados","Brunei Darussalam","Bhutan","Botswana","Central African Republic","Canada","Central Europe and the Baltics","Switzerland","Channel Islands","Chile","China","Cote d'Ivoire","Cameroon","Congo, Dem. Rep.","Congo, Rep.","Colombia","Comoros","Cabo Verde","Costa Rica","Caribbean small states","Cuba","Curacao","Cayman Islands","Cyprus","Czech Republic","Germany","Djibouti","Dominica","Denmark","Dominican Republic","Algeria","East Asia & Pacific (excluding high income)","Early-demographic dividend","East Asia & Pacific","Europe & Central Asia (excluding high income)","Europe & Central Asia","Ecuador","Egypt, Arab Rep.","Euro area","Eritrea","Spain","Estonia","Ethiopia","European Union","Fragile and conflict affected situations","Finland","Fiji","France","Faroe Islands","Micronesia, Fed. Sts.","Gabon","United Kingdom","Georgia","Ghana","Gibraltar","Guinea","Gambia, The","Guinea-Bissau","Equatorial Guinea","Greece","Grenada","Greenland","Guatemala","Guam","Guyana","High income","Hong Kong SAR, China","Honduras","Heavily indebted poor countries (HIPC)","Croatia","Haiti","Hungary","IBRD only","IDA & IBRD total","IDA total","IDA blend","Indonesia","IDA only","Isle of Man","India","Ireland","Iran, Islamic Rep.","Iraq","Iceland","Israel","Italy","Jamaica","Jordan","Japan","Kazakhstan","Kenya","Kyrgyz Republic","Cambodia","Kiribati","St. Kitts and Nevis","Korea, Rep.","Kuwait","Latin America & Caribbean (excluding high income)","Lao PDR","Lebanon","Liberia","Libya","St. Lucia","Latin America & Caribbean","Least developed countries: UN classification","Low income","Liechtenstein","Sri Lanka","Lower middle income","Low & middle income","Lesotho","Late-demographic dividend","Lithuania","Luxembourg","Latvia","Macao SAR, China","St. Martin (French part)","Morocco","Monaco","Moldova","Madagascar","Maldives","Middle East & North Africa","Mexico","Marshall Islands","Middle income","North Macedonia","Mali","Malta","Myanmar","Middle East & North Africa (excluding high income)","Montenegro","Mongolia","Northern Mariana Islands","Mozambique","Mauritania","Mauritius","Malawi","Malaysia","North America","Namibia","New Caledonia","Niger","Nigeria","Nicaragua","Netherlands","Norway","Nepal","Nauru","New Zealand","OECD members","Oman","Other small states","Pakistan","Panama","Peru","Philippines","Palau","Papua New Guinea","Poland","Pre-demographic dividend","Puerto Rico","Korea, Dem. People's Rep.","Portugal","Paraguay","West Bank and Gaza","Pacific island small states","Post-demographic dividend","French Polynesia","Qatar","Romania","Russian Federation","Rwanda","South Asia","Saudi Arabia","Sudan","Senegal","Singapore","Solomon Islands","Sierra Leone","El Salvador","San Marino","Somalia","Serbia","Sub-Saharan Africa (excluding high income)","South Sudan","Sub-Saharan Africa","Small states","Sao Tome and Principe","Suriname","Slovak Republic","Slovenia","Sweden","Eswatini","Sint Maarten (Dutch part)","Seychelles","Syrian Arab Republic","Turks and Caicos Islands","Chad","East Asia & Pacific (IDA & IBRD)","Europe & Central Asia (IDA & IBRD)","Togo","Thailand","Tajikistan","Turkmenistan","Latin America & Caribbean (IDA & IBRD)","Timor-Leste","Middle East & North Africa (IDA & IBRD)","Tonga","South Asia (IDA & IBRD)","Sub-Saharan Africa (IDA & IBRD)","Trinidad and Tobago","Tunisia","Turkey","Tuvalu","Tanzania","Uganda","Ukraine","Upper middle income","Uruguay","United States","Uzbekistan","St. Vincent and the Grenadines","Venezuela, RB","British Virgin Islands","Virgin Islands (U.S.)","Vietnam","Vanuatu","World","Samoa","Kosovo","Yemen, Rep.","South Africa","Zambia","Zimbabwe"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list_of_countries);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countries.setAdapter(spinnerAdapter);

        final Spinner years = (Spinner) findViewById(R.id.year);

        String [] list_of_years = {"1990","1991","1992","1993","1994","1995","1996","1997","1998","1999","2000","2001","2002","2003","2004","2005","2006","2007","2008","2009","2010","2011","2012","2013","2014","2015","2016","2017","2018","2019","2020"};

        ArrayAdapter<String> yearsadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list_of_years);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        years.setAdapter(yearsadapter);
        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Получаем выбранный объект
                String item = (String)parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        countries.setOnItemSelectedListener(itemSelectedListener);


        final Button count = (Button) findViewById(R.id.count);
        count.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                downloadDestimulators("excel");
                try {
                    Intent intent = new Intent(MainActivity.this, ResultsActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slidein, R.anim.slideout);
                    downloadDestimulators("excel");
                    downloadStimulators("excel");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        final Button info = (Button) findViewById(R.id.info);
        info.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slidein, R.anim.slideout);
            }
        });
    }

    public void downloadDestimulators(String format) {
        try {
            File file = new File("destimulator.txt");

            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            while (line != null) {
                String path_to_folder = file.getCanonicalPath().substring(0, file.getCanonicalPath().lastIndexOf("\\"));
                download("https://api.worldbank.org/v2/en/indicator/"+line+"?downloadformat="+format, line+".xls" );
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void downloadStimulators(String format) {
        try {
            File file = new File("stimulator.txt");
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            while (line != null) {
                String path_to_folder = file.getCanonicalPath().substring(0, file.getCanonicalPath().lastIndexOf("\\"));
                download("https://api.worldbank.org/v2/en/indicator/"+line+"?downloadformat="+format,line +"xls");
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void download(String url, String fileName) throws Exception {
        FileOutputStream fos = null;
        InputStream file = null;
        try {
            file = URI.create(url).toURL().openStream();
            fos = openFileOutput(fileName, MODE_PRIVATE);
            fos.write(file.read());
        }
        catch(IOException ex) {

        }
        finally{
            try{
                if(fos!=null)
                    fos.close();
                    file.close();
            }
            catch(IOException ex){

            }
        }
    }

    private List<String> getCountries(){
            List<String> countries = new ArrayList<String>();
            File myExternalFile = new File("assets/","countries.txt");
            try {
                FileInputStream fis = new FileInputStream(myExternalFile);
                DataInputStream in = new DataInputStream(fis);
                BufferedReader br = new BufferedReader(new InputStreamReader(in));

                String strLine;
                while ((strLine = br.readLine()) != null) {
                    countries.add(strLine);
                }
                br.close();
                in.close();
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return countries;
    }
}