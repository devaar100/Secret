package aarnavjindal.secret;

import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.IBinder;
import android.provider.MediaStore;

public class SecretService extends Service implements SensorEventListener {
    int i=0;
    public SecretService() {
    }
SensorManager sm;
    Sensor accSensor,proxSensor;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sm=(SensorManager)getSystemService(SENSOR_SERVICE);
        accSensor=sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        proxSensor=sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        sm.registerListener(this,accSensor, 1000);
        sm.registerListener(this,proxSensor,1000);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER&&(event.values[i]>15||event.values[i]<-15)) {
        work();
        }
        else if(event.sensor.getType()==Sensor.TYPE_PROXIMITY&&event.values[0]<5)
        {
work();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sm.unregisterListener(this,accSensor);
        sm.unregisterListener(this,proxSensor);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation==Configuration.ORIENTATION_LANDSCAPE)
            i=1;
        else
            i=0;
    }
    void work()
    {
        long t=System.currentTimeMillis();
        t%=8;
        Intent i=null;
        switch ((int)t)
        {
            case 0:
                i=new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/"));
                break;
            case 1:
                i = new Intent(MediaStore.INTENT_ACTION_MUSIC_PLAYER);
                break;
            case 2:
                i = new Intent(Intent.ACTION_VIEW,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            break;
            case 3:
            Uri uri = Uri.parse("http://www.google.com");
            i = new Intent(Intent.ACTION_VIEW, uri);
            break;
            case 4:
            i = new Intent("android.media.action.IMAGE_CAPTURE");
            break;
            case 5:
            i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("sms:"));
                break;
            case 6:
            i= new Intent(Intent.ACTION_DIAL);
                break;
            case 7:
                i= new Intent(Intent.ACTION_SEND);
                i.setData(Uri.parse("mailto:"));
                i.setType("text/plain");
        }
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
}
