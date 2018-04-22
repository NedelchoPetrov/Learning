public class ThreeSum {


	public static boolean threeSum(int[] input){

		//boolean result;
		int length = input.length;

		for(int i = 0; i < length; i++){
			for(int j = 0; j < length; j++){
				for(int k = 0; k < length; k++){

					if(input[k] + input[j] + input[i] == 0 && k != j && k != i && j != i){
						return true;
					}


				}
			}
		}


		return false;
	}



public static void main(String[] args){
	
	int[] a = {-6, 2, 4};
	int[] b = {-6, 2, 5};
	int[] c = {-6, 3, 10, 200};
	int[] d = {8, 2, -1, 15};
	int[] e = {8, 2, -1, -1, 15};
	int[] f = {5, 1, 0, 3, 6};


	System.out.println(threeSum(a));
	System.out.println(threeSum(b));
	System.out.println(threeSum(c));
	System.out.println(threeSum(d));
	System.out.println(threeSum(e));
	System.out.println(threeSum(f));



}


}