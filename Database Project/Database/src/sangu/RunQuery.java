package sangu;

import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.TreeMap;

public class RunQuery {
//	private static TreeMap<Integer, ColumnDataTypeMapper> columnDataTypes = new TreeMap<Integer, ColumnDataTypeMapper>();
	private final static ArrayList<String> supportedDataTypes = new ArrayList<String>(Arrays.asList("BYTE", "SHORT INT", "SHORT", "INT", "LONG INT", "LONG", "CHAR",
																									"VARCHAR", "FLOAT", "DOUBLE", "DATETIME", "DATE"));
	
	public RunQuery(){}
	
	public void displaySchemas() throws IOException{
		String infoSchema = Settings.schemata;
		RandomAccessFile schemataTableFile = new RandomAccessFile(infoSchema, "r");
		try{
			while(true){
				byte varcharLength = schemataTableFile.readByte();
				for(int i = 0; i < varcharLength; i++)
					System.out.print((char)schemataTableFile.readByte());
				System.out.println("");
			}
		}catch(EOFException eof){
			
		}
	}

	public void createSchema(String schema_name) throws FileNotFoundException, IOException {
		RandomAccessFile schema = new RandomAccessFile(Settings.schemata, "rw");
		schema.seek(schema.length());
		schema.writeByte(schema_name.length());
		schema.writeBytes(schema_name);
		System.out.println("successfully created schema '"+schema_name+"'");
		schema.close();
	}

	public void showTables() throws FileNotFoundException, IOException {
		RandomAccessFile tables = new RandomAccessFile(Settings.tables, "r");
		try{
			while(true){
				byte schemaNameLength = tables.readByte();
				byte[] schema = new byte[schemaNameLength];
				for(int i = 0; i < schemaNameLength; i++) {
					schema[i] = tables.readByte();
				}
				byte tableNameLength = tables.readByte();
				byte[] tableName = new byte[tableNameLength];
				for(int i = 0; i < tableNameLength; i++) {
					tableName[i] = tables.readByte();
				}
				if(Settings.currentSchema.equals(new String(schema))){
					System.out.println(new String(tableName));
					//System.out.println("");
				}
				tables.readLong();
			}
		}catch(EOFException eof){
			tables.close();
		}
		tables.close();
		
	}

	public void useSchema(String schema) throws FileNotFoundException, IOException {
		String infoSchema = Settings.schemata;
		boolean isSchemaFound = false;
		RandomAccessFile schemataTableFile = new RandomAccessFile(infoSchema, "r");
		try{
//			for(int noOfRecords = 1; noOfRecords <= Settings.no_Of_schemaTable_records; noOfRecords++) {
			while(true){
				byte varcharLength = schemataTableFile.readByte();
				char[] schemaName = new char[varcharLength];
				for(int i = 0; i < varcharLength; i++)
					schemaName[i] = (char)schemataTableFile.readByte();
				if(schema.equals(new String(schemaName))){
					System.out.println("Using schema "+schema);
					isSchemaFound = true;
					break;
				}
			}
			if(!isSchemaFound){
				System.out.println("The schema '"+schema+"' is not defined");
			}else{
				Settings.currentSchema = schema;
			}
		}catch(EOFException eof){
			
		}
	}

	//TODO: add index table for the created table; Add support for Primary Key and NOT NULL constraints;   
	public void createTable(QueryContents contents) throws FileNotFoundException, IOException {
		// Store the table information in the tables table
		if(!checkIfTableExistsInCurrentSchema(contents.getTableName())){
			if(areValidDataTypes(contents.getDataTypes())){
				RandomAccessFile tables = new RandomAccessFile(Settings.tables, "rw");
				tables.seek(tables.length());
				tables.writeByte(Settings.currentSchema.length());
				tables.writeBytes(Settings.currentSchema);
				tables.writeByte(contents.getTableName().length());
				tables.writeBytes(contents.getTableName());
				tables.writeLong(0);
				tables.close();
				
				RandomAccessFile columns = new RandomAccessFile(Settings.columns, "rw");
				columns.seek(columns.length());
				ArrayList<String> sColumns = contents.getColumnNames();
				ArrayList<String> sDataTypes = contents.getDataTypes();
				int ordinalPosition = 1;
				for(int i = 0; i < sColumns.size(); i++){
					columns.writeByte(Settings.currentSchema.length());//Schema Length
					columns.writeBytes(Settings.currentSchema);//Schema Name
					columns.writeByte(contents.getTableName().length());// Table Length
					columns.writeBytes(contents.getTableName());//Table Name
					columns.writeByte(sColumns.get(i).length());//Column Length
					columns.writeBytes(sColumns.get(i));//Column Name
					columns.writeInt(ordinalPosition);// Position of the column within the table
					columns.writeByte(sDataTypes.get(i).length());// DataType length
					columns.writeBytes(sDataTypes.get(i));// Data Type
					columns.writeByte("NO".length());// IS_NULLABLE Length
					columns.writeBytes("NO");// IS_NULLABLE
					columns.writeByte("".length());//COLUMN_KEY length
					columns.writeBytes("");// COLUMN_KEY a.k.a is_it_primary_key?PRI:empty
					ordinalPosition++;
				}
				columns.close();
				
				RandomAccessFile table = new RandomAccessFile(Settings.currentSchema+"."+contents.getTableName()+".tbl", "rw");
				table.close();
				RandomAccessFile indexTable = new RandomAccessFile(Settings.currentSchema+"."+contents.getTableName()+".ndx", "rw");
				indexTable.close();
			}
		}else{
			System.out.println("The table '"+contents.getTableName()+"' already exists in the schema '"+Settings.currentSchema+"'");
		}
		
	}
	
