public class SSort {
	


	//Recursive versions
	public static void sortRekursive(String[] a, int l, int u){
		if (l < u){
			int k = indexOfLargestRekursive(a, l, u);
			String tmp = a[k]; a[k] = a[u]; a[u] = tmp;
			sortRekursive(a, l, u-1);
		}
		//print(a);
	}


	public static int indexOfLargestRekursive(String[] v, int i0, int i1){
		if(i0 >= i1){
			return i1;
		}
		else{
			int k = indexOfLargestRekursive(v, i0 + 1, i1);
			return(v[i0].compareTo(v[k]) > 0) ? i0 : k;
		}
	}



	//Iterative versions
	public static void sortIterative(String[] a, int l, int u){
		while(l < u){
			int k = indexOfLargestIterative(a, l, u);
			String tmp = a[k]; a[k] = a[u]; a[u] = tmp;
			u -= 1;

		}

		print(a);
	}

public static int indexOfLargestIterative(String[] v, int i0, int i1){
	int i, k;
	k = i1;
	for(i = i1-1; i>=i0; i-=1){
		k = (v[i].compareTo(v[k]) > 0) ? i : k;
	}
	return k;
}


//Printing

static void print(String[] a){
	for(int i = 0; i < a.length; i++){
		System.out.print(a[i] + " ");
	}
}

	public static void main(String[] args){
		System.out.println("hueh");
		int u = args.length - 1;

		sortRekursive(args, 0, u);
		sortIterative(args, 0, u);

	}
}