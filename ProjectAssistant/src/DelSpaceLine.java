import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;


public class DelSpaceLine {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String fromFile = "E:/最新行政区划.txt";
		String toFile = "E:/AreaCode.txt";
		BufferedReader br = new BufferedReader(new FileReader(new File(fromFile)));
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(toFile)));
		String temp = "";
		while((temp = br.readLine()) != null){
			if(temp.trim().length() == 0)
				continue;
			bw.write("insert into areacode (codeid) values ('" + temp + "');");
			bw.newLine();
		}
		br.close();
		bw.close();
		
	}

}
