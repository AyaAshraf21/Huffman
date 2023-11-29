import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Huffman {

    static String readFromFile(String pathName) {
        File file = new File(pathName);
        String text = "";
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                text += line;
            }

        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        return text;
    }


    static Map<Character, Integer> countFrequency (String input) {
        Map<Character, Integer> charfreq = new HashMap<>();
        for (char c : input.toCharArray()) {
            charfreq.put(c, charfreq.getOrDefault(c, 0) + 1);
        }
        return charfreq;
    }


    static Node buildHuffmanTree (Map<Character, Integer> charfreq){
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
        for(char c : charfreq.keySet()){
            priorityQueue.add(new Node(c , charfreq.get(c)));
        }

        while (priorityQueue.size() > 1) {
            Node left = priorityQueue.poll();
            Node right = priorityQueue.poll();

            Node newNode = new Node('\0', left.frequency + right.frequency);
            newNode.left = left;
            newNode.right = right;
            priorityQueue.add(newNode);
        }
        return priorityQueue.poll();
    }

    static void generateCode(Node root , Map<Character, String> charCode , String code ){
        if(root == null){
            return ;
        }
        if(root.symbol != '\0'){
            charCode.put(root.symbol , code);
        }

        generateCode(root.left , charCode , code+"0");
        generateCode(root.right , charCode , code+"1");
    }


    static void compress(String pathName){
        String input = readFromFile(pathName);
        Map<Character, String> charCode = new HashMap<>();
        Map<Character, Integer> charFrequency = countFrequency(input);
        Node root = buildHuffmanTree(charFrequency);
        generateCode(root , charCode , "");

        String binaryText = "" , compressedText = "";
        for(char ch : input.toCharArray()) {
            binaryText += charCode.get(ch);
        }
        for(int i = 0; i < binaryText.length(); i+=8) {
            String binaryString =  binaryText.substring(i, Math.min(i + 8, binaryText.length()));
            int intValue = Integer.parseInt(binaryString, 2);
            compressedText += (char) intValue;
        }

        String huffmanString = "";
        for(char ch : charCode.keySet()){
            huffmanString += ch;
            huffmanString += charCode.get(ch);
            huffmanString += " ";
        }

        String fileName = "compress.bin";
        try {
            Path filePath = Paths.get(fileName);
            Files.createFile(filePath);
            System.out.println("File created successfully at: " + filePath.toAbsolutePath());
        }
        catch (IOException e) {
            System.err.println("An error occurred while creating the file: " + e.getMessage());
        }
        File file = new File(fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))){
            writer.write( huffmanString+ "  " + compressedText);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }


    static void decompress(String pathName) {
        String input = readFromFile(pathName);
        //Map<Character, Integer> charFreq = new HashMap<>();
        Map<String, Character> charCode = new HashMap<>();
        String fileName = "decompress.txt";

        try {
            Path filePath = Paths.get(fileName);
            Files.createFile(filePath);
            System.out.println("File created successfully at: " + filePath.toAbsolutePath());
        }
        catch (IOException e) {
            System.err.println("An error occurred while creating the file: " + e.getMessage());
        }


       int i = 0;
       while(input.charAt(i) != ' ' && input.charAt(i + 1) != ' ') {
           char ch = input.charAt(i);
           String val = "";
           i++;
           while(input.charAt(i) != ' ') {
               val += input.charAt(i);
               i++;
           }
           charCode.put(val , ch);
           i++;
       }
        i += 2;

        String decompressedText = "" , substr = "";
        for (; i < input.length(); i++) {
            if(!charCode.containsKey(substr)){
                substr += input.charAt(i);
            }
            else{
                decompressedText += charCode.get(substr);
                substr = "";
            }
        }

        System.out.println(decompressedText);

        File file = new File(fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(decompressedText);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

