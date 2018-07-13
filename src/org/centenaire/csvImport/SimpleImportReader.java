package org.centenaire.csvImport;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.lang3.StringUtils;
import org.centenaire.edition.ExtraInfoStudent;
import org.centenaire.edition.entities.Exams;
import org.centenaire.edition.entities.Mark;
import org.centenaire.edition.entities.Individuals;
import org.centenaire.general.GeneralController;

public class SimpleImportReader extends AbstractImportReader {

	
	/**
	 * Simplest import reader possible: 
	 * creates new Student and new Mark, 
	 * without checking whether they already exist in the database.
	 * 
	 * Using the simple solution provided by https://www.mkyong.com/java/how-to-read-and-parse-csv-file-in-java/
	 */

	@Override
	public void importReader(String path) {
		GeneralController gc = GeneralController.getInstance();
        String line = "";
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(
        		new InputStreamReader(
        				new FileInputStream(path), "UTF8"))) {
            /* Assuming the format of the csv file is:
            * _ a single header line
            * _ values separated by commas
            * _ header="N° APOGEE,NOM,PRENOM,E-mail,numéro dans notre classement APB,Note S1,Note S2"
            */
        	
        	br.readLine(); // header line
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] elements = line.split(cvsSplitBy);
                
                Individuals stud = new Individuals(
                		StringUtils.capitalize(elements[2].toLowerCase()), //first_name
                		StringUtils.capitalize(elements[1].toLowerCase())  //last_name
                		);
                gc.getStudentDao().create(stud); // should update index accordingly
                
                ExtraInfoStudent info = new ExtraInfoStudent(
                		false, //	boolean hasApbNum;
                		"", //String apbNum;
                		true, // boolean hasApogeeNum;
                		elements[0], // String apogeeNum;
                		!(elements[3].equals("")), // boolean hasEmail;
                		elements[3] //private String email;
                		);
                gc.getStudentDao().updateInfo(stud, info);
                
                // elements[5] corresponds to the exam with index 1
                Exams exam0 = gc.getExamsDao().find(1);
                try{
                	float markFloat = Float.parseFloat(elements[5]);
                    Mark mark = new Mark(
                    		markFloat,// float mark;
                    		stud, // Student student;
                    		exam0 // Exams exam;
                    		);
                    gc.getMarkDao().create(mark);
                } catch (NumberFormatException e){
                // case when elements[5] cannot be parsed to a float:
                // NumberFormatException
                	// do nothing
                }

                
             // elements[6] corresponds to the exam with index 2
                Exams exam1 = gc.getExamsDao().find(2);
                try{
                	float markFloat = Float.parseFloat(elements[6]);
                    Mark mark = new Mark(
                    		markFloat,// float mark;
                    		stud, // Student student;
                    		exam1 // Exams exam;
                    		);
                    gc.getMarkDao().create(mark);
                } catch (NumberFormatException e){
                // case when elements[6] cannot be parsed to a float:
                // NumberFormatException
                	// do nothing
                }
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	public String toString(){
		return "SimpleImportReader";
	}
}
