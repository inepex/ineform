package com.inepex.ineom.shared.util;

public class DoubleUtil {

	public static final double MAX_VALUE = 1.7976931348623157e+308;
	public static final double MIN_VALUE = 4.9e-324;
	public static final double MIN_NORMAL = 2.2250738585072014e-308;
	public static final int MAX_EXPONENT = 1023;
	// ==Math.getExponent(Double.MAX_VALUE);
	public static final int MIN_EXPONENT = -1022;
	// ==Math.getExponent(Double.MIN_NORMAL);

	public static final double NaN = 0d / 0d;
	public static final double NEGATIVE_INFINITY = -1d / 0d;
	public static final double POSITIVE_INFINITY = 1d / 0d;
	public static final int SIZE = 64;
	public static final Class<Double> TYPE = double.class;

	// 2^512, 2^-512
	private static final double POWER_512 = 1.3407807929942597E154;
	private static final double POWER_MINUS_512 = 7.458340731200207E-155;
	// 2^256, 2^-256
	private static final double POWER_256 = 1.157920892373162E77;
	private static final double POWER_MINUS_256 = 8.636168555094445E-78;
	// 2^128, 2^-128
	private static final double POWER_128 = 3.4028236692093846E38;
	private static final double POWER_MINUS_128 = 2.9387358770557188E-39;
	// 2^64, 2^-64
	private static final double POWER_64 = 18446744073709551616.0;
	private static final double POWER_MINUS_64 = 5.421010862427522E-20;
	// 2^52, 2^-52
	private static final double POWER_52 = 4503599627370496.0;
	// 2^32, 2^-32
	private static final double POWER_32 = 4294967296.0;
	private static final double POWER_MINUS_32 = 2.3283064365386963E-10;
	// 2^20, 2^-20
	private static final double POWER_20 = 1048576.0;
	private static final double POWER_MINUS_20 = 9.5367431640625E-7;
	// 2^16, 2^-16
	private static final double POWER_16 = 65536.0;
	private static final double POWER_MINUS_16 = 0.0000152587890625;
	// 2^8, 2^-8
	private static final double POWER_8 = 256.0;
	private static final double POWER_MINUS_8 = 0.00390625;
	// 2^4, 2^-4
	private static final double POWER_4 = 16.0;
	private static final double POWER_MINUS_4 = 0.0625;
	// 2^2, 2^-2
	private static final double POWER_2 = 4.0;
	private static final double POWER_MINUS_2 = 0.25;
	// 2^1, 2^-1
	private static final double POWER_1 = 2.0;
	private static final double POWER_MINUS_1 = 0.5;

	private static final double[] powers = { POWER_512, POWER_256, POWER_128,
			POWER_64, POWER_32, POWER_16, POWER_8, POWER_4, POWER_2, POWER_1 };

	private static final double[] invPowers = { POWER_MINUS_512,
			POWER_MINUS_256, POWER_MINUS_128, POWER_MINUS_64, POWER_MINUS_32,
			POWER_MINUS_16, POWER_MINUS_8, POWER_MINUS_4, POWER_MINUS_2,
			POWER_MINUS_1 };

	/**
	 * Method copy from GWT 2.5... can be deleted after GWT version up!
	 */
	public static long doubleToLongBits(double value) {
		if (Double.isNaN(value)) {
			return 0x7ff8000000000000L;
		}

		boolean negative = false;
		if (value == 0.0) {
			if (1.0 / value == NEGATIVE_INFINITY) {
				return 0x8000000000000000L; // -0.0
			} else {
				return 0x0L;
			}
		}
		if (value < 0.0) {
			negative = true;
			value = -value;
		}
		if (Double.isInfinite(value)) {
			if (negative) {
				return 0xfff0000000000000L;
			} else {
				return 0x7ff0000000000000L;
			}
		}

		int exp = 0;

		// Scale d by powers of 2 into the range [1.0, 2.0)
		// If the exponent would go below -1023, scale into (0.0, 1.0) instead
		if (value < 1.0) {
			int bit = 512;
			for (int i = 0; i < 10; i++, bit >>= 1) {
				if (value < invPowers[i] && exp - bit >= -1023) {
					value *= powers[i];
					exp -= bit;
				}
			}
			// Force into [1.0, 2.0) range
			if (value < 1.0 && exp - 1 >= -1023) {
				value *= 2.0;
				exp--;
			}
		} else if (value >= 2.0) {
			int bit = 512;
			for (int i = 0; i < 10; i++, bit >>= 1) {
				if (value >= powers[i]) {
					value *= invPowers[i];
					exp += bit;
				}
			}
		}

		if (exp > -1023) {
			// Remove significand of non-denormalized mantissa
			value -= 1.0;
		} else {
			// Insert 0 bit as significand of denormalized mantissa
			value *= 0.5;
		}

		// Extract high 20 bits of mantissa
		long ihi = (long) (value * POWER_20);

		// Extract low 32 bits of mantissa
		value -= ihi * POWER_MINUS_20;

		long ilo = (long) (value * POWER_52);

		// Exponent bits
		ihi |= (exp + 1023) << 20;

		// Sign bit
		if (negative) {
			ihi |= 0x80000000L;
		}

		return (ihi << 32) | ilo;
	}

	public static Double stubToMaxDigits(int maxdigits, Double number) {
		if (number == null)
			return null;

		int mul = number >= 0 ? 1 : -1;
		double stubber = 1;
		for (int i = 0; i < maxdigits; i++) {
			stubber *= 10;
		}

		return mul * (Math.floor(Math.abs(number) * stubber) / stubber);
	}

}
