# LightLink-Java
LightLink-Java is the library used to control LightLink devices with WPILibJ

## Getting Started
To use LightLink-Java, you first need to get the library into your robot code

### GradleRIO
Setting up LightLink-Java with GradleRIO is easy

First, add this line to the `repositories` block in your `build.gradle`

`maven { url 'https://jitpack.io' }`

Finally, add this line to the `dependencies` block in your `build.gradle`

`compile 'com.github.team401:LightLink-Java:1.2'`

## Hello World
Assuming you have your hardware set up correctly, you are ready to test it!

To initialize a LightLink object, put this line in your `Robot.java` file

`LightLinkStrip led = new LightLinkStrip(0);`

Now, in `teleopInit()`, put this line

`led.rainbow()`

If you enable your robot in teleop mode, you should see your lights glowing in a rainbow!
