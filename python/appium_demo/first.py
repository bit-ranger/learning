import time

from appium import webdriver
from appium.webdriver import WebElement
from appium.webdriver.common.touch_action import TouchAction

desired_caps = {
    'automationName': 'UiAutomator2',
    'platformName': 'Android',
    'deviceName': 'Android Emulator',
    'app': 'D:\\Download\\com.miui.calculator_10.1.12_200012.apk',
    'platformVersion': '9'
}
driver = webdriver.Remote('http://localhost:4723/wd/hub', desired_caps)

actions = [
    {'actions': 'tap', 'x': 112, 'y': 1303},
    {'actions': 'tap', 'x': 937, 'y': 1509},
    {'actions': 'tap', 'x': 672, 'y': 1509},
    {'actions': 'tap', 'x': 986, 'y': 1691}
]

for action in actions:
    time.sleep(2)
    TouchAction(driver).tap(x=action['x'], y=action['y']).perform()

tar_ele: WebElement = driver.find_element_by_xpath('/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout[2]/com.miui.support.view.ViewPager/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout[2]/android.widget.LinearLayout/android.widget.HorizontalScrollView[3]/android.widget.LinearLayout/android.widget.TextView[2]')
tar_txt = tar_ele.get_attribute('text')

