class Solution {
  public static void main(String[] args) {
    String s[][]={{"Bob","87"}, {"Mike", "35"},{"Bob", "52"}, {"Jason","35"}, {"Mike", "55"}, {"Jessica", "50"}};
    Map<String,List<Integer>> scores = new HashMap<String,List<Integer>>();
    for(String[] student:s){
      String name = student[0];
      int score = Integer.parseInt(student[1]);
      if(!scores.containsKey(name))
        scores.put(name, new ArrayList<Integer>());
        scores.get(name).add(score);
    }
     List<Double> averages= new ArrayList<Double>();
    for(String name: scores.keySet()){
       List<Integer> primes=scores.get(name);
       double avg = primes.stream().mapToDouble((x) -> x)
                                   .average().getAsDouble();
       averages.add(avg);
    }
       System.out.println();
    Collections.sort(averages);
    System.out.println("Best Average score:"+averages.get(averages.size()-1));
  }
}
