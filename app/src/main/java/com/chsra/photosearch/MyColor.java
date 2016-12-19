package com.chsra.photosearch;

import android.graphics.Color;

public class MyColor {


    public MyColor()
    {

    }

    public static int getColor(String alphabet)
    {
        int c=0;
        switch(alphabet) {
            case "a":
                c = Color.RED;
                break;
            case "b":
                c = Color.GREEN;
                break;
            case "c":
                c = Color.BLACK;
                break;
            case "d":
               c = Color.MAGENTA;
                break;
            case "e":
                c = Color.YELLOW;
                break;
            case "f":
               c= Color.CYAN;
                break;
            case "g":
                c =  Color.DKGRAY;
                break;
            case "h":
                c = Color.LTGRAY;
                break;
            case "i":
               c = Color.BLUE;
                break;
            case "j":
                c = R.color.hintTextColor;
                break;
            case "k":
                c =  R.color.cardview_light_background;
                break;
            case "l":
                c =  R.color.colorPrimary;
                break;
            case "m":
                c = Color.YELLOW;
                break;
            case "n":
                c =  R.color.myLightColor;
                break;
            case "o":
                c = Color.YELLOW;
                break;
            case "p":
                c = Color.GREEN;
                break;
            case "q":
                c =  Color.GREEN;
                break;
            case "r":
                c = Color.GRAY;
                break;
            case "s":
                c =  Color.MAGENTA;
                break;
            case "t":
                c = Color.YELLOW;
                break;
            case "u":
                c = R.color.colorPrimaryDark;
                break;
            case "v":
                c = Color.BLACK;
                break;
            case "w":
                c =  R.color.cardview_shadow_start_color;
                break;
            case "x":
                c = Color.BLACK;
                break;
            case "y":
              c = Color.BLACK;
                break;
            case "z":
                c =  Color.BLACK;
                break;

        }
        return c;

    }
}
