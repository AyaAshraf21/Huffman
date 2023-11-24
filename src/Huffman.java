import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Huffman {

    Map<Character, Integer> countFrequency (String input) {
        Map<Character, Integer> charfreq = new HashMap<>();
        for (char c : input.toCharArray()) {
            charfreq.put(c, charfreq.getOrDefault(c, 0) + 1);
        }
        return charfreq;
    }


    Node buildHuffmanTree (Map<Character, Integer> charfreq){
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

    void generateCode(Node root , Map<Character, String> charCode , String code ){
        if(root == null){
            return;
        }
        if(root.symbol != '\0'){
            charCode.put(root.symbol , code);
        }

        generateCode(root.left , charCode , code+"0");
        generateCode(root.right , charCode , code+"1");
    }


    void compress(String input){
        Map<Character, String> charCode = new HashMap<>();
        Map<Character, Integer> charFrequency = countFrequency(input);
        System.out.println(charFrequency);
        Node root = buildHuffmanTree(charFrequency);
        generateCode(root , charCode , "");

        System.out.println(charCode);
    }


    void decompress(String input){

    }

}

