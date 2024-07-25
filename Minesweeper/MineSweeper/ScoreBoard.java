package MineSweeper;

import java.io.*;
import java.util.*;

public class ScoreBoard {
    String dbpath;
    LinkedHashMap<String, Double> top3Scores;
    public HashMap<String, Double> getTop3Scores() {
        return top3Scores;
    }

    public void setTop3Scores(String[] names, Double[] scores)
    {
        top3Scores.put(names[0],scores[0]);
        top3Scores.put(names[1],scores[1]);
        top3Scores.put(names[2],scores[2]);
    }


    public String getDbpath() {
        return dbpath;
    }

    public void setDbpath(String dbpath) {
        this.dbpath = dbpath;
    }

    public  HashMap<String,Double> getScoreBoard() {
        HashMap allScores = new HashMap<String, Double>();
        BufferedReader br = null;
        try {
            // create file object
            File file = new File(getDbpath());

            // create BufferedReader object from the File
            br = new BufferedReader(new FileReader(file));
            String line ;
            while ((line = br.readLine()) != null) {

                // split the line by :
                String[] parts = line.split(":");
                String name = parts[0].trim();
                String number = parts[1].trim();
                // put name, number in HashMap if they are not empty
                if (!name.equals("") && !number.equals(""))
                {
                    allScores.put(name,number);
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {

            // Always close the BufferedReader
            if (br != null) {
                try {
                    br.close();
                }
                catch (Exception e) {
                }
            }
        }
        return allScores;
    }

    private void sortScore() //not sure if its ascending or descending order
    {
        HashMap allScores;
        allScores = this.getScoreBoard();
        List<Map.Entry<String, Double>> list = new ArrayList<>(allScores.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Double> >() //making a new Comparator right here
        {
            public int compare(Map.Entry<String, Double> o1,
                               Map.Entry<String, Double> o2)
            {
                return (o1.getValue()).compareTo(o2.getValue());
            }
            });
        // put data from sorted list to hashmap
        HashMap<String, Double> temp = new LinkedHashMap<String, Double>();
        for (Map.Entry<String, Double> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        writeToFile(this.dbpath,temp);

    }
    private void sortScore(HashMap<String, Double> allScores) //not sure if its ascending or descending order will overwrite old file
    {
        List<Map.Entry<String, Double>> list = new ArrayList<>(allScores.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Double> >() //making a new Comparator right here
        {
            public int compare(Map.Entry<String, Double> o1,
                               Map.Entry<String, Double> o2)
            {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });
        // put data from sorted list to hashmap
        HashMap<String, Double> temp = new LinkedHashMap<String, Double>();
        for (Map.Entry<String, Double> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        writeToFile(this.dbpath,temp);

    }

    private void writeToFile(String dbpath, HashMap<String, Double> map)//This Method will Overwrite the old list with the new One
    { //This Method will Overwrite the old list with the new One
        File file = new File(dbpath);
        BufferedWriter bf = null;
        try {
            // create new BufferedWriter for the output file
            bf = new BufferedWriter(new FileWriter(file));
            // iterate map entries
            for (Map.Entry<String, Double> entry :
                    map.entrySet()) {
                // put key and value separated by a colon
                bf.write(entry.getKey() + ":" + entry.getValue());
                // new line
                bf.newLine();
            }
            bf.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {

                // always close the writer
                bf.close();
            } catch (Exception e) {
            }
        }
    }

    static String createScoreDB() //create a file to store scores if non exist
    {
        String dbpath;
        File newFile = new File("scoredb.txt");
        try {
            newFile.createNewFile();
            dbpath = newFile.getPath();
            System.out.println("File Created successfully");
            return (dbpath);
        } catch (IOException e) {
            throw new RuntimeException(e); //should not happen this method should only be called if there is no file
        }

    }
    static void createScoreDB(String dbpath) //create a file to store scores if non exist
    {
        File newFile = new File(dbpath);
        try {
            newFile.createNewFile();
            dbpath = newFile.getPath();
            System.out.println("File Created successfully");
        } catch (IOException e) {
            throw new RuntimeException(e); //should not happen this method should only be called if there is no file
        }

    }
    ScoreBoard(String dbpath) //Constructor
    {
        setDbpath(dbpath);
        try{
            File fil = new File(dbpath);
            Scanner reader = new Scanner(fil);
            String[] names = new String[2];
            Double[] scores = new Double[2];
            for(int i = 0 ; i < 3 ;i++)
            {
                if(reader.hasNextLine()) {
                    String data = reader.nextLine();
                    String[] namenumber = data.split(":");
                    names[i] = namenumber[0];
                    scores[i] = Double.parseDouble(namenumber[1]);
                }
            }
            setTop3Scores(names,scores);
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred: File not found creating new file");
            this.top3Scores = new LinkedHashMap<>();
            createScoreDB(dbpath);
        }
    }
    public void addScore(Player player)
    {
        String name = player.getName();
        Double score = player.getScore();
        HashMap allScores;
        allScores = this.getScoreBoard();
        allScores.put(name, score);
        sortScore(allScores);
        updateTop3(allScores);
    }
    public void updateTop3(HashMap<String, Double> allScores)
    {
        File fil = new File(dbpath);
        Scanner reader = null;
        try {
            reader = new Scanner(fil);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String[] names = new String[2];
        Double[] scores = new Double[2];
        for(int i = 0 ; i < 3 ;i++)
        {
            String data = reader.nextLine();
            String[] namenumber = data.split(":");
            names[i] =namenumber[0];
            scores[i]  = Double.parseDouble(namenumber[1]);
        }
        setTop3Scores(names,scores);
    }
    public void delScore(Player player)
    {
        String name = player.getName();
        Double score = player.getScore();
        delScore(name,score);
    }
    public void delScore(String name)
    {
        HashMap allScores;
        allScores = this.getScoreBoard();
        allScores.remove(name);
        sortScore(allScores);
        updateTop3(allScores);
    }
    public void delScore(String name, Double score)
    {
        HashMap allScores;
        allScores = this.getScoreBoard();
        allScores.remove(name,score);
        sortScore(allScores);
        updateTop3(allScores);
    }
}
