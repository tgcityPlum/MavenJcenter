package com.tgcity.function.banner;


import android.support.v4.view.ViewPager.PageTransformer;

import com.tgcity.function.banner.transformer.AccordionTransformer;
import com.tgcity.function.banner.transformer.BackgroundToForegroundTransformer;
import com.tgcity.function.banner.transformer.CubeInTransformer;
import com.tgcity.function.banner.transformer.CubeOutTransformer;
import com.tgcity.function.banner.transformer.DefaultTransformer;
import com.tgcity.function.banner.transformer.DepthPageTransformer;
import com.tgcity.function.banner.transformer.FlipHorizontalTransformer;
import com.tgcity.function.banner.transformer.FlipVerticalTransformer;
import com.tgcity.function.banner.transformer.ForegroundToBackgroundTransformer;
import com.tgcity.function.banner.transformer.RotateDownTransformer;
import com.tgcity.function.banner.transformer.RotateUpTransformer;
import com.tgcity.function.banner.transformer.ScaleInOutTransformer;
import com.tgcity.function.banner.transformer.StackTransformer;
import com.tgcity.function.banner.transformer.TabletTransformer;
import com.tgcity.function.banner.transformer.ZoomInTransformer;
import com.tgcity.function.banner.transformer.ZoomOutSlideTransformer;
import com.tgcity.function.banner.transformer.ZoomOutTranformer;

public class Transformer {
    public static Class<? extends PageTransformer> Default = DefaultTransformer.class;
    public static Class<? extends PageTransformer> Accordion = AccordionTransformer.class;
    public static Class<? extends PageTransformer> BackgroundToForeground = BackgroundToForegroundTransformer.class;
    public static Class<? extends PageTransformer> ForegroundToBackground = ForegroundToBackgroundTransformer.class;
    public static Class<? extends PageTransformer> CubeIn = CubeInTransformer.class;
    public static Class<? extends PageTransformer> CubeOut = CubeOutTransformer.class;
    public static Class<? extends PageTransformer> DepthPage = DepthPageTransformer.class;
    public static Class<? extends PageTransformer> FlipHorizontal = FlipHorizontalTransformer.class;
    public static Class<? extends PageTransformer> FlipVertical = FlipVerticalTransformer.class;
    public static Class<? extends PageTransformer> RotateDown = RotateDownTransformer.class;
    public static Class<? extends PageTransformer> RotateUp = RotateUpTransformer.class;
    public static Class<? extends PageTransformer> ScaleInOut = ScaleInOutTransformer.class;
    public static Class<? extends PageTransformer> Stack = StackTransformer.class;
    public static Class<? extends PageTransformer> Tablet = TabletTransformer.class;
    public static Class<? extends PageTransformer> ZoomIn = ZoomInTransformer.class;
    public static Class<? extends PageTransformer> ZoomOut = ZoomOutTranformer.class;
    public static Class<? extends PageTransformer> ZoomOutSlide = ZoomOutSlideTransformer.class;
}
