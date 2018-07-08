public class LinkedListDeque<E> implements Deque<E>{

    public DLNode sentinel;
    public int size;



    //Constructor
    public LinkedListDeque(){
        sentinel = new DLNode<E>(null, null, null);
        sentinel.previous = sentinel;
        sentinel.next = sentinel;

        size = 0;
    }



    //Adds element to the beginning fo the deque
    @Override
    public void addFirst(E item){
        DLNode<E> node = new DLNode<E>(this.sentinel, this.sentinel.next, item);
        this.sentinel.next.previous = node;
        this.sentinel.next = node;
        node.next.previous = node; //set the previous link of the second item to the new first item
        size += 1;

        if(this.sentinel.previous == this.sentinel){
            this.sentinel.previous = node;
        }

    }


    //Adds element to the end of the deque
    @Override
    public void addLast(E item){
        DLNode<E> node = new DLNode<E>(this.sentinel.previous, this.sentinel, item);
        this.sentinel.previous.next = node;
        this.sentinel.previous = node;
        node.previous.next = node; //set the next link of the second last item to the new last item
        size += 1;

        if(this.sentinel.next == this.sentinel){
            this.sentinel.next = node;
        }
    }


    //Checks if deque is empty
    @Override
    public boolean isEmpty(){
        if(this.sentinel.next == this.sentinel){
            return true;
        }else{
            return false;
        }
    }


    //Return the size of a deque
    @Override
    public int size(){
        return this.size;
    }


    //Helper method for printDeque()
    public String printDequeHelper(){

        String result = "";
        DLNode pointer = new DLNode();
        pointer = this.sentinel;

        while(pointer.next != this.sentinel){
            pointer = pointer.next;
            result += pointer.item + " ";
        }

        if(!result.equals("")){
            result = result.substring(0, result.length()-1);
        }

        return result;
    }


    @Override
    public void printDeque(){
        System.out.println(this.printDequeHelper());
    }


    @Override
    public E removeFirst(){

        if(this.size == 0){
            return null;
        }else{
            E result = (E) this.sentinel.next.item;
            this.sentinel.next.next.previous = this.sentinel;
            this.sentinel.next = this.sentinel.next.next;
            size -= 1;
            return result;
        }

    }


    @Override
    public E removeLast(){
        if (this.size == 0){
            return null;
        } else {
            E result = (E) this.sentinel.previous.item;
            this.sentinel.previous.previous.next = this.sentinel;
            this.sentinel.previous = this.sentinel.previous.previous;
            this.size -= 1;
            return result;

        }

    }


    @Override
    public E get(int pos){
        DLNode node = new DLNode();
        node = this.sentinel;
        if(pos >= this.size){
            return null;
        }else{
            node = node.next;
            for(int i = 0; i < pos; i++){
                node = node.next;
            }
            return (E) node.item;
        }
    }

    public E getRecursive(int pos){
        if(pos >= this.size){
            return null;
        }else {
            return getRecursiveHelper(pos, this.sentinel.next);
        }
    }

    public static <E> E getRecursiveHelper(int pos, DLNode node){
        if(pos == 0){
            E result = (E) node.item;
            return result;
        }else{
            return getRecursiveHelper(pos -1, node.next);
        }
    }



    //Helper class
    public static class DLNode<E> {

        public DLNode<E> next;
        public DLNode<E> previous;
        public E item;

        public DLNode(DLNode previous, DLNode next, E item){
            this.next = next;
            this.previous = previous;
            this.item = item;
        }
        public DLNode(){

        }
    }
}