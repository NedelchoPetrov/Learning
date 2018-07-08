public class ArrayDeque<E> implements Deque<E>{

    public E[] items;
    int size;
    int first;
    int last;



    public ArrayDeque(){
        this.items = (E[]) new Object[8];
        this.size = 0;
        this.first = 0;
        this.last = 1;
    }


    @Override
    public void addFirst(E item){
        items[first] = item;
        size += 1;
        first -= 1;

        if(this.first < 0){
            first = items.length-1;
        }

        if(size == items.length){
            first += 1;     //When the last item is inserted the first and last
            last -= 1;      //are actually swapped
            this.resize();
        }
    }


    @Override
    public void addLast(E item){
        items[last] = item;
        size += 1;
        last +=1;

        if(last == items.length){
            last = 0;
        }
        if(size == items.length){
            last += 1;
            first -= 1;
            this.resize();
        }
        return;
    }


    public void resize(){
        //TODO: Check for bug with firstpart negative;
        E[] newArray = (E[]) new Object[items.length*2];
        int firstpart = items.length-1-last;
        System.arraycopy(items, first, newArray, 0, firstpart);
        System.arraycopy(items, 0, newArray, firstpart, last +1);

        this.items = newArray;
        first = newArray.length - 1;
        last = size;
    }


    @Override
    public boolean isEmpty(){
        if(size == 0){
            return true;
        }else{
            return false;
        }
    }


    @Override
    public int size(){
        return size;
    }


    public String printDequeHelper(){
        String result = "";

        if(size == 0){

            return result;

        }else if(first < last){

            for(int i = first + 1; i < last; i++){
                result += items[i] + " ";
            }

            result = result.substring(0, result.length()-1);
            return result;

        }else{

            for(int i = first + 1; i < items.length; i ++){
                result += items[i] + " ";
            }
            for(int i = 0; i < last; i ++){
                result += items[i] + " ";
            }

            result = result.substring(0, result.length()-1);
            return result;
        }
    }


    @Override
    public void printDeque(){
        String result = printDequeHelper();
        System.out.println(result);
    }


    @Override
    public E removeFirst(){
            if(size == 0){
                return null;
            }else{
                first += 1;
                if(first == items.length){
                    first = 0;
                }
                E result = items[first];
                items[first] = null;
                size -= 1;
                return result;
            }
    }


    @Override
    public E removeLast(){
        E result;
        if(size == 0){
            return null;
        }else{
            last -= 1;
            if(last < 0){
                last = items.length -1;
            }
            result = items[last];
            items[last] = null;
            size -= 1;
            return result;
        }
    }


    @Override
    public E get(int pos){
        if(pos >= items.length){
            return null;
        }else {
            return items[pos];
        }
    }
}
