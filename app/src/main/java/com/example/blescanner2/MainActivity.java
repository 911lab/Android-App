package com.example.blescanner2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanRecord;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Vector;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {
    BluetoothAdapter mBluetoothAdapter;

    BluetoothLeScanner mBluetoothLeScanner;

    BluetoothLeAdvertiser mBluetoothLeAdvertiser;

    private static final int PERMISSIONS = 100;

    Vector<Beacon> beacon;

//    Beacon beacon;

    BeaconAdapter beaconAdapter;

    ListView beaconListView;
    TextView message;

    ScanSettings.Builder mScanSettings;

    List<ScanFilter> scanFilters;
    List<ScanFilter> scanFilters2;
    List<ScanFilter> scanFilters3;
    List<ScanFilter> scanFilters4;
    List<ScanFilter> scanFilters5;
    List<ScanFilter> scanFilters6;
    List<ScanFilter> scanFilters7;
    List<ScanFilter> scanFilters8;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss:SSS", Locale.KOREAN);

    ScanFilter scan;
    ScanFilter scan2;
    ScanFilter scan3;
    ScanFilter scan4;
    ScanFilter scan5;
    ScanFilter scan6;
    ScanFilter scan7;
    ScanFilter scan8;

    int count;

    private float tempAlpha;
    private int lossNum;

    private RequestQueue requestQueue;
    private StringRequest stringRequest;

    String url;

    double responseX;
    double responseY;

    int exitNum;

    int triangleNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this,
                new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.BLUETOOTH,
                        Manifest.permission.BLUETOOTH_ADMIN,
                        Manifest.permission.BLUETOOTH_SCAN,
                        Manifest.permission.BLUETOOTH_CONNECT}, PERMISSIONS);

        message = (TextView) findViewById(R.id.message);
        beaconListView = (ListView) findViewById(R.id.beaconListView);


        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mBluetoothLeScanner = mBluetoothAdapter.getBluetoothLeScanner();
        mBluetoothLeAdvertiser = mBluetoothAdapter.getBluetoothLeAdvertiser();
        beacon = new Vector<>();
        beacon.add(new Beacon("0",1,"1", 1));
        beacon.add(new Beacon("1",1,"1", 1));
        beacon.add(new Beacon("2",1,"1", 1));
        beacon.add(new Beacon("3",1,"1", 1));
        beacon.add(new Beacon("4",1,"1", 1));
        beacon.add(new Beacon("5",1,"1", 1));
        beacon.add(new Beacon("6",1,"1", 1));
        beacon.add(new Beacon("7",1,"1", 1));

        mScanSettings = new ScanSettings.Builder();

        // mScanSettings.setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY).setCallbackType(ScanSettings.CALLBACK_TYPE_ALL_MATCHES);

        // mScanSettings.setCallbackType(ScanSettings.CALLBACK_TYPE_ALL_MATCHES).setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY);

