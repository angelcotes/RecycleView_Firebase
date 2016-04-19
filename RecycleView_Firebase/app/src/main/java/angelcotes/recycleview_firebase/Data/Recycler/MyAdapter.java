package angelcotes.recycleview_firebase.Data.Recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import angelcotes.recycleview_firebase.Data.Person;
import angelcotes.recycleview_firebase.R;

/**
 * Created by Angel on 18/04/2016.
 */
public class MyAdapter extends RecyclerView.Adapter<MyHolder> {

    Context c;
    ArrayList<Person> persons;

    public MyAdapter(Context c, ArrayList<Person> persons) {
        this.c = c;
        this.persons = persons;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.model, parent, false);
        MyHolder holder = new MyHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        String name = persons.get(position).getNamePerson();
        holder.nameTxt.setText(name);
    }

    @Override
    public int getItemCount() {
        return persons.size();
    }
}
