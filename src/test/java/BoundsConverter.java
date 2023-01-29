import java.util.Arrays;

public class BoundsConverter {

	private final String bounds = """
			69;83;
			60;93;
			68;110;
			86;116;
			101;109;
			102;98;
			94;86;
			83;84;
			""".trim();

	public BoundsConverter() {
		final String[] split = bounds.split(";");
		System.out.println("split = " + Arrays.toString(split));

		int width = 192, height = 192;

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
