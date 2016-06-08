package pbru.it.sukuma.itpbru;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {


    //explicit
    private MyMange myManage;
    private static final String urlJSON = "http://swiftcodingthai.com/pbru2/get_user_master.php";
    private EditText userEditText, passwordEditText;
    private String userString, passwordString;
    private String[] loginStrings;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bind Widget
        userEditText = (EditText) findViewById(R.id.editText5);
        passwordEditText = (EditText) findViewById(R.id.editText6);


        myManage = new MyMange(this);

        //Test Add New User
        //myManage.addNewUser("123", "name", "sur", "user", "pass");

        //delete a
        deleteAllSQLite();

        mySynJSON();






    }   // Main Method -- statment ;

    public void clickSignIn(View view) {

        userString = userEditText.getText().toString().trim();
        passwordString = passwordEditText.getText().toString().trim();

        //check space
        if (userString.equals("") || passwordString.equals("")) {
            MyAlert myAlert = new MyAlert();

            myAlert.myDialog(this, "Have Space", "Pls fill all blank");



        } else {
            checkUserAnPassword();
        }

    }   //clickSignin

    private void checkUserAnPassword() {
        try {

            SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                    MODE_PRIVATE, null);
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM userTABLE WHERE User = " + "'"+ userString + "'", null);
            cursor.moveToFirst();

            loginStrings = new String[cursor.getColumnCount()];
            for (int i=0; i <cursor.getColumnCount();i++) {
                loginStrings[i] = cursor.getString(i);
            }
            cursor.close();

            //check password
            if (passwordString.equals(loginStrings[4])) {
                Toast.makeText(this, "Welcome " + loginStrings[1] + " " + loginStrings[2],
                    Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
                intent.putExtra("Login", loginStrings);
                startActivity(intent);
                finish();



            } else {
                MyAlert myAlert = new MyAlert();
                myAlert.myDialog(this, "Password False", "Pls Try again");
            }

        } catch (Exception e) {
            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this, "no user ?" , "no" + userString + "in my DB");
        }

    } // checkuser

    private void mySynJSON() {
        ConnectedUserTABLE connectedUserTABLE = new ConnectedUserTABLE(this);
        connectedUserTABLE.execute();
    }

    //Create Inner Class
    private class ConnectedUserTABLE extends AsyncTask<Void, Void, String> {



        private Context context;
        private ProgressDialog progressDialog;

        public ConnectedUserTABLE(Context context) {
            this.context = context;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(context, "Synchronize Server",
                    "Please Wait a mini... Process Synchronize");

        }

        @Override
        protected String doInBackground(Void... params) {

            try {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(urlJSON).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();


            } catch (Exception e) {
                Log.d("7June", "error DoIn ==> " + e.toString());


            }



            return null;
        } //doInback

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                progressDialog.dismiss();
                Log.d("7June", "JSON ==>" + s);

                JSONArray jsonArray = new JSONArray(s);

                String[] idStrings = new String[jsonArray.length()];
                String[] nameStrings = new String[jsonArray.length()];
                String[] surnameStrings = new String[jsonArray.length()];
                String[] userStrings = new String[jsonArray.length()];
                String[] passwordStrings = new String[jsonArray.length()];
                for (int i=0; i<jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    idStrings[i] = jsonObject.getString("id");
                    nameStrings[i] = jsonObject.getString(MyMange.colume_name);
                    surnameStrings[i] = jsonObject.getString(MyMange.colume_surname);
                    userStrings[i] = jsonObject.getString(MyMange.colume_user);
                    passwordStrings[i] = jsonObject.getString(MyMange.colume_password);

                    myManage.addNewUser(idStrings[i], nameStrings[i],
                            surnameStrings[i], userStrings[i], passwordStrings[i]);



                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }   // onPost

        //test



    } //connect class



    private void deleteAllSQLite() {

        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        sqLiteDatabase.delete(MyMange.user_table, null, null);




    }   //delete AllSQLite


    public void clickSignUpMain(View view ) {
        startActivity(new Intent(MainActivity.this, SignUpActivity.class));

    }


}   //main class นี่คือ คลาสหลัก


