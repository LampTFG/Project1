package utils.urlimageviewhelper;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.ScaleAnimation;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.project01.R;

public class MyAdapter extends ArrayAdapter<String> {

	Activity context;
    public MyAdapter(Context context) {
        super(context, 0);
        this.context = (Activity) context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView iv;
        if (convertView == null)
            convertView = context.getLayoutInflater().inflate(R.layout.image, null);

        iv = (ImageView)convertView.findViewById(R.id.image);
        
        iv.setAnimation(null);
        // yep, that's it. it handles the downloading and showing an interstitial image automagically.
        UrlImageViewHelper.setUrlDrawable(iv, getItem(position), R.drawable.transparent, new UrlImageViewCallback() {
            @Override
            public void onLoaded(ImageView imageView, Bitmap loadedBitmap, String url, boolean loadedFromCache) {
                if (!loadedFromCache) {
                    ScaleAnimation scale = new ScaleAnimation(0, 1, 0, 1, ScaleAnimation.RELATIVE_TO_SELF, .5f, ScaleAnimation.RELATIVE_TO_SELF, .5f);
                    scale.setDuration(140);
                    imageView.startAnimation(scale);
                }
            }
        });

        return convertView;
    }
}