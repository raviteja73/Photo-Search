package com.chsra.photosearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        String str = "Getting better doesn’t stop because it’s getting colder. The best athletes don’t just overcome the elements, they embrace them with Nike Hyperwarm. Gear up for winter: <a href=\"/l.php?u=http%3A%2F%2Fwww.nike.com%2Fhyperwarm&amp;h=4AQEBIq17&amp;s=1\" rel=\"nofollow nofollow\" target=\"_blank\" onmouseover=\"LinkshimAsyncLink.swap(this, &quot;http:\\/\\/www.nike.com\\/hyperwarm&quot;);\" onclick=\"LinkshimAsyncLink.swap(this, &quot;\\/l.php?u=http\u00253A\u00252F\u00252Fwww.nike.com\u00252Fhyperwarm&amp;h=4AQEBIq17&amp;s=1&quot;);\">http://www.nike.com/hyperwarm</a><br/><br/><a href=\"http://www.facebook.com/photo.php?v=10151882076318445\" id=\"\" title=\"\" target=\"\" onclick=\"\" style=\"\"><img class=\"img\" src=\"http://vthumb.ak.fbcdn.net/hvthumb-ak-ash3/t15/1095964_10151882078663445_10151882076318445_40450_2013_b.jpg\" alt=\"\" style=\"height:90px;\" /></a><br/><a href=\"http://www.facebook.com/photo.php?v=10151882076318445\" id=\"\" style=\"\">Winning in a Winter Wonderland</a>\"";
        Document doc = (Document) Jsoup.parse(str);
        setContentView(R.layout.activity_splash);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                finish();
            }
        }, 1500);
    }
}
