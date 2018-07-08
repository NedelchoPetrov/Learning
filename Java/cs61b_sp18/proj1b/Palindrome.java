public class Palindrome {

    public Deque<Character> wordToDeque(String word){
         LinkedListDeque<Character> result = new LinkedListDeque();

         for(char c : word.toCharArray()){
            result.addLast(c);
         }

        return result;
    }


    public boolean isPalindrome(String word){
        Palindrome p = new Palindrome();
        LinkedListDeque<Character> deque = (LinkedListDeque<Character>) p.wordToDeque(word);
        //boolean result = false;

        while(deque.size() > 1){
            char a = deque.removeFirst();
            char b = deque.removeLast();

            if(a != b){
                return false;
            }
        }
        return true;
    }

    public boolean isPalindrome(String word, CharacterComparator cc){
        Palindrome p = new Palindrome();
        LinkedListDeque<Character> deque = (LinkedListDeque<Character>) p.wordToDeque(word);
        //boolean result = false;

        while(deque.size() > 1){
            char a = deque.removeFirst();
            char b = deque.removeLast();

            if(!cc.equalChars(a, b)){
                return false;
            }
        }
        return true;
    }
}