	private boolean areValidDataTypes(ArrayList<String> dataTypes) {
		for(int i = 0; i < dataTypes.size(); i++){
			if(dataTypes.get(i).toUpperCase().contains("varchar".toUpperCase())){
//				dataTypes.set(i, dataTypes.get(i).substring(0, dataTypes.get(i).indexOf("(")));
				if(!supportedDataTypes.contains(dataTypes.get(i).substring(0, dataTypes.get(i).indexOf("(")).toUpperCase() )){
					return false;
				}
			}
			else if(dataTypes.get(i).toUpperCase().contains("char".toUpperCase())){
//				dataTypes.set(i, dataTypes.get(i).substring(0, dataTypes.get(i).indexOf("(")));
				if(!supportedDataTypes.contains(dataTypes.get(i).substring(0, dataTypes.get(i).indexOf("(")).toUpperCase() )){
					return false;
				}
			}else if(!supportedDataTypes.contains(dataTypes.get(i).toUpperCase())){
				System.out.println("The dataType '"+dataTypes.get(i)+"' is not supported.Type dataTypehelp to know the supported data types or 'amGood' to continue.");
				Scanner sc = new Scanner(System.in);
				String userInput = sc.nextLine().trim();
				sc.close();
				if(userInput.equalsIgnoreCase("dataTypehelp")){
					displaySupportedDataTypes();
					break;
				}else{
					break;
				}
			}
		}
		return true;
	}

	private void displaySupportedDataTypes() {
		System.out.println("BYTE- 1 - A signed two’s compliment byte: range -128 to 127");
		System.out.println("SHORT INT, SHORT- 2 - A signed two’s compliment short integer: range -32768 to 32767");
		System.out.println("INT - 4 - A signed two’s compliment integer: range -2147483648 to 2147483647");
		System.out.println("LONG INT, LONG - 8 - A signed two’s compliment long integer: range –2^63 to 2^63 – 1");
		System.out.println("CHAR(n) - n - A fixed length ASCII string of n characters, including the string terminator \n (i.e. 0x00). Strings less than n are padded with \0’s.");
		System.out.println("VARCHAR(n) - variable - A variable length ASCII string with a maximum of n characters. n may be 0-127. Each instance is prepended with an unsigned byte indicating the number of ASCII characters that follow.");
		System.out.println("FLOAT - 4 - A single precision IEEE 754 floating point number");
		System.out.println("DOUBLE - 8 - A double precision IEEE 754 floating point number");
		System.out.println("DATETIME - 8 - An unsigned long int that represents the specified number of milliseconds since the standard base time known as \"the epoch\". It should display as: YYYY-MM-DD_hh:mm:ss, e.g. 2016-03-23_13:52:23.");
		System.out.println("DATE - 8 - A datetime whose time component is 00:00:00, but does not display.");
	}

