class Quick extends Sorter {

    void sort(){
      QuickSort(0,A.length-1);
    }
    int[] QuickSort(int low, int high){
      if(leq(high,low)){
        return A;
      }
      int p = Partition(low,high);
      QuickSort(low,p-1);
      QuickSort(p+1,high);
      return A;
    }

    int Partition(int low, int high){
      int p = (int)(Math.random() * (high - low + 1) + low);

      int temp = A[p];
      A[p] = A[high];
      A[high] = temp;

      int pivot = A[high];
      int left = low;
      int right = high-1;

      while(leq(left,right)){
        while(leq(left,right) && leq(A[left],pivot)){
          left++;
        }
        while(leq(left,right) && leq(pivot,A[right])){
          right --;
        }
        if(lt(left,right)){
          swap(left,right);
        }
      }

      swap(left,high);

      return left;
    }

    String algorithmName() {
        return "quick";
    }
}
