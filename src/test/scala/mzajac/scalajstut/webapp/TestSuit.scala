package mzajac.scalajstut.webapp

import utest._

import org.scalajs.jquery.jQuery

object ScalajstutTest extends TestSuite {

  TutorialApp.setupUI()

  def tests = TestSuite {
    'HelloWorld {
      assert(jQuery("p:contains('Hello World!')").length == 1)
    }
  }
}
