import java.util.Arrays;

public class BoundsConverter {

	private final String bounds = """
			149;38;
			156;27;
			189;27;
			186;68;
			158;69;
			160;135;
			171;170;
			119;214;
			98;202;
			133;152;
			133;92;
			157;63;""";

	public BoundsConverter() {
		final String[] split = bounds.split(";");
		System.out.println("split = " + Arrays.toString(split));

		int width = 260, height = 233;

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
