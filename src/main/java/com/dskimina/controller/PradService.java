package com.dskimina.controller;

import com.dskimina.mapping.Odczyt;
import com.dskimina.mapping.PradOdczyt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by daniel on 31.07.16.
 */
@Service
public class PradService {

    @Autowired
    private DataSource ds;

    @Autowired
    private PradRepository pradRepo;

public static String[][] tab = {
        new String[]{"2004-09-01", "0", "2"},
        new String[]{"2004-11-29","0","2174"},
        new String[]{"2005-05-31","4787","7055"},
        new String[]{"2005-11-24","4159","11206"},
        new String[]{"2006-05-25","4826","16340"},
        new String[]{"2006-11-23","4037","20711"},
        new String[]{"2007-05-25","5147","25696"},
        new String[]{"2007-11-21","4380","29928"},
        new String[]{"2008-05-27","4888","35367"},
        new String[]{"2008-11-24","4162","39876"},
        new String[]{"2009-05-20","5168","45053"},
        new String[]{"2009-11-23","4635","50200"},
        new String[]{"2010-05-19","5235","55725"},
        new String[]{"2010-11-19","5018","60545"},
        new String[]{"2011-05-19","5710","65568"},
        new String[]{"2011-11-22","4867","69418"},
        new String[]{"2012-05-21","5044","73736"},
        new String[]{"2012-11-21","3782","77860"},
        new String[]{"2013-05-20","4336","82240"},
        new String[]{"2013-11-20","4137","86014"},
        new String[]{"2014-05-23","4292","89850"},
        new String[]{"2014-11-15","3690","92990"},
        new String[]{"2015-05-12","3755","96218"},
        new String[]{"2015-11-13","3230","98823"},
        new String[]{"2016-05-16","3265","102247"},
        new String[]{"2016-10-31","2535","0"}
};


    public List<PradOdczyt> getOdczyty(){
        List<PradOdczyt> odczyty = new LinkedList<PradOdczyt>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for(String[] row : tab){
            try {
                Date d = sdf.parse(row[0]);
                Double predicted = Double.parseDouble(row[1].trim());
                Double current = Double.parseDouble(row[2].trim());
                PradOdczyt po = new PradOdczyt();
                po.setTime(d);
                po.setCurrent(current);
                po.setPredicted(predicted);

                odczyty.add(po);
            }catch(ParseException ex){
                ex.printStackTrace();
            }

        }

        return odczyty;
    }

    public List<Long> getDates() throws SQLException{
        List<Long> dates = new ArrayList<>();
        try(ResultSet r = ds.getConnection().createStatement().executeQuery("select * from prad")){
            while(r.next()){
                dates.add(r.getLong("date"));
            }
        }
        return dates;
    }

    public List<Double> getPredicts() throws SQLException{
        List<Double> predicts = new ArrayList<>();

        for(Odczyt o : pradRepo.getAll()){
            predicts.add(o.getPredicted());
        }

        return predicts;
    }


    public static void main(String[] args) throws Exception{

        Class.forName("org.sqlite.JDBC");
        Connection c = DriverManager.getConnection("jdbc:sqlite:/home/daniel/db/rachunki.sqlite");

        System.out.println("Opened database successfully");

        PreparedStatement ps = c.prepareStatement("insert into prad(date, predicted, current) values (?, ?, ?)");


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for(String[] row : tab){
            Date d = sdf.parse(row[0]);
            Double predicted = Double.parseDouble(row[1].trim());
            Double current = Double.parseDouble(row[2].trim());

            ps.setDate(1, new java.sql.Date(d.getTime()));
            ps.setDouble(2, predicted);
            ps.setDouble(3, current);

            ps.executeUpdate();

            System.out.println(d.toString()+" "+predicted+" "+current);
        }
    }
}
