import java.util.ArrayList;
import java.util.List;

public class Perceptron {
	List<Matrixf> weights = new ArrayList<>();
	List<Matrixf> z;
	List<Matrixf> a;
	float result_before_sigmoid;
	float result_after_sigmoid;
	//weight 1 takes the input as input
	//weight n outputs y where n is the number of layers
	public Perceptron(int num_layers, int layer_size, int input_size){
		
		//w1 (I.length, x1.length)
		//w2 - wn
		//wy or wn+1 (xn.length, 1)
		
		add_weight(layer_size, input_size);
		for(int i = 0; i < num_layers - 1; i++){
			add_weight(layer_size, layer_size);
		}
		add_weight(1, layer_size);
		z = new ArrayList<>();
		a = new ArrayList<>();
		for(int i = 0; i < num_layers; i++){
			a.add(new Matrixf(layer_size, 1, "<a"+i+">"));
			z.add(new Matrixf(layer_size, 1, "<z"+i+">"));
		}
		System.out.println(weights.size());
		System.out.println();
	}
	
	public void propagate(float actual, float training_rate, float weight_punishment){
		float[] gradients;
		float y_loss = (float) Math.pow(Math.abs(actual - result_before_sigmoid), 2);
		float y_loss_derived;
		//we do weights 1..levels
		//we effect z's 0..levels - 1
		int levels = z.size();
		int layer_size = z.get(0).rows;
		gradients = new float[layer_size];
		for(int j = 0; j < layer_size; j++){
			gradients[j] = 0.0f;
			for(int i = 0; i < 1; i++){
				float sigmoid_prime = result_after_sigmoid*(1-result_after_sigmoid);
				float whatever = weights.get(levels).get(i, j);
				float loss_prime = 2*sigmoid_prime*whatever;
				gradients[j] += -1 * loss_prime;
			}
		}
		for(int l = levels; l >= 1; l--){
			for(int j = 0; j < layer_size; j++){
				gradients[j] = 0.0f;
				for(int i = 0; i < layer_size; i++){
					float sigmoid_prime = z.get(l).get(i,1)*(1-z.get(l).get(i,1));
					float whatever = weights.get(levels).get(i, j);
					float loss_prime = 2*sigmoid_prime*whatever;
					gradients[j] += -1 * loss_prime;
				}
			}
		}
		
		
		
		
	}
	
	public void calculate(Matrixf input){
		int n = z.size();
		System.out.println("calculating: z: " + n + " a: " + a.size());
		//get layer i from inputs
		//x0 = sigmoid(w0 * input)
		a.get(0).set(weights.get(0).multiply(input));
		z.get(0).set(a.get(0).sigmoid());
		for(int i = 1; i < n; i++){
			//xn = sigmoid(wn * xn-1)
			//System.out.println("multiplying: ");
			//System.out.println("this: "+weights.get(i));
			//System.out.println("this: "+z.get(i));
			Matrixf temp = weights.get(i).multiply(z.get(i - 1));
			
			//System.out.println("product: "+temp);
			//System.out.println("done multiplying");
			a.get(i).set(temp);
			z.get(i).set(a.get(i).sigmoid());
		}
		Matrixf temp = weights.get(n).multiply(z.get(n - 1));
		result_before_sigmoid = temp.get(0, 0);
		result_after_sigmoid = sigmoid(result_before_sigmoid);
		for(int i = 0; i < n; i++){
			System.out.println(a.get(i));
			System.out.println(z.get(i));
		}
		System.out.println("result:\n"+temp);
	}
	
	private void add_weight(int rows, int columns){
		Matrixf temp = new Matrixf(rows, columns, "<w"+weights.size()+">");
		randomize(temp);
		weights.add(temp);
		System.out.println(temp);
	}
	
	public static float sigmoid(float num){
		float result = (float) (1.0f / (1.0f + Math.pow(2.718, -1.0f * num)));
		return result;
	}
	
	private void randomize(Matrixf matrix){
		float max = 1.0f;
		for(int i = 0; i < matrix.rows; i++){
			for(int j = 0; j < matrix.columns; j++){
				matrix.set(i , j, (float) Math.random() * max);
			}
		}
	}
	
	
}
