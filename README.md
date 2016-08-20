# Maps Demo

 You will create an app that displays the user's location on the map,
 allows the user to add markers to the map, and receives regular updates
 on the user's location.  The task is to read and complete each of the
 TODOs in the project.

 You will learn about maps, Google Play Services, and location APIs.
 The lab also offers a design pattern for handling the app's non-sequential
 execution flow, which is driven by callbacks.  You should think of this
 approach similar to the way spreadsheets work.  When one cell updates, all
 the cells that depend on it also update, and so on. See the comments above
 onCreate in MapsActivity for further explanation specific to this app.  The
 pattern is partly implemented with headless fragments, which are fragments
 without a view.

## Presentation slides
 https://docs.google.com/presentation/d/1MEs_JAyRC3J0YOX0bce1XBG42w5Q6rAvMvHb46yEbpo/edit?usp=sharing

## Helpful Links
 Setup troubleshooting: http://guides.codepath.com/android/Google-Maps-Fragment-Guide#troubleshooting
 
 Completed lab: https://github.com/boes-matt/android-google-maps-demo/tree/completed

 Maps guide: http://guides.codepath.com/android/Google-Maps-API-v2-Usage

 Google Play Services code reference: https://developers.google.com/android/reference/packages

 Android code reference: https://developer.android.com/reference/packages.html

## Running the app
 My advice is to use an actual device or the official Android emulator:
 http://guides.codepath.com/android/Google-Maps-Fragment-Guide#setup-x86-based-emulator

 But if using the Genymotion emulator, set up Google Play Services and GPS:
 http://guides.codepath.com/android/Genymotion-2.0-Emulators-with-Google-Play-support#setup-google-play-services

## Summary of TODOs
 I suggest you compile and run the project after completing each section.

* A1 Read instructions (MapsActivity)
* A2 Prepare Android Studio (MapsActivity)
* A3 Get Google Maps API key (AndroidManifest)
* A4 Check out the project's library dependencies (app/build.gradle)
* A5 Read what onCreate does (MapsActivity)  
* B1 Build GoogleApiClient (MapsActivity)
* B2 Get the map asynchronously (MapsActivity)
* B3 Register callbacks on the GoogleApiClient (MapsActivity)  
* C1 Enable location layer (action.AddLocationLayer)
* C2 Move map to current location (action.MoveToLocationFirstTime)
* C3 Build CameraPosition (action.MoveToLocationFirstTime)
* D1 Set long click listener (action.AddMarkerOnLongClick)
* D2 Show dialog (action.AddMarkerOnLongClick)
* D3 Build IconGenerator (MapsActivity)
* D4 Add marker (action.AddToMap)
* D5 Animate marker (action.AddToMap)
* E1 Build LocationRequest (MapsActivity)
* E2 Request location updates (action.TrackLocation)
* E3 Remove location updates (action.TrackLocation)
* E4 Log location updates (action.LogLocation)
* F1 Test user stories (MapsActivity)
* G1 Understand how OnActivity works (helper.OnActivity)
* G2 Understand how OnPermission works (helper.OnPermission)
* G3 Understand how PlaceManager works (helper.PlaceManager)
