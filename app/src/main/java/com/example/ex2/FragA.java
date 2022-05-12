package com.example.ex2;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class FragA extends Fragment implements OnClickListener {
    View view;
    FragAListener listener; // hold the mainactivity referance
    EditText operand1,operand2;
    float num1,num2;
    float val;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onAttach(@NonNull Context context) {
        //this connect our main activity with the A fragment when the context var is the main activity
        try{
            this.listener = (FragAListener)context;
        }catch(ClassCastException e){
            throw new ClassCastException("the class " +
                    context.getClass().getName() +
                    " must implements the interface 'FragAListener'");
        }
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_a, container,false); //NEED TO ADD FRAGA
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        this.view=view;
        ((EditText)view.findViewById(R.id.editTextTextPersonName)).addTextChangedListener(new ValidOperandTextWatcher());
        ((EditText)view.findViewById(R.id.editTextTextPersonName2)).addTextChangedListener(new  ValidOperandTextWatcher());
        view.findViewById(R.id.add).setOnClickListener(this);
        view.findViewById(R.id.sub).setOnClickListener(this);
        view.findViewById(R.id.mul).setOnClickListener(this);
        view.findViewById(R.id.divide).setOnClickListener(this);
        view.findViewById(R.id.button5).setOnClickListener(this);
        super.onViewCreated(view, savedInstanceState);

    }

    //-------------------------TEXT WATCHER METHODS----------------------------------------------

    private class ValidOperandTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            try{
                num1= getOp1();
                num2= getOp2();
                if(num2!=0)
                    ((Button)view.findViewById(R.id.divide)).setEnabled(true);
                enableButtons();
            }
            catch(Exception e) {
                 disableButtons();
            }

        }
    }
    //---------------------------------------------------------------------------------------

    private int getOp1() throws Exception {
        operand1 = (EditText)view.findViewById(R.id.editTextTextPersonName);
        String str = operand1.getText().toString();
        if(str.matches(""))
            throw new Exception("") ;
        return Integer.valueOf(str);

    }

    private int getOp2() throws Exception{
        operand1 = (EditText)view.findViewById(R.id.editTextTextPersonName2);
        String str = operand1.getText().toString();
        if(str.matches(""))
            throw new Exception("") ;
        return Integer.valueOf(str);
    }


    //the interface of this fragment that include the methods
    public interface FragAListener{
        public void sendingValToCalculate(View view, float num1, float num2);
        public void enableDisable(boolean bool);
    }

    @Override
    public void onClick(View view)  {
        int id = ((Button)view).getId();
        String btnName = getResources().getResourceEntryName(id);
        if(btnName.equals("button5")){
        ((EditText)this.view.findViewById(R.id.editTextTextPersonName)).setText("");
        ((EditText)this.view.findViewById(R.id.editTextTextPersonName2)).setText("");
        listener.enableDisable(false);
        }
        else
             listener.sendingValToCalculate(view,num1,num2);
    }

    private void enableButtons(){
        ((Button)view.findViewById(R.id.add)).setEnabled(true);
        ((Button)view.findViewById(R.id.sub)).setEnabled(true);
        ((Button)view.findViewById(R.id.mul)).setEnabled(true);
        listener.enableDisable(true);

    }

    private void disableButtons(){
        ((Button)getView().findViewById(R.id.add)).setEnabled(false);
        ((Button)getView().findViewById(R.id.sub)).setEnabled(false);
        ((Button)getView().findViewById(R.id.mul)).setEnabled(false);
        ((Button)getView().findViewById(R.id.divide)).setEnabled(false);
        listener.enableDisable(false);

    }

}
