public class Main {
	
	
	//multiply layer
	//calculate loss
	//propogate
	
	public static void main(String[] args){
		Matrixf a = new Matrixf(new float[][]{
				{1, 0, 2, 3},
				{3, 4, 2, 1},
				{5, 2, 1, 0},
				{0, 2, 1, 0}
		}, null);
		Matrixf b = new Matrixf(new float[][]{
				{2, 2, 2, 1},
				{3, 2, 0, 1},
				{-1, 2, 0, 0},
				{-1, 0, 0, 0}
		}, null);
		Matrixf c = new Matrixf(new float[][]{
				{1},
				{1},
				{3},
				{-2}
		}, "input");
		
		//System.out.println(a.multiply(b));
		
		Perceptron model = new Perceptron(2, 5, 4);
		model.calculate(c);
	}
}
