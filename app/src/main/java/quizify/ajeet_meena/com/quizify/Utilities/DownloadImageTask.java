package quizify.ajeet_meena.com.quizify.Utilities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Image Downloader with image url and image view
 */
public class DownloadImageTask extends AsyncTask<String, Void, Bitmap>
{
    CircularImageView bmImage;

    public DownloadImageTask(CircularImageView bmImage)
    {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls)
    {
        String urldisplay = urls[0];

        try
        {
            URL url = new URL(urldisplay);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }

    }

    protected void onPostExecute(Bitmap result)
    {
        bmImage.setImageBitmap(result);
    }


}