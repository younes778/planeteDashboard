package d2si.apps.planetedashboard.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.List;


import d2si.apps.planetedashboard.R;

import static com.norbsoft.typefacehelper.TypefaceHelper.typeface;

/**
 * Adapter
 * <p>
 * RecyclerViewAdapter that represents the product adapter
 *
 * @author younessennadj
 */

public class ChoiceRecyclerAdapter extends RecyclerView.Adapter {
    private List<String> mDataSet = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context mContext;


    public ChoiceRecyclerAdapter(Context context, List<String> dataSet) {
        mContext = context;
        mDataSet = dataSet;
        mInflater = LayoutInflater.from(context);

        // uncomment if you want to open only one row at a time
        // binderHelper.setOpenOnlyOne(true);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_item_choice, parent, false);
        typeface(view);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder h, int position) {
        final ViewHolder holder = (ViewHolder) h;

        if (mDataSet != null && 0 <= position && position < mDataSet.size()) {
            final String data = mDataSet.get(position);

            // Bind your gnb.apps.salesdashboard.data here
            holder.bind(data);
        }
    }

    @Override
    public int getItemCount() {
        if (mDataSet == null)
            return 0;
        return mDataSet.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private CheckBox checkChoice;

        public ViewHolder(View itemView) {
            super(itemView);
            checkChoice = itemView.findViewById(R.id.check);

        }

        public void bind(final String data) {
            checkChoice.setText(data);

        }
    }
}