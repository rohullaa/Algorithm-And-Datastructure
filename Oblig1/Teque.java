import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;


class Teque{
  public static void main(String[] args) throws IOException{
    ArrayList<Integer> numbers = new ArrayList<Integer>();

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    int N = Integer.parseInt(br.readLine());
    for (int i = 0; i < N; i++){
      String[] line = br.readLine().split(" ");
      String cmd = line[0];
      int x = Integer.parseInt(line[1]);
      if(cmd.equals("push_front")){
        numbers.add(0,x);
      }else if(cmd.equals("push_middle")){
        int mid = (int) Math.round((numbers.size())/(double) 2) ;
        numbers.add(mid,x);
      }else if( cmd.equals("push_back")){
        numbers.add(numbers.size(),x);
      }else if(cmd.equals("get")){
        System.out.println(numbers.get(x));
      }
    }
  }
}
