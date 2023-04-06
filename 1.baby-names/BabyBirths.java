/**
 * 
 * @author Ginny Dang
 * @version July 7th, 2022
 */

import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;

public class BabyBirths {
    public void printNames() {
        FileResource fr = new FileResource();
        // fr.getCSVParser(false), false means no header row
        for (CSVRecord rec : fr.getCSVParser(false)) {
            int numBorn = Integer.parseInt(rec.get(2));
            if (numBorn <= 100) {
            System.out.println("Name " + rec.get(0) + 
                               " Gender " + rec.get(1) +
                               " Num Born " + rec.get(2));
            }
        }
    }
    
    public void totalBirths(FileResource fr) {
        int totalBirths = 0;
        int totalBoys = 0;
        int totalGirls = 0;
        int totalNames = 0;
        int totalBoyNames = 0;
        int totalGirlNames = 0;
        
        for (CSVRecord rec : fr.getCSVParser(false)) {
            int numBorn = Integer.parseInt(rec.get(2));
            totalBirths += numBorn;
            totalNames += 1;
            if (rec.get(1).equals("M")) {
                totalBoys += numBorn;
                totalBoyNames += 1;
            } else {
                totalGirls += numBorn;
                totalGirlNames += 1;
            }
        }
        System.out.println("total births = " + totalBirths);
        System.out.println("total names = " + totalNames);
        System.out.println("total girls = " + totalGirls);
        System.out.println("total girl names = " + totalGirlNames);
        System.out.println("total boys = " + totalBoys);
        System.out.println("total boy names = " + totalBoyNames);
    }
    
    public int totalGenderNames(FileResource fr, String gender) {
        int totalGenderNames = 0;
        for (CSVRecord rec : fr.getCSVParser(false)) {
            if (rec.get(1).equals(gender)) {
                totalGenderNames += 1;
            }
        }
        return totalGenderNames;
    }
    
    public int getRank(int year, String name, String gender) {
        int rank = 0;
        int numBirths = 0;
        //FileResource fr = new FileResource("us_babynames/us_babynames_test/yob" + Integer.toString(year) + "short.csv");
        FileResource fr = new FileResource("us_babynames/us_babynames_by_year/yob" + Integer.toString(year) + ".csv");
        int totalGenderNames = totalGenderNames(fr, gender);
        for (CSVRecord rec : fr.getCSVParser(false)) {
            if (rec.get(1).equals(gender)) {
                rank += 1;
                if (rec.get(0).equals(name)) {
                    break;
                } else if (rank == totalGenderNames) {
                    rank = -1;
                    break;
                }
            }
        }
        //System.out.println("Total Gender Names: " + Integer.toString(totalGenderNames));
        return rank;
    }
    
    public String getName(int year, int rank, String gender) {
        String name = "";
        //FileResource fr = new FileResource("us_babynames/us_babynames_test/yob" + Integer.toString(year) + "short.csv");
        FileResource fr = new FileResource("us_babynames/us_babynames_by_year/yob" + Integer.toString(year) + ".csv");
        for (CSVRecord rec : fr.getCSVParser(false)) {
            if (rec.get(1).equals(gender)) {
                String currName = rec.get(0);
                int currRank = getRank(year, currName, gender);
                if (currRank == rank) {
                    name = currName;
                    break;
                }
            }
        }
        if (name.equals("")) {
            name = "NO NAME";
        }
        return name;
    }
    
    public void whatIsNameInYear(String name, int year, int newYear, String gender) {
        int bornRank = getRank(year, name, gender);
        String newName = getName(newYear, bornRank, gender);
        if (gender.equals("F")) {
            System.out.println(name + " born in " + Integer.toString(year) + " would be " + newName + " if she was born in " + Integer.toString(newYear) + ".");
        } else {
            System.out.println(name + " born in " + Integer.toString(year) + " would be " + newName + " if he was born in " + Integer.toString(newYear) + ".");
        }
    }
    
    public int yearOfHighestRank(String name, String gender) {
        int highestYear = -1;
        int highestRank = Integer.MAX_VALUE;
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            int currYear = Integer.parseInt(f.getName().substring(3, 7));
            int currRank = getRank(currYear, name, gender);
            if (highestRank > currRank && currRank != -1) {
                highestRank = currRank;
                highestYear = currYear;
            }
        }
        return highestYear;
    }
    
    public double getAverageRank(String name, String gender) {
        double avgRank = 0;
        double totalRank = 0;
        int count = 0;
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            int currYear = Integer.parseInt(f.getName().substring(3, 7));
            int currRank = getRank(currYear, name, gender);
            if (currRank == -1) {
                avgRank = -1;
                break;
            } else {
                totalRank += currRank;
                count += 1;
            }
        }
        if (avgRank != -1) {
            avgRank = totalRank / count;
        }
        return avgRank;
    }
    
    public int getTotalBirthsRankedHigher(int year, String name, String gender) {
        int numBirths = 0;
        //FileResource fr = new FileResource("us_babynames/us_babynames_test/yob" + Integer.toString(year) + "short.csv");
        FileResource fr = new FileResource("us_babynames/us_babynames_by_year/yob" + Integer.toString(year) + ".csv");
        for (CSVRecord rec : fr.getCSVParser(false)) {
            if (rec.get(1).equals(gender)) {
                if(rec.get(0).equals(name)) {
                    break;
                }
                numBirths += Integer.parseInt(rec.get(2));
            }
        }
        return numBirths;
    }
    
    public void testTotalBirths() {
        FileResource fr = new FileResource("us_babynames/us_babynames_by_year/yob1905.csv");
        totalBirths(fr);
    }
    
    public void testGetRank() {
        int rank = getRank(1971, "Frank", "M");
        System.out.println(rank);
        //rank = getRank(2012, "Mason", "F");
        //System.out.println(rank);
    }
    
    public void testGetName() {
        String name = getName(1980, 350, "F");
        System.out.println(name);
        name = getName(1982, 450, "M");
        System.out.println(name);
        //name = getName(2012, 7, "M");
        //System.out.println(name);
        //name = getName(2012, 4, "M");
        //System.out.println(name);
    }
    
    public void testWhatIsNameInYear() {
        whatIsNameInYear("Susan", 1972, 2014, "F");
        whatIsNameInYear("Owen", 1974, 2014, "M");
    }
    
    public void testYearOfHighestRank() {
        int highestYear = yearOfHighestRank("Mich", "M");
        System.out.println(highestYear);
    }
    
    public void testGetAverageRank() {
        double avgRank = getAverageRank("Susan", "F");
        System.out.println(avgRank);
        avgRank = getAverageRank("Robert", "M");
        System.out.println(avgRank);
    }
    
    public void testGetTotalBirthsRankedHigher() {
        int numBirthsRankedHigher = getTotalBirthsRankedHigher(1990, "Emily", "F");
        System.out.println(numBirthsRankedHigher);
        numBirthsRankedHigher = getTotalBirthsRankedHigher(1990, "Drew", "M");
        System.out.println(numBirthsRankedHigher);
    }
}