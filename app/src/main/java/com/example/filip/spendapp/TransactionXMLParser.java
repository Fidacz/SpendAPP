package com.example.filip.spendapp;

import android.util.Xml;

import com.example.filip.spendapp.data.Transaction;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;

/**
 *
 * Created by Filip on 14. 6. 2015.
 */
public class TransactionXMLParser {



    public TransactionXMLParser() {
    }



    public static ArrayList<Transaction> parser (XmlPullParser myParser){
        int event;
        String text=null;

        ArrayList<Transaction> transactionList = new ArrayList<>();
        int id = 0;
        double value = 0;
        String date = null;
        String comment = null;
        String category = null;
        int type = 0;

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
                            date = text;
                        }else if(name.equals("Coment")){
                            comment = text;
                        }else if (name.equals("Category")) {
                            category = text;
                        }else if (name.equals("type")){
                            type = Integer.parseInt(text);

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

    public static String creteXML (ArrayList<Transaction> transactions){
        // vytvareni xml

        XmlSerializer xmlSerializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();

        try {

            xmlSerializer.setOutput(writer);
            xmlSerializer.startDocument("UTF-8", true);
            xmlSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
            xmlSerializer.startTag("", "Export");

            for (int i = 0 ; transactions.size() != i; i++ ) {
                Transaction transaction = transactions.get(i);

                xmlSerializer.startTag("", "Transaction");
                xmlSerializer.startTag("", "id");
                xmlSerializer.text(Integer.toString(transaction.getId()));
                xmlSerializer.endTag("", "id");

                xmlSerializer.startTag("", "Value");
                xmlSerializer.text(Double.toString(transaction.getValue()));
                xmlSerializer.endTag("", "Value");

                xmlSerializer.startTag("", "Date");
                xmlSerializer.text(transaction.getDate());
                xmlSerializer.endTag("", "Date");

                xmlSerializer.startTag("", "Category");
                xmlSerializer.text(transaction.getCategory().getName());
                xmlSerializer.endTag("", "Category");

                if (transaction.getCategory().getMasterCategory() != null){
                    //pokud existuje masterCategory
                    xmlSerializer.startTag("", "MasterCategory");
                    xmlSerializer.text(transaction.getCategory().getMasterCategory());
                    xmlSerializer.endTag("", "MasterCategory");
                }
                xmlSerializer.startTag("", "Coment");
                xmlSerializer.text(transaction.getComment());
                xmlSerializer.endTag("", "Coment");

                xmlSerializer.startTag("", "Type");
                xmlSerializer.text(String.valueOf(transaction.getType()));
                xmlSerializer.endTag("", "Type");
                xmlSerializer.endTag("", "Transaction");



            }
            xmlSerializer.endTag("", "Export");

            xmlSerializer.endDocument();

            return writer.toString();


        } catch (IOException e) {
            e.printStackTrace();
        }
        // start DOCUMENT
        return null;



    }


}
