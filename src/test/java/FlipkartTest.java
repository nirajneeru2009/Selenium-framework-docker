import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class FlipkartTest {

	@Test
	public void test() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\DELL\\Downloads\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("https://www.flipkart.com");
		//driver.manage().timeouts().implicitlyWait(null);
		List<WebElement> discount1 = driver.findElements(By.xpath("//div[contains(text(),'%')]"));
		List<Integer> percentages = new ArrayList<>();
		for(int i =0;i<discount1.size();i++) {
			String percentage = discount1.get(i).getText().replaceAll("[A-Za-z%. ]", "");
			percentages.add(Integer.parseInt(percentage));
			
		}
		
		List<String> items= discount1.stream().map(element -> element.getText()).collect(Collectors.toList());
		List<WebElement> products = driver.findElements(By.xpath("//div[contains(text(),'%')]/preceding-sibling::div"));
		Map<Integer,String> data = new LinkedHashMap<>();
		for(int i =0;i<items.size();i++) {
			data.put(percentages.get(i), products.get(i).getText());
		}
		for(Map.Entry<Integer, String> mp:data.entrySet()) {
			if(mp.getKey()>50) {
				System.out.println(mp.getValue());
			}
		}
		driver.quit();
		
	}

}
