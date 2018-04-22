public class Shove{
	

	public static boolean checkK(int[] array, int i, int max){
		int k;
		int pivot = array[i];
		boolean result = true;

		for(k = i; k < max; k++){
			if(array[k]<pivot){
				result = false;
			}
		}
		return result;
	}

	public static void moveOver(int[] array){

		int max = array.length;
		int[] result = new int[max];
		for (int i = 0; i < max; i++){
			if(checkK(array, i, max)){

				int current = i;
				
				

				for(int j = 0; j< i; j++){
					result[j] = array[j];
				}
				result[i] = array[max-1];
				for(int k = i; k< max-1; k++){
					result[k+1] = array[k];
				}

				
				
				break;
				}
				
			}
		
		printArray(result);
	}

	public static void printArray(int[] array){
		System.out.print("[");
		for (int x : array){
			System.out.print(x + ", ");
		}
		System.out.print("]");
		System.out.println("");
	}


public static void main(String[] args){
	int[] a = { 1, 9, 4, 3, 0, 12, 11, 9, 15, 22, 12 };

	int[] b = { -2, 1, 9, 4, 3, 0, 12, 11, 9, 15, 22 };

	moveOver(a);
	moveOver(b);

}

		




}