	private TreeMap<Integer, ColumnDataTypeMapper> populateDataTypesAndConstraints(String tableName) throws FileNotFoundException, IOException {
		RandomAccessFile columns = new RandomAccessFile(Settings.columns, "rw");
		TreeMap<Integer, ColumnDataTypeMapper> columnDataTypes = new TreeMap<Integer, ColumnDataTypeMapper>();
		try{	
			while(true){
				byte schemaLength = columns.readByte();
				for(int i = 0; i < schemaLength; i++){
					columns.readByte();
				}
				byte tableLength = columns.readByte();
				byte[] table = new byte[tableLength];
				for(int i = 0; i < tableLength; i++){
					table[i] = columns.readByte();
				}
				byte columnLength = columns.readByte();
				for(int i = 0; i < columnLength; i++) {
					columns.readByte();
				}
				int ordinalPosition = columns.readInt();//read ordinal position
				byte dataTypeLength = columns.readByte();
				byte[] dataType = new byte[dataTypeLength];
				for(int i = 0; i < dataTypeLength; i++){// read datatype
					dataType[i] = columns.readByte();
				}
				byte nullableLength = columns.readByte();
				byte[] nullable = new byte[nullableLength];
				for(int i = 0; i< nullableLength; i++){// read nullable field
					nullable[i] = columns.readByte();
				}
				byte primKeyLength = columns.readByte();
				byte[] primKey = new byte[primKeyLength];
				for(int i = 0; i< primKeyLength; i++){// read column key
					primKey[i] = columns.readByte();
				}
				if(tableName.equals(new String(table))){
					ColumnDataTypeMapper mapper = new ColumnDataTypeMapper();
					mapper.setDataType(new String(dataType));
					if("YES".equalsIgnoreCase(new String(nullable))){
						mapper.setIsNOTNULL(true);
					}else{
						mapper.setIsNOTNULL(false);
					}
					if("PRI".equalsIgnoreCase(new String(primKey))){
						mapper.setIsPrimaryKey(true);
					}else{
						mapper.setIsPrimaryKey(false);
					}
					columnDataTypes.put(ordinalPosition, mapper);
				}
			}
		}catch(EOFException e){
			return columnDataTypes;
		}
	}

	private boolean checkIfTableExistsInCurrentSchema(String cTableName) throws FileNotFoundException, IOException {
		RandomAccessFile tables = new RandomAccessFile(Settings.tables, "r");
		try{
			while(true){
				byte schemaNameLength = tables.readByte();
				byte[] schema = new byte[schemaNameLength];
				for(int i = 0; i < schemaNameLength; i++) {
					schema[i] = tables.readByte();
				}
				byte tableNameLength = tables.readByte();
				byte[] tableName = new byte[tableNameLength];
				for(int i = 0; i < tableNameLength; i++) {
					tableName[i] = tables.readByte();
				}
				if(Settings.currentSchema.equals(new String(schema))){
					if(cTableName.equalsIgnoreCase(new String(tableName))){
						return true;
					}
				}
				tables.readLong();
			}
		}catch(EOFException eof){
			tables.close();
		}
		tables.close();
		return false;
	}

