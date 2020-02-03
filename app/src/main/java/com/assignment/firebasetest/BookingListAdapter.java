package com.assignment.firebasetest;

import android.app.Person;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class BookingListAdapter extends ArrayAdapter<Booking> {
    private Context mContext;
    int mResource;

    public BookingListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Booking> objects) {
        super(context, resource, objects);
        this.mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String booking_type = getItem(position).getWash_class();
        String booking_date = getItem(position).getDate();
        String bookihn_time = getItem(position).getTime();

        Booking booking = new Booking(booking_type,booking_date, bookihn_time);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView textView = convertView.findViewById(R.id.booking_info);
        TextView textView1 = convertView.findViewById(R.id.text_date);
        TextView textView2 = convertView.findViewById(R.id.text_timw);

        textView.setText(booking_type);
        textView1.setText(booking_date);
        textView2.setText(bookihn_time);

        return convertView;
    }
}
