/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pvscreator.template.decision;

import java.awt.Color;
import java.util.ArrayList;
import org.sicut.util.pdf.AvailableFonts;
import org.sicut.util.pdf.LARectangle;

/**
 *
 * @author Sicut
 */
public abstract class DecisionPage {
    protected final LARectangle MINISTERY, SCHOOL_INFOS, SEMESTER, YEAR, LEVEL, GROUP;
    protected LARectangle code, name, gender, decision;
    protected LARectangle[] years;
    protected float MAX_HEIGHT, FIRST_Y, LAST_Y, HEIGHT;
    protected final LARectangle DIVIDER;
    private float rowHeight, size;

    public DecisionPage() {
        MINISTERY = new LARectangle();
        MINISTERY.setFormat(9f, AvailableFonts.MAGHRIBI);
        MINISTERY.reset(329.079f, 14.173f, 230.520f, 54.786f);
        SCHOOL_INFOS = new LARectangle();
        SCHOOL_INFOS.setFormat(9f, AvailableFonts.MAGHRIBI);
        SCHOOL_INFOS.reset(35.676f, 14.173f, 230.520f, 54.786f);
        SEMESTER = new LARectangle();
        SEMESTER.setFormat(10f, AvailableFonts.TIMES);
        SEMESTER.reset(74.820f, 77.155f, 138.359f, 16.658f);
        YEAR = new LARectangle();
        YEAR.setFormat(12f, AvailableFonts.TIMES);
        YEAR.reset(430.212f, 104.287f, 93.428f, 15.341f);
        LEVEL = new LARectangle();
        LEVEL.setFormat(12f, AvailableFonts.TIMES);
        LEVEL.reset(116.461f, 104.287f, 273.878f, 15.341f);
        GROUP = new LARectangle();
        GROUP.setFormat(12f, AvailableFonts.TAHOMA, true);
        GROUP.reset(15.339f, 104.287f, 73.495f, 15.341f);
        DIVIDER = new LARectangle();
        DIVIDER.setColor(new Color(0x7F, 0x7F, 0x7F));
        DIVIDER.reset(14.499f, 0, 566.277f, 0.961f);
        MAX_HEIGHT = 20.329f;
        FIRST_Y = 167.721f;
        LAST_Y = 826.76f;
        HEIGHT = LAST_Y - FIRST_Y;
    }
    
    
    public void prepareRows(long size) {
        float h = ( HEIGHT - DIVIDER.getHeight() * ( size - 1 ) ) / size;
        if ( h > MAX_HEIGHT )
            h = MAX_HEIGHT;
        code.setHeight(h);
        name.setHeight(h);
        gender.setHeight(h);
        decision.setHeight(h);
        for ( int i = 0; i < 3; i++ ) {
            years[i].setHeight(h);
        }
        rowHeight = h + DIVIDER.getHeight();
        this.size = size;
    }
    
    public void prepareRow(int index) {
        float y = FIRST_Y + rowHeight * index;
        code.setY(y);
        name.setY(y);
        gender.setY(y);
        decision.setY(y);
        DIVIDER.setY(y - DIVIDER.getHeight());
        for ( int i = 0; i < 3; i++ ) {
            years[i].setY(y);
        }
    }
    
    public ArrayList<LARectangle> prepareDividers() throws CloneNotSupportedException {
        ArrayList<LARectangle> arr = new ArrayList();
        LARectangle tmp;
        if ( size < 31 )
            size = 31;
        for ( int i = 1; i < size; i++ ) {
            tmp = (LARectangle) DIVIDER.clone();
            tmp.setY(FIRST_Y + rowHeight * i - DIVIDER.getHeight());
            arr.add(tmp);
        }
        return arr;
    }

    public LARectangle getMINISTERY() {
        return MINISTERY;
    }

    public LARectangle getSCHOOL_INFOS() {
        return SCHOOL_INFOS;
    }

    public LARectangle getSEMESTER() {
        return SEMESTER;
    }

    public LARectangle getYEAR() {
        return YEAR;
    }

    public LARectangle getLEVEL() {
        return LEVEL;
    }

    public LARectangle getGROUP() {
        return GROUP;
    }

    public LARectangle getCode() {
        return code;
    }

    public LARectangle getName() {
        return name;
    }

    public LARectangle getGender() {
        return gender;
    }

    public LARectangle getDecision() {
        return decision;
    }

    public LARectangle[] getYears() {
        return years;
    }

    public float getRowHeight() {
        return rowHeight;
    }
}
