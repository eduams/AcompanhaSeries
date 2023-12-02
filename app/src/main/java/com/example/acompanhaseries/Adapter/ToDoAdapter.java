package com.example.acompanhaseries.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.acompanhaseries.MainActivity;
import com.example.acompanhaseries.Model.ToDoModel;
import com.example.acompanhaseries.R;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.MyViewHolder> {

    private List<ToDoModel> todoList;
    private MainActivity activity;
    private FirebaseFirestore firestore;
    public ToDoAdapter(MainActivity mainScreen, List<ToDoModel> todoList){
        this.todoList = todoList;
        activity = mainScreen;
    }
    private int episodeCount;
    @androidx.annotation.NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@androidx.annotation.NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.each_task, parent, false);
        firestore = FirebaseFirestore.getInstance();
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@androidx.annotation.NonNull MyViewHolder holder, int position) {
        ToDoModel toDoModel = todoList.get(position);
        holder.check.setText(toDoModel.getSerie());
        holder.plataforma.setText(toDoModel.getPlataforma());
        holder.temporada.setText(toDoModel.getTemporada());
        holder.ultimoEp.setText(toDoModel.getUltimoEp());
        holder.diaSemana.setText(toDoModel.getDiaSemana());
        holder.check.setChecked(toBoolean(toDoModel.getStatus()));

        holder.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Remove a tarefa da lista
                    todoList.remove(toDoModel);

                    // Notifica o adapter sobre a remoção
                    notifyDataSetChanged();

                    // Deleta a tarefa do Firestore
                    firestore.collection("serie").document(toDoModel.TaskId).delete();
                }
            }
        });

        holder.ultimoEp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    int currentEp = Integer.parseInt(holder.ultimoEp.getText().toString());
                    int newEp = currentEp + 1;
                    holder.ultimoEp.setText(String.valueOf(newEp));
                    ToDoModel toDoModel = todoList.get(position);
                    toDoModel.setUltimoEp(String.valueOf(newEp));
                    firestore.collection("serie").document(toDoModel.TaskId)
                            .update("ultimoEp", String.valueOf(newEp));
                }
            }
        });
    }

    private boolean toBoolean(int status){
        return status != 0;
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder{

        CheckBox check;
        TextView plataforma;
        TextView temporada;
        CheckBox ultimoEp;
        TextView diaSemana;


        public MyViewHolder(@NonNull View itemView){

            super(itemView);
            check = itemView.findViewById(R.id.checkBox);
            plataforma = itemView.findViewById(R.id.plataforma);
            temporada = itemView.findViewById(R.id.temporada);
            ultimoEp = itemView.findViewById(R.id.ultimoEp);
            diaSemana = itemView.findViewById(R.id.semanaAssistir);

        }
    }
}
