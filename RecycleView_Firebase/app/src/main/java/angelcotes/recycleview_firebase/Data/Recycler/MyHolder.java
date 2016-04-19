package angelcotes.recycleview_firebase.Data.Recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import angelcotes.recycleview_firebase.R;

/**
 * Created by Angel on 18/04/2016.
 */
public class MyHolder extends RecyclerView.ViewHolder {

    TextView nameTxt;


    public MyHolder(View itemView) {
        super(itemView);

        nameTxt =  (TextView) itemView.findViewById(R.id.nameTxt);
    }
}
