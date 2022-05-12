package com.example.ex2;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import java.lang.reflect.Method;
import java.lang.Class;
import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements FragA.FragAListener,FragB.FragBListener {//implements SeekBar.OnSeekBarChangeListener {

    private FragB fragB=null;

    String keepEd1,keepEd2;
    float val=0;
    int move;

    @Override
    //MEMBER CLASS
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        getSupportFragmentManager().executePendingTransactions();

        fragB = (FragB) getSupportFragmentManager().findFragmentByTag("FRAGB");

        if ((getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)){
            if (fragB != null) {
                getSupportFragmentManager().beginTransaction()
                        .show(fragB)
                        .addToBackStack(null)
                        .commit();
            }
            else {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragContainer, FragB.class,null, "FRAGB")
                        .commit();
            }
            getSupportFragmentManager().executePendingTransactions();
        }
    }


    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        try {
            outState.putString("str", fragB.getCurrentString());
            outState.putFloat("res", val);
            outState.putInt("move", fragB.getSeekBarState());
        }
        catch (Exception e) {
            outState.putString("str", "");
        }

    }

    @Override
    public void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        FragB fragB;
        fragB = (FragB) getSupportFragmentManager().findFragmentByTag("FRAGB");

        super.onRestoreInstanceState(savedInstanceState);


        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .show(fragB)
                    .addToBackStack("BBB")
                    .commit();
            getSupportFragmentManager().executePendingTransactions();
        }
        String cs = savedInstanceState.getString("str");
        if (!cs.equals("")) { //there is a val to update
            val = savedInstanceState.getFloat("res");
            move = savedInstanceState.getInt("move");
            fragB.notifyFragB(val);
            fragB.fixState(move);
        }
    }


    public void enableDisable(boolean bool){
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE & fragB!=null) {
            //landscape mode
            if (bool) {
                fragB.enableButtons();
            } else
                fragB.disableButtons();
        }

    }

    public void sendingValToCalculate(View view,float num1,float num2){
        int id = ((Button)view).getId();
        String btnName = getResources().getResourceEntryName(id);
        if(btnName.equals("sum")) val = num1+num2;
        else if(btnName.equals("sub")) val = num1-num2;
        else if(btnName.equals("mul")) val = num1*num2;
        else if(btnName.equals("divide")) val = num1/num2;

        addFragB();
        //sending frag b the val to present
        fragB.notifyFragB(val);

    }

    private void addFragB(){
        //before we notify frag b we want to upload him if we are in portrait view
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragContainer, FragB.class, null,"FRAGB")
                    .addToBackStack("BBB")
                    .commit();
            getSupportFragmentManager().executePendingTransactions();
        }
        fragB = (FragB) getSupportFragmentManager().findFragmentByTag("FRAGB");
    }


}