package com.cscie88a.week6

import org.scalatest.{ WordSpec, BeforeAndAfterAll, Matchers }

class FunctionUtils2Test
    extends WordSpec
    with Matchers
    with BeforeAndAfterAll {
    // add your tests below
    "tests" should {

      "all pass" ignore {
        true should be (false)
      }

      // 1.2
      "FunctionUtils2.prefixLogger(\"<scala>\")(\"rocks\") should return \"<scala>rocks\"" in {
        FunctionUtils2.prefixLogger("<scala>")("rocks") should be("<scala>rocks") ;
      }

      // 2.2
      "2.2" in {
        FunctionUtils2.sumInt(List(1,2,3,4,5)) should be(15);
        FunctionUtils2.productInt(List(1,2,3,4,5)) should be(120);
        FunctionUtils2.all(List(true,true,false,true,true)) should be(false);
        FunctionUtils2.any(List(false,false,true,false,false)) should be(true);
      }

      // 3.2
      "3.2" in {
        FunctionUtils2.tokenize(List("a", "a b", "a b c")) should be(List("a", "a", "b", "a", "b", "c"));
        FunctionUtils2.updateCounts("a", Map("a" -> 3, "b" -> 2, "c" -> 1)) should be(Map("a" -> 4, "b" -> 2, "c" -> 1));
        FunctionUtils2.wordCount(List("a", "a b", "a b c")) should be(Map("a" -> 3, "b" -> 2, "c" -> 1));
      }
    }
}
