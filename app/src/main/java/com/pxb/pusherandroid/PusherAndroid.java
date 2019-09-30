package com.pxb.pusherandroid;

import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;
import com.pusher.client.channel.PusherEvent;
import com.pusher.client.connection.ConnectionEventListener;
import com.pusher.client.connection.ConnectionStateChange;
import com.pusher.client.connection.ConnectionState;

public class PusherAndroid {

    public void connectToPusher(String app_key, String channel_name, String event_name) {

        PusherOptions options = new PusherOptions().setCluster("eu");
        Pusher pusher = new Pusher(app_key, options);

        pusher.connect(new ConnectionEventListener() {
            @Override
            public void onConnectionStateChange(ConnectionStateChange change) {
                System.out.println("State changed to " + change.getCurrentState() +
                        " from " + change.getPreviousState());
            }

            @Override
            public void onError(String message, String code, Exception e) {
                System.out.println("There was a problem connecting!");
            }
        }, ConnectionState.ALL);

        Channel channel = pusher.subscribe(channel_name);

        channel.bind(event_name, new SubscriptionEventListener() {
            @Override
            public void onEvent(PusherEvent event) {
                System.out.println("Received event with data: " + event.toString());
            }
        });

    }
}