//        mScanSettings.setReportDelay(1).setCallbackType(ScanSettings.CALLBACK_TYPE_ALL_MATCHES).setNumOfMatches(ScanSettings.MATCH_NUM_ONE_ADVERTISEMENT);

        mScanSettings.setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY);
        // 얘는 스캔 주기를 2초로 줄여주는 Setting입니다.
        // 공식문서에는 위 설정을 사용할 때는 다른 설정을 하지 말고
        // 위 설정만 단독으로 사용하라고 되어 있네요 ^^
        // 위 설정이 없으면 테스트해 본 결과 약 10초 주기로 스캔을 합니다.
        ScanSettings scanSettings = mScanSettings.build();

        scanFilters = new Vector<>();
        scanFilters2 = new Vector<>();
        scanFilters3 = new Vector<>();
        scanFilters4 = new Vector<>();
        scanFilters5 = new Vector<>();
        scanFilters6 = new Vector<>();
        scanFilters7 = new Vector<>();
        scanFilters8 = new Vector<>();

        ScanFilter.Builder scanFilter = new ScanFilter.Builder();
        scanFilter.setDeviceAddress("DC:0D:30:00:1A:7C"); //ex) 00:00:00:00:00:00
        scan = scanFilter.build();

        ScanFilter.Builder scanFilter2 = new ScanFilter.Builder();
        scanFilter2.setDeviceAddress("DC:0D:30:00:1A:5E"); //ex) 00:00:00:00:00:00
        scan2 = scanFilter2.build();

        ScanFilter.Builder scanFilter3 = new ScanFilter.Builder();
        scanFilter3.setDeviceAddress("DC:0D:30:00:26:BD"); //ex) 00:00:00:00:00:00
        scan3 = scanFilter3.build();

        ScanFilter.Builder scanFilter4 = new ScanFilter.Builder();
        scanFilter4.setDeviceAddress("DC:0D:30:18:F2:B1"); //ex) 00:00:00:00:00:00
        scan4 = scanFilter4.build();

        ScanFilter.Builder scanFilter5 = new ScanFilter.Builder();
        scanFilter5.setDeviceAddress("DC:0D:30:18:F2:97"); //ex) 00:00:00:00:00:00
        scan5 = scanFilter5.build();

        ScanFilter.Builder scanFilter6 = new ScanFilter.Builder();
        scanFilter6.setDeviceAddress("DC:0D:30:18:F2:A9"); //ex) 00:00:00:00:00:00
        scan6 = scanFilter6.build();

        ScanFilter.Builder scanFilter7 = new ScanFilter.Builder();
        scanFilter7.setDeviceAddress("DC:0D:30:18:F2:A8"); //ex) 00:00:00:00:00:00
        scan7 = scanFilter7.build();

        ScanFilter.Builder scanFilter8 = new ScanFilter.Builder();
        scanFilter8.setDeviceAddress("DC:0D:30:18:F2:C0"); //ex) 00:00:00:00:00:00
        scan8 = scanFilter8.build();




        scanFilters.add(scan);
        scanFilters2.add(scan2);
        scanFilters3.add(scan3);
        scanFilters4.add(scan4);
        scanFilters5.add(scan5);
        scanFilters6.add(scan6);
        scanFilters7.add(scan7);
        scanFilters8.add(scan8);

        count = 0;

        try {
            mBluetoothLeScanner.startScan(scanFilters, scanSettings, mScanCallback);
            mBluetoothLeScanner.startScan(scanFilters2, scanSettings, mScanCallback2);
            mBluetoothLeScanner.startScan(scanFilters3, scanSettings, mScanCallback3);
            mBluetoothLeScanner.startScan(scanFilters4, scanSettings, mScanCallback4);
            mBluetoothLeScanner.startScan(scanFilters5, scanSettings, mScanCallback5);
            mBluetoothLeScanner.startScan(scanFilters6, scanSettings, mScanCallback6);
            mBluetoothLeScanner.startScan(scanFilters7, scanSettings, mScanCallback7);
            mBluetoothLeScanner.startScan(scanFilters8, scanSettings, mScanCallback8);
        } catch (SecurityException e) {
            e.printStackTrace();
        }

        // filter와 settings 기능을 사용하지 않을 때는
        //mBluetoothLeScanner.startScan(mScanCallback); //처럼 사용하시면 돼요.

        //RequestQueue 객체 생성
        requestQueue = Volley.newRequestQueue(this);    // this==getApplicationContext();

        // 서버에 요청할 주소
