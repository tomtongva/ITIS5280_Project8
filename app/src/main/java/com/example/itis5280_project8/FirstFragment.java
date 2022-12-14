package com.example.itis5280_project8;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bluecats.sdk.BCBatteryStatus;
import com.bluecats.sdk.BCBeacon;
import com.bluecats.sdk.BCBeaconCallback;
import com.bluecats.sdk.BCBeaconCommandCallback;
import com.bluecats.sdk.BCBeaconLoudness;
import com.bluecats.sdk.BCBeaconManager;
import com.bluecats.sdk.BCBeaconManagerCallback;
import com.bluecats.sdk.BCBeaconMode;
import com.bluecats.sdk.BCBeaconRegion;
import com.bluecats.sdk.BCCategory;
import com.bluecats.sdk.BCCustomValue;
import com.bluecats.sdk.BCEddystone;
import com.bluecats.sdk.BCMapPoint;
import com.bluecats.sdk.BCMeasurement;
import com.bluecats.sdk.BCSite;
import com.bluecats.sdk.BCTargetSpeed;
import com.bluecats.sdk.BCTeam;
import com.bluecats.sdk.BlueCatsSDK;
import com.example.itis5280_project8.apicalls.Item;
import com.example.itis5280_project8.apicalls.ItemResponse;
import com.example.itis5280_project8.apicalls.RetrofitInterface;
import com.example.itis5280_project8.databinding.FragmentFirstBinding;
import com.example.itis5280_project8.util.AddBeaconForTestingUtil;
import com.example.itis5280_project8.util.Globals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FirstFragment extends Fragment {
    public static String TAG = "Project8";

    private FragmentFirstBinding binding;

    RetrofitInterface retrofitInterface;
    Retrofit retrofit;

    ArrayList<Item> items = new ArrayList<>();
    LinearLayoutManager layoutManager;
    ItemsRecyclerViewAdapter itemsRecyclerViewAdapter;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("MissingPermission")
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);

        layoutManager = new LinearLayoutManager(getActivity());
        binding.recyclerView.setLayoutManager(layoutManager);

        itemsRecyclerViewAdapter = new ItemsRecyclerViewAdapter(items);
        binding.recyclerView.setAdapter(itemsRecyclerViewAdapter);

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });*/

        retrofit = new Retrofit.Builder()
                .baseUrl(Globals.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        getActivity().setTitle("All Aisle Items");
        getItems("");

        requestBlePermissions(getActivity(), 001);

        BlueCatsSDK.startPurringWithAppToken(getContext(), Globals.BlueCatsToken);
        final Map<String, String> options = new HashMap<>();
        options.put(BlueCatsSDK.BC_OPTION_CACHE_REFRESH_TIME_INTERVAL_IN_MILLISECONDS, "10");
        options.put(BlueCatsSDK.BC_OPTION_CACHE_SITES_NEARBY_BY_LOCATION, "true");
        options.put(BlueCatsSDK.BC_OPTION_DISCOVER_BEACONS_NEARBY, "true");
        options.put(BlueCatsSDK.BC_OPTION_MONITOR_ALL_AVAILABLE_REGIONS_ON_STARTUP, "true");
        BlueCatsSDK.setOptions(options);
        final BCBeaconManager beaconManager = new BCBeaconManager();
        beaconManager.registerCallback(mBeaconManagerCallback);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private static final String[] BLE_PERMISSIONS = new String[]{
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
    };

    private static final String[] ANDROID_12_BLE_PERMISSIONS = new String[]{
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.ACCESS_FINE_LOCATION,
    };

    public static void requestBlePermissions(Activity activity, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
            ActivityCompat.requestPermissions(activity, ANDROID_12_BLE_PERMISSIONS, requestCode);
        else
            ActivityCompat.requestPermissions(activity, BLE_PERMISSIONS, requestCode);
    }

    String previousBeaconName = "";
    int previousRegionProximity = 10; // a beacon only has 0, 1, and 2 (based on proximity enum)
    private final BCBeaconManagerCallback mBeaconManagerCallback = new BCBeaconManagerCallback() {

        private int findIndexOfBeacon(String name, List<BCBeacon> beacons) {
            int retIndex = -1;

            for (int i = 0; i < beacons.size(); ++i) {
                if (name.equals(beacons.get(i).getName())) {
                    retIndex = i;
                }
            }

            return retIndex;
        }

        private List<Integer> findAllIndices(List<BCBeacon> beacons, int proximityValue) {
            List<Integer> retList = new ArrayList<>();

            for (int i = 0; i < beacons.size(); ++i) {
                if (proximityValue == beacons.get(i).getProximity().getValue())
                    retList.add(i);
            }

            return retList;
        }

        @Override
        public void didRangeBlueCatsBeacons(List<BCBeacon> beacons) {
            for (BCBeacon beacon : beacons) {
                Log.d(TAG, "existing beacon " + beacon.getName() + " " + beacon.getProximity().getValue());
            }
            if (AddBeaconForTestingUtil.TESTING) {
                ArrayList<BCBeacon.BCProximity> proximities = new ArrayList<>();
                proximities.add(BCBeacon.BCProximity.BC_PROXIMITY_IMMEDIATE);
                proximities.add(BCBeacon.BCProximity.BC_PROXIMITY_NEAR);
                proximities.add(BCBeacon.BCProximity.BC_PROXIMITY_FAR);
                proximities.add(BCBeacon.BCProximity.BC_PROXIMITY_UNKNOWN);

                AddBeaconForTestingUtil.addToBeacons(beacons, "B301", proximities.get((new Random()).nextInt(4)));
                AddBeaconForTestingUtil.addToBeacons(beacons, "B201", proximities.get((new Random()).nextInt(4)));
                AddBeaconForTestingUtil.addToBeacons(beacons, "B101", proximities.get(0));
            }

            int prevBeaconIndex = findIndexOfBeacon(previousBeaconName, beacons); // find the previous beacon from the current beacons
            int prevBeaconCurrentProximityVal = -1;
            if (prevBeaconIndex > -1)
                prevBeaconCurrentProximityVal = beacons.get(prevBeaconIndex).getProximity().getValue(); // the current proximity of previous beacon to compare against all other beacon proximities

            List<Integer> beaconsWithSamePrevProximityVal = findAllIndices(beacons, prevBeaconCurrentProximityVal); // returns indexes from smallest to largest
            Collections.reverse(beaconsWithSamePrevProximityVal); // sort from largest index to smallest to prevent index out of bounds when removing beacon from beacons

            for (int index : beaconsWithSamePrevProximityVal) {
                Log.d(TAG, "beacon " + beacons.get(index).getName() + " " + beacons.get(index).getProximity().getValue());
                if (beacons.get(index).getProximity().getValue() >= previousRegionProximity) // remove a current beacon if it's proximity is >= the previous' proximity
                    beacons.remove(index);
            }

            boolean previousBeaconNameChanged = false; // flag to prevent reloading of images
            for (BCBeacon currentBeacon : beacons) {
                Log.d(TAG, currentBeacon.getName() + " proximity:" + currentBeacon.getProximity().getValue()
                        + " total array size:" + beacons.size());

                // reset the beacon (name AND proximity value) to compare all other beacons against
                // currentBeacon.getProximity().getValue() < previousRegionProximity ==> the very first time a beacon comes into range (i.e. the beacons proximity val < 10)
                // currentBeacon.getProximity().getValue() < prevBeaconCurrentProximityVal ==> compare a current beacon from the modidifed beacons list to the previous proximity val
                if (currentBeacon.getProximity().getValue() < previousRegionProximity || currentBeacon.getProximity().getValue() < prevBeaconCurrentProximityVal) {
                    if (!previousBeaconName.equalsIgnoreCase(currentBeacon.getName())) // set the flag so we don't reload images (causes image flickering)
                        previousBeaconNameChanged = true;

                    // now update the beacon the next scan needs to compare to
                    previousRegionProximity = prevBeaconCurrentProximityVal = currentBeacon.getProximity().getValue();
                    previousBeaconName = currentBeacon.getName();
                }

                Log.d(TAG, "current beacon " + previousBeaconName + " at proximity " + previousRegionProximity);
            }

            // retrigger get items server API and therefore retriggering reloading of images only if the beacon identifier/name has changed
            if (previousBeaconNameChanged) {
                getItems(Globals.blueCatsToRegionMap.get(previousBeaconName));
                getActivity().setTitle(Globals.blueCatsToRegionMap.get(previousBeaconName).toUpperCase(Locale.US) + " Aisle " + " Items");
                for (Item item : items) {
                    Log.d(TAG, item.getName() + " " + item.getPhoto());
                }
            } else {
                previousRegionProximity = prevBeaconCurrentProximityVal;
            }
            super.didRangeBlueCatsBeacons(beacons);
        }
    };

    // call API to get items from DB
    private void getItems(String region) {
        Log.d(TAG, "get items for " + region);
        Call<ItemResponse> call = retrofitInterface.getItems(region);
        call.enqueue(new Callback<ItemResponse>() {
            @Override
            public void onResponse(Call<ItemResponse> call, Response<ItemResponse> response) {
                ItemResponse itemResponse = response.body();

                if (itemResponse != null && itemResponse.getItemsArray() != null) {
//                    items = new ArrayList<>(Arrays.asList(itemResponse.getItemsArray()));
                    items.clear();
                    items.addAll(Arrays.asList(itemResponse.getItemsArray()));
                    Log.d(TAG, "items " + items.toString());
                    PutDataIntoRecyclerView(items);
//                    binding.textView.setText(items.get(0).getName());
                }
            }

            @Override
            public void onFailure(Call<ItemResponse> call, Throwable t) {
                Log.d(TAG, "Failed " + t.getMessage());
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void PutDataIntoRecyclerView(ArrayList<Item> itemList) {
//        itemsRecyclerViewAdapter = new ItemsRecyclerViewAdapter(itemList);

        Log.d(TAG, "items are " + itemList);
//        binding.recyclerView.setAdapter(itemsRecyclerViewAdapter);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                itemsRecyclerViewAdapter.notifyDataSetChanged();
            }
        });
    }

}