	public void insertIntoTable(String tableName, ArrayList<String> values) throws FileNotFoundException, IOException, ParseException {
		if(checkIfTableExistsInCurrentSchema(tableName)){
			RandomAccessFile table = new RandomAccessFile(Settings.currentSchema+"."+tableName+".tbl", "rw");
			RandomAccessFile indexFile = new RandomAccessFile(Settings.currentSchema+"."+tableName+".ndx", "rw");
			TreeMap<Integer, ColumnDataTypeMapper> columnDataTypes = populateDataTypesAndConstraints(tableName);
			table.seek(table.length());
			for(int i = 0, j = 1; j <= columnDataTypes.size(); i++,j++){
				ColumnDataTypeMapper mapper = columnDataTypes.get(j);
				String dataType = mapper.getDataType();
				if("datetime".equalsIgnoreCase(dataType)){
					SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
				    Date strDate = sdfDate.parse(values.get(i).substring(1, values.get(i).length()-1));
				    indexFile.writeLong(table.getFilePointer());
				    table.writeLong(strDate.getTime());
				    indexFile.writeLong(strDate.getTime());
				}else if("date".equalsIgnoreCase(dataType)){
					SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");//dd/MM/yyyy
				    Date strDate = sdfDate.parse(values.get(i).substring(1, values.get(i).length()-1));
				    indexFile.writeLong(table.getFilePointer());
				    table.writeLong(strDate.getTime());
				    indexFile.writeLong(strDate.getTime());
				}else if("byte".equalsIgnoreCase(dataType)){
					indexFile.writeLong(table.getFilePointer());
					table.writeBytes(values.get(i));
					indexFile.writeBytes(values.get(i));
				}else if("short".equalsIgnoreCase(dataType) || "short int".equalsIgnoreCase(dataType)){
					indexFile.writeLong(table.getFilePointer());
					table.writeShort(Integer.parseInt(values.get(i)));
					indexFile.writeShort(Integer.parseInt(values.get(i)));
				}else if("int".equalsIgnoreCase(dataType)){
					indexFile.writeLong(table.getFilePointer());
					table.writeInt(Integer.parseInt(values.get(i)));
					indexFile.writeInt(Integer.parseInt(values.get(i)));
				}else if("long".equalsIgnoreCase(dataType) || "long int".equalsIgnoreCase(dataType)) {
					indexFile.writeLong(table.getFilePointer());
					table.writeLong(Long.parseLong(values.get(i)));
					indexFile.writeLong(Long.parseLong(values.get(i)));
				}else if(dataType.toUpperCase().contains("VARCHAR")){
					int supportedVarcharLength = Integer.parseInt(dataType.substring(dataType.indexOf("(")+1, dataType.indexOf(")")));
					if(values.get(i).length() > supportedVarcharLength){
						indexFile.writeLong(table.getFilePointer());
						table.writeByte(supportedVarcharLength);
						table.writeBytes(values.get(i).substring(0, supportedVarcharLength));
						indexFile.writeBytes(values.get(i).substring(0, supportedVarcharLength));
					}else{
						indexFile.writeLong(table.getFilePointer());
						table.writeByte(values.get(i).length());
						table.writeBytes(values.get(i));
						indexFile.writeBytes(values.get(i));
					}
				}else if(dataType.toUpperCase().contains("CHAR")){
					int charLength = Integer.parseInt(dataType.substring(dataType.indexOf("(")+1, dataType.indexOf(")")));
					if(values.get(i).length() > charLength){
						indexFile.writeLong(table.getFilePointer());
						table.writeByte(charLength);
						table.writeBytes(values.get(i).substring(0, charLength));
						indexFile.writeBytes(values.get(i).substring(0, charLength));
					}else if(values.get(i).length() < charLength){
						indexFile.writeLong(table.getFilePointer());
						for(int k = 0; k < charLength-values.get(i).length(); k++){
							String value = values.get(k);
							value += 0;
							values.set(k, value);
						}
						table.writeByte(charLength);
						table.writeBytes(values.get(i));
						indexFile.writeBytes(values.get(i));
					}
				}else if("float".equalsIgnoreCase(dataType)){
					indexFile.writeLong(table.getFilePointer());
					table.writeFloat(Float.parseFloat(values.get(i)));
					indexFile.writeFloat(Float.parseFloat(values.get(i)));
				}else if("double".equalsIgnoreCase(dataType)){
					indexFile.writeLong(table.getFilePointer());
					table.writeDouble(Double.parseDouble(values.get(i)));
					indexFile.writeDouble(Double.parseDouble(values.get(i)));
				}
			}
			table.close();
		}else{
			System.out.println("The table '"+tableName+"' doesnot exist in the schema '"+Settings.currentSchema+"'");
		}
	}

	public void selectFromTable(QueryContents contents) throws FileNotFoundException, IOException {
		String tableName = contents.getTableName();
		String whereLHS = contents.getWhereLHS();
		String whereRHS = contents.getWhereRHS();
		String whereCondition = contents.getWhereCondition();
		String cDataType = getColumnDataType(contents.getWhereLHS(), contents.getTableName());
		if(checkIfTableExistsInCurrentSchema(tableName)){
			File f = new File(Settings.currentSchema+"."+tableName+".tbl");
			if(f.exists()){
				try{
					RandomAccessFile table = new RandomAccessFile(Settings.currentSchema+"."+tableName+".tbl", "rw"); 
					TreeMap<Integer, ColumnDataTypeMapper> columnDataTypes = populateDataTypesAndConstraints(tableName);
					int i = 1;
					while(true){
						if(i-1 == columnDataTypes.size()){
							System.out.println();
							i=1;
						}
							String dataType = columnDataTypes.get(i).getDataType();
							if("datetime".equalsIgnoreCase(dataType)){
								Long dateT = table.readLong();
								Date dateTime = new Date(dateT);
								SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
								format.format(dateTime);
								System.out.print(dateTime.getTime()+"\t");
							}else if("date".equalsIgnoreCase(dataType)){
								Long date = table.readLong();
								Date dt = new Date(date);
								SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
								format.format(dt);
								System.out.print(dt+"\t");
							}else if("byte".equalsIgnoreCase(dataType)){
								System.out.print(table.readByte()+"\t");
							}else if("short".equalsIgnoreCase(dataType) || "short int".equalsIgnoreCase(dataType)){
								System.out.print(table.readInt()+"\t");
							}else if("int".equalsIgnoreCase(dataType)){
								System.out.print(table.readInt()+"\t");
							}else if("long".equalsIgnoreCase(dataType) || "long int".equalsIgnoreCase(dataType)) {
								System.out.print(table.readLong()+"\t");
							}else if(dataType.toUpperCase().contains("VARCHAR")){
								byte varcharLength = table.readByte();
								byte[] varchar = new byte[varcharLength];
								for(int j = 0; j < varcharLength; j++){
									varchar[i] = table.readByte();
								}
								System.out.print(new String(varchar)+"\t");
							}else if(dataType.toUpperCase().contains("CHAR")){
								byte charLength = table.readByte();
								byte[] character = new byte[charLength];
								for(int j = 0; j < charLength; j++){
									character[i] = table.readByte();
								}
								System.out.print(new String(character)+"\t");
							}else if("float".equalsIgnoreCase(dataType)){
								System.out.print(table.readFloat()+"\t");
							}else if("double".equalsIgnoreCase(dataType)){
								System.out.print(table.readDouble()+"\t");
							}
							i++;
//						}
					}
				}catch(EOFException eof){
					
				}
			}
		}
	}

