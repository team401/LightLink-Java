# LightLink-Java
LightLink-Java is the library used to control LightLink devices with WPILibJ

## Getting Started
To use LightLink-Java, you first need to get the library into your robot code

### GradleRIO
Setting up LightLink-Java with GradleRIO is easy

First, add this line to the `repositories` block in your `build.gradle`

`maven { url "http://dl.bintray.com/team401/SnakeSkin" }`

Finally, add this line to the `dependencies` block in your `build.gradle`

`compile(group: 'org.team401', name: 'LightLink-Java', version: '+')`

## Hello World
Assuming you have your hardware set up correctly, you are ready to test it!

To initialize a LightLink object, put this line in your `Robot.java` file

`LightLink led = new LightLink();`

Now, in `teleopInit()`, put this line

`led.rainbow()`

If you enable your robot in teleop mode, you should see your lights glowing in a rainbow!
