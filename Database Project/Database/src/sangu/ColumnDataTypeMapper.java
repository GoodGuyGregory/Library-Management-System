package sangu;

public class ColumnDataTypeMapper {
	private String dataType;
	private boolean isPrimaryKey;
	private boolean isNOTNULL;
	private String columnName;
	
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public boolean getIsPrimaryKey() {
		return isPrimaryKey;
	}
	public void setIsPrimaryKey(boolean isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
	}
	public boolean getIsNOTNULL() {
		return isNOTNULL;
	}
	public void setIsNOTNULL(boolean isNOTNULL) {
		this.isNOTNULL = isNOTNULL;
	}
	
}
/*
 * System.out.println("BYTE- 1 - A signed two’s compliment byte: range -128 to 127");
		System.out.println("SHORT INT, SHORT- 2 - A signed two’s compliment short integer: range -32768 to 32767");
		System.out.println("INT - 4 - A signed two’s compliment integer: range -2147483648 to 2147483647");
		System.out.println("LONG INT, LONG - 8 - A signed two’s compliment long integer: range –2^63 to 2^63 – 1");
		System.out.println("CHAR(n) - n - A fixed length ASCII string of n characters, including the string terminator \n (i.e. 0x00). Strings less than n are padded with \0’s.");
		System.out.println("VARCHAR(n) - variable - A variable length ASCII string with a maximum of n characters. n may be 0-127. Each instance is prepended with an unsigned byte indicating the number of ASCII characters that follow.");
		System.out.println("FLOAT - 4 - A single precision IEEE 754 floating point number");
		System.out.println("DOUBLE - 8 - A double precision IEEE 754 floating point number");
		System.out.println("DATETIME - 8 - An unsigned long int that represents the specified number of milliseconds since the standard base time known as \"the epoch\". It should display as: YYYY-MM-DD_hh:mm:ss, e.g. 2016-03-23_13:52:23.");
		System.out.println("DATE - 8 - A datetime whose time component is 00:00:00, but does not display.");
 * */
