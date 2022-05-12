package com.example.ex2;
//import android.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Locale;
public class FragB extends Fragment implements SeekBar.OnSeekBarChangeListener {
    int move;
    float val;
    boolean startSeekBar=false;
    double ex = 123.0;

    FragBListener listener; // hold the mainactivity referance

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    // -------------------------FRAGMENTS METHODS ---------------------------------------------

    @Override
    public void onAttach(@NonNull Context context) {
        //this connect our main activity with the B fragment when the context var is the main activity
        try{
            this.listener = (FragBListener)context;
        }catch(ClassCastException e){
            throw new ClassCastException("the class " +
                    getActivity().getClass().getName() +
                    " must implements the interface 'FragBListener'");
        }
        super.onAttach(context);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_b, container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ((SeekBar)getView().findViewById(R.id.seekbar)).setOnSeekBarChangeListener(this);
        ((SeekBar)getView().findViewById(R.id.seekbar)).setEnabled(false);
        ((TextView)getView().findViewById(R.id.textView6)).setText("Example: 123");
        super.onViewCreated(view, savedInstanceState);
    }
    //-------------------------------------------------------------------------------------------

    //----------------------------SEEKBAR METHODS------------------------------------------------
    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        move = i;
        if (i == 0 && startSeekBar) {
            ((TextView) getView().findViewById(R.id.textView5)).setText(Integer.toString((int) val));
            ((TextView) getView().findViewById(R.id.textView6)).setText("Example: 123");
            startSeekBar=false;

    } else {
            ((TextView) getView().findViewById(R.id.textView5)).setText(String.format("%." + i + "f", val));
            ((TextView) getView().findViewById(R.id.textView6)).setText(String.format("Example: " + "%." + i + "f", ex));
        }
    }

        @Override
        public void onStartTrackingTouch (SeekBar seekBar){

        }

        @Override
        public void onStopTrackingTouch (SeekBar seekBar){

        }

    //the activity informs fragB about new click in fragA
    public void notifyFragB(float sum){
        val = sum;
        ((TextView)getView().findViewById(R.id.textView5)).setText(Integer.toString((int)val));
        enableButtons();

    }


    //the interface of this fragment that include the methods
    public interface FragBListener{
        //put here methods you want to utilize to communicate with the hosting activity
    }

    public void enableButtons(){
        ((SeekBar)getView().findViewById(R.id.seekbar)).setEnabled(true);
        startSeekBar=true;
    }

    public void disableButtons(){
        try{
        ((TextView)getView().findViewById(R.id.textView5)).setText("");
        ((SeekBar)getView().findViewById(R.id.seekbar)).setProgress(0);
        ((SeekBar)getView().findViewById(R.id.seekbar)).setEnabled(false);
        ((TextView)getView().findViewById(R.id.textView6)).setText("Example: 123");
        move=0;}
        catch(Exception e) {
            return;
        }
    }

    public int getSeekBarState(){
        return move;
    }
    public void fixState(int move)
    {
        ((TextView) getView().findViewById(R.id.textView5)).setText(String.format("%." + move + "f", val));
        ((TextView) getView().findViewById(R.id.textView6)).setText(String.format("Example: " + "%." + move + "f", ex));
    }
    public String getCurrentString() {
        TextView tx = (TextView) getView().findViewById(R.id.textView5);
        return tx.getText().toString();
    }
}
