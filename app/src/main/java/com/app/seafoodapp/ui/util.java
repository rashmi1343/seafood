package com.app.seafoodapp.ui;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;


import com.app.seafoodapp.Const.ConstApi;
import com.app.seafoodapp.model.Countrystdcode;

import java.util.ArrayList;

public class util {

    static ArrayList<Countrystdcode> list;


    public static String userid="";

    public static double calculategstprice(double amount, double gstpercentage) {

        double res = 0.0;

        try {
          if (!Double.valueOf(amount).equals("")) {
              res = (amount / 100.0f) * gstpercentage;

          }
      }catch(Exception e) {
          Log.d("Exception_gst",e.toString());
      }

        return res;
    }




    public static boolean isOnline(Context ctx) {
        ConnectivityManager cm = (ConnectivityManager)ctx. getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }




            public static void setcountrylist(Context ctx) {

                    list = new ArrayList<>();


                list.add(new Countrystdcode("Afghanistan", "93", "AF"));
                list.add(new Countrystdcode("Albania", "355", "AL"));
                list.add(new Countrystdcode("Algeria", "213", "DZ"));
                list.add(new Countrystdcode("American Samoa", "1684", "AS"));
                list.add(new Countrystdcode("Andorra ", "376", "AD"));
                list.add(new Countrystdcode("Angola ", "244", "AO"));
                list.add(new Countrystdcode("Anguilla ", "1264", "AI"));
                list.add(new Countrystdcode("Antarctica", "672", "AQ"));
                list.add(new Countrystdcode("Antigua", "1268", "AG"));
                list.add(new Countrystdcode("Argentina", "54", "AR"));
                list.add(new Countrystdcode("Armenia ", "374", "AM"));
                list.add(new Countrystdcode("Aruba", "297", "AW"));
                list.add(new Countrystdcode("Ascension", "247",""));
                list.add(new Countrystdcode("Australia ", "61", "AU"));
                list.add(new Countrystdcode("Australian External Territories", "672", ""));
                list.add(new Countrystdcode("Austria", "43", "AT"));
                list.add(new Countrystdcode("Azerbaijan", "994", "AZ"));
                list.add(new Countrystdcode("Bahamas", "1242", "BS"));
                list.add(new Countrystdcode("Bahrain", "973", "BH"));
                list.add(new Countrystdcode("Bangladesh", "880", "BD"));
                list.add(new Countrystdcode("Barbados", "1246", "BB"));
                list.add(new Countrystdcode("Barbuda", "1268", ""));
                list.add(new Countrystdcode("Belarus", "375","BY"));
                list.add(new Countrystdcode("Belgium", "32","BE"));
                list.add(new Countrystdcode("Belize", "501","BZ"));
                list.add(new Countrystdcode("Benin", "229", "BJ"));
                list.add(new Countrystdcode("Bermuda", "1441","BM"));
                list.add(new Countrystdcode("Bhutan", "975","BT"));
                list.add(new Countrystdcode("Bolivia ", "591", "BO"));
                list.add(new Countrystdcode("Bosnia & Herzegovina ", "387", "BA"));
                list.add(new Countrystdcode("Botswana ", "267", "BW"));
                list.add(new Countrystdcode("Brazil ", "55","BR"));
                list.add(new Countrystdcode("British Virgin Islands", "1284", "VG"));
                list.add(new Countrystdcode("Brunei Darussalam", "673","brn" ));

                list.add(new Countrystdcode("Bulgaria", "359", "BG"));
                list.add(new Countrystdcode("Burkina Faso ", "226","BF"));
                list.add(new Countrystdcode("Burundi", "257", "BN"));
                list.add(new Countrystdcode("Cambodia", "855","KH"));
                list.add(new Countrystdcode("Cameroon", "237","CM"));
                list.add(new Countrystdcode("Canada", "1","CA"));
                list.add(new Countrystdcode("Cape Verde Islands", "238","CV"));
                list.add(new Countrystdcode("Cayman Islands", "1345","KY"));
                list.add(new Countrystdcode("Central African Republic", "236","CF"));
                list.add(new Countrystdcode("Chad ", "235","TD"));
                list.add(new Countrystdcode("Chatham Island (New Zealand)", "64","CL"));
                list.add(new Countrystdcode("Chile ", "56","CL"));
                list.add(new Countrystdcode("China", "86","CN"));
                list.add(new Countrystdcode("Christmas Island", "61","CX"));
                list.add(new Countrystdcode("Cocos-Keeling Islands", "61","CK"));
                list.add(new Countrystdcode("Colombia ", "57","CO"));
                list.add(new Countrystdcode("Comoros", "269","KM"));
                list.add(new Countrystdcode("Congo", "242",""));
                list.add(new Countrystdcode("Congo, Dem. Rep. of  (former Zaire) ", "243",""));
                list.add(new Countrystdcode("Cook Islands", "682",""));
                list.add(new Countrystdcode("Costa Rica", "506","CR"));
                list.add(new Countrystdcode("Côte d'Ivoire (Ivory Coast)", "225",""));
                list.add(new Countrystdcode("Croatia", "385","HR"));
                list.add(new Countrystdcode("Cuba", "53","CU"));
                list.add(new Countrystdcode("Cuba (Guantanamo Bay)", "5399","CU"));
                list.add(new Countrystdcode("Curaçao", "5999","CW"));
                list.add(new Countrystdcode("Cyprus", "357","CY"));
                list.add(new Countrystdcode("Czech Republic", "420","CZ"));
                list.add(new Countrystdcode("Denmark", "45","DK"));
                list.add(new Countrystdcode("Diego Garcia", "246",""));
                list.add(new Countrystdcode("Djibouti", "253","DJ"));
                list.add(new Countrystdcode("Dominica", "1767","DM"));
                list.add(new Countrystdcode("Dominican Republic ", "1809","DO"));
                list.add(new Countrystdcode("East Timor", "670","TL"));
                list.add(new Countrystdcode("Easter Island", "56",""));
                list.add(new Countrystdcode("Ecuador ", "593","EC"));
                list.add(new Countrystdcode("Egypt", "20","EG"));
                list.add(new Countrystdcode("El Salvador", "503","SV"));
                list.add(new Countrystdcode("Equatorial Guinea", "240","GQ"));
                list.add(new Countrystdcode("Eritrea", "291","ER"));
                list.add(new Countrystdcode("Estonia", "372","EE"));
                list.add(new Countrystdcode("Ethiopia", "251","ET"));
                list.add(new Countrystdcode("Falkland Islands (Malvinas)", "500","FK"));
                list.add(new Countrystdcode("Faroe Islands", "298","FO"));
                list.add(new Countrystdcode("Fiji Islands", "679","FJ"));
                list.add(new Countrystdcode("Finland", "358","FI"));
                list.add(new Countrystdcode("France", "33","FR"));
                list.add(new Countrystdcode("French Antilles", "596",""));
                list.add(new Countrystdcode("French Guiana", "594",""));
                list.add(new Countrystdcode("French Polynesia", "689","PF"));
                list.add(new Countrystdcode("Gabonese Republic", "241","GA"));
                list.add(new Countrystdcode("Gambia", "220","GM"));
                list.add(new Countrystdcode("Georgia", "995","GE"));
                list.add(new Countrystdcode("Germany", "49","DE"));
                list.add(new Countrystdcode("Ghana ", "233","GH"));
                list.add(new Countrystdcode("Gibraltar ", "350","GI"));
                list.add(new Countrystdcode("Greece ", "30","GR"));
                list.add(new Countrystdcode("Greenland ", "299","GL"));
                list.add(new Countrystdcode("Grenada", "1473",""));
                list.add(new Countrystdcode("Guadeloupe", "590",""));
                list.add(new Countrystdcode("Guam", "1671","GU"));
                list.add(new Countrystdcode("Guantanamo Bay", "5399",""));
                list.add(new Countrystdcode("Guatemala ", "502","GT"));
                list.add(new Countrystdcode("Guinea-Bissau ", "245","GW"));
                list.add(new Countrystdcode("Guinea", "224","GN"));
                list.add(new Countrystdcode("Guyana", "592","GY"));
                list.add(new Countrystdcode("Haiti ", "509","HT"));
                list.add(new Countrystdcode("Honduras", "504","HN"));
                list.add(new Countrystdcode("Hong Kong", "852","HK"));
                list.add(new Countrystdcode("Hungary ", "36","HU"));
                list.add(new Countrystdcode("Iceland", "354","IS"));
                list.add(new Countrystdcode("India", "91","IN"));
                list.add(new Countrystdcode("Indonesia", "62","ID"));
                list.add(new Countrystdcode("Iran", "98","IR"));
                list.add(new Countrystdcode("Iraq", "964",""));
                list.add(new Countrystdcode("Ireland", "353","IE"));
                list.add(new Countrystdcode("Israel ", "972","IL"));
                list.add(new Countrystdcode("Italy ", "39","IT"));
                list.add(new Countrystdcode("Jamaica ", "1876","JM"));
                list.add(new Countrystdcode("Japan ", "81","JP"));
                list.add(new Countrystdcode("Jordan", "962","JO"));
                list.add(new Countrystdcode("Kazakhstan", "7","KZ"));
                list.add(new Countrystdcode("Kenya", "254","KE"));
                list.add(new Countrystdcode("Kiribati ", "686","KI"));
                list.add(new Countrystdcode("Korea (North)", "850",""));
                list.add(new Countrystdcode("Korea (South)", "82",""));
                list.add(new Countrystdcode("Kuwait ", "965","KW"));
                list.add(new Countrystdcode("Kyrgyz Republic", "996",""));
                list.add(new Countrystdcode("Laos", "856","LA"));
                list.add(new Countrystdcode("Latvia ", "371","LV"));
                list.add(new Countrystdcode("Lebanon", "961","LB"));
                list.add(new Countrystdcode("Lesotho", "266","LS"));
                list.add(new Countrystdcode("Liberia", "231","LR"));
                list.add(new Countrystdcode("Libya", "218","LY"));
                list.add(new Countrystdcode("Liechtenstein", "423","LI"));
                list.add(new Countrystdcode("Lithuania ", "370","LT"));
                list.add(new Countrystdcode("Luxembourg", "352","LU"));
                list.add(new Countrystdcode("Macao", "853","MO"));
                list.add(new Countrystdcode("Macedonia (Former Yugoslav Rep of.)", "389","MK"));
                list.add(new Countrystdcode("Madagascar", "261","MG"));
                list.add(new Countrystdcode("Malawi ", "265","MW"));
                list.add(new Countrystdcode("Malaysia", "60","MY"));
                list.add(new Countrystdcode("Maldives", "960","MV"));
                list.add(new Countrystdcode("Mali Republic", "223","ML"));
                list.add(new Countrystdcode("Malta", "356","MT"));
                list.add(new Countrystdcode("Marshall Islands", "692","MH"));
                list.add(new Countrystdcode("Martinique", "596",""));
                list.add(new Countrystdcode("Mauritania", "222","MR"));
                list.add(new Countrystdcode("Mauritius", "230","MU"));
                list.add(new Countrystdcode("Mayotte Island", "269",""));
                list.add(new Countrystdcode("Mexico", "52","MX"));
                list.add(new Countrystdcode("Micronesia, (Federal States of)", "691","FM"));
                list.add(new Countrystdcode("Midway Island", "1808",""));
                list.add(new Countrystdcode("Moldova ", "373",""));
                list.add(new Countrystdcode("Monaco", "377","MC"));
                list.add(new Countrystdcode("Mongolia ", "976","MN"));
                list.add(new Countrystdcode("Montenegro", "382","ME"));
                list.add(new Countrystdcode("Montserrat ", "1664","MS"));
                list.add(new Countrystdcode("Morocco", "212","MA"));
                list.add(new Countrystdcode("Mozambique", "258","MZ"));
                list.add(new Countrystdcode("Myanmar", "95","MM"));
                list.add(new Countrystdcode("Namibia", "264","NA"));
                list.add(new Countrystdcode("Nauru", "674","NR"));
                list.add(new Countrystdcode("Nepal ", "977","NP"));
                list.add(new Countrystdcode("Netherlands", "31","NL"));
                list.add(new Countrystdcode("Netherlands Antilles", "599","AN"));
                list.add(new Countrystdcode("Nevis", "1869",""));
                list.add(new Countrystdcode("New Caledonia", "687","NC"));
                list.add(new Countrystdcode("New Zealand", "64","NZ"));
                list.add(new Countrystdcode("Nicaragua", "505","NI"));
                list.add(new Countrystdcode("Niger", "227",""));
                list.add(new Countrystdcode("Nigeria", "234","NG"));
                list.add(new Countrystdcode("Niue", "683","NU"));
                list.add(new Countrystdcode("Norfolk Island", "672",""));
                list.add(new Countrystdcode("Northern Marianas Islands (Saipan, Rota, & Tinian)", "1670","MP"));
                list.add(new Countrystdcode("Norway", "47","NO"));
                list.add(new Countrystdcode("Oman", "968","OM"));
                list.add(new Countrystdcode("Pakistan", "92","PK"));
                list.add(new Countrystdcode("Palau", "680","PW"));
                list.add(new Countrystdcode("Palestinian Settlements", "970",""));
                list.add(new Countrystdcode("Panama", "507","PA"));
                list.add(new Countrystdcode("Papua New Guinea", "675","PG"));
                list.add(new Countrystdcode("Paraguay", "595","PY"));
                list.add(new Countrystdcode("Peru", "51","PE"));
                list.add(new Countrystdcode("Philippines", "63","PH"));
                list.add(new Countrystdcode("Poland", "48","PL"));
                list.add(new Countrystdcode("Portugal", "351","PT"));
                list.add(new Countrystdcode("Puerto Rico", "1787","PR"));
                list.add(new Countrystdcode("Qatar", "974","QA"));
                list.add(new Countrystdcode("Réunion Island", "262",""));
                list.add(new Countrystdcode("Romania", "40","RO"));
                list.add(new Countrystdcode("Russia", "7","RU"));
                list.add(new Countrystdcode("Rwandese Republic", "250","RW"));
                list.add(new Countrystdcode("St. Helena", "290","SH"));
                list.add(new Countrystdcode("St. Kitts/Nevis", "1869","KN"));
                list.add(new Countrystdcode("St. Lucia", "1758","LC"));
                list.add(new Countrystdcode("St. Pierre & Miquelon", "508","PM"));
                list.add(new Countrystdcode("St. Vincent & Grenadines", "1784","VC"));
                list.add(new Countrystdcode("Samoa", "685","WS"));
                list.add(new Countrystdcode("San Marino", "378","SM"));
                list.add(new Countrystdcode("São Tomé and Principe", "239","ST"));
                list.add(new Countrystdcode("Saudi Arabia", "966","SA"));
                list.add(new Countrystdcode("Senegal ", "221","SN"));
                list.add(new Countrystdcode("Serbia", "381","RS"));
                list.add(new Countrystdcode("Seychelles Republic", "248",""));
                list.add(new Countrystdcode("Sierra Leone", "232","SL"));
                list.add(new Countrystdcode("Singapore", "65","SG"));
                list.add(new Countrystdcode("Slovak Republic", "421","SK"));
                list.add(new Countrystdcode("Slovenia ", "386","SI"));
                list.add(new Countrystdcode("Solomon Islands", "677","SB"));
                list.add(new Countrystdcode("Somali Democratic Republic", "252",""));
                list.add(new Countrystdcode("South Africa", "27","ZA"));
                list.add(new Countrystdcode("Spain", "34","ES"));
                list.add(new Countrystdcode("Sri Lanka", "94","LK"));
                list.add(new Countrystdcode("Sudan", "249","SD"));
                list.add(new Countrystdcode("Suriname ", "597","SR"));
                list.add(new Countrystdcode("Swaziland", "268","SZ"));
                list.add(new Countrystdcode("Sweden", "46","SE"));
                list.add(new Countrystdcode("Switzerland", "41","CH"));
                list.add(new Countrystdcode("Syria", "963","SY"));
                list.add(new Countrystdcode("Taiwan", "886","TW"));
                list.add(new Countrystdcode("Tajikistan", "992","TJ"));
                list.add(new Countrystdcode("Tanzania", "255","TZ"));
                list.add(new Countrystdcode("Thailand", "66","TH"));
                list.add(new Countrystdcode("Timor Leste", "670",""));
                list.add(new Countrystdcode("Togolese Republic", "228",""));
                list.add(new Countrystdcode("Tokelau ", "690","TK"));
                list.add(new Countrystdcode("Tonga Islands", "676",""));
                list.add(new Countrystdcode("Trinidad & Tobago", "1868","TT"));
                list.add(new Countrystdcode("Tunisia", "216","TN"));
                list.add(new Countrystdcode("Turkey", "90 ","TR"));
                list.add(new Countrystdcode("Turkmenistan ", "993","TM"));
                list.add(new Countrystdcode("Turks and Caicos Islands", "1649","TC"));
                list.add(new Countrystdcode("Tuvalu", "688","TV"));
                list.add(new Countrystdcode("Uganda", "256","UG"));
                list.add(new Countrystdcode("Ukraine", "380","UA"));
                list.add(new Countrystdcode("United Arab Emirates", "971","AE"));
                list.add(new Countrystdcode("United Kingdom", "44","GB"));
                list.add(new Countrystdcode("United States of America", "1","US"));
                list.add(new Countrystdcode("US Virgin Islands", "1340",""));
                list.add(new Countrystdcode("Uruguay", "598","UY"));
                list.add(new Countrystdcode("Uzbekistan", "998","UZ"));
                list.add(new Countrystdcode("Vanuatu", "678","VU"));
                list.add(new Countrystdcode("Venezuela", "58","VE"));
                list.add(new Countrystdcode("Vietnam", "84","VN"));
                list.add(new Countrystdcode("Wake Island", "808",""));
                list.add(new Countrystdcode("Wallis and Futuna Islands", "681","WF"));
                list.add(new Countrystdcode("Yemen", "967","YE"));
                list.add(new Countrystdcode("Zambia ", "260","ZM"));
                list.add(new Countrystdcode("Zanzibar", "255",""));
                list.add(new Countrystdcode("Zimbabwe ", "263","ZW"));
            }





    public static void getcountrycode( String countryname) {

        for (int i=0;i<list.size();i++) {
            if (list.get(i).getCountryname().equalsIgnoreCase(countryname)) {
                Log.d("countrycode",list.get(i).getCountrystdcode());
                ConstApi.stdcountrycode = list.get(i).getCountrystdcode();
                break;
            }
        }

    }


}
