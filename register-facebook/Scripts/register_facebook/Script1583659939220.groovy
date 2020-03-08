import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable

/** Declare Local Static Variables, and fill them with global variables*/
String hostname = GlobalVariable.hostname
String firstName = GlobalVariable.first_name
String lastName = GlobalVariable.last_name
String emailOrPhone = GlobalVariable.email_or_phone
String password = GlobalVariable.password
String dateOfBirth = GlobalVariable.dob
String gender = GlobalVariable.gender
String projectDir = RunConfiguration.getProjectDir()


/** Declare Local Dynamic Variables*/
int objTimeout = 5
int browserWidth = 1280
int browserHeight = 800

WebUI.comment(projectDir)

WebUI.openBrowser('')
WebUI.setViewPortSize(browserWidth, browserHeight)
WebUI.navigateToUrl(hostname)
WebUI.waitForPageLoad(objTimeout)

/** Wait for fields ready to be filled*/
WebUI.waitForElementPresent(findTestObject('Landing_Page_Facebook/input_first_name'), objTimeout)
WebUI.waitForElementClickable(findTestObject('Landing_Page_Facebook/button_sign_up'), objTimeout)

/** Fill all the required fields*/
WebUI.setText(findTestObject('Landing_Page_Facebook/input_first_name'), firstName)
WebUI.setText(findTestObject('Landing_Page_Facebook/input_last_name'), lastName)
WebUI.setText(findTestObject('Landing_Page_Facebook/input_email_or_phone'), emailOrPhone)
WebUI.setText(findTestObject('Landing_Page_Facebook/input_re_email_or_phone'), emailOrPhone, FailureHandling.CONTINUE_ON_FAILURE)
WebUI.setText(findTestObject('Landing_Page_Facebook/input_password'), password)

/** Split the date of birth with dash and store it into the array */
String[] dobArr = dateOfBirth.split('-')

/** Convert Day and Month into integer, to remove '0' if exist
 * example: 01 will be convert to 1 
 */
int newDayDob = dobArr[0] as Integer
int newMonthDob = dobArr[1] as Integer

WebUI.selectOptionByValue(findTestObject('Landing_Page_Facebook/select_day_of_birth'), newDayDob.toString(), false)
WebUI.selectOptionByValue(findTestObject('Landing_Page_Facebook/select_month_of_birth'), newMonthDob.toString(), false)
WebUI.selectOptionByValue(findTestObject('Landing_Page_Facebook/select_year_of_birth'), dobArr[2], false)

/** Select radio button for gender */
if(gender == 'female'){
	WebUI.click(findTestObject('Object Repository/Landing_Page_Facebook/rdb_gender_female'))
}else if (gender == 'male'){
	WebUI.click(findTestObject('Object Repository/Landing_Page_Facebook/rdb_gender_male'))
}else if (gender == 'custom'){
	WebUI.click(findTestObject('Object Repository/Landing_Page_Facebook/rdb_gender_custom'))
}else{
	WebUI.click(findTestObject('Object Repository/Landing_Page_Facebook/rdb_gender_male'))
}

/** Click button Sign Up*/
WebUI.click(findTestObject('Landing_Page_Facebook/button_sign_up'))

/** Verify the object exist after input all the data,
 * if the object doesnt exist, it will return a failed test case
 */
boolean objExist = WebUI.verifyElementPresent(findTestObject('Object Repository/Landing_Page_Facebook/label_header_email_or_phone_confirmation'), objTimeout, FailureHandling.CONTINUE_ON_FAILURE)

/** Take Capture if the object above not present */
if(!objExist){
	WebUI.takeScreenshot(projectDir + '/Captures/error filling the required fields.png', FailureHandling.OPTIONAL)
}

WebUI.closeBrowser()