import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class IPLTest {

	@Test
	public void test() {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\DELL\\Downloads\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("https://www.iplt20.com/points-table/men");
		Map<Integer,List<String>> map = new LinkedHashMap<>();
		List<String> teams = new ArrayList<>();
		for(int i =1;i<=10;i++) {
			WebElement point = driver.findElement(By.xpath("//tbody[@id]/tr["+i+"]/td[11]"));
			WebElement teamName = driver.findElement(By.xpath("//tbody[@id]/tr["+i+"]/td[3]/div/h2"));
			String actual = point.getText();
			map.computeIfAbsent(Integer.parseInt(actual), k -> new ArrayList<>()).add(teamName.getText());
		}
		System.out.println(map);
		driver.quit();
	}

}
