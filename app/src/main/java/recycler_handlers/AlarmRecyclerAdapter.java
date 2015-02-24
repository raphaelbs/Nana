package recycler_handlers;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.createlier.nana.nana.R;

/**
 * Created by dede on 22/02/2015.
 */
public class AlarmRecyclerAdapter extends RecyclerView.Adapter<AlarmRecyclerAdapter.CustomViewHolder> {
    private ArrayList<InfoHolder> list;

    public AlarmRecyclerAdapter(ArrayList<InfoHolder> list) {
        this.list = list;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.alarm_list_row, parent, false);
        return new CustomViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        InfoHolder ap = list.get(position);

        holder.icon.setImageResource(ap.getRes());
        holder.text.setText(ap.getTime());
        holder.about.setText(ap.getDescription());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        public ImageView icon;
        public TextView text;
        public TextView about; // que porra de git

        public CustomViewHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.alarm_row_icon);
            text = (TextView) itemView.findViewById(R.id.alarm_row_text);
            about = (TextView) itemView.findViewById(R.id.alarm_row_about);
        }
    }
}



