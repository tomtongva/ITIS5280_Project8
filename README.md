# Project 8: ITIS 5280
## UNC Charlotte | Advanced Mobile Application Development
### Members:
- Alex Miller
- Tom Va
- Jared Tamulynas

## Implementation
APIs:
	- Get Aisle Items: /api/getItems <br />
		- Get items by region/aisle: i.e. grocery, produce, lifestyle <br />

## Postman
Get Aisle Items <br />
https://project8-5280.herokuapp.com/api/getitems <br />
set header key region to one of these values: grocery, produce, or lifestyle. <br />

## Video
https://www.youtube.com/watch?v=SlsKu8G6cd8

## Project Purpose

This assignment focuses on using bluetooth beacons to provide indoor proximity information in a grocery store environment. The app you are going to work on assumes a grocery store which has several aisles on which different products are placed. Bluetooth Beacons will places on each of the store’s aisles. The app should provide the user a context aware customized list of discounted products for the aisle closest to the user. The below figure shows the layout of the store and the location of the different beacons.

store_layout.png

Figure 1, the store layout.

 

API requirements:

You are provided with a “discount.json” file, which includes the list of items that are discounted in the different regions. Download the fileDownload Download the file.
Create the required DB schema to store and manage the product discount information. Import the provided JSON file as part of your DB schema.
Import the provided photo files so that they are accessible through the server.
Setup your server to provide the an API that enables the fetching of discounted products for a specific region.
 

Application Requirements:

The application consists of a single window that enables the user to view the discounted items around him while shopping in the store. The list of discounted items should show the item name, item image, discount, price and region.
Create a new application, download and setup the BlueCats SDK.
Please use the following AndroidManifest.xml Download AndroidManifest.xmlas an example to show you the required permissions and the android:usesCleartextTraffic="true" attribute.
When implementing the BCBeaconManagerCallback, we are only using the didRangeBlueCatsBeacons method to discover BlueCatsBeacons. Use the Beacon Name to identify your beacons, and proximity to identify how close the beacon is to the phone.
The app will require an app token below are the app tokens for each group:
Group 1 Token : 23bb6bd1-cff2-46e7-b6cc-bc53f865f236
Group 2 Token : 06e8c088-fae4-419c-aeb6-c56e8def1c42 
The regions include, "grocery", "lifesytle" and "produce". You are provided with BLE beacons which you should assign each beacon to represent a region. Please note that the provided discount information provides a region attribute, which describes the region that a specific product is located.
When the user is in the store, the app should locate the closest beacon and present only the products for the region belonging to the closest beacon.
As the user moves the app should contact the api to get the list of discounted items in the closest region, and the list should be refreshed to show the retrieved list of discounted products for the closest region.
Your application should avoid oscillating between regions, which is when the app during a scan assumes region 1 then in the next scan assumes region 2, and then region 1. This case might happen when the user is equidistant from multiple beacons or due to errors in the distance estimations. This will affect the user experience and your app should present a usable solution to this problem.
If the app is unable to locate any beacons it should display all the discounted products sorted by region.
 

Submission should include:

Create a Github or Bitbucket repo for the assignment.
Push your code to the created repo. Should contain both the mobile and web code. 
On the same repo create a wiki page describing your api design and implementation. The wiki page should describe the API routes, DB Schema and all the assumptions required to provide authentication. In addition describe any data that is stored on the device or on the server.
Include the Postman file in the repo.
The API should be demonstrated using Postman, you should create an api component in Postman for each of your created APIs.
Demo your App and record an app screencast showing the different app features. Your video should be posted on Github and included with your submission. You should demo the following:
The ability to show the discounted products for a specific region when the user enters that region.
Show that the app does not oscillate back and forth between regions when transitioning or moving between regions.
