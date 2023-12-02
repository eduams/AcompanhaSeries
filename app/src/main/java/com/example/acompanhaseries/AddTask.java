package com.example.acompanhaseries;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddTask extends BottomSheetDialogFragment {
    public static final String TAG = "AddTask";
    private EditText taskedit;
    private EditText taskedit2;
    private EditText taskedit3;
    private EditText taskedit4;

    private Spinner spinner;
    private Button saveBt;
    private FirebaseFirestore firestore;
    private String diaSemana;
    public static AddTask newInstance(){
        return new AddTask();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_task, container, false);
    }

    public void epChange () {
        String serie = taskedit.getText().toString();
        String plataforma = taskedit2.getText().toString();
        String temporada = taskedit3.getText().toString();
        String ultimoEp = taskedit4.getText().toString();

        Map<String, Object> taskmap = new HashMap<>();
        taskmap.put("serie", serie);
        taskmap.put("diaSemana", diaSemana);
        taskmap.put("plataforma", plataforma);
        taskmap.put("temporada", temporada);
        taskmap.put("ultimoEp", ultimoEp);
        taskmap.put("status", 0);
        firestore.collection("serie").add(taskmap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                Toast.makeText(getActivity(), "Texto salvo",
                        Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //adicionar toast
            }
        });    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        taskedit = view.findViewById(R.id.textinput);
        taskedit2 = view.findViewById(R.id.textinput2);
        taskedit3 = view.findViewById(R.id.textinput3);
        taskedit4 = view.findViewById(R.id.textinput4);
        saveBt = view.findViewById(R.id.savebtn);
        spinner = view.findViewById(R.id.spinner1);
        firestore = FirebaseFirestore.getInstance();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(), R.array.dia_semana, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        taskedit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")){
                    saveBt.setEnabled(false);
                }
                else{
                    saveBt.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position){
                    case 0:
                        diaSemana = "Domingo";
                        break;
                    case 1:
                        diaSemana = "Segunda";
                        break;
                    case 2:
                        diaSemana = "Terça";
                        break;
                    case 3:
                        diaSemana = "Quarta";
                        break;
                    case 4:
                        diaSemana = "Quinta";
                        break;
                    case 5:
                        diaSemana = "Sexta";
                        break;
                    case 6:
                        diaSemana = "Sábado";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        saveBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String serie = taskedit.getText().toString();
                String plataforma = taskedit2.getText().toString();
                String temporada = taskedit3.getText().toString();
                String ultimoEp = taskedit4.getText().toString();

                Map<String, Object> taskmap = new HashMap<>();
                taskmap.put("serie", serie);
                taskmap.put("diaSemana", diaSemana);
                taskmap.put("plataforma", plataforma);
                taskmap.put("temporada", temporada);
                taskmap.put("ultimoEp", ultimoEp);
                taskmap.put("status", 0);
                firestore.collection("serie").add(taskmap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(getActivity(), "Texto salvo",
                                Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //adicionar toast
                    }
                });
            }
        });
    }
}
