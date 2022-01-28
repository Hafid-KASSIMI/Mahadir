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
public class S1PVPage {
    private final LARectangle ministery, schoolInfos, year, semester, councilDate, councilTime, group, president;
    private final LARectangle[] matter, grp1stMark, grpLstMark, grpAvgMark, grpGotAvgN, grpGotAvgP, grpBestName, grpBestMark;
    private final LARectangle[] lev1stMark, levLstMark, levAvgMark, levGotAvgN, levGotAvgP, levBestName, levBestMark;
    private final LARectangle[] attrN, attrP, cntrlN, cntrlP;
    private final LARectangle levelName;
    private final LARectangle[] grp, grpAvg;

    public S1PVPage() {
        float tmp;
        ministery = new LARectangle();
        ministery.setFormat(9f, AvailableFonts.MAGHRIBI);
        ministery.reset(345.480f, 24.238f, 230.520f, 57.723f);
        schoolInfos = new LARectangle();
        schoolInfos.setFormat(9f, AvailableFonts.MAGHRIBI);
        schoolInfos.reset(21.602f, 24.238f, 230.520f, 57.723f);
        year = new LARectangle();
        year.setFormat(10f, AvailableFonts.TAHOMA);
        year.reset(52.320f, 87.602f, 138.359f, 15.477f);
        semester = new LARectangle();
        semester.setFormat(10f, AvailableFonts.TAHOMA);
        semester.reset(52.320f, 103.079f, 138.359f, 15.477f);
        councilDate = new LARectangle();
        councilDate.setFormat(11f, AvailableFonts.TAHOMA, true);
        councilDate.reset(374.879f, 123.121f, 123f, 22.437f);
        councilTime = new LARectangle();
        councilTime.setFormat(11f, AvailableFonts.TAHOMA, true);
        councilTime.reset(221.281f, 123.121f, 76.918f, 22.437f);
        group = new LARectangle();
        group.setFormat(11f, AvailableFonts.TAHOMA, true);
        group.reset(21.602f, 123.121f, 123f, 22.437f);
        president = new LARectangle();
        president.setFormat(11f, AvailableFonts.TAHOMA, true);
        president.reset(236.641f, 145.441f, 199.801f, 22.437f);
        matter = new LARectangle[14];
        for ( int i = 0; i < 14; i++ ) {
            matter[i] = new LARectangle();
            matter[i].setFormat(8f, AvailableFonts.TAHOMA);
            matter[i].reset(i < 7 ? 391.138f : 99.198f, 190.078f + ( i % 7 ) * 22.321f, 61.562f, 22.441f);
        }
        grp1stMark = new LARectangle[3];
        grpLstMark = new LARectangle[3];
        grpAvgMark = new LARectangle[3];
        grpGotAvgN = new LARectangle[3];
        grpGotAvgP = new LARectangle[3];
        grpBestName = new LARectangle[3];
        grpBestMark = new LARectangle[3];
        lev1stMark = new LARectangle[3];
        levLstMark = new LARectangle[3];
        levAvgMark = new LARectangle[3];
        levGotAvgN = new LARectangle[3];
        levGotAvgP = new LARectangle[3];
        levBestName = new LARectangle[3];
        levBestMark = new LARectangle[3];
        cntrlN = new LARectangle[3];
        cntrlP = new LARectangle[3];
        for ( int i = 0; i < 3; i++ ) {
            tmp = 390.961f + i * 22.322f;
            grp1stMark[i] = new LARectangle();
            grp1stMark[i].setFormat(12f, AvailableFonts.TIMES);
            grp1stMark[i].reset(468.323f, tmp, 44.915f, 22.434f);
            grpLstMark[i] = new LARectangle();
            grpLstMark[i].setFormat(12f, AvailableFonts.TIMES);
            grpLstMark[i].reset(422.570f, tmp, 45.214f, 22.434f);
            grpAvgMark[i] = new LARectangle();
            grpAvgMark[i].setFormat(12f, AvailableFonts.TIMES);
            grpAvgMark[i].reset(345.789f, tmp, 76.241f, 22.434f);
            grpGotAvgN[i] = new LARectangle();
            grpGotAvgN[i].setFormat(12f, AvailableFonts.TIMES);
            grpGotAvgN[i].reset(253.548f, tmp, 91.621f, 22.434f);
            grpGotAvgP[i] = new LARectangle();
            grpGotAvgP[i].setFormat(12f, AvailableFonts.TIMES);
            grpGotAvgP[i].reset(191.858f, tmp, 61.028f, 22.434f);
            grpBestName[i] = new LARectangle();
            grpBestName[i].setFormat(12f, AvailableFonts.TIMES);
            grpBestName[i].reset(69.174f, tmp, 106.846f, 22.434f);
            grpBestMark[i] = new LARectangle();
            grpBestMark[i].setFormat(12f, AvailableFonts.TIMES);
            grpBestMark[i].reset(22.940f, tmp, 45.590f, 22.434f);
            tmp = 681.121f + i * 22.322f;
            lev1stMark[i] = new LARectangle();
            lev1stMark[i].setFormat(12f, AvailableFonts.TIMES);
            lev1stMark[i].reset(468.323f, tmp, 44.915f, 22.434f);
            levLstMark[i] = new LARectangle();
            levLstMark[i].setFormat(12f, AvailableFonts.TIMES);
            levLstMark[i].reset(422.570f, tmp, 45.214f, 22.434f);
            levAvgMark[i] = new LARectangle();
            levAvgMark[i].setFormat(12f, AvailableFonts.TIMES);
            levAvgMark[i].reset(345.789f, tmp, 76.241f, 22.434f);
            levGotAvgN[i] = new LARectangle();
            levGotAvgN[i].setFormat(12f, AvailableFonts.TIMES);
            levGotAvgN[i].reset(253.548f, tmp, 91.621f, 22.434f);
            levGotAvgP[i] = new LARectangle();
            levGotAvgP[i].setFormat(12f, AvailableFonts.TIMES);
            levGotAvgP[i].reset(191.858f, tmp, 61.028f, 22.434f);
            levBestName[i] = new LARectangle();
            levBestName[i].setFormat(12f, AvailableFonts.TIMES);
            levBestName[i].reset(69.174f, tmp, 106.846f, 22.434f);
            levBestMark[i] = new LARectangle();
            levBestMark[i].setFormat(12f, AvailableFonts.TIMES);
            levBestMark[i].reset(22.940f, tmp, 45.590f, 22.434f);
            tmp = 502.5f + i * 22.322f;
            cntrlN[i] = new LARectangle();
            cntrlN[i].setFormat(12f, AvailableFonts.TIMES);
            cntrlN[i].reset(84.248f, tmp, 60.713f, 22.434f);
            cntrlP[i] = new LARectangle();
            cntrlP[i].setFormat(12f, AvailableFonts.TIMES);
            cntrlP[i].reset(23.039f, tmp, 60.713f, 22.434f);
        }
        attrN = new LARectangle[6];
        attrP = new LARectangle[6];
        for ( int i = 0; i < 6; i++ ) {
            tmp = 502.5f + i * 22.322f;
            attrN[i] = new LARectangle();
            attrN[i].setFormat(12f, AvailableFonts.TIMES);
            attrN[i].reset(391.272f, tmp, 45.970f, 22.434f);
            attrP[i] = new LARectangle();
            attrP[i].setFormat(12f, AvailableFonts.TIMES);
            attrP[i].reset(329.738f, tmp, 61.534f, 22.434f);
        }
        levelName = new LARectangle();
        levelName.setFormat(12f, AvailableFonts.TIMES, true);
        levelName.reset(514.437f, 770.406f, 60.904f, 22.434f);
        grp = new LARectangle[16];
        grpAvg = new LARectangle[16];
        for ( int i = 0; i < 16; i++ ) {
            tmp = 483.425f - i * 30.712f;
            grp[i] = new LARectangle();
            grp[i].setFormat(11f, AvailableFonts.TIMES);
            grp[i].reset(tmp, 770.406f, 30.571f, 22.434f);
            grpAvg[i] = new LARectangle();
            grpAvg[i].setFormat(11f, AvailableFonts.TIMES);
            grpAvg[i].reset(tmp, 792.840f, 30.571f, 22.434f);
        }
        
    }

    public LARectangle getSemester() {
        return semester;
    }

    public LARectangle getMinistery() {
        return ministery;
    }

    public LARectangle getSchoolInfos() {
        return schoolInfos;
    }

    public LARectangle getYear() {
        return year;
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

    public LARectangle[] getGrpGotAvgP() {
        return grpGotAvgP;
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

    public LARectangle[] getLevGotAvgP() {
        return levGotAvgP;
    }

    public LARectangle[] getLevBestName() {
        return levBestName;
    }

    public LARectangle[] getLevBestMark() {
        return levBestMark;
    }

    public LARectangle[] getAttrN() {
        return attrN;
    }

    public LARectangle[] getAttrP() {
        return attrP;
    }

    public LARectangle[] getCntrlN() {
        return cntrlN;
    }

    public LARectangle[] getCntrlP() {
        return cntrlP;
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
    
}
