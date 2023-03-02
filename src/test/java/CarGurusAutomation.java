import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CarGurusAutomation {
    @Test


    public void automationTest() throws InterruptedException {


        WebDriver driver = new ChromeDriver();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().maximize();
        driver.get("https://www.cargurus.com/");

        driver.findElement(By.xpath("//label[contains(. ,'Buy Used')]")).click();

        WebElement makesElement = driver.findElement(By.id("carPickerUsed_makerSelect"));
        Select select = new Select(makesElement);
        Assert.assertEquals(select.getFirstSelectedOption().getText(), "All Makes");

        select.selectByVisibleText("Lamborghini");

        Assert.assertEquals(new Select(driver.findElement(By.id("carPickerUsed_modelSelect"))).getFirstSelectedOption().getText(), "All Models");

        List<WebElement> modelDropDownOptions = new Select(driver.findElement(By.id("carPickerUsed_modelSelect"))).getOptions();

        List<String> originalModelList = new ArrayList<>();
        List<String> expectedModelList = List.of("All Models", "Aventador", "Huracan", "Urus", "400GT", "Centenario", "Countach", "Diablo", "Espada", "Gallardo", "Murcielago");

        for (WebElement each : modelDropDownOptions) {

            originalModelList.add(each.getText());
        }

        //System.out.println("originalModelList "+originalModelList);

        Assert.assertEquals(originalModelList, expectedModelList);

        new Select(driver.findElement(By.id("carPickerUsed_modelSelect"))).selectByVisibleText("Gallardo");


        driver.findElement(By.id("dealFinderZipUsedId_dealFinderForm")).sendKeys("22031", Keys.ENTER);

        Thread.sleep(1000);

        List<WebElement> searchResult = driver.findElements(By.xpath("//a[contains(@href,'NONE')]"));

        int realSize = searchResult.size();
        //System.out.println(realSize);
        int exeptedSize = 15;
        Assert.assertEquals(realSize, exeptedSize);


        List<WebElement> resultsTitleList = driver.findElements(By.xpath("//a[contains(@href,'NONE')]//h4[@class='vO42pn']"));

        List<String> resultsOfTexts = new ArrayList<>();

        for (WebElement each : resultsTitleList) {

            Assert.assertTrue(each.getText().contains("Lamborghini Gallardo"));

            //System.out.println("search result list test :"+each.getText());
        }


        //11. From the dropdown on the left corner choose “Lowest price first”

        new Select(driver.findElement(By.id("sort-listing"))).selectByVisibleText("Lowest price first");

        //verify that all 15 results are sorted from lowest to highest.
        //$107,499->107499

        Thread.sleep(2000);
        List<WebElement> priceElements = driver.findElements(By.xpath("//a[contains(@href,'NONE')]//span[@class='JzvPHo']"));

        List<Integer> acactualPriceList = new ArrayList<>();

        for (WebElement each : priceElements) {

            acactualPriceList.add(Integer.parseInt(each.getText().substring(0,each.getText().indexOf(" ")).replaceAll("[$,]","")));
        }


        System.out.println("acactualPriceList "+acactualPriceList);
        List<Integer> expectedSortedPriceList= new ArrayList<>(acactualPriceList);

        Collections.sort(expectedSortedPriceList);

        //System.out.println(expectedSortedPriceList);

        Assert.assertEquals(acactualPriceList,expectedSortedPriceList);


        // 12. From the dropdown menu, choose “Highest mileage first” option

        new Select(driver.findElement(By.id("sort-listing"))).selectByVisibleText("Highest mileage first");

        //verify that all 15 results are sorted from highest to lowest

        Thread.sleep(2000);
        List<WebElement> mileageElements = driver.findElements(By.xpath("//a[contains(@href,'NONE')]//p[@class='JKzfU4 umcYBP']"));

        //46,465 mi-> 46465
        List<Integer> actualMileageList = new ArrayList<>();
        for (WebElement each : mileageElements) {

            actualMileageList.add(Integer.parseInt(each.getText().substring(0, each.getText().indexOf(" ")).replace(",", "")));

        }
        System.out.println("actualMileageList : "+actualMileageList);

        List<Integer> expectedMileageList = new ArrayList<>(actualMileageList);

        Collections.sort(expectedMileageList, Collections.reverseOrder());

        Assert.assertEquals(actualMileageList,expectedMileageList);

        System.out.println("expected : "+expectedMileageList);

        //13. On the left menu, click on Coupe AWD checkbox

        driver.findElement(By.xpath("//*[ text()='Coupe AWD']")).click();

        //verify that all results on the page contains “Coupe AWD”
        Thread.sleep(2000);
        List<WebElement> coupeAwdElements = driver.findElements(By.xpath("//h4[@class='vO42pn']"));

        List<String> actualCoupeAwdList= new ArrayList<>();


            for (WebElement each : coupeAwdElements) {

                //System.out.println(each.getText());
                Assert.assertTrue(each.getText().contains("Coupe AWD"));
        }


            //14. Click on the last result (get the last result dynamically, i.e., your code should click on the
        //last result regardless of how many results are there)


        List<WebElement> lastElements = driver.findElements(By.xpath("//a[@class='lmXF4B c7jzqC A1f6zD']"));

            int size=lastElements.size();

            lastElements.get(size-1).click();
//            System.out.println(size);

        //15. Once you are in the result details page go back to the results page and verify that the clicked result has “Viewed” text on it.

        driver.findElement(By.xpath("//button[@class='r1inOn']")).click();

        Assert.assertEquals(driver.findElement(By.xpath("//div[@class='HWinWE x1gK4I']//p")).getText(),"Viewed");
        //System.out.println(driver.findElement(By.xpath("//div[@class='HWinWE x1gK4I']//p")).getText());
    }

}