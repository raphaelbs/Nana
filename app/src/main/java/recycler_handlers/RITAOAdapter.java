package recycler_handlers;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.createlier.nana.nana.R;

/**
 * Created by dede on 22/02/2015.
 */
public class RITAOAdapter extends RecyclerView.Adapter<RITAOAdapter.CustomViewHolder> {
    private final static int IS_EDITABLE = 10;
    private InfoHolder list;
    private int type;

    public RITAOAdapter(InfoHolder list) {
        this.type = list.getCurrent_content_value();
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {
        if (type == InfoHolder.CONTAIN_ONLY_TEXT)
            return InfoHolder.CONTAIN_ONLY_TEXT;
        if (type == InfoHolder.CONTAIN_ICON_TEXT) {
            if (list.getIcon().get(position) == 0)
                return InfoHolder.CONTAIN_ONLY_TEXT;
            if (list.getIsEditable().get(position))
                return IS_EDITABLE;
            else
                return InfoHolder.CONTAIN_ICON_TEXT;
        }
        if (type == InfoHolder.CONTAIN_ICON_TEXT_ABOUT) {
            if (list.getIcon().get(position) == 0 && list.getDescription().get(position) == "")
                return InfoHolder.CONTAIN_ONLY_TEXT;
            if (list.getIsEditable().get(position))
                return IS_EDITABLE;
            else {
                if (list.getDescription().get(position) == "")
                    return InfoHolder.CONTAIN_ICON_TEXT;
            }
            return InfoHolder.CONTAIN_ICON_TEXT_ABOUT;
        }
        return 999;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        if (viewType == InfoHolder.CONTAIN_ONLY_TEXT) {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_divisor_text, parent, false);
            return new CustomViewHolder(v, InfoHolder.CONTAIN_ONLY_TEXT);
        }
        if (viewType == InfoHolder.CONTAIN_ICON_TEXT) {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_icon_text, parent, false);
            return new CustomViewHolder(v, InfoHolder.CONTAIN_ICON_TEXT);
        }
        if (viewType == InfoHolder.CONTAIN_ICON_TEXT_ABOUT) {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_icon_text_about, parent, false);
            return new CustomViewHolder(v, InfoHolder.CONTAIN_ICON_TEXT_ABOUT);
        }
        if (viewType == IS_EDITABLE) {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_icon_editable, parent, false);
            return new CustomViewHolder(v, IS_EDITABLE);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        String text = list.getTextList().get(position);
        Boolean ed = list.getIsEditable().get(position);
        try {
            holder.setClickListener(list.getOcl().get(position));
        } catch (Exception e) {
        }

        if (type == InfoHolder.CONTAIN_ONLY_TEXT) {
            holder.text.setText(text);
        }
        if (type == InfoHolder.CONTAIN_ICON_TEXT) {
            int res = list.getIcon().get(position);
            if (res != 0)
                holder.icon.setImageResource(res);
            if (ed) {
                holder.eText.setText(text);
            } else {
                holder.text.setText(text);
            }
        }
        if (type == InfoHolder.CONTAIN_ICON_TEXT_ABOUT) {
            int res = list.getIcon().get(position);
            String about = list.getDescription().get(position);
            if (res != 0)
                holder.icon.setImageResource(res);
            if (ed) {
                holder.eText.setText(text);
                if (about != "")
                    holder.eText.setHint(about);
            } else {
                if (about != "")
                    holder.about.setText(about);
                holder.text.setText(text);
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.getSize();
    }


    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        public ImageView icon;
        public TextView text;
        public EditText eText;
        public TextView about;
        private View parent;

        public CustomViewHolder(View itemView, int CONTAINER_TYPE) {
            super(itemView);
            parent = itemView;

            if (CONTAINER_TYPE == InfoHolder.CONTAIN_ONLY_TEXT) {
                text = (TextView) itemView.findViewById(R.id.title_row_text);
            }
            if (CONTAINER_TYPE == IS_EDITABLE) {
                eText = (EditText) itemView.findViewById(R.id.rie_edit_text);
                icon = (ImageView) itemView.findViewById(R.id.rie_icon);
            }
            if (CONTAINER_TYPE == InfoHolder.CONTAIN_ICON_TEXT) {
                text = (TextView) itemView.findViewById(R.id.rit_text);
                icon = (ImageView) itemView.findViewById(R.id.rit_icon);
            }
            if (CONTAINER_TYPE == InfoHolder.CONTAIN_ICON_TEXT_ABOUT) {
                text = (TextView) itemView.findViewById(R.id.rita_text);
                icon = (ImageView) itemView.findViewById(R.id.rita_icon);
                about = (TextView) itemView.findViewById(R.id.rita_about);
            }
        }

        public void setClickListener(View.OnClickListener ocl) {
            parent.setOnClickListener(ocl);
        }
    }
}



