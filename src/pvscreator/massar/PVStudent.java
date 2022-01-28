/*
 * Copyright (C) 2020 Sicut
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package pvscreator.massar;

import java.util.ArrayList;
import org.sicut.util.Misc;

/**
 *
 * @author Sicut
 */
public final class PVStudent {
        
    private String code, name, gender, group, levelFull, levelAbrev;
    private String academy, direction, school, year, choice1, choice2;
    private Double s1, s2, loc, reg, avg, s13A;
    private Integer groupIndex, rank, yearsSum;
    private Integer[] years;
    private String dec;
    private Boolean isGrpL3;

    public PVStudent() {
        years = new Integer[] { 0, 0, 0 };
    }

    public PVStudent(Double s1, Double s2, Double avg, 
            Integer y0, Integer y1, Integer y2, String dec, 
            String code, String name, String gender, String group, 
            String levelFull) {
        this();
        this.s1 = s1;
        this.s2 = s2;
        this.avg = avg;
        this.code = code;
        setDecision(dec);
        setName(name);
        setGender(gender);
        setGroup(group);
        setLevelFull(levelFull);
        levelAbrev = Misc.getLevelAbrev(group);
        try {
            groupIndex = Integer.parseInt(Misc.getGroupIndex(group));
        } catch ( NumberFormatException nfe ) { 
            groupIndex = 0;
        }
        years[0] = y0;
        years[1] = y1;
        years[2] = y2;
        yearsSum = y0 + y1 + y2;
    }

    public Double getS1() {
        return s1;
    }

    public void setS1(Double s1) {
        this.s1 = s1;
    }

    public Double getS2() {
        return s2;
    }

    public void setS2(Double s2) {
        this.s2 = s2;
    }

    public Double getLoc() {
        return loc;
    }

    public void setLoc(Double loc) {
        this.loc = loc;
    }

    public Double getReg() {
        return reg;
    }

    public void setReg(Double reg) {
        this.reg = reg;
    }

    public Double getAvg() {
        return avg;
    }

    public void setAvg(Double avg) {
        this.avg = avg;
    }

    public Integer[] getYears() {
        return years;
    }

    public void setYears(Integer[] years) {
        this.years = years;
    }

    public String getDecision() {
        return dec;
    }

    public void setDecision(String dec) {
        this.dec = Misc.justLetters(dec);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = Misc.justLetters(name);
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = Misc.justLetters(gender);
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
        isGrpL3 = group.startsWith("3A");
    }

    public String getLevelFull() {
        return levelFull;
    }

    public void setLevelFull(String levelFull) {
        this.levelFull = Misc.justLetters(levelFull);
    }

    public String getLevelAbrev() {
        return levelAbrev;
    }

    public void setLevelAbrev(String levelAbrev) {
        this.levelAbrev = levelAbrev;
    }
    
    public void setSchoolInfos(String academy, String direction, String school, String year) {
        this.academy = Misc.justLetters(academy);
        this.direction = Misc.justLetters(direction);
        this.school = Misc.justLetters(school);
        this.year = year;
    }

    public String getAcademy() {
        return academy;
    }

    public String getDirection() {
        return direction;
    }

    public String getSchool() {
        return school;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Integer getGroupIndex() {
        return groupIndex;
    }

    public Double getS13A() {
        return s13A;
    }

    public void setS13A(Double s13A) {
        this.s13A = s13A;
    }

    public void calculateS13A() {
        s13A = ( s1 + loc * 2 ) / 3;
    }
    
    public Double getL3Mark(int i) {
        switch (i) {
            case 0:
                return s1;
            case 1:
                return s2;
            case 2:
                return loc;
            case 3:
                return reg;
            default:
                return avg;
        }
    }
    
    public Double getLN3Mark(int i) {
        switch (i) {
            case 0:
                return s1;
            case 1:
                return s2;
            default:
                return avg;
        }
    }
    
    public Double getMark(int i) {
        return isGrpL3 ? getL3Mark(i) : getLN3Mark(i);
    }
    
    public int getS1MarksSize() {
        return ( isGrpL3 ) ? 3 : 1;
    }
    
    public Double getS1Mark(int i) {
        switch (i) {
            case 0:
                return s1;
            case 1:
                return loc;
            default:
                return s13A;
        }
    }
    
    public Double getS1AvgMark() {
        return isGrpL3 ? s13A : s1;
    }

    public Boolean getIsGrpL3() {
        return isGrpL3;
    }
    
    public Boolean isFemale() {
        return gender.contains("ث");
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getChoice1() {
        return choice1;
    }

    public void setChoice1(String choice1) {
        this.choice1 = choice1;
    }

    public String getChoice2() {
        return choice2;
    }

    public void setChoice2(String choice2) {
        this.choice2 = choice2;
    }

    public Integer getYearsSum() {
        return yearsSum;
    }
    
}
