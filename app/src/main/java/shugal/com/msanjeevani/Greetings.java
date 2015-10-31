package shugal.com.msanjeevani;

import android.content.Intent;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro2;

/**
 * Created by Deepinder on 10/31/2015.
 */
public class Greetings extends AppIntro2 {
    @Override
    public void init(Bundle savedInstanceState) {

        addSlide(SampleSlide.newInstance(R.layout.intro1));

        addSlide(SampleSlide.newInstance(R.layout.intro2));

        addSlide(SampleSlide.newInstance(R.layout.intro3));

        addSlide(SampleSlide.newInstance(R.layout.intro4));

        showDoneButton(true);

    }

    @Override
    public void onDonePressed() {
        Intent proceedToMain = new Intent(Greetings.this, MainActivity.class);
        startActivity(proceedToMain);
        finish();
    }
}
