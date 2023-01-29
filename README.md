![Linerover](https://media.discordapp.net/attachments/1045280404971720725/1069302110769922148/poster-tate-runner.png?width=496&height=701)

---

# Tate Runner

Hey! Welcome to the official repository of my **2D-Scroller Game** called TateRunner. During my first year of Software Engineering, I had 8 weeks to work on a game. I chose to almost completely write it from scratch, and ended up with a pretty awesome game, if I may say so myself!

&nbsp;

### 💻 Software

I wrote this project in C++ using the Arduino framework, which I learned by following multiple classes at my university
and doing a lot of research online. By combining a very intelligent IDE like Clion by Jetbrains, and Platformio, I was
able to write and test my code very efficiently.

#### Launching the Game (!! **Java17 REQUIRED** !!)

```java
public final class TateRunnerBootstrap {

	public static void main(String[] args) {
		try {
			// Initialize the game and call the start game function
			final TateRunnerGame tateRunner = new TateRunnerGame();
			tateRunner.startGame(args);

			// Add a shutdown hook to the runtime
			Runtime.getRuntime().addShutdownHook(new Thread(tateRunner::stopGame));
		} catch (Throwable throwable) {
			throwable.printStackTrace();
			System.exit(throwable.hashCode());
		}
	}
}
```

You can start the game by running the TateRunnerBootstrap. This will initialize the game and call the startGame function. It will also add a shutdown hook to the runtime, which will call the stopGame function when the game is closed.
_Java 17 is required to run the game._

&nbsp;

### 🔧 UML Diagram

Open the image in a new tab to see it in full size, and have the ability to zoom in and out.

![UML Diagram](https://cdn.discordapp.com/attachments/456717855841320991/1069329932989251764/tate-runner-project-diagram.png)

&nbsp;

### 📹 Preview Images

![Preview Image 1](https://media.discordapp.net/attachments/1045280404971720725/1067888502236262450/image.png?width=810&height=456)
![Preview Image 2](https://media.discordapp.net/attachments/1045280404971720725/1067888502580183070/image.png?width=810&height=456)
![Preview Image 3](https://media.discordapp.net/attachments/1045280404971720725/1067888503091900436/image.png?width=810&height=456)
![Preview Image 4](https://media.discordapp.net/attachments/1045280404971720725/1067888503632961638/image.png?width=810&height=456)
![Preview Image 5](https://media.discordapp.net/attachments/1045280404971720725/1067940153047392369/image.png?width=810&height=456)
![Preview Image 6](https://media.discordapp.net/attachments/1045280404971720725/1067899162001154179/image.png?width=810&height=456)
![Preview Image 7](https://media.discordapp.net/attachments/1045280404971720725/1067940152766382191/image.png?width=810&height=456)