package be.ehb.Todolo;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import be.ehb.Todolo.interfaces.AsyncJsonHttpResponse;

public class DownloadJsonAsynck extends AsyncTask<String, Void, String> {

    public AsyncJsonHttpResponse listener = null;

    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        listener.processFinish(result);
    }

    // call from https://stackoverflow.com/questions/33229869/get-json-data-from-url-using-android
    @Override
    protected String doInBackground(String... strings) {
        try {
            HttpURLConnection urlConnection = null;
            URL url = new URL(strings[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */ );
            urlConnection.setConnectTimeout(15000 /* milliseconds */ );
            urlConnection.setDoOutput(true);
            urlConnection.connect();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
            bufferedReader.close();
            return stringBuilder.toString();
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }


}
