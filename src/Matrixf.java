public class Matrixf {
	int rows;
	int columns;
	String name;
	public float[][] values;
	public Matrixf(int rows, int columns, String name){
		this.name = name;
		this.rows = rows;
		this.columns = columns;
		values = new float[rows][columns];
	}
	
	public Matrixf(float[][] init, String name){
		this.name = name;
		rows = init.length;
		columns = init[0].length;
		values = init;
	}
	
	public float get(int r, int c){
		return values[r][c];
	}
	
	public void set(int r, int c, float value){
		values[r][c] = value;
	}
	public void add(int r, int c, float value){
		values[r][c] += value;
	}
	
	public void set(Matrixf new_matrix){
		//System.out.println("setting: ");
		//System.out.println("from: \n"+new_matrix);
		//System.out.println("to: \n"+this);
		values = new_matrix.values;
		//System.out.println("now: \n" + this);
		//System.out.println("done setting");
	}
	
	public Matrixf sigmoid(){
		Matrixf matrix = new Matrixf(rows, columns, "<sigmoid>");
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < columns; j++){
				matrix.values[i][j] = Perceptron.sigmoid(values[i][j]);
			}
		}
		return matrix;
	}
	
	
	public Matrixf multiply(Matrixf matrix){
		if(columns != matrix.rows){
			System.out.println("error incompatible matricies");
			System.out.println(this);
			System.out.println(matrix);
			return null;
		}
		
		Matrixf product = new Matrixf(rows, matrix.columns, "<product>");
		
		for(int row = 0; row < product.rows; row++){
			for(int column = 0; column < product.columns; column++){
				product.set(row, column, 0.0f);
				for(int k = 0; k < columns; k++){
					product.add(row, column, get(row, k) * matrix.get(k, column));
				}
			}
		}
		
		
		//System.out.println(rows+" "+columns+") ("+matrix.rows+" "+matrix.columns+") ("+product.rows+" "+product.columns);
		return product;
	}
	
	public String toString(){
		String result = "<"+rows+" "+columns+"> "+name+"\n";
		if(columns == 1){
			for(int i = 0; i < rows; i++){
				result += String.format("%.2f", get(i, 0));
				result += ", ";
			}
			return result;
		}
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < columns; j++){
				result += String.format("%.2f", get(i, j));
				result += ", ";
			}
			result += "\n";
		}
		
		
		return result;
	}
}
