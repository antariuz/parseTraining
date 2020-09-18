import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main {

    private static boolean isSameSize(Elements list1, Elements list2) {
        return list1.size() == list2.size();
    }

    public static void main(String[] args) throws IOException, MyException {

        //Getting HTML document by Jsoup
        String url = "https://mysku.ru";
        Document document = Jsoup.connect(url)
                .userAgent("Chrome/4.0.249.0 Safari/532.5")
                .referrer("https://www.google.com")
                .get();

        //Selecting topic names and dates
        Elements listTopicName = document.select("div.topic-title");
        Elements listTopicDate = document.getElementsByClass("date");

        //Putting topic names and dates to our collection
        Map<String, String> parsedList = new HashMap<String, String>();
        if (isSameSize(listTopicName, listTopicDate)) {
            for (int i = 0; i < listTopicDate.size(); i++) {
                parsedList.put(listTopicName.get(i).text(), listTopicDate.get(i).text());
            }

            //Just showing what we have
            for (Map.Entry<String, String> map : parsedList.entrySet()) {
                System.out.println(map.getKey() + " - " + map.getValue());
            }
        } else throw new MyException("Something has gone wrong! Check sizes of parsed elements");

        // Writing text file of HashMap
        File file = new File("parsedList.txt");
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(file));
            for (Map.Entry<String, String> pair : parsedList.entrySet()) {
                bufferedWriter.write(pair.getKey() + " - " + pair.getValue());
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
