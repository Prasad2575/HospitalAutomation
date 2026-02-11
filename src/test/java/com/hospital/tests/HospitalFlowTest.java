package com.hospital.tests;

import com.hospital.base.BaseTest;
import com.hospital.pages.HomePage;
import com.hospital.pages.HospitalsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HospitalFlowTest extends BaseTest {

    @Test
    public void selectCityAndSearchHospitalTest() {

        HomePage homePage = new HomePage(driver);

        // Operation 1: Select City
        homePage.selectCity("Bangalore");

        // 🔎 DEBUG PRINT HERE
        System.out.println("After city select URL = " + driver.getCurrentUrl());
        System.out.println("After city select TITLE = " + driver.getTitle());

        // Operation 2: Search Hospital
        HospitalsPage hospitalsPage = homePage.searchHospitals();

        // Validation
        Assert.assertTrue(hospitalsPage.isHospitalsPageDisplayed(),
                "Hospitals page is not displayed!");
    }

}
