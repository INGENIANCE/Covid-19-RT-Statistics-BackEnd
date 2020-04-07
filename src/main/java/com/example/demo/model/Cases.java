package com.example.demo.model;

import java.util.HashMap;
import java.util.Map;

public class Cases {

    private Map<String,String[]> cases=new HashMap<>();
    private static final Cases instance=new Cases();

    private Cases(){}

    public static Cases getInstence(){ return instance; }

    public boolean add(String country, String cases, String deaths, String recovered){
        String[] info=this.cases.get(country);
        if(info==null){
            this.cases.put(country, new String[]{cases,deaths,recovered});
            return true;
        }else{
            boolean ok=false;
            if(!info[0].equals(cases)) {
                ok=true;
                info[0]=cases;
            }
            if(!info[1].equals(deaths)) {
                ok=true;
                info[1]=deaths;
            }
            if(!info[2].equals(recovered)){
                ok=true;
                info[2]=recovered;
            }
            return ok;
        }
    }

}
