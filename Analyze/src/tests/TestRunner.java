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
      
      
      result = JUnitCore.runClasses(GenericTest.class);
      for (Failure failure : result.getFailures()) {
         System.out.println(failure.toString());
      }
      System.out.println("Test GenericTest:" + result.wasSuccessful());
      
      
      result = JUnitCore.runClasses(SignatureTest.class);
      for (Failure failure : result.getFailures()) {
         System.out.println(failure.toString());
      }
      System.out.println("Test SignatureTest:" + result.wasSuccessful());
      
      
      result = JUnitCore.runClasses(NameAndSignatureTest.class);
      for (Failure failure : result.getFailures()) {
         System.out.println(failure.toString());
      }
      System.out.println("Test NameAndSignatureTest:" + result.wasSuccessful());
      
      
      result = JUnitCore.runClasses(SignatureAndOutputTest.class);
      for (Failure failure : result.getFailures()) {
         System.out.println(failure.toString());
      }
      System.out.println("Test SignatureAndOutputTest:" + result.wasSuccessful());
       
       
      result = JUnitCore.runClasses(NameGenericOutputTest.class);
      for (Failure failure : result.getFailures()) {
         System.out.println(failure.toString());
      }
      System.out.println("Test NameGenericOutputTest:" + result.wasSuccessful());
      
      
      result = JUnitCore.runClasses(NameSignatureOutputTest.class);
      for (Failure failure : result.getFailures()) {
         System.out.println(failure.toString());
      }
      System.out.println("Test NameSignatureOutputTest:" + result.wasSuccessful());
      
      result = JUnitCore.runClasses(GenericAndSignature.class);
      for (Failure failure : result.getFailures()) {
         System.out.println(failure.toString());
      }
      System.out.println("Test GenericAndSignature:" + result.wasSuccessful());
      
      result = JUnitCore.runClasses(GenericOutput.class);
      for (Failure failure : result.getFailures()) {
         System.out.println(failure.toString());
      }
      System.out.println("Test GenericOutput:" + result.wasSuccessful());
      
      result = JUnitCore.runClasses(GenericSignatureOutput.class);
      for (Failure failure : result.getFailures()) {
         System.out.println(failure.toString());
      }
      System.out.println("Test GenericSignatureOutput:" + result.wasSuccessful());
     
      result = JUnitCore.runClasses(NameGenericSignatureOutput.class);
      for (Failure failure : result.getFailures()) {
         System.out.println(failure.toString());
      }
      System.out.println("Test NameGenericSignatureOutput:" + result.wasSuccessful());
      
      result = JUnitCore.runClasses(OutputTest.class);
      for (Failure failure : result.getFailures()) {
         System.out.println(failure.toString());
      }
      System.out.println("Test OutputTest:" + result.wasSuccessful());
      
      result = JUnitCore.runClasses(SignatureTest1.class);
      for (Failure failure : result.getFailures()) {
         System.out.println(failure.toString());
      }
      System.out.println("Test SignatureTest1:" + result.wasSuccessful());
      
      result = JUnitCore.runClasses(Generic1Test.class);
      for (Failure failure : result.getFailures()) {
         System.out.println(failure.toString());
      }
      System.out.println("Test Generic1Test:" + result.wasSuccessful());
      
      result = JUnitCore.runClasses(NameTest.class);
      for (Failure failure : result.getFailures()) {
         System.out.println(failure.toString());
      }
      System.out.println("Test NameTest:" + result.wasSuccessful());
       
      result = JUnitCore.runClasses(TypesTest.class);
      for (Failure failure : result.getFailures()) {
         System.out.println(failure.toString());
      }
      System.out.println("UTest TypesTest:" + result.wasSuccessful());
   }
}
