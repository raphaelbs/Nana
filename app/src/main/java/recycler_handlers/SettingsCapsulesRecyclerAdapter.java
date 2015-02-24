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
public class SettingsCapsulesRecyclerAdapter extends RecyclerView.Adapter<SettingsCapsulesRecyclerAdapter.CustomViewHolder> {
    private ArrayList<InfoHolder> list;
    private int listCounter;
    private TitleRowHandler titleList;
    private final int IS_TITLE = 1;
    private final int IS_LIST_ITEM = 0;

    public SettingsCapsulesRecyclerAdapter(TitleRowHandler titleList, ArrayList<InfoHolder> list) {
        this.list = list;
        this.titleList = titleList;
        this.listCounter = 0;
    }

    private InfoHolder getItemInListOrded() {
        return list.get(listCounter++);
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0 || position == 8)
            //if (titleList.getPositionList().contains(position))
            return IS_TITLE;

        else return IS_LIST_ITEM;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == IS_TITLE) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.title_row, parent, false);
            return new CustomViewHolder(v, true);
        }
        if (viewType == IS_LIST_ITEM) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.settings_row_list, parent, false);
            return new CustomViewHolder(v, false);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        int index = titleList.getPositionList().indexOf(position);
        if (index != -1) {
            holder.text.setText(titleList.getTextList().get(index));
        } else {
            InfoHolder ap = getItemInListOrded();

            holder.icon.setImageResource(ap.getRes());
            holder.text.setText(ap.getTime());
        }
    }

    @Override
    public int getItemCount() {
        return list.size() + titleList.getPositionList().size();
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        public ImageView icon;
        public TextView text;

        public CustomViewHolder(View itemView, boolean isTitle) {
            super(itemView);

            if (isTitle) {
                text = (TextView) itemView.findViewById(R.id.title_row_text);
            } else {
                icon = (ImageView) itemView.findViewById(R.id.settings_row_icon);
                text = (TextView) itemView.findViewById(R.id.settings_row_text);
            }
        }
    }
}



