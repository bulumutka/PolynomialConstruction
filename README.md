# PolynomialConstruction
Program for Сonstructing the Polynomial Corresponding to a Random Walk on a Geometric Graph

## Running

Firstly download the [latest release](https://github.com/bulumutka/PolynomialConstruction/releases).

This application requires JDK 11, JavaFX 11 and python3 iterpreter.

1.  Install JDK11 from official site by [link](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html), or use terminal command.
```
sudo apt-get install openjdk-11-jdk
```
2.  Install JavaFX 11 by [link](https://gluonhq.com/products/javafx/).
3.  You should have python3 iterpreter with sympy and numpy modules.

Now you should unpack archive and edit `run.sh` file, change `PATH_TO_FX` value according to the path of installed JavaFX 11 library. As an example: 
```
PATH_TO_FX="/home/misha/Library/javafx-sdk-11.0.2/lib"
```

Execute `run.sh` script and the program will start. 

## Instruction for Windows
The example of run.bat for widows

```
set PATH_TO_FX="C:\Program Files\Java\javafx-sdk-11.0.2\lib"
java --module-path %PATH_TO_FX% --add-modules=javafx.controls,javafx.fxml -jar PolynomialConstruction.jar 

```

