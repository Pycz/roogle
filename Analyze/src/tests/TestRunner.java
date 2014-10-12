package tests;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import unitTest.AnalyzeGenericTest;
import unitTest.AnalyzeRequestTest;
import unitTest.AnalyzeSignatureTest;
import unitTest.PrimaryRegexpTest;

public class TestRunner {
   public static void main(String[] args) {
      Result result = JUnitCore.runClasses(AnalyzeSignatureTest.class);
      for (Failure failure : result.getFailures()) {
         System.out.println(failure.toString());
      }
      System.out.println("UTest AnalyzeSignatureTest:" + result.wasSuccessful());
      
      result = JUnitCore.runClasses(AnalyzeRequestTest.class);
      for (Failure failure : result.getFailures()) {
         System.out.println(failure.toString());
      }
      System.out.println("UTest AnaLyzeRequestTest:" + result.wasSuccessful());
      
      result = JUnitCore.runClasses(AnalyzeGenericTest.class);
      for (Failure failure : result.getFailures()) {
         System.out.println(failure.toString());
      }
      System.out.println("UTest AnalyzeGenericTest:" + result.wasSuccessful());
      
      result = JUnitCore.runClasses(PrimaryRegexpTest.class);
      for (Failure failure : result.getFailures()) {
         System.out.println(failure.toString());
      }
      System.out.println("UTest PrimaryRegexpTest:" + result.wasSuccessful());
   }
}