	private boolean validateFilterCondition(QueryContents contents, String dataType) throws IOException {
		if(!"".equals(dataType)){
			if("datetime".equalsIgnoreCase(dataType)){
				SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
			    try{
			    	sdfDate.parse(contents.getWhereRHS());
			    	return true;
			    }catch(ParseException p){
			    	return false;
			    }
			}else if("date".equalsIgnoreCase(dataType)){
				SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");//dd/MM/yyyy
			    try{
			    	sdfDate.parse(contents.getWhereRHS());
			    	return true;
			    }catch(ParseException p){
			    	return false;
			    }
			}else if("byte".equalsIgnoreCase(dataType)){
				return true;
			}else if("short".equalsIgnoreCase(dataType) || "short int".equalsIgnoreCase(dataType)){
				try{
					Integer.parseInt(contents.getWhereRHS());
					return true;
				}catch(NumberFormatException nfe){
					return false;
				}
			}else if("int".equalsIgnoreCase(dataType)){
				try{
					Integer.parseInt(contents.getWhereRHS());
				}catch(NumberFormatException nfe){
					return false;
				}
			}else if("long".equalsIgnoreCase(dataType) || "long int".equalsIgnoreCase(dataType)) {
				try{
					Long.parseLong(contents.getWhereRHS());
					return true;
				}catch(NumberFormatException nfe){
					return false;
				}
			}else if(dataType.toUpperCase().contains("VARCHAR")){
				return true;
			}else if(dataType.toUpperCase().contains("CHAR")){
				return true;
			}else if("float".equalsIgnoreCase(dataType)){
				try{
					Float.parseFloat(contents.getWhereRHS());
					return true;
				}catch(NumberFormatException nfe){
					return false;
				}
			}else if("double".equalsIgnoreCase(dataType)){
				try{
					Double.parseDouble(contents.getWhereRHS());
					return true;
				}catch(NumberFormatException nfe){
					return false;
				}
			}
		}
		return false;
		
	}

	private String getColumnDataType(String whereLHS, String tableName) throws IOException {
		RandomAccessFile columns = new RandomAccessFile(Settings.columns, "rw");
		TreeMap<Integer, ColumnDataTypeMapper> columnDataTypes = new TreeMap<Integer, ColumnDataTypeMapper>();
		try{	
			while(true){
				byte schemaLength = columns.readByte();
				for(int i = 0; i < schemaLength; i++){
					columns.readByte();
				}
				
				byte tableLength = columns.readByte();
				byte[] table = new byte[tableLength];
				for(int i = 0; i < tableLength; i++){
					table[i] = columns.readByte();
				}
				
				byte columnLength = columns.readByte();
				byte[] column = new byte[columnLength];
				for(int i = 0; i < columnLength; i++) {
					column[i] = columns.readByte();
				}
				
				int ordinalPosition = columns.readInt();//read ordinal position
				byte dataTypeLength = columns.readByte();
				byte[] dataType = new byte[dataTypeLength];
				for(int i = 0; i < dataTypeLength; i++){// read datatype
					dataType[i] = columns.readByte();
				}
				
				byte nullableLength = columns.readByte();				
				for(int i = 0; i< nullableLength; i++){// read nullable field
					columns.readByte();
				}
				
				byte primKeyLength = columns.readByte();				
				for(int i = 0; i< primKeyLength; i++){// read column key
					columns.readByte();
				}
				
				if(tableName.equals(new String(table))){
					if(whereLHS.equals(new String(column))){
						return new String(dataType);
					}
				}
			}
		}catch(EOFException e){
			return "";
		}
	}

	public void dropTable(QueryContents contents) {
		File f = new File(Settings.currentSchema+"."+contents.getTableName()+".tbl");
		if(f.exists()){
			f.delete();
		}
	}
}
