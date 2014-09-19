import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import bean.capital;
import bean.capitals;
import bean.group;
import bean.result;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * 
 * @ClassName: ParseUpXml
 * @Description: TODO(解析XML)
 * @author 刘斌
 * @date 2013-5-14 下午3:52:59
 *
 */
public class ParseUpXml {

	/**
	 * @Title: main
	 * @Description: TODO(入口方法)
	 * @param @param args 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public static void main(String[] args) {
		ParseUpXml p = new ParseUpXml();
		result result = p.parse("");
		System.out.println(result.getGroup().getSerialnumber());
	}

	/**
	 * 
	 * @Title: parseUpDataXML
	 * @Description: TODO(解析XML)
	 * @param @param xmlData
	 * @param @return 设定文件
	 * @return ProvIdcDataSync 返回类型
	 * @throws
	 */
	private result parseUpDataXML(String xmlData) {
		result result = null;
		if (xmlData != null && !xmlData.trim().equals("")) {
			// // 接收同步xml解析
			XStream xs = new XStream(new DomDriver());
			xs.alias("result", result.class);
			xs.alias("group", group.class);
			xs.alias("capitals", capitals.class);
			xs.addImplicitCollection(capitals.class, "capital", capital.class);
			xs.alias("capital", capital.class);
			result = (result) xs.fromXML(xmlData);
		}
		return result;
	}

	/**
	 * 
	 * @Title: parse
	 * @Description: TODO(将集团xml同步到系统集团表中)
	 * @param @param dataName
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public result parse(String dataName) {
		String xmlData = null;
		result result = null;
		ParseUpXml parseUpXml = new ParseUpXml();
		try {
			xmlData = new ParseUpXml().loadStringFromFile(
					new File("c:\\1.xml"), "UTF-8");
			result = parseUpXml.parseUpDataXML(xmlData);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 从文件中读取文本内容
	 * 
	 * @param file
	 *            目标文件
	 * @param encoding
	 *            目标文件的文本编码格式
	 * @return
	 * @throws IOException
	 */
	public String loadStringFromFile(File file, String encoding)
			throws IOException {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(file), encoding), 10 * 1024 * 1024);
			StringBuilder builder = new StringBuilder();
			char[] chars = new char[4096];
			int length = 0;
			while (0 < (length = reader.read(chars))) {
				builder.append(chars, 0, length);
			}
			return builder.toString();
		} finally {
			try {
				if (reader != null)
					reader.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

}
