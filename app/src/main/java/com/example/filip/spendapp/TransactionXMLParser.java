package com.example.filip.spendapp;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Filip on 14. 6. 2015.
 */
public class TransactionXMLParser {

    private ArrayList<Transaction> transactionList = new ArrayList<>();

    public TransactionXMLParser() {
    }

    private void creatXMLFile (){

    }

    public ArrayList<Transaction> parser (XmlPullParser myParser){
        int event;
        String text=null;

        int id = 0;
        double value = 0;
        Date date = null;
        String comment = null;
        String category = null;

        try {
            event = myParser.getEventType();

            while (event != XmlPullParser.END_DOCUMENT) {
                String name=myParser.getName();

                switch (event){
                    case XmlPullParser.START_TAG:
                        break;

                    case XmlPullParser.TEXT:
                        text = myParser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if(name.equals("id")){
                            id = Integer.parseInt(text);
                        }else if(name.equals("Value")) {
                            value = Double.parseDouble(text);
                        }else if(name.equals("Date")){
                            //TODO date parser
                        }else if(name.equals("Coment")){
                            comment = text;
                        }else if (name.equals("Category")){
                            category = text;
                        }else if(name.equals("Transaction")){
                            transactionList.add(new Transaction(id,value,date,comment, category));
                        }
                        break;




                }
                event = myParser.next();
            }

        }

        catch (Exception e) {
            e.printStackTrace();
        }



    return transactionList;
    }

    public void addTrasaction (Transaction transaction){


        XmlSerializer xmlSerializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();

        try {
            //TODO saving xml
            xmlSerializer.setOutput(writer);
            xmlSerializer.startDocument("UTF-8", true);

            xmlSerializer.startTag("", "Transaction");
                xmlSerializer.startTag("", "id");
                    xmlSerializer.text(Integer.toString(transaction.getId()));
                xmlSerializer.endTag("", "id");

                xmlSerializer.startTag("", "Value");
                    xmlSerializer.text(Double.toString(transaction.getValue()));
                xmlSerializer.endTag("","Value");


            xmlSerializer.endTag("","Transaction");


        } catch (IOException e) {
            e.printStackTrace();
        }
        // start DOCUMENT




    }


}
