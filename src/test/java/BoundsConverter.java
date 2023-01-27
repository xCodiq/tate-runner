import java.util.Arrays;

public class BoundsConverter {

	private final String bounds = """
			65;2;
			134;1;
			176;23;
			200;65;
			200;136;
			176;176;
			139;198;
			68;197;
			26;176;
			4;133;
			2;66;
			26;26;
			""".trim();

	public BoundsConverter() {
		final String[] split = bounds.split(";");
		System.out.println("split = " + Arrays.toString(split));

		int width = 200, height = 200;

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
