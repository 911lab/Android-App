package com.example.blescanner2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Vector;



public class BeaconAdapter extends BaseAdapter {
    private Vector<Beacon> beacons;
//    private Beacon beacons;
    private LayoutInflater layoutInflater;

//    private double distance;

    private float tempAlpha;
    private int lossNum;

    private int exitNum;

    public BeaconAdapter(Vector<Beacon> beacons, LayoutInflater layoutInflater, int exitNum) {
        this.beacons = beacons;
        this.layoutInflater = layoutInflater;
        this.exitNum = exitNum;
    }

//    public BeaconAdapter(ArrayList<Beacon> beacons, LayoutInflater layoutInflater) {
//        this.beacons = beacons;
//        this.layoutInflater = layoutInflater;
//    }

    @Override
    public int getCount() {
        return beacons.size();
//        return 1;
    }

    @Override
    public Object getItem(int position) {
        return beacons.get(position);
//        return beacons;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BeaconHolder beaconHolder;
        if (convertView == null) {
            beaconHolder = new BeaconHolder();

//            beaconHolder.message = convertView.findViewById(R.id.message);

            convertView = layoutInflater.inflate(R.layout.item, parent, false);
            beaconHolder.address = convertView.findViewById(R.id.address);
            beaconHolder.rssi = convertView.findViewById(R.id.rssi);
            beaconHolder.distance = convertView.findViewById(R.id.distance);

            //beaconHolder.time = convertView.findViewById(R.id.time);
            convertView.setTag(beaconHolder);
        } else {
            beaconHolder = (BeaconHolder)convertView.getTag();
        }

//        beaconHolder.time.setText("시간 :" + beacons.get(position).getNow());
//        beaconHolder.address.setText("Beacon Num :"+ beacons.get(position).getAddress());

//        if(exitNum != 0) {
//            //beaconHolder.message.setText("\"가장 가까운 비상구는 " + exitNum + "번 비상구 입니다.\"");
//            beaconHolder.message.setText("\"가장 가까운 비상구는 번 비상구 입니다.\"");
//        } else {
//            beaconHolder.message.setText("\"위치를 측정 중입니다...\"");
//        }
        beaconHolder.address.setText("Beacon Num : "+ position);
        beaconHolder.rssi.setText("RSSI :"+beacons.get(position).getRssi() + "dBm");
        beaconHolder.distance.setText("Distance :" + String.format("%.2f", calculateDistance( beacons.get(position).getRssi())) + "m");



        return convertView;
    }

    public double calculateDistance(double tempRssi) {

        tempAlpha = -30;
        lossNum = 4;

        double distance = Math.pow(10, (tempAlpha-tempRssi)/(10*lossNum));

        return distance;
    }



    private class BeaconHolder {
        TextView address;
        TextView rssi;
        TextView distance;
        TextView time;
        TextView message;
    }
}
