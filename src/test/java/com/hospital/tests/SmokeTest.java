package com.hospital.tests;

import com.hospital.base.BaseTest;
import com.hospital.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SmokeTest extends BaseTest {

    @Test
    public void homePageValidationTest() {
        HomePage homePage = new HomePage(driver);

        System.out.println("Page Title: " + homePage.getPageTitle());
        Assert.assertTrue(homePage.isHomePageDisplayed(), "Home page is not displayed!");


    }
    @Test
    public void searchHospitalsInBangalore() {

        HomePage homePage = new HomePage(driver);

        homePage.selectCity("Bangalore");
        homePage.searchHospital();
    }
}