import java.lang.Math;
class Heap extends Sorter {

    void sort(){
      heapSort();
    }
    void heapSort(){
      buildMaxHeap();
      for(int i=n-1;leq(0,i); i--){
        swap(0,i);
        bubbleDown(0,i);
      }
    }

    void bubbleDown(int i, int n){
      int largest = i;
      int left = 2*i +1;
      int right = 2*i +2;

      if (lt(left,n)  && lt(A[largest],A[left])){
        int a = largest;
        largest = left;
        left = a;
      }

      if (lt(right,n) && lt(A[largest],A[right])){
        int a = largest;
        largest = right;
        right = a;
      }

      if(noteq(i,largest)){
        swap(i,largest);
        bubbleDown(largest,n);
      }
    }

    void buildMaxHeap(){
      int n_half = Math.round(n/2);
      for(int i = n_half; leq(0,i); i--){
        bubbleDown(i,n);
      }
    }



    String algorithmName() {
        return "heapsort";
    }
}
