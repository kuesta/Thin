package at.htlklu;

public interface RefVars {
	public static int ERRORMASK_NO_ERROR = -1;
	public static int ERRORMASK_NOT_VALID_VE_ID = 2 ^ 1;
	public static int ERRORMASK_NOT_VALID_LED_ID = 2 ^ 2;
	public static int ERRORMASK_NOT_VALID_RED_INPUT = 2 ^ 3;
	public static int ERRORMASK_NOT_VALID_GREEN_INPUT = 2 ^ 4;
	public static int ERRORMASK_NOT_VALID_BLUE_INPUT = 2 ^ 5;
}