//        url = "http://192.168.103.195:8080/api/distance"; // 희진 폰 핫스팟 IP -> 테스트용
//        url = "http://172.20.10.6:8080/api/distance"; // 재혁 폰 핫스팟 IP -> 테스트용
        url = "http://113.198.245.104:8080/api/distance";    // 911 재혁 컴 IP

        Log.i("OnCreate Finish !!!!!!!!!!!", "OnCreate Finish !!!!!!!!!!!!");

        exitNum = 0;

        responseX= -1.0;
        responseY= -1.0;

}

    ScanCallback mScanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);

            try {
                Log.i("ScanCallback Start !!!!!!!!!!!!!!!!!!!!!!!", "ScanCallback Start !!!!!!!!!!!!!!!!!!!!!!!");
                ScanRecord scanRecord = result.getScanRecord();

                try {
//                    Log.d("getTxPowerLevel()", scanRecord.getTxPowerLevel() + "");
                    Log.d("onScanResult()", result.getDevice().getAddress() + "\n" + result.getRssi() + "\n" + calculateDistance(result.getRssi()) + "\n" + result.getDevice().getName()
                            + "\n" + result.getDevice().getBondState() + "\n" + result.getDevice().getType());
                } catch (SecurityException e) {
                    e.printStackTrace();
                }

                final ScanResult scanResult = result;

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                vectorClear();
                                beacon.set(0, new Beacon(scanResult.getDevice().getAddress(), scanResult.getRssi(), simpleDateFormat.format(new Date()), calculateDistance(scanResult.getRssi())));
                                Log.i("time", "Time : "+ beacon.get(0).getNow());

                                sendRequest(beacon);

                                if(responseX != -1 && responseY != -1) {
                                    exitNum = getExitNum();
                                }

                                if(exitNum != 0) {
                                    message.setText("\"가장 가까운 비상구는 " + exitNum + "번 비상구 입니다.\"");
//                                    message.setText("\"가장 가까운 비상구는 번 비상구 입니다.\"");
                                } else {
                                    message.setText("\"위치를 측정 중입니다...\"");
                                }
                                beaconAdapter = new BeaconAdapter(beacon, getLayoutInflater(), exitNum);
                                beaconListView.setAdapter(beaconAdapter);
                                beaconAdapter.notifyDataSetChanged();
//                                beacon.set(0, new Beacon("0",1,"1", 1));
                            }
                        });
                    }
                }).start();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            super.onBatchScanResults(results);

            Log.d("onBatchScanResults", results.size() + "");
            int i = 0;
            for (ScanResult result : results) {
                Log.d(Integer.toString(i), "onBatchScanResults: " + result.toString());
                i++;
            }
        }

        @Override
        public void onScanFailed(int errorCode) {
            super.onScanFailed(errorCode);
            Log.d("onScanFailed()", errorCode+"");
        }
    };


    ScanCallback mScanCallback2 = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);

            try {
                Log.i("ScanCallback Start !!!!!!!!!!!!!!!!!!!!!!!", "ScanCallback Start !!!!!!!!!!!!!!!!!!!!!!!");
                ScanRecord scanRecord = result.getScanRecord();

                try {
//                    Log.d("getTxPowerLevel()", scanRecord.getTxPowerLevel() + "");
                    Log.d("onScanResult()", result.getDevice().getAddress() + "\n" + result.getRssi() + "\n" + calculateDistance(result.getRssi()) + "\n" + result.getDevice().getName()
                            + "\n" + result.getDevice().getBondState() + "\n" + result.getDevice().getType());
                } catch (SecurityException e) {
                    e.printStackTrace();
                }


                final ScanResult scanResult = result;

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                vectorClear();
                                beacon.set(1, new Beacon(scanResult.getDevice().getAddress(), scanResult.getRssi(), simpleDateFormat.format(new Date()), calculateDistance(scanResult.getRssi())));
                                Log.i("time", "Time : "+ beacon.get(1).getNow());


                                sendRequest(beacon);
                                if(responseX != -1 && responseY != -1) {
                                    exitNum = getExitNum();
                                }

                                if(exitNum != 0) {
                                    message.setText("\"가장 가까운 비상구는 " + exitNum + "번 비상구 입니다.\"");
                                } else {
                                    message.setText("\"위치를 측정 중입니다...\"");
                                }
                                beaconAdapter = new BeaconAdapter(beacon, getLayoutInflater(), exitNum);
                                beaconListView.setAdapter(beaconAdapter);
                                beaconAdapter.notifyDataSetChanged();
//                                beacon.set(1, new Beacon("1",1,"1", 1));
                            }
                        });
                    }
                }).start();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            super.onBatchScanResults(results);

            Log.d("onBatchScanResults", results.size() + "");
            int i = 0;
            for (ScanResult result : results) {
                Log.d(Integer.toString(i), "onBatchScanResults: " + result.toString());
                i++;
            }
        }

        @Override
        public void onScanFailed(int errorCode) {
            super.onScanFailed(errorCode);
            Log.d("onScanFailed()", errorCode+"");
        }
    };




    ScanCallback mScanCallback3 = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);

            try {
                Log.i("ScanCallback Start !!!!!!!!!!!!!!!!!!!!!!!", "ScanCallback Start !!!!!!!!!!!!!!!!!!!!!!!");
                ScanRecord scanRecord = result.getScanRecord();

                try {
//                    Log.d("getTxPowerLevel()", scanRecord.getTxPowerLevel() + "");
                    Log.d("onScanResult()", result.getDevice().getAddress() + "\n" + result.getRssi() + "\n" + calculateDistance(result.getRssi()) + "\n" + result.getDevice().getName()
                            + "\n" + result.getDevice().getBondState() + "\n" + result.getDevice().getType());
                } catch (SecurityException e) {
                    e.printStackTrace();
                }


                final ScanResult scanResult = result;

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                vectorClear();
                                beacon.set(2, new Beacon(scanResult.getDevice().getAddress(), scanResult.getRssi(), simpleDateFormat.format(new Date()), calculateDistance(scanResult.getRssi())));
                                Log.i("time", "Time : "+ beacon.get(2).getNow());
                                sendRequest(beacon);
                                if(responseX != -1 && responseY != -1) {
                                    exitNum = getExitNum();
                                }

                                if(exitNum != 0) {
                                    message.setText("\"가장 가까운 비상구는 " + exitNum + "번 비상구 입니다.\"");
                                } else {
                                    message.setText("\"위치를 측정 중입니다...\"");
                                }
                                beaconAdapter = new BeaconAdapter(beacon, getLayoutInflater(), exitNum);
                                beaconListView.setAdapter(beaconAdapter);
                                beaconAdapter.notifyDataSetChanged();
//                                beacon.set(2, new Beacon("2",1,"1", 1));

                            }
                        });
                    }
                }).start();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            super.onBatchScanResults(results);

            Log.d("onBatchScanResults", results.size() + "");
            int i = 0;
            for (ScanResult result : results) {
                Log.d(Integer.toString(i), "onBatchScanResults: " + result.toString());
                i++;
            }
        }

        @Override
        public void onScanFailed(int errorCode) {
            super.onScanFailed(errorCode);
            Log.d("onScanFailed()", errorCode+"");
        }
    };


    ScanCallback mScanCallback4 = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);

            try {
                Log.i("ScanCallback Start !!!!!!!!!!!!!!!!!!!!!!!", "ScanCallback Start !!!!!!!!!!!!!!!!!!!!!!!");
                ScanRecord scanRecord = result.getScanRecord();

                try {
//                    Log.d("getTxPowerLevel()", scanRecord.getTxPowerLevel() + "");
                    Log.d("onScanResult()", result.getDevice().getAddress() + "\n" + result.getRssi() + "\n" + calculateDistance(result.getRssi()) + "\n" + result.getDevice().getName()
                            + "\n" + result.getDevice().getBondState() + "\n" + result.getDevice().getType());
                } catch (SecurityException e) {
                    e.printStackTrace();
                }


                final ScanResult scanResult = result;

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                vectorClear();
                                beacon.set(3, new Beacon(scanResult.getDevice().getAddress(), scanResult.getRssi(), simpleDateFormat.format(new Date()), calculateDistance(scanResult.getRssi())));
                                Log.i("time", "Time : "+ beacon.get(3).getNow());
                                sendRequest(beacon);
                                if(responseX != -1 && responseY != -1) {
                                    exitNum = getExitNum();
                                }

                                if(exitNum != 0) {
                                    message.setText("\"가장 가까운 비상구는 " + exitNum + "번 비상구 입니다.\"");
                                } else {
                                    message.setText("\"위치를 측정 중입니다...\"");
                                }
                                beaconAdapter = new BeaconAdapter(beacon, getLayoutInflater(), exitNum);
                                beaconListView.setAdapter(beaconAdapter);
                                beaconAdapter.notifyDataSetChanged();
//                                beacon.set(3, new Beacon("3",1,"1", 1));
                            }
                        });
                    }
                }).start();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            super.onBatchScanResults(results);

            Log.d("onBatchScanResults", results.size() + "");
            int i = 0;
            for (ScanResult result : results) {
                Log.d(Integer.toString(i), "onBatchScanResults: " + result.toString());
                i++;
            }
        }

        @Override
        public void onScanFailed(int errorCode) {
            super.onScanFailed(errorCode);
            Log.d("onScanFailed()", errorCode+"");
        }
    };

    ScanCallback mScanCallback5 = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);

            try {
                Log.i("ScanCallback Start !!!!!!!!!!!!!!!!!!!!!!!", "ScanCallback Start !!!!!!!!!!!!!!!!!!!!!!!");
                ScanRecord scanRecord = result.getScanRecord();

                try {
//                    Log.d("getTxPowerLevel()", scanRecord.getTxPowerLevel() + "");
                    Log.d("onScanResult()", result.getDevice().getAddress() + "\n" + result.getRssi() + "\n" + calculateDistance(result.getRssi()) + "\n" + result.getDevice().getName()
                            + "\n" + result.getDevice().getBondState() + "\n" + result.getDevice().getType());
                } catch (SecurityException e) {
                    e.printStackTrace();
                }


                final ScanResult scanResult = result;

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                vectorClear();
                                beacon.set(4, new Beacon(scanResult.getDevice().getAddress(), scanResult.getRssi(), simpleDateFormat.format(new Date()), calculateDistance(scanResult.getRssi())));
                                Log.i("time", "Time : "+ beacon.get(4).getNow());
                                sendRequest(beacon);
                                if(responseX != -1 && responseY != -1) {
                                    exitNum = getExitNum();
                                }

                                if(exitNum != 0) {
                                    message.setText("\"가장 가까운 비상구는 " + exitNum + "번 비상구 입니다.\"");
                                } else {
                                    message.setText("\"위치를 측정 중입니다...\"");
                                }
                                beaconAdapter = new BeaconAdapter(beacon, getLayoutInflater(), exitNum);
                                beaconListView.setAdapter(beaconAdapter);
                                beaconAdapter.notifyDataSetChanged();
//                                beacon.set(4, new Beacon("4",1,"1", 1));
                            }
                        });
                    }
                }).start();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            super.onBatchScanResults(results);

            Log.d("onBatchScanResults", results.size() + "");
            int i = 0;
            for (ScanResult result : results) {
                Log.d(Integer.toString(i), "onBatchScanResults: " + result.toString());
                i++;
            }
        }

        @Override
        public void onScanFailed(int errorCode) {
            super.onScanFailed(errorCode);
            Log.d("onScanFailed()", errorCode+"");
        }
    };

    ScanCallback mScanCallback6 = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);

            try {
                Log.i("ScanCallback Start !!!!!!!!!!!!!!!!!!!!!!!", "ScanCallback Start !!!!!!!!!!!!!!!!!!!!!!!");
                ScanRecord scanRecord = result.getScanRecord();

                try {
//                    Log.d("getTxPowerLevel()", scanRecord.getTxPowerLevel() + "");
                    Log.d("onScanResult()", result.getDevice().getAddress() + "\n" + result.getRssi() + "\n" + calculateDistance(result.getRssi()) + "\n" + result.getDevice().getName()
                            + "\n" + result.getDevice().getBondState() + "\n" + result.getDevice().getType());
                } catch (SecurityException e) {
                    e.printStackTrace();
                }


                final ScanResult scanResult = result;

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                vectorClear();
                                beacon.set(5, new Beacon(scanResult.getDevice().getAddress(), scanResult.getRssi(), simpleDateFormat.format(new Date()), calculateDistance(scanResult.getRssi())));
                                Log.i("time", "Time : "+ beacon.get(5).getNow());
                                sendRequest(beacon);
                                if(responseX != -1 && responseY != -1) {
                                    exitNum = getExitNum();
                                }

                                if(exitNum != 0) {
                                    message.setText("\"가장 가까운 비상구는 " + exitNum + "번 비상구 입니다.\"");
                                } else {
                                    message.setText("\"위치를 측정 중입니다...\"");
                                }
                                beaconAdapter = new BeaconAdapter(beacon, getLayoutInflater(), exitNum);
                                beaconListView.setAdapter(beaconAdapter);
                                beaconAdapter.notifyDataSetChanged();
//                                beacon.set(5, new Beacon("5",1,"1", 1));
                            }
                        });
                    }
                }).start();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            super.onBatchScanResults(results);

            Log.d("onBatchScanResults", results.size() + "");
            int i = 0;
            for (ScanResult result : results) {
                Log.d(Integer.toString(i), "onBatchScanResults: " + result.toString());
                i++;
            }
        }

        @Override
        public void onScanFailed(int errorCode) {
            super.onScanFailed(errorCode);
            Log.d("onScanFailed()", errorCode+"");
        }
    };


    ScanCallback mScanCallback7= new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);

            try {
                Log.i("ScanCallback Start !!!!!!!!!!!!!!!!!!!!!!!", "ScanCallback Start !!!!!!!!!!!!!!!!!!!!!!!");
                ScanRecord scanRecord = result.getScanRecord();

                try {
//                    Log.d("getTxPowerLevel()", scanRecord.getTxPowerLevel() + "");
                    Log.d("onScanResult()", result.getDevice().getAddress() + "\n" + result.getRssi() + "\n" + calculateDistance(result.getRssi()) + "\n" + result.getDevice().getName()
                            + "\n" + result.getDevice().getBondState() + "\n" + result.getDevice().getType());
                } catch (SecurityException e) {
                    e.printStackTrace();
                }


                final ScanResult scanResult = result;

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                vectorClear();
                                beacon.set(6, new Beacon(scanResult.getDevice().getAddress(), scanResult.getRssi(), simpleDateFormat.format(new Date()), calculateDistance(scanResult.getRssi())));
                                Log.i("time", "Time : "+ beacon.get(6).getNow());
                                sendRequest(beacon);
                                if(responseX != -1 && responseY != -1) {
                                    exitNum = getExitNum();
                                }

                                if(exitNum != 0) {
                                    message.setText("\"가장 가까운 비상구는 " + exitNum + "번 비상구 입니다.\"");
                                } else {
                                    message.setText("\"위치를 측정 중입니다...\"");
                                }
                                beaconAdapter = new BeaconAdapter(beacon, getLayoutInflater(), exitNum);
                                beaconListView.setAdapter(beaconAdapter);
                                beaconAdapter.notifyDataSetChanged();
//                                beacon.set(6, new Beacon("6",1,"1", 1));
                            }
                        });
                    }
                }).start();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            super.onBatchScanResults(results);

            Log.d("onBatchScanResults", results.size() + "");
            int i = 0;
            for (ScanResult result : results) {
                Log.d(Integer.toString(i), "onBatchScanResults: " + result.toString());
                i++;
            }
        }

        @Override
        public void onScanFailed(int errorCode) {
            super.onScanFailed(errorCode);
            Log.d("onScanFailed()", errorCode+"");
        }
    };

    ScanCallback mScanCallback8 = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);

            try {
                Log.i("ScanCallback Start !!!!!!!!!!!!!!!!!!!!!!!", "ScanCallback Start !!!!!!!!!!!!!!!!!!!!!!!");
                ScanRecord scanRecord = result.getScanRecord();

                try {
//                    Log.d("getTxPowerLevel()", scanRecord.getTxPowerLevel() + "");
                    Log.d("onScanResult()", result.getDevice().getAddress() + "\n" + result.getRssi() + "\n" + calculateDistance(result.getRssi()) + "\n" + result.getDevice().getName()
                            + "\n" + result.getDevice().getBondState() + "\n" + result.getDevice().getType());
                } catch (SecurityException e) {
                    e.printStackTrace();
                }


                final ScanResult scanResult = result;

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                vectorClear();
                                beacon.set(7, new Beacon(scanResult.getDevice().getAddress(), scanResult.getRssi(), simpleDateFormat.format(new Date()), calculateDistance(scanResult.getRssi())));
                                Log.i("time", "Time : "+ beacon.get(7).getNow());
                                sendRequest(beacon);
                                if(responseX != -1 && responseY != -1) {
                                    exitNum = getExitNum();
                                }

                                if(exitNum != 0) {
                                    message.setText("\"가장 가까운 비상구는 " + exitNum + "번 비상구 입니다.\"");
                                } else {
                                    message.setText("\"위치를 측정 중입니다...\"");
                                }
                                beaconAdapter = new BeaconAdapter(beacon, getLayoutInflater(), exitNum);
                                beaconListView.setAdapter(beaconAdapter);
                                beaconAdapter.notifyDataSetChanged();
//                                beacon.set(7, new Beacon("7",1,"1", 1));
                            }
                        });
                    }
                }).start();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            super.onBatchScanResults(results);

            Log.d("onBatchScanResults", results.size() + "");
            int i = 0;
            for (ScanResult result : results) {
                Log.d(Integer.toString(i), "onBatchScanResults: " + result.toString());
                i++;
            }
        }

        @Override
        public void onScanFailed(int errorCode) {
            super.onScanFailed(errorCode);
            Log.d("onScanFailed()", errorCode+"");
        }
    };





    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            mBluetoothLeScanner.stopScan(mScanCallback);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }


    public double calculateDistance(double tempRssi) {
        tempAlpha = -55;
        lossNum = 3;

        double distance = Math.pow(10, (tempAlpha-tempRssi)/(10*lossNum));

        return distance;
    }


    public void sendRequest(Vector<Beacon> beacons) {
        // 요청시 필요한 문자열 객체 생성  매개변수  4개(통신방식(get,post),요청url주소, new 리스너(익명클래스)-응답시필요한부분 작성함)
        stringRequest = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>(){
            // 응답데이터를 받아오는 곳
            // 응답시 데이터 받아오는 곳 - 통신이 잘됐다면 로그캣에서 확인하게출력함
            @Override
            public void onResponse(String response) {
                Log.v("resultValue",response);
                Log.v("resultValue", response.length()+"");         //응답글자 수 보여짐,
                if(response.length() > 0) {
                    //위치측위 성공 0은 실패
                    try {
                        JSONObject jsonObject = new JSONObject(response);   //response가 JSON타입이 아닐 수 있어서 예외처리 해주기
                        triangleNum = (int)jsonObject.getDouble("triangleNum");
                        responseX = jsonObject.getDouble("x");
                        responseY = jsonObject.getDouble("y");

                        //위치측위 성공시 먼 비콘 값들 삭제
                        partClear(triangleNum);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            // 서버와의 연동 에러시 출력
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Override //response를 UTF8로 변경해주는 소스코드
            protected com.android.volley.Response<String> parseNetworkResponse(NetworkResponse response) {
                try {
                    String utf8String = new String(response.data, "UTF-8");
                    return com.android.volley.Response.success(utf8String, HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    // log error
                    return com.android.volley.Response.error(new ParseError(e));
                } catch (Exception e) {
                    // log error
                    return Response.error(new ParseError(e));
                }
            }

            // 보낼 데이터를 저장하는 곳 해쉬맵에 저장해서 보냄 - key,value
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
//                String id = edt_login_id.getText().toString();
//                String pw = edt_login_pw.getText().toString();

//                String deviceName = json.getString("deviceName");
                String deviceName = "HJ";
//                String deviceName = "BG";

                String d1 = Double.toString(beacons.get(0).getDistance());
                String r1 = Double.toString(beacons.get(0).getRssi());

                String d2 = Double.toString(beacons.get(1).getDistance());
                String r2 = Double.toString(beacons.get(1).getRssi());

                String d3 = Double.toString(beacons.get(2).getDistance());
                String r3 = Double.toString(beacons.get(2).getRssi());

                String d4 = Double.toString(beacons.get(3).getDistance());
                String r4 = Double.toString(beacons.get(3).getRssi());

                String d5 = Double.toString(beacons.get(4).getDistance());
                String r5 = Double.toString(beacons.get(4).getRssi());

                String d6 = Double.toString(beacons.get(5).getDistance());
                String r6 = Double.toString(beacons.get(5).getRssi());

                String d7 = Double.toString(beacons.get(6).getDistance());
                String r7 = Double.toString(beacons.get(6).getRssi());

                String d8 = Double.toString(beacons.get(7).getDistance());
                String r8 = Double.toString(beacons.get(7).getRssi());

                params.put("deviceName", deviceName);
                params.put("distance1",d1);
                params.put("rssi1",r1);
                params.put("distance2",d2);
                params.put("rssi2",r2);
                params.put("distance3",d3);
                params.put("rssi3",r3);
                params.put("distance4",d4);
                params.put("rssi4",r4);
                params.put("distance5",d5);
                params.put("rssi5",r5);
                params.put("distance6",d6);
                params.put("rssi6",r6);
                params.put("distance7",d7);
                params.put("rssi7",r7);
                params.put("distance8",d8);
                params.put("rssi8",r8);


                // key값은 서버에서 지정한 name과 동일하게
                Log.d("params setting", params.toString());
                return params;

            }
        };
//        stringRequest.setTag("main");       //구분자 어떤클라이언트에서 요청했는지 나타냄 (중요하지않음)
        requestQueue.add(stringRequest);        //실행 요청 add에 담으면 자동요청

    }

    public void vectorClear() {
        beacon.clear();
        beacon.add(new Beacon("0",1,"1", 1));
        beacon.add(new Beacon("1",1,"1", 1));
        beacon.add(new Beacon("2",1,"1", 1));
        beacon.add(new Beacon("3",1,"1", 1));
        beacon.add(new Beacon("4",1,"1", 1));
        beacon.add(new Beacon("5",1,"1", 1));
        beacon.add(new Beacon("6",1,"1", 1));
        beacon.add(new Beacon("7",1,"1", 1));

    }


    public void partClear(int num) {
        for(int i=0; i<8; i++) {
            if(Math.abs(i-num) > 2) {
                String address = Integer.toString(i);
                beacon.set(i, new Beacon(address, 1, "1", 1));
            }
        }
    }

    public double setDistanceDeviation(double exitx, double exity, double x, double y) {
        return Math.sqrt( ( Math.pow( (x-exitx), 2) ) + ( Math.pow(y-exity, 2) ) );
    }

    public int getExitNum() {

        double exit1x = 0;
        double exit1y = 0;

        double exit2x = 30.0;
        double exit2y = 0;

        double exit1Dis = setDistanceDeviation(exit1x, exit1y, responseX, responseY);
        double exit2Dis = setDistanceDeviation(exit2x, exit2y, responseX, responseY);

        if(exit1Dis <= exit2Dis) {
            return 1;
        }else {
            return 2;
        }
        //return 1;
    }








}