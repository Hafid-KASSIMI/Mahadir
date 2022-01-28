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
public class S2PVPage {
    private final LARectangle ministery, schoolInfos, yearNSemester, councilDate, councilTime, group, president;
    private final LARectangle[] matter;
    protected LARectangle[] grpBestName, grpBestMark, grp1stMark, grpLstMark, grpAvgMark, grpGotAvgN;
    protected LARectangle[] levBestName, levBestMark, lev1stMark, levLstMark, levAvgMark, levGotAvgN;
    private final LARectangle[] grpAttr, levAttr, grpCntrl, levCntrl;
    private final LARectangle levelName;
    private final LARectangle[] grp, grpAvg;
    private final LARectangle threshold, grpSuc, grpUnsuc, grpRep, grpFir, levSuc, levUnsuc, levRep, levFir;
    protected final float GAP = 19.523f;
    protected final float HEIGHT = 18.592f;
    protected int marksSize, bestsSize;

    public S2PVPage() {
        float tmp;
        ministery = new LARectangle();
        ministery.setFormat(9f, AvailableFonts.MAGHRIBI);
        ministery.reset(330.481f, 14.173f, 230.520f, 54.786f);
        schoolInfos = new LARectangle();
        schoolInfos.setFormat(9f, AvailableFonts.MAGHRIBI);
        schoolInfos.reset(37.078f, 14.173f, 230.520f, 54.786f);
        yearNSemester = new LARectangle();
        yearNSemester.setFormat(9f, AvailableFonts.TAHOMA);
        yearNSemester.reset(62.820f, 69.717f, 138.359f, 24.096f);
        councilDate = new LARectangle();
        councilDate.setFormat(11f, AvailableFonts.TAHOMA, true);
        councilDate.reset(374.879f, 102.849f, 138.366f, HEIGHT);
        councilTime = new LARectangle();
        councilTime.setFormat(11f, AvailableFonts.TAHOMA, true);
        councilTime.reset(221.281f, 102.849f, 76.918f, HEIGHT);
        group = new LARectangle();
        group.setFormat(11f, AvailableFonts.TAHOMA, true);
        group.reset(21.602f, 102.849f, 123f, HEIGHT);
        president = new LARectangle();
        president.setFormat(11f, AvailableFonts.TAHOMA, true);
        president.reset(230.768f, 121.441f, 221.040f, HEIGHT);
        matter = new LARectangle[14];
        for ( int i = 0; i < 14; i++ ) {
            matter[i] = new LARectangle();
            matter[i].setFormat(8f, AvailableFonts.TAHOMA);
            matter[i].reset(i < 7 ? 391.671f : 99.840f, 160.598f + ( i % 7 ) * GAP, 60.490f, HEIGHT);
        }
        
        grpCntrl = new LARectangle[3];
        levCntrl = new LARectangle[3];
        for ( int i = 0; i < 3; i++ ) {
            tmp = 590.965f + i * GAP;
            grpCntrl[i] = new LARectangle();
            grpCntrl[i].setFormat(12f, AvailableFonts.TIMES);
            grpCntrl[i].reset(107.731f, tmp, 71.730f, HEIGHT);
            levCntrl[i] = new LARectangle();
            levCntrl[i].setFormat(12f, AvailableFonts.TIMES);
            levCntrl[i].reset(23.040f, tmp, 83.730f, HEIGHT);
        }
        grpAttr = new LARectangle[6];
        levAttr = new LARectangle[6];
        for ( int i = 0; i < 6; i++ ) {
            tmp = 590.965f + i * GAP;
            grpAttr[i] = new LARectangle();
            grpAttr[i].setFormat(12f, AvailableFonts.TIMES);
            grpAttr[i].reset(393.570f, tmp, 65.730f, HEIGHT);
            levAttr[i] = new LARectangle();
            levAttr[i].setFormat(12f, AvailableFonts.TIMES);
            levAttr[i].reset(314.879f, tmp, 77.730f, HEIGHT);
        }
        
        tmp = 747.369f;
        threshold = new LARectangle();
        threshold.setFormat(12f, AvailableFonts.TIMES, true);
        threshold.reset(529.918f, tmp, 45.121f, HEIGHT);
        grpSuc = new LARectangle();
        grpSuc.setFormat(11f, AvailableFonts.TIMES);
        grpSuc.reset(466.798f, tmp, 62.159f, HEIGHT);
        grpUnsuc = new LARectangle();
        grpUnsuc.setFormat(11f, AvailableFonts.TIMES);
        grpUnsuc.reset(403.679f, tmp, 62.159f, HEIGHT);
        grpRep = new LARectangle();
        grpRep.setFormat(11f, AvailableFonts.TIMES);
        grpRep.reset(340.559f, tmp, 62.159f, HEIGHT);
        grpFir = new LARectangle();
        grpFir.setFormat(11f, AvailableFonts.TIMES);
        grpFir.reset(277.440f, tmp, 62.159f, HEIGHT);
        
        levSuc = new LARectangle();
        levSuc.setFormat(11f, AvailableFonts.TIMES);
        levSuc.reset(212.398f, tmp, 62.159f, HEIGHT);
        levUnsuc = new LARectangle();
        levUnsuc.setFormat(11f, AvailableFonts.TIMES);
        levUnsuc.reset(149.278f, tmp, 62.159f, HEIGHT);
        levRep = new LARectangle();
        levRep.setFormat(11f, AvailableFonts.TIMES);
        levRep.reset(86.159f, tmp, 62.159f, HEIGHT);
        levFir = new LARectangle();
        levFir.setFormat(11f, AvailableFonts.TIMES);
        levFir.reset(23.039f, tmp, 62.159f, HEIGHT);
        
        levelName = new LARectangle();
        levelName.setFormat(12f, AvailableFonts.TIMES, true);
        levelName.reset(514.559f, 786.486f, 60.480f, HEIGHT);
        grp = new LARectangle[16];
        grpAvg = new LARectangle[16];
        for ( int i = 0; i < 16; i++ ) {
            tmp = 483.840f - i * 30.723f;
            grp[i] = new LARectangle();
            grp[i].setFormat(11f, AvailableFonts.TIMES);
            grp[i].reset(tmp, 786.486f, 29.762f, HEIGHT);
            grpAvg[i] = new LARectangle();
            grpAvg[i].setFormat(11f, AvailableFonts.TIMES);
            grpAvg[i].reset(tmp, 806.049f, 29.762f, HEIGHT);
        }
        
    }

