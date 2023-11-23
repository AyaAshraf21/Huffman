import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<String, Double> characterProbabilityMap = new HashMap<>();
        Huffman h = new Huffman();
        h.countProbability(characterProbabilityMap , "AABBAABCCD");
        System.out.println("Character counts: " + characterProbabilityMap);
        h.calcHuffmanCode(characterProbabilityMap);

    }
}