/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pvscreator.template.pv;

import org.sicut.util.pdf.AvailableFonts;
import org.sicut.util.pdf.LARectangle;

/**
 *
 * @author Sicut
 */
public class S2L3PVPage extends S2PVPage {

    public S2L3PVPage() {
        super();
        marksSize = 5;
        bestsSize = 3;
        grp1stMark = new LARectangle[marksSize];
        grpLstMark = new LARectangle[marksSize];
        grpAvgMark = new LARectangle[marksSize];
        grpGotAvgN = new LARectangle[marksSize];
        lev1stMark = new LARectangle[marksSize];
        levLstMark = new LARectangle[marksSize];
        levAvgMark = new LARectangle[marksSize];
        levGotAvgN = new LARectangle[marksSize];
        for ( int i = 0; i < marksSize; i++ ) {
            float tmp = 356.242f + i * GAP;
            grp1stMark[i] = new LARectangle();
            grp1stMark[i].setFormat(12f, AvailableFonts.TIMES);
            grp1stMark[i].reset(499.199f, tmp, 45.121f, HEIGHT);
            grpLstMark[i] = new LARectangle();
            grpLstMark[i].setFormat(12f, AvailableFonts.TIMES);
            grpLstMark[i].reset(453.121f, tmp, 45.121f, HEIGHT);
            grpAvgMark[i] = new LARectangle();
            grpAvgMark[i].setFormat(12f, AvailableFonts.TIMES);
            grpAvgMark[i].reset(401.820f, tmp, 50.340f, HEIGHT);
            grpGotAvgN[i] = new LARectangle();
            grpGotAvgN[i].setFormat(12f, AvailableFonts.TIMES);
            grpGotAvgN[i].reset(284.281f, tmp, 116.578f, HEIGHT);
            lev1stMark[i] = new LARectangle();
            lev1stMark[i].setFormat(12f, AvailableFonts.TIMES);
            lev1stMark[i].reset(238.078f, tmp, 43.320f, HEIGHT);
            levLstMark[i] = new LARectangle();
            levLstMark[i].setFormat(12f, AvailableFonts.TIMES);
            levLstMark[i].reset(192f, tmp, 45.121f, HEIGHT);
            levAvgMark[i] = new LARectangle();
            levAvgMark[i].setFormat(12f, AvailableFonts.TIMES);
            levAvgMark[i].reset(140.699f, tmp, 50.340f, HEIGHT);
            levGotAvgN[i] = new LARectangle();
            levGotAvgN[i].setFormat(12f, AvailableFonts.TIMES);
            levGotAvgN[i].reset(23.039f, tmp, 116.699f, HEIGHT);
        }
        
        grpBestName = new LARectangle[bestsSize];
        grpBestMark = new LARectangle[bestsSize];
        levBestName = new LARectangle[bestsSize];
        levBestMark = new LARectangle[bestsSize];
        for ( int i = 0; i < bestsSize; i++ ) {
            float tmp = 493.164f + i * GAP;
            grpBestName[i] = new LARectangle();
            grpBestName[i].setFormat(12f, AvailableFonts.TIMES);
            grpBestName[i].reset(407.039f, tmp, 168f, HEIGHT);
            grpBestMark[i] = new LARectangle();
            grpBestMark[i].setFormat(12f, AvailableFonts.TIMES);
            grpBestMark[i].reset(314.879f, tmp, 91.199f, HEIGHT);
            levBestName[i] = new LARectangle();
            levBestName[i].setFormat(12f, AvailableFonts.TIMES);
            levBestName[i].reset(115.199f, tmp, 168f, HEIGHT);
            levBestMark[i] = new LARectangle();
            levBestMark[i].setFormat(12f, AvailableFonts.TIMES);
            levBestMark[i].reset(23.039f, tmp, 91.199f, HEIGHT);
        }
    }
    
}

