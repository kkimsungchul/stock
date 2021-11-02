package com.sungchul.stock.csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@AllArgsConstructor
@Service("csvService")
public class CSVService {

    public List<List<String>> readToList() {
        List<List<String>> list = new ArrayList<List<String>>();
        File csv = new File("20211102_stockCode.csv");
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(csv));
            Charset.forName("UTF-8");
            String line = "";
            int i=0;
            while((line=br.readLine()) != null) {
                String[] tempToken = line.split(",");
                String[] stock = {tempToken[1] ,tempToken[2]};
                List<String> tempList = new ArrayList<String>(Arrays.asList(stock));
                list.add(tempList);
            }

        } catch (FileNotFoundException e) {
            log.info("Error : {}",e);
        } catch (IOException e) {
            log.info("Error : {}",e);
        } finally {
            try {
                if(br != null) {br.close();}
            } catch (IOException e) {
                log.info("Error : {}",e);
            }
        }

        return list;
    }
}
