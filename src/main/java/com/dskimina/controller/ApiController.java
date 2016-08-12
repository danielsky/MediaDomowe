package com.dskimina.controller;

import com.dskimina.mapping.PradOdczyt;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Created by daniel on 31.07.16.
 */
@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private PradService pradService;



    @RequestMapping(value = "/prad", method = RequestMethod.GET)
    public List<PradOdczyt> getValues(){
        List<PradOdczyt> odczyty = pradService.getOdczyty();
        Date previousDate = odczyty.get(0).getTime();
        for(PradOdczyt po : odczyty){
            Date currentDate = po.getTime();
            Days dd = Days.daysBetween(LocalDate.fromDateFields(previousDate), LocalDate.fromDateFields(currentDate));
            po.setDifference(dd.getDays());
            previousDate = po.getTime();
        }


        return odczyty;
    }

    @RequestMapping(value = "/dates", method = RequestMethod.GET)
    public List<Long> getDates() throws SQLException{
        return pradService.getDates();
    }

    @RequestMapping(value = "/predicts", method = RequestMethod.GET)
    public List<Double> getPredicts() throws SQLException{
        return pradService.getPredicts();
    }


}
