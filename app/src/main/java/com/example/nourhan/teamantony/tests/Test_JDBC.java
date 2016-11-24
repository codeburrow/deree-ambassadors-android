package com.example.nourhan.teamantony.tests;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by nourhan on 30/12/2015.
 */
public class Test_JDBC extends AsyncTask<Void,Void,Void> {
    final static String username="ashoka";
    final static String password="thisisfun0007";
    final static String databaseURL="jdbc:mysql://ashoka.students.acg.edu:3306/ashoka_Nourhan";
    static Connection myConnection;
    private Statement sqlStatement=null;
    private PreparedStatement sqlPreparedStatement=null;
    private ResultSet rs=null;


        @Override
        protected Void doInBackground(Void... params) {

            try {
                Class.forName("com.mysql.jdbc.Driver");
                myConnection = DriverManager.getConnection(databaseURL, username, password);
                if (myConnection == null) {
                    Log.v("HELLOOOOO", "myConnection is NULL !!!!!!!!: ");
                }
                Log.v("HELLOOOOO", "myConnection: " + myConnection);
                Log.d("HELLOOOOO", "CONNECTION SUCCESS!!");
            } catch (ClassNotFoundException e) {
                Log.d("HELLOOOOO", "MySql Driver jar file not included in the project!" + e.getMessage());
            } catch (SQLException e) {
                Log.d("HELLOOOOO", "Check username/password and Database URL " + e.getMessage());
            }

//            try{
//                Log.v("HELLOOOOO", "I am into insert!");
//                sqlPreparedStatement = myConnection.prepareStatement("Insert into Users (id, name) " +
//                        "values (?,?)");
//                Log.v("HELLOOOOO", "sqlStatement: " + sqlPreparedStatement);
//                sqlPreparedStatement.setInt(1,600);
//                sqlPreparedStatement.setString(2,"zsdf");
//                sqlPreparedStatement.executeUpdate();
//            }catch (SQLException s){
//                Log.d("HELLOOOOO", "SQL Error : Inserting data. " + s.getMessage());
//            }

//            RadioButton formal, casual;
//
//            formal = (RadioButton) findViewById(R.id.formal_radiobut);
//            casual = (RadioButton) findViewById(R.id.casual_radiobut);
//            if (formal  == null && casual == null){
//                toast("Please pick a dress code!");
//            }else if (casual == null){
//                insert(6, "formal");
//            } else {
//                insert(6, "casual");
//            }

            return null;
        }

//        @Override
//        protected void onPostExecute(Void result) {
//            try {
//                myConnection.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//            super.onPostExecute(result);
//        }

    public void insert(int id, String name){

        try{
            Log.v("HELLOOOOO", "I am into insert!");
            sqlPreparedStatement = myConnection.prepareStatement("Insert into Users (id, name) " +
                    "values (?,?)");
            Log.v("HELLOOOOO", "sqlStatement: " + sqlPreparedStatement);
            sqlPreparedStatement.setInt(1,id);
            sqlPreparedStatement.setString(2,name);
            sqlPreparedStatement.executeUpdate();
        }catch (SQLException s){
            Log.d("HELLOOOOO", "SQL Error : Inserting data. " + s.getMessage());
        }
    }
//    public void toast (String msg){
//
//        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
//
//    }
}
