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
public class S1LN3DecPage extends S1DecPage {

    public S1LN3DecPage() {
        super();
        s1 = new LARectangle();
        s1.setFormat(12f, AvailableFonts.TIMES);
        s1.setXW(141.937f, 52.199f);
        decision = new LARectangle();
        decision.setFormat(12f, AvailableFonts.TIMES);
        decision.setXW(15.456f, 88.441f);
        rank = new LARectangle();
        rank.setFormat(12f, AvailableFonts.TIMES);
        rank.setXW(104.859f, 36.117f);
        
        float tmp = -38.44f;
        years = new LARectangle[3];
        for ( int i = 0; i < 3; i++ ) {
            years[i] = new LARectangle();
            years[i].setFormat(12f, AvailableFonts.TIMES);
            years[i].setXW(271.977f + tmp * i, 37.479f);
        }
    }
    
}
