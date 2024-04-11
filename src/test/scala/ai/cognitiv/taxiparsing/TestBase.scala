package ai.cognitiv.taxiparsing

import org.mockito.scalatest.MockitoSugar
import org.scalatest.GivenWhenThen
import org.scalatest.featurespec.AnyFeatureSpec
import org.scalatest.matchers.should.Matchers

trait TestBase extends AnyFeatureSpec with GivenWhenThen with Matchers with MockitoSugar {

}
