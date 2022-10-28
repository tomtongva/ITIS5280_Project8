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
import com.example.itis5280_project8.util.Globals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
            BCBeacon three = new BCBeacon() {
                @Override
                public String getBeaconID() {
                    return null;
                }

                @Override
                public void setBeaconID(String s) {

                }

                @Override
                public String getSerialNumber() {
                    return null;
                }

                @Override
                public String getIdentifierData() {
                    return null;
                }

                @Override
                public String getModelNumber() {
                    return null;
                }

                @Override
                public String getName() {
                    return "B301";
                }

                @Override
                public void setName(String s) {

                }

                @Override
                public String getPeripheralIdentifier() {
                    return null;
                }

                @Override
                public void setPeripheralIdentifier(String s) {

                }

                @Override
                public String getMessage() {
                    return null;
                }

                @Override
                public Date getLastRangedAt() {
                    return null;
                }

                @Override
                public Date getLastUpdatedAt() {
                    return null;
                }

                @Override
                public String getWireframeURLString() {
                    return null;
                }

                @Override
                public String getProximityUUIDString() {
                    return null;
                }

                @Override
                public void setProximityUUIDString(String s) {

                }

                @Override
                public Integer getMajor() {
                    return null;
                }

                @Override
                public void setMajor(Integer integer) {

                }

                @Override
                public Integer getMinor() {
                    return null;
                }

                @Override
                public void setMinor(Integer integer) {

                }

                @Override
                public BCEddystone getEddystone() {
                    return null;
                }

                @Override
                public void setEddystone(BCEddystone bcEddystone) {

                }

                @Override
                public String getUptime() {
                    return null;
                }

                @Override
                public Double getAccuracy() {
                    return null;
                }

                @Override
                public void setAccuracy(Double aDouble) {

                }

                @Override
                public Integer getRSSI() {
                    return null;
                }

                @Override
                public void setRSSI(Integer integer) {

                }

                @Override
                public BCProximity getProximity() {
                    return BCProximity.BC_PROXIMITY_IMMEDIATE;
                }

                @Override
                public void setProximity(BCProximity bcProximity) {

                }

                @Override
                public BCCategory[] getCategories() {
                    return new BCCategory[0];
                }

                @Override
                public void setCategories(BCCategory[] bcCategories) {

                }

                @Override
                public BCCustomValue[] getCustomValues() {
                    return new BCCustomValue[0];
                }

                @Override
                public void setCustomValues(BCCustomValue[] bcCustomValues) {

                }

                @Override
                public BCMapPoint getMapPoint() {
                    return null;
                }

                @Override
                public BCBatteryStatus getBatteryStatus() {
                    return null;
                }

                @Override
                public Integer getLastKnownBatteryLevel() {
                    return null;
                }

                @Override
                public String getTeamID() {
                    return null;
                }

                @Override
                public BCSite getSite() {
                    return null;
                }

                @Override
                public String getSiteID() {
                    return null;
                }

                @Override
                public String getSiteName() {
                    return null;
                }

                @Override
                public void setSiteID(String s) {

                }

                @Override
                public String getBluetoothAddress() {
                    return null;
                }

                @Override
                public BCBeaconRegion getBeaconRegion() {
                    return null;
                }

                @Override
                public void setBeaconRegion(BCBeaconRegion bcBeaconRegion) {

                }

                @Override
                public BCBeaconMode getBeaconMode() {
                    return null;
                }

                @Override
                public void setBeaconMode(BCBeaconMode bcBeaconMode) {

                }

                @Override
                public BCBeaconLoudness getBeaconLoudness() {
                    return null;
                }

                @Override
                public void setBeaconLoudness(BCBeaconLoudness bcBeaconLoudness) {

                }

                @Override
                public BCTargetSpeed getTargetSpeed() {
                    return null;
                }

                @Override
                public void setTargetSpeed(BCTargetSpeed bcTargetSpeed) {

                }

                @Override
                public Integer getMeasuredPowerAt1Meter() {
                    return null;
                }

                @Override
                public void setMeasuredPowerAt1Meter(Integer integer) {

                }

                @Override
                public Integer getPrivacyDuration() {
                    return null;
                }

                @Override
                public void setPrivacyDuration(Integer integer) {

                }

                @Override
                public String getVersion() {
                    return null;
                }

                @Override
                public void setVersion(String s) {

                }

                @Override
                public String getPendingVersion() {
                    return null;
                }

                @Override
                public void setPendingVersion(String s) {

                }

                @Override
                public String getFirmwareVersion() {
                    return null;
                }

                @Override
                public String getLatestFirmwareVersion() {
                    return null;
                }

                @Override
                public void setLatestFirmwareVersion(String s) {

                }

                @Override
                public boolean isDiscovered() {
                    return false;
                }

                @Override
                public BCBeacon copy() {
                    return null;
                }

                @Override
                public void copyApiPropertiesFromBeacon(BCBeacon bcBeacon) {

                }

                @Override
                public void getLatestBeacon(BCBeaconCallback bcBeaconCallback) {

                }

                @Override
                public void getBeaconVersion(Integer integer, BCBeaconCallback bcBeaconCallback) {

                }

                @Override
                public void getLatestBeaconVersion(BCBeaconCallback bcBeaconCallback) {

                }

                @Override
                public void getBeaconModesFromApi(BCBeaconCallback bcBeaconCallback, Map<String, String> map) {

                }

                @Override
                public void getBeaconLoudnessesFromApi(BCBeaconCallback bcBeaconCallback, Map<String, String> map) {

                }

                @Override
                public void getBeaconTargetSpeedsFromApi(BCBeaconCallback bcBeaconCallback, Map<String, String> map) {

                }

                @Override
                public void getBeaconRegionsInTeamFromApi(BCBeaconCallback bcBeaconCallback) {

                }

                @Override
                public List<BCMeasurement> getMeasurementsOfType(BCMeasurementType bcMeasurementType) {
                    return null;
                }

                @Override
                public List<BCMeasurementType> getAvailableMeasurementTypes() {
                    return null;
                }

                @Override
                public void refreshBeacon(BCBeaconCallback bcBeaconCallback) {

                }

                @Override
                public void updateBeacon(BCBeaconCallback bcBeaconCallback) {

                }

                @Override
                public void moveBeaconToTeam(BCTeam bcTeam, BCSite bcSite, boolean b, BCBeaconCallback bcBeaconCallback) {

                }

                @Override
                public Date getCachedAt() {
                    return null;
                }

                @Override
                public Date getSyncedAt() {
                    return null;
                }

                @Override
                public BCSyncStatus getSyncStatus() {
                    return null;
                }

                @Override
                public boolean isSyncedOrRestored() {
                    return false;
                }

                @Override
                public boolean isNewBorn() {
                    return false;
                }

                @Override
                public boolean isIBeacon() {
                    return false;
                }

                @Override
                public boolean isEddystone() {
                    return false;
                }

                @Override
                public boolean isBlueCats() {
                    return false;
                }

                @Override
                public boolean isSecure() {
                    return false;
                }

                @Override
                public boolean isBlueCatsIdentifier() {
                    return false;
                }

                @Override
                public boolean isEddystoneUid() {
                    return false;
                }

                @Override
                public boolean isEddystoneUrl() {
                    return false;
                }

                @Override
                public boolean isBlueCatsIBeacon() {
                    return false;
                }

                @Override
                public boolean hasDiscoveredEddystoneUIDFrame() {
                    return false;
                }

                @Override
                public boolean hasDiscoveredEddystoneURLFrame() {
                    return false;
                }

                @Override
                public boolean hasDiscoveredEddystoneTelemetryFrame() {
                    return false;
                }

                @Override
                public String getLocalName() {
                    return null;
                }

                @Override
                public String getCacheIdentifier() {
                    return null;
                }

                @Override
                public Integer getMeasuredPowerAt1MeterFromAdData() {
                    return null;
                }

                @Override
                public boolean supportsButton() {
                    return false;
                }

                @Override
                public boolean hasRangingFlagSet() {
                    return false;
                }

                @Override
                public boolean hasOffloadFlagSet() {
                    return false;
                }

                @Override
                public boolean hasOnChargeFlagSet() {
                    return false;
                }

                @Override
                public boolean hasAlarmFlagSet() {
                    return false;
                }

                @Override
                public Integer getSourceType() {
                    return null;
                }

                @Override
                public void updateSettingsInBeacon(BCBeacon bcBeacon, BCBeaconCommandCallback bcBeaconCommandCallback) {

                }

                @Override
                public void updateFirmwareInBeacon(BCBeacon bcBeacon, String s, BCBeaconCommandCallback bcBeaconCommandCallback) {

                }

                @Override
                public void updateFirmwareInBeacon(BCBeacon bcBeacon, BCBeaconCommandCallback bcBeaconCommandCallback) {

                }

                @Override
                public void shutdownInBeacon(BCBeacon bcBeacon, BCBeaconCommandCallback bcBeaconCommandCallback) {

                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {

                }
            };
            beacons.add(three);

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
                    previousRegionProximity = currentBeacon.getProximity().getValue();
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