    public LARectangle getMinistery() {
        return ministery;
    }

    public LARectangle getSchoolInfos() {
        return schoolInfos;
    }

    public LARectangle getCouncilDate() {
        return councilDate;
    }

    public LARectangle getCouncilTime() {
        return councilTime;
    }

    public LARectangle getGroup() {
        return group;
    }

    public LARectangle getPresident() {
        return president;
    }

    public LARectangle[] getMatter() {
        return matter;
    }

    public LARectangle[] getGrp1stMark() {
        return grp1stMark;
    }

    public LARectangle[] getGrpLstMark() {
        return grpLstMark;
    }

    public LARectangle[] getGrpAvgMark() {
        return grpAvgMark;
    }

    public LARectangle[] getGrpGotAvgN() {
        return grpGotAvgN;
    }

    public LARectangle[] getGrpBestName() {
        return grpBestName;
    }

    public LARectangle[] getGrpBestMark() {
        return grpBestMark;
    }

    public LARectangle[] getLev1stMark() {
        return lev1stMark;
    }

    public LARectangle[] getLevLstMark() {
        return levLstMark;
    }

    public LARectangle[] getLevAvgMark() {
        return levAvgMark;
    }

    public LARectangle[] getLevGotAvgN() {
        return levGotAvgN;
    }

    public LARectangle[] getLevBestName() {
        return levBestName;
    }

    public LARectangle[] getLevBestMark() {
        return levBestMark;
    }

    public LARectangle[] getGrpAttr() {
        return grpAttr;
    }

    public LARectangle[] getLevAttr() {
        return levAttr;
    }

    public LARectangle[] getGrpCntrl() {
        return grpCntrl;
    }

    public LARectangle[] getLevCntrl() {
        return levCntrl;
    }

    public LARectangle getLevelName() {
        return levelName;
    }

    public LARectangle[] getGrp() {
        return grp;
    }

    public LARectangle[] getGrpAvg() {
        return grpAvg;
    }

    public LARectangle getYearNSemester() {
        return yearNSemester;
    }

    public LARectangle getThreshold() {
        return threshold;
    }

    public LARectangle getGrpSucceeded() {
        return grpSuc;
    }

    public LARectangle getGrpUnsucceeded() {
        return grpUnsuc;
    }

    public LARectangle getGrpRepeaters() {
        return grpRep;
    }

    public LARectangle getGrpFired() {
        return grpFir;
    }

    public LARectangle getLevSucceeded() {
        return levSuc;
    }

    public LARectangle getLevUnsucceeded() {
        return levUnsuc;
    }

    public LARectangle getLevRepeaters() {
        return levRep;
    }

    public LARectangle getLevFired() {
        return levFir;
    }

    public int getMarksSize() {
        return marksSize;
    }

    public int getBestsSize() {
        return bestsSize;
    }
    
}
