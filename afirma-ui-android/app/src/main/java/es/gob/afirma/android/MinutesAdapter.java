package es.gob.afirma.android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import es.gob.afirma.R;

public class MinutesAdapter extends BaseAdapter {

    private Context context;

    private ArrayList<Integer> minutesList;

    private int selectedPosition = -1;

    private static final String DEFAULT_MINUTES = "5";

    public MinutesAdapter(ArrayList<Integer> minutesList, Context context) {
        this.minutesList = minutesList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return minutesList.size();
    }

    @Override
    public Object getItem(int position) {
        return minutesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String minutes = String.valueOf(getItem(position));
        convertView = LayoutInflater.from(context).inflate(R.layout.minutes_group, null);
        TextView tvGroup = convertView.findViewById(R.id.minutesGroup);
        tvGroup.setText(minutes);
        if (DEFAULT_MINUTES.equals(minutes)) {
            convertView.findViewById(R.id.byDefaultTv).setVisibility(View.VISIBLE);
        }
        if (position == selectedPosition) {
            convertView.findViewById(R.id.minutesIndicatorImg).setVisibility(View.VISIBLE);
            convertView.findViewById(R.id.minutesGroupContent).setBackgroundResource(R.color.lightGray);
            selectedPosition = position;
        } else {
            convertView.findViewById(R.id.minutesIndicatorImg).setVisibility(View.GONE);
        }
        return convertView;
    }

    public void setSelectedPosition(int position) {
        selectedPosition = position;
        notifyDataSetChanged();
    }

    public int getSelectedPosition() {
        return this.selectedPosition;
    }
}
