package com.bar.imageloader.processors;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.bar.imageloader.ImageRequest;
import com.bar.imageloader.filters.ImageFilter;

import java.util.List;

import pl.droidsonroids.gif.GifDrawable;

public class ExternalImageProcessor extends ImageFileProcessor {

    @Override
    public Drawable process(ImageRequest request, List<ImageFilter<Bitmap>> bitmapImageFilters) throws Exception {
        switch(request.getTargetImageType()){
            case GIF:
                return new GifDrawable(request.getOriginalRequestFile());
            case BITMAP:
            case SVG:
            default:
                return processBitmap(request,
                        request.getOriginalRequestFile(),
                        bitmapImageFilters);
        }
    }

}
