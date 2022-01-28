/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pvscreator.template.decision;

import org.sicut.util.pdf.AvailableFonts;
import org.sicut.util.pdf.LARectangle;

/**
 *
 * @author Sicut
 */
public class S2LN3DecPage extends S2DecPage {
    private final LARectangle rank;

    public S2LN3DecPage() {
        super();
        
        code = new LARectangle();
        code.setFormat(11f, AvailableFonts.TIMES);
        code.setXW(495.819f, 83.996f);
        name = new LARectangle();
        name.setFormat(11f, AvailableFonts.TIMES);
        name.setXW(339.339f, 155.520f);
        gender = new LARectangle();
        gender.setFormat(11f, AvailableFonts.ICOMOON);
        gender.setXW(310.417f, 27.961f);
        
        s1 = new LARectangle();
        s1.setFormat(12f, AvailableFonts.TIMES);
        s1.setXW(181.655f, 34.980f);
        s2 = new LARectangle();
        s2.setFormat(12f, AvailableFonts.TIMES);
        s2.setXW(145.714f, 34.980f);
        average = new LARectangle();
        average.setFormat(12f, AvailableFonts.TIMES);
        average.setXW(103.478f, 41.275f);
        decision = new LARectangle();
        decision.setFormat(12f, AvailableFonts.TIMES);
        decision.setXW(15.456f, 60.297f);
        rank = new LARectangle();
        rank.setFormat(12f, AvailableFonts.TIMES);
        rank.setXW(76.714f, 25.803f);
        
        float tmp = -30.94f;
        years = new LARectangle[3];
        for ( int i = 0; i < 3; i++ ) {
            years[i] = new LARectangle();
            years[i].setFormat(12f, AvailableFonts.TIMES);
            years[i].setXW(279.477f + tmp * i, 29.979f);
        }
        
    }

    public LARectangle getRank() {
        return rank;
    }
    
    
    @Override
    public void prepareRows(long size) {
        super.prepareRows(size);
        rank.setHeight(code.getHeight());
    }
    
    @Override
    public void prepareRow(int index) {
        super.prepareRow(index);
        rank.setY(code.getY());
    }
}
