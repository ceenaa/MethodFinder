# MethodFinder

**MethodFinder** is a Java command-line tool that locates and displays the method in a Java source file that contains a specified line number. It uses the JavaParser library to analyze Java source code.

## Features

- Finds the method containing a given line number in a Java file.
- Outputs method information in JSON format for easy parsing.
- Provides method name, signature, start/end lines, and body.
- Simple command-line interface.

## Prerequisites

- Java 24 (or compatible with your `pom.xml` settings)
- Maven 3.x

## Setup

1. **Clone the repository** (if needed):
   ```sh
   git clone https://github.com/ceenaa/MethodFinder.git
   cd MethodFinder
   ```

2. **Build the project using Maven:**
   ```sh
   mvn clean package
   ```

   This will download dependencies and compile the project.

## Usage

You can run the tool using Maven or directly with Java:

### Using Maven

```sh
mvn exec:java -Dexec.mainClass=MethodLocator -Dexec.args="<source-file> <line-number>"
```

Example:
```sh
mvn exec:java -Dexec.mainClass=MethodLocator -Dexec.args="src/main/java/MethodLocator.java 15"
```

### Using Java Directly

1. Compile the project (if not already done):
   ```sh
   mvn clean package
   ```

2. Run the program:
   ```sh
   java -cp target/classes:~/.m2/repository/com/github/javaparser/javaparser-symbol-solver-core/3.27.0/javaparser-symbol-solver-core-3.27.0.jar MethodLocator <source-file> <line-number>
   ```

   Replace `<source-file>` with the path to your Java file and `<line-number>` with the line number you want to check.

## Example Output

When a method is found at the specified line:
```json
{
  "found": true,
  "methodName": "myMethod",
  "startLine": 10,
  "endLine": 25,
  "methodSignature": "public void myMethod(String arg)",
  "methodBody": "public void myMethod(String arg) {\n    // method body\n}"
}
```

When no method is found at the given line:
```json
{
  "found": false,
  "message": "No method found at line 42"
}
```

## Notes

- The tool outputs results in JSON format for easy integration with other tools and scripts.
- The tool requires the JavaParser library, which is managed via Maven.
- Make sure your Java version matches the one specified in `pom.xml` (`24`).
- JSON output includes proper escaping of special characters in strings.

## License

MIT (or specify your license)
