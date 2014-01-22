//https://www.bionicspirit.com/blog/2012/02/09/howto-build-naive-bayes-classifier.html
//http://idibon.com/natural-language-processing-tutorial-with-ebert/

public class NaiveBayes {
	/*
	 * Returns the probability of A given B
	 * Takes 3 doubles. Probability of B given A, Probability of A and Probability of B. Probabilities should be in decimal form
	 */
	public double simpleNaiveBayesProbA_B(double probB_A, double ProbA, double probB){
		double probA_B = 0;
		probA_B = probB_A * ProbA;
		probA_B = probA_B / probB;
		return probA_B;
	}//end method
	
	public double naiveBayesProbA_B(double probA[], double probB[]){
		//multiply the denominator
		double denominator = multipleArrayOfDoubles(probB);
		double numerator = multipleArrayOfDoubles(probA);
		return numerator / denominator;
	}//end method
	
	/*
	 * Multiplies an array of doubles
	 * Takes an array of doubles and returns a double
	 */
	private double multipleArrayOfDoubles(double array[]){
		double total = array[0];
		for(int i=0; i<array.length; i++){
			if(i < array.length-1){
				total = total * array[i+1];
			}else{
				break;
			}//end if
		}//end for
		return total;
	}//end method
}
