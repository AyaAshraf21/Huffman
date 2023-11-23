import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Huffman {

    Map<String, Double> characterProbabilityMap = new HashMap<>();

    void countProbability (Map<String, Double> charprob , String input) {
        for (char c : input.toCharArray()) {
            String charKey = String.valueOf(c);
            charprob.put(charKey, charprob.getOrDefault(charKey, 0.0) + 1.0);
        }
        for (Map.Entry<String, Double> entry : charprob.entrySet()) {
            entry.setValue(entry.getValue() / input.length());
        }
    }

    void calcHuffmanCode(Map<String, Double> charprob){
        List<Map.Entry<String, Double>> entryList = new ArrayList<>(charprob.entrySet());
        entryList.sort(Map.Entry.<String, Double>comparingByValue().reversed());


        while (entryList.size() > 2){
            Map.Entry<String , Double> lastProb = entryList.remove(entryList.size()-1);
            Map.Entry<String , Double> secondlastProb = entryList.remove(entryList.size()-1);

            String sumKey = lastProb.getKey()+secondlastProb.getKey();
            double sumValue = lastProb.getValue()+secondlastProb.getValue();

            Map.Entry<String , Double> sumLastProb = new HashMap.SimpleEntry<>(sumKey , sumValue);
            entryList.add(sumLastProb);
            entryList.sort(Map.Entry.<String, Double>comparingByValue().reversed());

        }
        System.out.println(entryList);

    }

}

