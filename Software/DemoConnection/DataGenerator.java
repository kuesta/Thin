package at.htlklu;

public class DataGenerator {

	public static int ERRORMASK_NO_ERROR = 0;
	public static int ERRORMASK_NOT_VALID_VE_ID = 1;
	public static int ERRORMASK_NOT_VALID_LED_ID = 2;
	public static int ERRORMASK_NOT_VALID_RED_INPUT = 4;
	public static int ERRORMASK_NOT_VALID_GREEN_INPUT = 8;
	public static int ERRORMASK_NOT_VALID_BLUE_INPUT = 16;

	int veId;	// 4Bit
	int ledId;	// 4Bit
	int verlauf;
	int red;	// 7Bit
	int green;	// 7Bit
	int blue;   // 7Bit

	int errorMask;

	public DataGenerator(int veId, int ledId, int red, int green, int blue) {
		super();
		this.veId = veId;
		this.ledId = ledId;
		verlauf=0;
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
	public DataGenerator(int veId, int ledId, boolean verlauf) {
		super();
		this.veId = veId;
		this.ledId = ledId;
		if(verlauf){
			this.verlauf=1;
		}else{
			this.verlauf=0;
		}
		this.red = 0;
		this.green = 0;
		this.blue = 0;
	}

	private int generateData() {
		errorMask = 0;
		if (veId < 1 || veId > 5) {
			errorMask += 1;
		}
		if (ledId < 1 || ledId > 5) {
			errorMask += 2;
		}
		if (red < 0 || red > 128) {
			errorMask += 4;
		}
		if (green < 0 || green > 128) {
			errorMask += 8;
			
		}
		if (blue < 0 || blue > 128) {
			errorMask += 16;
		}
		if (errorMask != ERRORMASK_NO_ERROR) {
			return 0;
		}else{
			int data = 0;
			data += veId << 26;
			data += ledId << 22;
			data += verlauf << 21;
			data += red << 14;
			data += green << 7;
			data += blue;
			return data;
		}

	}

	public int getErrorMaskNumber() {
		return errorMask;
	}

	public int getData() {
		return generateData();
	}

	public void setVeId(int veId) {
		this.veId = veId;
	}

	public void setLedId(int ledId) {
		this.ledId = ledId;
	}

	public void setRed(int red) {
		this.red = red;
	}

	public void setGreen(int green) {
		this.green = green;
	}

	public void setBlue(int blue) {
		this.blue = blue;
	}
	
	public void setRainbow(boolean on){
		if(on){
			verlauf=1;
		}else{
			verlauf=0;
		}
	}

}
