package com.hospital.tests;

import com.hospital.base.BaseTest;
import com.hospital.listeners.TestListener;
import com.hospital.pages.DiagnosticsPage;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.List;

@Listeners(TestListener.class)
public class DiagnosticsTest extends BaseTest {

    @Test
    public void printTopCitiesFromDiagnostics() {

        DiagnosticsPage diag = new DiagnosticsPage(driver);

        diag.openLabTests();

        List<String> cities = diag.getTopCities();

        System.out.println("========= TOP CITIES =========");
        for (int i = 0; i < cities.size(); i++) {
            System.out.println((i + 1) + ") " + cities.get(i));
        }

        Assert.assertTrue(cities.size() > 0, "No Top Cities captured from Diagnostics popup!");
    }
}
