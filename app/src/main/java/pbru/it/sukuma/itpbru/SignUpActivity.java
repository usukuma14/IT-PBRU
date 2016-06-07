package pbru.it.sukuma.itpbru;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SignUpActivity extends AppCompatActivity {

    //explicit ประกาศตัวแปร

    private EditText nameEditText, surnameEditText, userEditText, passwordEditText;
    private String nameString, surnameString, userString, passwordString;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // bind widget
        nameEditText = (EditText) findViewById(R.id.editText);
        surnameEditText = (EditText) findViewById(R.id.editText2);
        userEditText = (EditText) findViewById(R.id.editText3);
        passwordEditText = (EditText) findViewById(R.id.editText4);







    }   //main method

    public void clickSignUpSign(View view) {

        nameString = nameEditText.getText().toString().trim();
        surnameString = surnameEditText.getText().toString().trim();
        userString = surnameEditText.getText().toString().trim();
        passwordString = passwordEditText.getText().toString().trim();


        //check space
        if (checkSpace()) {
            //true
            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this, "Have space", "Fill in all blank space");




        } else {

            //false

        }


    }   //click sign

    private boolean checkSpace() {


        boolean result = true;

        result = nameString.equals("") || surnameString.equals("") || userString.equals("") ||
                passwordString.equals("");



        return result;
    }


}   //main class
