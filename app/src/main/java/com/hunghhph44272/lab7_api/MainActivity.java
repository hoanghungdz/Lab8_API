package com.hunghhph44272.lab7_api;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    EditText txtId,txtName,txtPrice,txtDes;
    Button btnI,btnU,btnD,btnS;
    TextView tvKQ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtId = findViewById(R.id.demo71Id);
        txtName=findViewById(R.id.demo71TxtName);
        txtPrice=findViewById(R.id.demo71TxtPrice);
        txtDes=findViewById(R.id.demo71TxtDes);
        btnI=findViewById(R.id.demo71BtnInsert);
        btnU=findViewById(R.id.demo71BtnUpdate);
        btnD=findViewById(R.id.demo71BtnDelete);
        btnS=findViewById(R.id.demo71BtnSelect);
        tvKQ=findViewById(R.id.demo71TvKQ);
        btnI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertRetrofit();
            }
        });
        btnS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectRetrofit();
            }
        });

        btnD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteRetrofit(txtId.getText().toString());
            }
        });

        btnU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateRetrofit();
            }
        });
    }
    String strKQ="";
    List<Prod> ls;
    private void selectRetrofit() {
        //b1. tao doi tuong Retrofit
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://192.168.1.23/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //b2. goi ham select trong interface
        //b2.1. tao doi tuong
        InterSelectProd interSelectProd
                =retrofit.create(InterSelectProd.class);
        //b2.2 chuan bi ham
        Call<SvrResponseProd> call=interSelectProd.getProd();
        //b2.3 thuc thi ham
        call.enqueue(new Callback<SvrResponseProd>() {
            //thanh cong
            @Override
            public void onResponse(Call<SvrResponseProd> call, Response<SvrResponseProd> response) {
                SvrResponseProd svrResponseProd=response.body();//lay ket qua tra ve tu server
                //convert sang dang list
                ls=new ArrayList<>(Arrays.asList(svrResponseProd.getProducts()));
                for(Prod p: ls )//doc du lieu tu list va dua vao chuoi
                {
                    strKQ += "Id: "+p.getPid()+"; Name: "+p.getName()+"; Price: "+p.getPrice()+"\n";
                }
                //dua ket qua len man hinh
                tvKQ.setText(strKQ);
            }
            //that bai
            @Override
            public void onFailure(Call<SvrResponseProd> call, Throwable t) {
                tvKQ.setText(t.getMessage());
            }
        });

    }

    private void insertRetrofit() {
        //b1. Tao doi tuong chua du lieu
        Prd prd=new Prd();
        //b2. dua du lieu vao doi tuong
        prd.setName(txtName.getText().toString());
        prd.setPrice(txtPrice.getText().toString());
        prd.setDesciption(txtDes.getText().toString());
        //b3. tao doi tuong retrofit
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://192.168.1.23/api/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        //b4. goi ham insert trong interface
        //4.0. Tao doi tuong kieu retrofit
        InterInsertPrd insertPrdObj=retrofit.create(InterInsertPrd.class);
        //4.1. chuan bi ham
        Call<SvrResponsePrd> call=insertPrdObj.insertPrd(prd.getName(),prd.getPrice(),prd.getDesciption());
        //4.2 thuc thi ham
        call.enqueue(new Callback<SvrResponsePrd>() {
            //thanh cong
            @Override
            public void onResponse(Call<SvrResponsePrd> call, Response<SvrResponsePrd> response) {
                SvrResponsePrd svrResponsePrd=response.body();//lay ket qua ma server tra ve
                tvKQ.setText(svrResponsePrd.getMessage());
            }
            //that bai
            @Override
            public void onFailure(Call<SvrResponsePrd> call, Throwable t) {
                tvKQ.setText(t.getMessage());
            }
        });
    }
    private void deleteRetrofit(String id) {
        //B0. Chuan bi du lieu
        Prd p=new Prd();
        p.setId(id);
        //b1. Tao doi tuong Retrofit
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://192.168.1.23/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //b2. Goi interface + chuan bi ham + thuc thi ham
        InterDelete interDelete=retrofit.create(InterDelete.class);
        Call<SvrResponseDelete> call=interDelete.deleteDB(p.getId());
        call.enqueue(new Callback<SvrResponseDelete>() {
            //thanh cong
            @Override
            public void onResponse(Call<SvrResponseDelete> call, Response<SvrResponseDelete> response) {
                //lay ve ket qua
                SvrResponseDelete svrResponseDelete=response.body();
                //dua ket qua len man hinh
                //tvKQ.setText(svrResponseDelete.getMessage());
                Toast.makeText(MainActivity.this, "" + p.getId(), Toast.LENGTH_SHORT).show();
            }
            //that bai
            @Override
            public void onFailure(Call<SvrResponseDelete> call, Throwable t) {
                //tvKQ.setText(t.getMessage());
            }
        });
    }

    private void updateRetrofit() {
        //B0. Chuan bi du lieu
        Prod p=new Prod();
        p.setPid(txtId.getText().toString());
        p.setName(txtName.getText().toString());
        p.setPrice(txtPrice.getText().toString());
        p.setDescription(txtDes.getText().toString());
        //b1. Tao doi tuong Retrofit
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://192.168.1.23/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //b2. Goi interface + chuan bi ham + thuc thi ham
        InterUpdate interUpdate=retrofit.create(InterUpdate.class);
        Call<SvrResponseUpdate> call=interUpdate.updateDB(p.getPid(),
                p.getName(),p.getPrice(),p.getDescription());
        call.enqueue(new Callback<SvrResponseUpdate>() {
            @Override
            public void onResponse(Call<SvrResponseUpdate> call, Response<SvrResponseUpdate> response) {
                //lay ve ket qua
                SvrResponseUpdate svrResponseUpdate=response.body();
                //dua ket qua len man hinh
                tvKQ.setText(svrResponseUpdate.getMessage());
            }

            @Override
            public void onFailure(Call<SvrResponseUpdate> call, Throwable t) {
                tvKQ.setText(t.getMessage());//ghi ra loi
            }
        });
    }
}