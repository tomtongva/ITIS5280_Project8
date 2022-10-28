package com.example.itis5280_project8.util;

import android.os.Parcel;

import com.bluecats.sdk.BCBatteryStatus;
import com.bluecats.sdk.BCBeacon;
import com.bluecats.sdk.BCBeaconCallback;
import com.bluecats.sdk.BCBeaconCommandCallback;
import com.bluecats.sdk.BCBeaconLoudness;
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

import java.util.Date;
import java.util.List;
import java.util.Map;

public class AddBeaconForTestingUtil {
    public static boolean TESTING = false;

    public static void addToBeacons(List<BCBeacon> beacons, String name, BCBeacon.BCProximity proximity) {
        BCBeacon newBeacon = new BCBeacon() {

            @Override
            public String getName() {
                return name;
            }

            @Override
            public void setName(String s) {
            }

            @Override
            public BCProximity getProximity() {
                return proximity;
            }

            @Override
            public void setProximity(BCProximity bcProximity) {

            }

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

        beacons.add(newBeacon);
    }
}
