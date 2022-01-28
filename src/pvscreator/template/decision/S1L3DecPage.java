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
public class S1L3DecPage extends S1DecPage {

    public S1L3DecPage() {
        super();
        
        s1 = new LARectangle();
        s1.setFormat(12f, AvailableFonts.TIMES);
        s1.setXW(181.655f, 34.980f);
        loc = new LARectangle();
        loc.setFormat(12f, AvailableFonts.TIMES);
        loc.setXW(145.714f, 34.980f);
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

    @Override
    public void prepareRows(long size) {
        super.prepareRows(size);
        average.setHeight(s1.getHeight());
        loc.setHeight(s1.getHeight());
    }

    @Override
    public void prepareRow(int index) {
        super.prepareRow(index);
        average.setY(s1.getY());
        loc.setY(s1.getY());
    }
    
}
