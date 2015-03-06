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
    private InfoHolder list;

    public RITAOAdapter(InfoHolder list) {
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {
        return list.getInfoDatas().get(position).getLayoutType();
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        if (viewType == InfoHolder.CONTAIN_ONLY_TEXT) {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_divisor_text, parent, false);
        }
        if (viewType == InfoHolder.CONTAIN_ICON_TEXT) {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_icon_text, parent, false);
        }
        if (viewType == InfoHolder.CONTAIN_ICON_TEXT_ABOUT) {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_icon_text_about, parent, false);
        }
        if (viewType == InfoHolder.CONTAIN_ICON_EDIT || viewType == InfoHolder.CONTAIN_ICON_EDIT_TEXT) {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_icon_editable, parent, false);
        }
        return new CustomViewHolder(v, viewType);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        InfoData current = list.getInfoDatas().get(position);
        switch (current.getLayoutType()) {
            case InfoHolder.CONTAIN_ONLY_TEXT:
                holder.text.setText(current.getMainText());
                break;
            case InfoHolder.CONTAIN_ICON_EDIT:
                holder.icon.setImageResource(current.getIconPath());
                holder.eText.setHint(current.getMainText());
                break;
            case InfoHolder.CONTAIN_ICON_EDIT_TEXT:
                holder.icon.setImageResource(current.getIconPath());
                holder.eText.setHint(current.getMainText());
                holder.eText.setText(current.getOptionalText());
                break;
            case InfoHolder.CONTAIN_ICON_TEXT:
                holder.icon.setImageResource(current.getIconPath());
                holder.text.setHint(current.getMainText());
                break;
            case InfoHolder.CONTAIN_ICON_TEXT_ABOUT:
                holder.icon.setImageResource(current.getIconPath());
                holder.text.setText(current.getMainText());
                holder.about.setText(current.getOptionalText());
                break;
        }
        if (current.haveAction())
            holder.setClickListener(current.getOnClickListener());
    }

    @Override
    public int getItemCount() {
        return list.getInfoDatas().size();
    }

    public InfoHolder getList() {
        return list;
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
            if (CONTAINER_TYPE == InfoHolder.CONTAIN_ICON_EDIT || CONTAINER_TYPE == InfoHolder.CONTAIN_ICON_EDIT_TEXT) {
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



