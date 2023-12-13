package QKART_SANITY_LOGIN.Module1;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Home {
    RemoteWebDriver driver;
    String url = "https://crio-qkart-frontend-qa.vercel.app";

    public Home(RemoteWebDriver driver) {
        this.driver = driver;
    }

    public void navigateToHome() {
        if (!this.driver.getCurrentUrl().equals(this.url)) {
            this.driver.get(this.url);
        }
    }

    public Boolean PerformLogout() throws InterruptedException {
        try {
            // Find and click on the Logout Button
            WebElement logout_button = driver.findElement(By.className("MuiButton-text"));
            logout_button.click();

            // SLEEP_STMT_10: Wait for Logout to complete
            // Wait for Logout to Complete
            Thread.sleep(3000);

            return true;
        } catch (Exception e) {
            // Error while logout
            return false;
        }
    }

    /*
     * Returns Boolean if searching for the given product name occurs without any errors
     */
    public Boolean searchForProduct(String product) {
        try {
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 03: MILESTONE 1
            // Clear the contents of the search box and Enter the product name in the search
            // box
            WebElement box = driver
                    .findElement(By.xpath("//input[@placeholder='Search for items/categories']"));
            box.clear();
            box.sendKeys(product);
            //Thread.sleep(5000);
            ExpectedCondition con1 = ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[text()=' No products found ']"));
            ExpectedCondition con2 = ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='MuiCardContent-root css-1qw96cp']"));
            WebDriverWait wait = new WebDriverWait(driver, 3);
            wait.until(ExpectedConditions.or(con1,con2));
            return true;
        } catch (Exception e) {
            System.out.println("Error while searching for a product: " + e.getMessage());
            return false;
        }
    }

    /*
     * Returns Array of Web Elements that are search results and return the same
     */
    public List<WebElement> getSearchResults() {
        List<WebElement> searchResults = new ArrayList<WebElement>() {};
        try {
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 03: MILESTONE 1
            // Find all webelements corresponding to the card content section of each of
            // search results
            searchResults = driver.findElements(
                    By.xpath("//div[@class='MuiCardContent-root css-1qw96cp']"));
            return searchResults;
        } catch (Exception e) {
            System.out.println("There were no search results: " + e.getMessage());
            return searchResults;

        }
    }

    /*
     * Returns Boolean based on if the "No products found" text is displayed
     */
    public Boolean isNoResultFound() {
        Boolean status = false;
        try {
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 03: MILESTONE 1
            // Check the presence of "No products found" text in the web page. Assign status
            // = true if the element is *displayed* else set status = false
            if (driver.findElement(By.xpath("//h4[text()=' No products found ']"))
                    .getText().equals("No products found")) {
                status = true;
            } else {
                status = false;
            }
            return status;
        } catch (Exception e) {
            return status;
        }
    }

    /*
     * Return Boolean if add product to cart is successful
     */
    public Boolean addProductToCart(String productName) {
        try {
            /*
             * Iterate through each product on the page to find the WebElement corresponding
             * to the matching productName
             * 
             * Click on the "ADD TO CART" button for that element
             * 
             * Return true if these operations succeeds
             */
            List<WebElement> productNameElements = driver.findElements(
                    By.xpath("//p[@class='MuiTypography-root MuiTypography-body1 css-yg30e6']"));
            List<WebElement> addToCartButtonElements =
                    driver.findElements(By.xpath("//button[text()='Add to cart']"));

            for (int i = 0; i < productNameElements.size(); i++) {
                WebElement productNameElement = productNameElements.get(i);
                String actualProductName = productNameElement.getText();

                if (actualProductName.equals(productName)) {
                    WebElement addToCartButton = addToCartButtonElements.get(i);
                    addToCartButton.click();
                    return true;
                }
            }
            System.out.println("Unable to find the given product");
            return false;
        } catch (Exception e) {
            System.out.println("Exception while performing add to cart: " + e.getMessage());
            return false;
        }
    }

    /*
     * Return Boolean denoting the status of clicking on the checkout button
     */
    public Boolean clickCheckout() {
        Boolean status = false;
        try {
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 05: MILESTONE 4
            // Find and click on the the Checkout button
            WebElement checkOut = driver.findElement(By.xpath("//button[text()='Checkout']"));
            checkOut.click();
            return status;
        } catch (Exception e) {
            System.out.println("Exception while clicking on Checkout: " + e.getMessage());
            return status;
        }
    }

    /*
     * Return Boolean denoting the status of change quantity of product in cart operation
     */
    public Boolean changeProductQuantityinCart(String productName, int quantity) {
        try {
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 06: MILESTONE 5

            // Find the item on the cart with the matching productName

            // Increment or decrement the quantity of the matching product until the current
            // quantity is reached (Note: Keep a look out when then input quantity is 0,
            // here we need to remove the item completely from the cart)
        List<WebElement> parentElements=driver.findElements(By.xpath("//div[@class='MuiBox-root css-1gjj37g']"));
        for (WebElement parentElement: parentElements) {
        WebElement titleElement = parentElement.findElement(By.xpath( "./div[1]"));
        String actualProductName = titleElement.getText();
        if (actualProductName.equals(productName)) {
        while(true){
        WebElement currentQuantityElement=parentElement.findElement(By.xpath(".//div[@data-testid='item-qty']")); 
        String currentQuantityText=currentQuantityElement.getText();
        int currentQuantity=Integer.parseInt(currentQuantityText);
        if (quantity> currentQuantity){
        WebElement plusButton = parentElement.findElement(By.xpath( ".//*[@data-testid='AddOutlinedIcon']"));
        plusButton.click();
        WebDriverWait wait = new WebDriverWait(driver, 3);
        wait.until(ExpectedConditions.textToBePresentInElement(
            parentElement.findElement(By.xpath(".//div[@data-testid='item-qty']")),
            String.valueOf(currentQuantity+1)));



        }
        else if (quantity < currentQuantity) {
        WebElement minusButton=parentElement.findElement(By.xpath( ".//*[@data-testid='RemoveOutlinedIcon']"));
        minusButton.click();
        WebDriverWait wait = new WebDriverWait(driver, 3);
        wait.until(ExpectedConditions.textToBePresentInElement(
            parentElement.findElement(By.xpath(".//div[@data-testid='item-qty']")),
            String.valueOf(currentQuantity-1)));


        }else if(quantity==currentQuantity){
	        break;
        }
        //Thread.sleep(3000);
        }
        }
        }

            return false;
        } catch (Exception e) {
            if (quantity == 0)
                return true;
            System.out.println("exception occurred when updating cart: " + e.getMessage());
            return false;
        }
    }

    /*
     * Return Boolean denoting if the cart contains items as expected
     */
    public Boolean verifyCartContents(List<String> expectedCartContents) {
        try {
            WebElement cartParent = driver.findElement(By.className("cart"));
            List<WebElement> cartContents = cartParent.findElements(By.className("css-zgtx0t"));

            ArrayList<String> actualCartContents = new ArrayList<String>() {};
            for (WebElement cartItem : cartContents) {
                actualCartContents.add(
                        cartItem.findElement(By.className("css-1gjj37g")).getText().split("\n")[0]);
            }

            for (String expected : expectedCartContents) {
                if (!actualCartContents.contains(expected)) {
                    return false;
                }
            }

            return true;

        } catch (Exception e) {
            System.out.println("Exception while verifying cart contents: " + e.getMessage());
            return false;
        }
    }
}
