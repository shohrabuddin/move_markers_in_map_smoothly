# move_markers_in_map_smoothly
From this tutorial you will learn how to move markers in google map smoothly in your android app.

## Project description

Sometimes you might need to update the location of something in google map and you have to show this update by moving the markers smoothly from one location to another. In this tutorial I will show you how to achieve this. 

I will fetch a list of car from a web service. All the cars will be initially displayed in a list view. If you select one car from the list then a map view will be opened. All the cars will be displayed in the map but the selected car will be focused by moving the camera to its position. 

Then each after 10 seconds I will update the lcoation of all cars by adding 0.00005 to the latitude and longitude of each car. I will use a Service and Broadcast to get this update data in every 10 seconds. I hope you have basic knowledge on Service and Broadcast. So I will not discuss on these two topics in details. 

**In real life scenario you have to call an API after every 10 seconds or so to get up-to-date data.** 

## How to move markers smoothly?

Update the position is not so complex. But if you want to show the users that the marker is moving from its current positino to the new position smoothly then its quite challeing and interesting. Here is the code snippet which can perform the smooth transition of markers in googl map:

```Java

   //This methos is used to move the marker of each car smoothly when there are any updates of their position
    public void animateMarker(final int position, final LatLng startPosition, final LatLng toPosition,
                              final boolean hideMarker) {


        final Marker marker = mMap.addMarker(new MarkerOptions()
                .position(startPosition)
                .title(mCarParcelableListCurrentLation.get(position).mCarName)
                .snippet(mCarParcelableListCurrentLation.get(position).mAddress)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.car)));


        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();

        final long duration = 1000;
        final Interpolator interpolator = new LinearInterpolator();

        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed
                        / duration);
                double lng = t * toPosition.longitude + (1 - t)
                        * startPosition.longitude;
                double lat = t * toPosition.latitude + (1 - t)
                        * startPosition.latitude;

                marker.setPosition(new LatLng(lat, lng));

                if (t < 1.0) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                } else {
                    if (hideMarker) {
                        marker.setVisible(false);
                    } else {
                        marker.setVisible(true);
                    }
                }
            }
        });
    }

```

You are welcome to clone the project and run it on your device. Have fun.
