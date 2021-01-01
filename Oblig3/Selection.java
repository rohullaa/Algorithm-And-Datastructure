class Selection extends Sorter {

    void sort(){
      for(int i = 0; leq(i,n -1); i++){
        int  k = i;
        for(int j = i+1; leq(j, n-1); j++){
          if(lt(A[j],A[k])){
            k = j;
          }
        }
        if(noteq(i,k)){
          swap(i,k);
        }
      }
    }

    String algorithmName() {
        return "selection";
    }
}
