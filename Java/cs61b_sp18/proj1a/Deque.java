public interface Deque<E> {
    public void addFirst(E item);

    public void addLast(E item);

    public boolean isEmpty();

    public int size();

    public void printDeque();

    public E removeFirst();

    public E removeLast();

    public E get(int index);
}
