package com.example.alyss.memory;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ProgressBar;
import java.util.ArrayList;

/**
 * Created by jazz on 16/11/15.
 */



public class JeuView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    //setting

   /* SharedPreferences prefs = getString(String key, String defValue)
    SharedPreferences.Editor editor = prefs.edit();*/



   static ScoreActivity sc = new ScoreActivity();

    Initialisation init = new Initialisation();
    ArrayList liste = new ArrayList();
    ArrayList liste_memo = new ArrayList();
    ProgressBar mProgressBar;
    CountDownTimer mCountDownTimer;
    Timer stat = new Timer();

        // Declaration des images
        private Bitmap 		vide;
        private Bitmap 	zone;
        private Bitmap[] 	anime_clear= new Bitmap[20];
    private Bitmap[] 	anime_color= new Bitmap[20];
    private Bitmap 	card_serpent;
    private Bitmap 	card_rat;
    private Bitmap 	card_chien;
    private Bitmap 	card_dragon;
    private Bitmap 	card_singe;
    private Bitmap 	card_cochon;
    private Bitmap 	card_vache;
    private Bitmap 	card_tigre;
    private Bitmap 	card_chevre;
    private Bitmap 	card_coq;
    private Bitmap fond;

        private Bitmap 		win;
        private Bitmap 		lose;

        // Declaration des objets Ressources et Context permettant d'acc der aux ressources de notre application et de les charger
        private Resources mRes;
        private Context mContext;

        // tableau modelisant la carte du jeu
        int[][] carte;
        int taille=5;


        // ancres pour pouvoir centrer la carte du jeu
        int        carteTopAnchor;                   // coordonn es en Y du point d'ancrage de notre carte
        int        carteLeftAnchor;                  // coordonn es en X du point d'ancrage de notre carte

        // taille de la carte
        static final int    carteWidth    = 5;
        static final int    carteHeight   = 5;
        static final int    carteTileSize = 225;
        static int cpt_anime=0;
        static int cpt_anime2=0;
        static int cpt_anime3=0;
        static int cpt_click=0;
        static int cpt_click2=0;
        static int cpt_next=0;
        static int cpt_win=0;
        static int carte_tmp=0;
        static int lock=0;
        static int lock2=0;
        static int score_fin=0;
    static  int perdu = 0;
    static  int gagner = 0;
    static int again =0;


        private long duree = 30000;
        static int cpt_time=0;
        static int score=30;
    static int cpt_thread=0;


        // constante modelisant les differentes types de cases
        static final int    CST_zone      = 3;
        static final int    CST_vide      = 4;
        static final int    CST_anime_clear      =5;
        static final int    CST_card_rat      = 6;
        static final int    CST_card_serpent      = 7;
        static final int    CST_card_chien      = 8;
        static final int    CST_card_dragon      = 9;
        static final int    CST_card_chevre      = 10;
        static final int    CST_card_cochon      = 11;
        static final int    CST_card_coq      = 12;
        static final int    CST_card_tigre      = 13;
        static final int    CST_card_vache      = 14;
        static final int    CST_card_singe      = 15;
        static final int    CST_anime_color      =16;

        // tableau de reference du terrain
        int [][] ref    = {

                {CST_vide, CST_zone, CST_zone,CST_zone,CST_zone},
                {CST_vide, CST_zone, CST_zone,CST_zone,CST_zone},
                {CST_vide, CST_zone, CST_zone,CST_zone,CST_zone},
                {CST_vide, CST_zone, CST_zone,CST_zone,CST_zone},
                {CST_vide, CST_zone, CST_zone,CST_zone,CST_zone}
        };

    int [][] ref_init    = {

            {CST_vide, CST_zone, CST_zone,CST_zone,CST_zone},
            {CST_vide, CST_zone, CST_zone,CST_zone,CST_zone},
            {CST_vide, CST_zone, CST_zone,CST_zone,CST_zone},
            {CST_vide, CST_zone, CST_zone,CST_zone,CST_zone},
            {CST_vide, CST_zone, CST_zone,CST_zone,CST_zone}
    };

    int [][] ref2    = {

            {CST_vide, CST_zone, CST_zone,CST_zone,CST_zone},
            {CST_vide, CST_zone, CST_zone,CST_zone,CST_zone},
            {CST_vide, CST_zone, CST_zone,CST_zone,CST_zone},
            {CST_vide, CST_zone, CST_zone,CST_zone,CST_zone},
            {CST_vide, CST_zone, CST_zone,CST_zone,CST_zone}
    };


    int [][] card_init    = {

            {-1, 0, 0,0,0},
            {-1, 0, 0,0,0},
            {-1, 0, 0,0,0},
            {-1, 0, 0,0,0},
            {-1, 0, 0,0,0}
    };

    int [][] card    = {

            {-1, 0, 0,0,0},
            {-1, 0, 0,0,0},
            {-1, 0, 0,0,0},
            {-1, 0, 0,0,0},
            {-1, 0, 0,0,0}
    };


    int xCard = 0;
    int yCard = 0;



        /* compteur et max pour animer les zones d'arriv e des diamants */
        int currentStepZone = 0;
        int currentStepZone2 = 0;

        // thread utiliser pour animer les zones de depot des diamants
        private     boolean in      = true;
        private     Thread  cv_thread;
        SurfaceHolder holder;

        Paint paint;

        /**
         * The constructor called from the main JetBoy activity
         *
         * @param context
         * @param attrs
         */
        public JeuView(Context context, AttributeSet attrs) {
            super(context, attrs);

            mProgressBar = (ProgressBar) findViewById(R.id.pBAsync);

            // permet d'ecouter les surfaceChanged, surfaceCreated, surfaceDestroyed
            holder = getHolder();
            holder.addCallback(this);

            // chargement des images
            mContext	= context;
            mRes 		= mContext.getResources();

            zone 	= BitmapFactory.decodeResource(mRes, R.mipmap.full);
            anime_clear[0] 	= BitmapFactory.decodeResource(mRes, R.drawable.clear1);
            anime_clear[1] 	= BitmapFactory.decodeResource(mRes, R.drawable.clear2);
            anime_clear[2] 	= BitmapFactory.decodeResource(mRes, R.drawable.clear3);
            anime_clear[3] 	= BitmapFactory.decodeResource(mRes, R.drawable.clear4);
            anime_clear[4] 	= BitmapFactory.decodeResource(mRes, R.drawable.clear5);
            anime_clear[5] 	= BitmapFactory.decodeResource(mRes, R.drawable.clear6);

            anime_color[0] 	= BitmapFactory.decodeResource(mRes, R.mipmap.color1);
            anime_color[1] 	= BitmapFactory.decodeResource(mRes, R.mipmap.color2);
            anime_color[2] 	= BitmapFactory.decodeResource(mRes, R.mipmap.color3);
            anime_color[3] 	= BitmapFactory.decodeResource(mRes, R.mipmap.color4);


            card_rat= BitmapFactory.decodeResource(mRes, R.drawable.rat);
            card_serpent= BitmapFactory.decodeResource(mRes, R.drawable.serpent);
            card_singe= BitmapFactory.decodeResource(mRes, R.drawable.singe);
            card_chevre= BitmapFactory.decodeResource(mRes, R.drawable.chevre);
            card_chien= BitmapFactory.decodeResource(mRes, R.drawable.chien);
            card_cochon= BitmapFactory.decodeResource(mRes, R.drawable.cochon);
            card_coq= BitmapFactory.decodeResource(mRes, R.drawable.coq);
            card_tigre= BitmapFactory.decodeResource(mRes, R.drawable.tigre);
            card_dragon= BitmapFactory.decodeResource(mRes, R.drawable.dragon);
            card_vache= BitmapFactory.decodeResource(mRes, R.drawable.vache);

            vide 		= BitmapFactory.decodeResource(mRes, R.mipmap.vide);
            fond        = BitmapFactory.decodeResource(mRes, R.drawable.fondjeu);
            win 		= BitmapFactory.decodeResource(mRes, R.drawable.win);
            lose 		= BitmapFactory.decodeResource(mRes, R.drawable.lose);
            // initialisation des parmametres du jeu
            initparameters();

            // creation du thread
            cv_thread   = new Thread(this);
            // prise de focus pour gestion des touches
            setFocusable(true);


        }

        // chargement du niveau a partir du tableau de reference du niveau
        public void loadlevel() {
            ref[0][0]=CST_vide;
            for (int i=0; i< carteHeight; i++) {
                for (int j=0; j< carteWidth; j++) {
                    carte[j][i]= ref[j][i];
                }
            }
        }

    public void reloadcarte() {
        ref[0][0]=CST_vide;
        for (int i=1; i< carteHeight; i++) {
            for (int j=0; j< carteWidth; j++) {
                carte[j][i]= CST_zone;
                ref[j][i]= CST_zone;
                card[j][i]=0;
            }
        }
    }


        public void map_alea(){
        liste = init.init();
        int cpt=0;
        for(int i=0;i<taille;i++) {
            for (int j = 1; j < taille; j++) {

                ref2[i][j]=(int)liste.get(cpt);
                cpt++;
            }
        }
    }


    public void reload (){

        stat.block(2);
    //    stat.block(0);
        currentStepZone = 0;
        currentStepZone2 = 0;
        xCard = 0;
        yCard = 0;
        cpt_anime=0;
        cpt_anime2=0;
        cpt_anime3=0;
        cpt_click2=1;
        cpt_click=0;
        cpt_next=0;
        cpt_win=0;
        carte_tmp=0;
        lock=0;
        lock2=0;
      /*  ref=ref_init;
        card=card_init;*/
        map_alea();
        //loadlevel();
        reloadcarte();
        setScore();
        if(perdu==1){
            //Thread.currentThread().interrupt();
            cpt_click=0;
            cpt_click2=0;
            stat.block(0);

            paint = new Paint();
            paint.setColor(0xff0000);

            paint.setDither(true);
            paint.setColor(0xFFFFFF00);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setStrokeWidth(3);
            paint.setTextAlign(Paint.Align.LEFT);
            carte           = new int[carteHeight][carteWidth];
            loadlevel();
            carteTopAnchor  = (getHeight()- carteHeight*carteTileSize+550)/2;
            carteLeftAnchor = (getWidth()- carteWidth*carteTileSize-150)/2;

            perdu=0;
        }

    }

        // initialisation du jeu
        public void initparameters() {
            mProgressBar = (ProgressBar) findViewById(R.id.pBAsync);
           map_alea();

            paint = new Paint();
            paint.setColor(0xff0000);

            paint.setDither(true);
            paint.setColor(0xFFFFFF00);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setStrokeWidth(3);
            paint.setTextAlign(Paint.Align.LEFT);
            carte           = new int[carteHeight][carteWidth];
            loadlevel();
            carteTopAnchor  = (getHeight()- carteHeight*carteTileSize+550)/2;
            carteLeftAnchor = (getWidth()- carteWidth*carteTileSize-150)/2;
            Log.i("-> FCT <-", "up "+ carteTopAnchor +" -  left "+ carteLeftAnchor);

            if ((cv_thread!=null) && (!cv_thread.isAlive())) {
                cv_thread.start();
                Log.e("-FCT-", "cv_thread.start()");
            }
        }



     void paintscore_finale(Canvas canvas){

        Paint paint = new Paint();
        paint.setDither(true);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);

        paint.setTextAlign(Paint.Align.CENTER);

         score_final(stat.status_time(), score);

        if(getHeight()==2460) {
            paint.setTextSize(250);
            canvas.drawText("Score : " + score_fin, carteLeftAnchor + (3 * carteTileSize), carteTopAnchor + -1 * (2 * carteTileSize), paint);
        }else if(getHeight()>=1800){
            paint.setTextSize(200);
            canvas.drawText("" + score, carteLeftAnchor + (11 * carteTileSize) / 4, carteTopAnchor + -1 * (3 * carteTileSize) / 2, paint);

        }else{
            paint.setTextSize(200);
            canvas.drawText("" + score, carteLeftAnchor + (11 * carteTileSize) / 4, carteTopAnchor + -1 * (2 * carteTileSize) / 2, paint);

        }

        //sc.save();
    }

    private void paintscore(Canvas canvas){
        Paint paint = new Paint();
        paint.setDither(true);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);

        paint.setTextAlign(Paint.Align.CENTER);
        if(getHeight()==2460) {
            paint.setTextSize(250);
            canvas.drawText("" + score, carteLeftAnchor + (3 * carteTileSize), carteTopAnchor + -1 * (2 * carteTileSize), paint);
        }else if(getHeight()>=1800){
            paint.setTextSize(200);
            canvas.drawText("" + score, carteLeftAnchor + (11 * carteTileSize) / 4, carteTopAnchor + -1 * (3 * carteTileSize) / 2, paint);

        }else{
            paint.setTextSize(200);
            canvas.drawText("" + score, carteLeftAnchor + (11 * carteTileSize) / 4, carteTopAnchor + -1 * (2 * carteTileSize) / 2, paint);

        }
    }

    // dessin du gagne si gagne
        private void paintlose(Canvas canvas) {
            if(getHeight()==2460) {
            canvas.drawBitmap(lose, carteLeftAnchor + -1 * (5 * carteTileSize / 6), carteTopAnchor + -1 * (2 * carteTileSize), null);
            }else{
                canvas.drawBitmap(lose, carteLeftAnchor +  1*( carteTileSize )/6, carteTopAnchor + -1 * ( carteTileSize), null);

            }
        }

     int score_final(int temps,int nb_coups){

        score_fin=temps * nb_coups;
        return score_fin;
    }

       // dessin du gagne si gagne
        private void paintwin(Canvas canvas) {
            if(getHeight()==2460) {
                canvas.drawBitmap(win, carteLeftAnchor + -1 * (5 * carteTileSize / 6), carteTopAnchor + -1 * (2 * carteTileSize), null);
            }else{
                canvas.drawBitmap(win, carteLeftAnchor +  1*( carteTileSize )/6, carteTopAnchor + -1 * ( carteTileSize), null);

            }
        }

        private void paintfond(Canvas canvas) {
            canvas.drawBitmap(fond, 0, 0, null);
        }

        // dessin de la carte du jeu
        private void paintcarte(Canvas canvas) {
            for (int i=0; i< carteHeight; i++) {
                for (int j=0; j< carteWidth; j++) {
                    switch (carte[i][j]) {

                        case CST_zone:
                            canvas.drawBitmap(zone,carteLeftAnchor+ j*carteTileSize, carteTopAnchor+ i*carteTileSize, null);
                            break;
                        case CST_anime_clear:
                            canvas.drawBitmap(anime_clear[currentStepZone],carteLeftAnchor+ j*carteTileSize, carteTopAnchor+ i*carteTileSize, null);
                            break;
                        case CST_anime_color:
                            canvas.drawBitmap(anime_color[currentStepZone2],carteLeftAnchor+ j*carteTileSize, carteTopAnchor+ i*carteTileSize, null);
                            break;
                        case CST_card_chevre:
                            canvas.drawBitmap(card_chevre,carteLeftAnchor+ j*carteTileSize, carteTopAnchor+ i*carteTileSize, null);
                            break;
                        case CST_card_rat:
                            canvas.drawBitmap(card_rat,carteLeftAnchor+ j*carteTileSize, carteTopAnchor+ i*carteTileSize, null);
                            break;
                        case CST_card_serpent:
                            canvas.drawBitmap(card_serpent,carteLeftAnchor+ j*carteTileSize, carteTopAnchor+ i*carteTileSize, null);
                            break;
                        case CST_card_chien:
                            canvas.drawBitmap(card_chien,carteLeftAnchor+ j*carteTileSize, carteTopAnchor+ i*carteTileSize, null);
                            break;
                        case CST_card_singe:
                            canvas.drawBitmap(card_singe,carteLeftAnchor+ j*carteTileSize, carteTopAnchor+ i*carteTileSize, null);
                            break;
                        case CST_card_dragon:
                            canvas.drawBitmap(card_dragon,carteLeftAnchor+ j*carteTileSize, carteTopAnchor+ i*carteTileSize, null);
                            break;
                        case CST_card_cochon:
                            canvas.drawBitmap(card_cochon,carteLeftAnchor+ j*carteTileSize, carteTopAnchor+ i*carteTileSize, null);
                            break;
                        case CST_card_coq:
                            canvas.drawBitmap(card_coq,carteLeftAnchor+ j*carteTileSize, carteTopAnchor+ i*carteTileSize, null);
                            break;
                        case CST_card_vache:
                            canvas.drawBitmap(card_vache,carteLeftAnchor+ j*carteTileSize, carteTopAnchor+ i*carteTileSize, null);
                            break;
                        case CST_card_tigre:
                            canvas.drawBitmap(card_tigre,carteLeftAnchor+ j*carteTileSize, carteTopAnchor+ i*carteTileSize, null);
                            break;
                        case CST_vide:
                            canvas.drawBitmap(vide,carteLeftAnchor+ j*carteTileSize, carteTopAnchor+ i*carteTileSize, null);
                            break;
                    }
                }
            }
        }


        // dessin du jeu (fond uni, en fonction du jeu gagne ou pas dessin du plateau et du joueur des diamants et des fleches)
        private void nDraw(Canvas canvas) {
            int i=0;
            canvas.drawRGB(255, 255, 255);
            paintfond(canvas);
            if (cpt_win==10) {
                stat.block(1);
               // paintcarte(canvas);
                //paintscore(canvas);
                paintwin(canvas);
                paintscore_finale(canvas);
                gagner=1;
                perdu=1;

            } else if (score==0 || stat.status_time()<=0) {
                paintcarte(canvas);
                paintscore(canvas);
                paintlose(canvas);

                lock=1;
               // in = false ;
                perdu=1;
            } else{

                    paintcarte(canvas);
                    paintscore(canvas);
                 //   System.out.println("time :"+stat.status_time());

                    //paintPlayer(canvas);
                    // paintdiamants(canvas);
                    // paintarrow(canvas);
                }

            // Activation du thread secondaire (class TIME)





            }
        public void setScore(){
        score=30;
    }

    public void stopThread(){
        cv_thread.interrupt();
        in = false ;


    }


        // callback sur le cycle de vie de la surfaceview
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            Log.i("-> FCT <-", "surfaceChanged " + width + " - " + height);
            initparameters();
            cv_thread   = new Thread(this);
            in = true;
        }

        public void surfaceCreated(SurfaceHolder arg0) {
            Log.i("-> FCT <-", "surfaceCreated");
        }


        public void surfaceDestroyed(SurfaceHolder arg0) {
            Log.i("-> FCT <-", "surfaceDestroyed");
            cv_thread.interrupt();
            cv_thread.interrupt();
            in = false ;
            currentStepZone = 0;
            currentStepZone2 = 0;
            xCard = 0;
            yCard = 0;
            cpt_anime=0;
            cpt_anime2=0;
            cpt_anime3=0;
            cpt_click=0;
            cpt_next=0;
            cpt_win=0;
            carte_tmp=0;
            lock=0;
            lock2=0;
          //  in = true;

        }

        /**
         * run (run du thread cr  )
         * on endort le thread, on modifie le compteur d'animation, on prend la main pour dessiner et on dessine puis on lib re le canvas
         */
        public void run() {
            Canvas c = null;

            while (in) {
                try {

                    ref[0][0]=CST_vide;
                    loadlevel();
                    cv_thread.sleep(30);

                    if (currentStepZone<5) {
                       // cv_thread.sleep(10);

                        currentStepZone = (currentStepZone + 1);
                        cpt_anime=1;
                    }else if(cpt_anime==1){

                        carte_tmp=ref2[xCard][yCard];
                        switch (carte_tmp){

                            case 0:
                                ref[xCard][yCard]=CST_card_chevre;
                                break;
                            case 1:
                                ref[xCard][yCard]=CST_card_chien;
                                break;
                            case 2:
                                ref[xCard][yCard]=CST_card_cochon;
                                break;
                            case 3:
                                ref[xCard][yCard]=CST_card_coq;
                                break;
                            case 4:
                                ref[xCard][yCard]=CST_card_dragon;
                                break;
                            case 5:
                                ref[xCard][yCard]=CST_card_rat;
                                break;
                            case 6:
                                ref[xCard][yCard]=CST_card_serpent;
                                break;
                            case 7:
                                ref[xCard][yCard]=CST_card_singe;
                                break;
                            case 8:
                                ref[xCard][yCard]=CST_card_tigre;
                                break;
                            case 9:
                                ref[xCard][yCard]=CST_card_vache;
                                break;


                            default:break;
                        }

                        cpt_anime=0;
                        cpt_next=1;
                        loadlevel();
                        lock=0;


                    }else if (cpt_click%2==0 && cpt_anime==0 && cpt_click!=0 && cpt_next==1){
                        lock2=1;
                        cv_thread.sleep(500);
                        check_click();
                        cpt_next=0;

                        score--;
                    } else
                    if (currentStepZone2<3 && cpt_anime2==1) {
                        // cv_thread.sleep(10);

                        currentStepZone2 = (currentStepZone2 + 1);
                        cpt_anime3=1;
                    }else if(cpt_anime3==1) {

                        ref[(int)liste_memo.get(0)][(int)liste_memo.get(1)]=CST_zone;
                        ref[(int)liste_memo.get(3)][(int)liste_memo.get(4)]=CST_zone;
                        cpt_anime2=0;
                        cpt_anime3=0;
                        lock2=0;
                        loadlevel();
                    }
                        try {
                        c = holder.lockCanvas(null);

                        nDraw(c);
                    } finally {
                        if (c != null) {
                            holder.unlockCanvasAndPost(c);
                        }
                    }
                } catch(Exception e) {
                    // Log.e("-> RUN <-", "PB DANS RUN");

                }
            }
        }


        // fonction permettant de recuperer les evenements tactiles
        public boolean onTouchEvent (MotionEvent event) {

            Log.i("-> FCT <-", "onTouchEvent: x: "+ event.getX()+" -> y: "+event.getY());

            for(int j=0;j<5;j++) {
                for (int i = 1; i < 5; i++) {
                    if (event.getX() >= carteLeftAnchor + i * carteTileSize && event.getX() <= carteLeftAnchor + (i + 1) * carteTileSize && event.getY() >= carteTopAnchor+(j*carteTileSize) && event.getY() <= carteTopAnchor + (j+1)*carteTileSize) {

                       if(card[j][i]==0 && lock==0 && lock2==0) {
                           lock=1;
                           currentStepZone = 0;
                           xCard = j;
                           yCard = i;
                           ref[j][i] = CST_anime_clear;
                           card[j][i] = 1;
                           loadlevel();
                           cpt_click++;
                           if(liste_memo.size()==6){
                               liste_memo.clear();
                           }
                           liste_memo.add(j);
                           liste_memo.add(i);
                           liste_memo.add(ref2[j][i]);
                           if(cpt_click==1){
                               if(cpt_click2==1){
                                   stat.block(0);
                                   cpt_click2=0;
                               }else {
                                   stat.launch();
                               }
                           }
                       }


                        Log.i("-> FCT <-", "touch: x: " + carteLeftAnchor + i*carteTileSize + " y: " + carteTopAnchor+(j*carteTileSize));
                    }

                }
            }



            return super.onTouchEvent(event);
        }


public void check_click(){
/*for (int i=0;i<6;i++){
    Log.i("liste_memo"," "+liste_memo.get(i));
}*/
    if ((int)liste_memo.get(2)!=(int)liste_memo.get(5)){
        currentStepZone2 = 0;
        cpt_anime2=1;
        ref[(int)liste_memo.get(0)][(int)liste_memo.get(1)]=CST_anime_color;
        ref[(int)liste_memo.get(3)][(int)liste_memo.get(4)]=CST_anime_color;
        card[(int)liste_memo.get(0)][(int)liste_memo.get(1)]=0;
        card[(int)liste_memo.get(3)][(int)liste_memo.get(4)]=0;
        loadlevel();
    }else{
        lock2=0;
        cpt_win++;
    }

}

    }

