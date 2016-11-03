package com.ihc.cefet.cidadealerta;

import com.stephentuso.welcome.WelcomeScreenBuilder;
import com.stephentuso.welcome.ui.WelcomeActivity;
import com.stephentuso.welcome.util.WelcomeScreenConfiguration;

/**
 * Created by izabellamelendezconigliaro on 30/09/16.
 */
public class IntroActivity  extends WelcomeActivity {

    @Override
    protected WelcomeScreenConfiguration configuration() {
        return new WelcomeScreenBuilder(this)
                .theme(R.style.CustomWelcomeScreenTheme)
                .defaultTitleTypefacePath("Montserrat-Bold.ttf")
                .defaultHeaderTypefacePath("Montserrat-Bold.ttf")
                .parallaxPage(R.layout.parallax_1, "Crie uma ocorrência", "Nos diga o que te incomoda na sua cidade e se conecte ao governo local", R.color.red, 0.2f, 2f)
                .parallaxPage(R.layout.parallax_2, "Se mantenha informado", "Receba notificações quando as suas ocorrências forem resolvidas", R.color.purple, 0.2f, 2f)
                .parallaxPage(R.layout.parallax_3, "Ajude a melhorar sua cidade", "Quando os cidadãos trabalham juntos, sua cidade fica ainda melhor!", R.color.greenish, 0.2f, 2f)
                .swipeToDismiss(true)
                .exitAnimation(android.R.anim.fade_out)
                .build();
    }


}