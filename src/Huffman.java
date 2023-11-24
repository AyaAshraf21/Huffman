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
            return;
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

        String freqChar = "";
        for(char ch : charFrequency.keySet()) {
            freqChar += ch;
            freqChar += charFrequency.get(ch);
            freqChar += ' ';
        }
        String compressedText = "";
        for(char ch : input.toCharArray()) {
            compressedText += charCode.get(ch);
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
            writer.write(freqChar + "  " + compressedText);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }


    static void decompress(String pathName) {
        String input = readFromFile(pathName);
        Map<Character, Integer> charFreq = new HashMap<>();
        Map<Character, String> charCode = new HashMap<>();
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
           charFreq.put(ch, Integer.parseInt(val));
           i++;
       }
        i += 2;
        Node root = buildHuffmanTree(charFreq);
        generateCode(root , charCode , "");
        String decompressedText = "";
        Node current = root;
        for (; i < input.length(); i++) {
            if (input.charAt(i) == '0') {
                current = current.left;
            }
            else {
                current = current.right;
            }

            if (current != null && current.symbol != '\0') {
                decompressedText += current.symbol;
                current = root;
            }
        }

        File file = new File(fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(decompressedText);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

