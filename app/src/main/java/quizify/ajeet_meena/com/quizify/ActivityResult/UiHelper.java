package quizify.ajeet_meena.com.quizify.ActivityResult;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import quizify.ajeet_meena.com.quizify.Utilities.Message;

/**
 * Created by Ajeet Meena on 26-04-2015.
 */
public class UiHelper
{

    Context context;
    ImageView imageView;

    UiHelper(Context context, ImageView imageView)
    {
        this.context = context;
        this.imageView = imageView;
    }

    void LoadProfileImage()
    {
        new DownloadImageTask(imageView)
                .execute("https://graph.facebook.com/ajeet.meena1/picture");
    }


    public class DownloadImageTask extends AsyncTask<String, Void, Bitmap>
    {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage)
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
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (IOException e)
            {
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(Bitmap bitmap)
        {
            super.onPostExecute(bitmap);
            imageView.setImageBitmap(bitmap);
            if (bitmap == null)
                Message.message(context, "Null Image");
        }
    }

}
