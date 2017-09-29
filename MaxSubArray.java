import java.util.Arrays;

public class MaxSubArray  {
	static MaxSubArray msa = new MaxSubArray();

	// O(n^2)
	public static MaxArray bruteForce(int[] array) {
		int low = 0;
		int high = 0;
		int sum = array[0];

		for (int i = 0 ; i < array.length ; i++) {
			int temp = 0;
			for (int j = i ; j < array.length ; j++) {
				temp += array[j];

				if (temp > sum) {
					sum = temp;
					low = i;
					high = j;
				}
			}
		}

		MaxArray ma = msa.new MaxArray(low, high, sum);
		return ma;	
	}

	// O(nlogn)
	public static MaxArray findMaxCrossArray(int[] arr, int low, int mid, int high) {

		// Get Maximum Left sum and index
		int leftSum = arr[mid];
		int leftTemp = 0;
		int leftIndex = mid;

		for (int i = mid ; i > 0 ; i--) {
			leftTemp += arr[i];

			if (leftTemp > leftSum) {
				leftSum = leftTemp;
				leftIndex = i;
			}
		}

		// Get Maximum Right sum and index
		int rightSum = arr[mid + 1];
		int rightTemp = 0;
		int rightIndex = mid + 1;

		for (int j = mid + 1; j < arr.length ; j++) {
			rightTemp += arr[j];

			if (rightTemp > rightSum) {
				rightSum = rightTemp;
				rightIndex = j;
			}
		}

		return msa.new MaxArray(leftIndex, rightIndex, leftSum + rightSum);
	}

	public static MaxArray findMaxArray(int[] arr, int low, int high) {
		if (low == high) {
			return msa.new MaxArray(low, high, arr[low]);
		}
		else {
			int mid = Math.floorDiv((low + high), 2);
			MaxArray leftSide = findMaxArray(arr, low, mid); // case 1: low and high on the left-half
			MaxArray rightSide = findMaxArray(arr, mid + 1, high); // case 2: low and high on the right-half
			MaxArray across = findMaxCrossArray(arr, low, mid, high); // case 3: low on left, high on right

			if (leftSide.sum >= rightSide.sum && leftSide.sum >= across.sum) {
				return leftSide;
			} else if (rightSide.sum >= leftSide.sum && rightSide.sum >= across.sum) {
				return rightSide;
			} else {
				return across;
			}
		}
	}

	class MaxArray {
		int lowIndex;
		int highIndex;
		int sum;

		public MaxArray(int lowIndex, int highIndex, int sum) {
			this.lowIndex = lowIndex;
			this.highIndex = highIndex;
			this.sum = sum;
		}
	}

	// O(n)
	public static int linearMaxSubArray(int[] array) {
		int max = array[0]; 
		int tempMax = 0;

		for (int i = 0; i < array.length; i++) {
			tempMax = tempMax + array[i];
			if (max < tempMax)
				max = tempMax;
			if (tempMax < 0)
				tempMax = 0;
		}
		return max;
	}


	public static void main(String[] args) {
		int[] array = {13, -3, -25, 20, -3, -16, -23, 18, 20, -7, 12, -5, -22, 15, -4, 7};

		System.out.println("Array: "+ Arrays.toString(array));

		MaxArray bruteForceResult = bruteForce(array);
		System.out.printf("Brute-Force: The maximum subarray has low index: %d, high index: %d, and max sum: %d",bruteForceResult.lowIndex, bruteForceResult.highIndex, bruteForceResult.sum);
		
		MaxArray divideAndConquerResult = findMaxArray(array, 0, array.length - 1);
		System.out.printf("\nDivide-and-Conquer: The maximum subarray has low index: %d, high index: %d, and max sum: %d", divideAndConquerResult.lowIndex, divideAndConquerResult.highIndex, divideAndConquerResult.sum);
		
		System.out.printf("\nLinear-Solution: The maximum subarray has max sum: %d", linearMaxSubArray(array));
	}
}
