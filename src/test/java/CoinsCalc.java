public class CoinsCalc {

	public CoinsCalc() {
		int score = 211;

		final double i = Math.pow(score, 0.5);
		System.out.println("i = " + i);

		int coins = (int) (Math.pow(score, 0.8));
		System.out.println("coins = " + coins);
	}

	public static void main(String[] args) {
		new CoinsCalc();
	}
}
