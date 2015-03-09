package recycler_handlers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import Utils.DatabaseAlarms;
import br.com.createlier.nana.nana.R;

/**
 * Created by dede on 22/02/2015.
 */
public class RITAOAdapter extends RecyclerView.Adapter<RITAOAdapter.CustomViewHolder> {
    private InfoHolder list;
    private Context mContext;

    public RITAOAdapter(InfoHolder list, Context context) {
        this.mContext = context;
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
    public void onBindViewHolder(CustomViewHolder holder, final int position) {
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

        if (current.isDeletable() && !current.haveAction())
            holder.setClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MaterialDialog.Builder md = new MaterialDialog.Builder(mContext)
                            .title("Deseja apagar este alarme?")
                            .content("Esta ação não pode ser desfeita.")
                            .positiveText(R.string.material_dialog_ok)
                            .negativeText(R.string.material_dialog_cancel)
                            .callback(new MaterialDialog.ButtonCallback() {
                                @Override
                                public void onPositive(MaterialDialog dialog) {
                                    DatabaseAlarms db = new DatabaseAlarms(mContext);
                                    db.deleteAlarm(position);
                                    db.close();
                                    list.removeInfoData(position);
                                    notifyItemRemoved(position);
                                }
                            });
                    md.show();
                }
            });
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

        public void setLongClickListener(View.OnLongClickListener lcl) {
            parent.setOnLongClickListener(lcl);
        }
    }
}



