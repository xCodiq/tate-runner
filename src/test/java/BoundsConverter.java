import java.util.Arrays;

public class BoundsConverter {

	private final String bounds = """
			156;92;
			176;70;
			212;63;
			224;41;
			249;42;
			246;84;
			226;83;
			192;154;
			190;219;
			134;265;
			124;247;
			170;207;
			174;183;
			157;175;
			177;135;
			182;104;
			""".trim();

	public BoundsConverter() {
		final String[] split = bounds.split(";");
		System.out.println("split = " + Arrays.toString(split));

		int width = 293, height = 279;

		final double[][] doubles = new double[split.length/2][];

		for (int i = 0; i < split.length; i+=2) {
			final double x = Double.parseDouble(split[i]);
			final double y = Double.parseDouble(split[i+1]);

			doubles[i/2] = new double[] {x/width, y/height};
		}

		for (double[] d : doubles) {
			System.out.println(d[0] + ";" + d[1]);
		}
	}

	public static void main(String[] args) {
		new BoundsConverter();
	}
}
